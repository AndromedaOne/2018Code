package kinematics;

import java.util.Vector;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

public class MotionProfilingControllerCanTalon //extends SendableBase
		/*implements MotionProfilingController, Sendable, Runnable*/ {
	// Copied most of this code from
	// https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/MotionProfile/src/org/usfirst/frc/team217/robot/MotionProfileExample.java

	private double [][] m_points;
	private int m_numOfPoints;


	/**
	 * C'tor
	 *
	 * @param talon
	 *            reference to Talon object to fetch motion profile status from.
	 */
	public MotionProfilingControllerCanTalon(TalonSRX talon) {
		m_talon = talon;
		/*
		 * since our MP is 10ms per point, set the control frame rate and the notifer to
		 * half that
		 */
		m_talon.changeMotionControlFramePeriod(5);
		m_notifer.startPeriodic(0.005);
	}

	/**
	 * The status of the motion profile executer and buffer inside the Talon.
	 * Instead of creating a new one every time we call getMotionProfileStatus, keep
	 * one copy.
	 */
	private MotionProfileStatus m_status = new MotionProfileStatus();

	/**
	 * reference to the talon we plan on manipulating. We will not changeMode() or
	 * call set(), just get motion profile status and make decisions based on motion
	 * profile.
	 */
	private TalonSRX m_talon;
	/**
	 * State machine to make sure we let enough of the motion profile stream to
	 * talon before we fire it.
	 */
	private int m_state = 0;
	/**
	 * Any time you have a state machine that waits for external events, its a good
	 * idea to add a timeout. Set to -1 to disable. Set to nonzero to count down to
	 * '0' which will print an error message. Counting loops is not a very accurate
	 * method of tracking timeout, but this is just conservative timeout. Getting
	 * time-stamps would certainly work too, this is just simple (no need to worry
	 * about timer overflows).
	 */
	private int m_loopTimeout = -1;
	/**
	 * If start() gets called, this flag is set and in the control() we will service
	 * it.
	 */
	private boolean m_bStart = false;

	/**
	 * Since the CANTalon.set() routine is mode specific, deduce what we want the
	 * set value to be and let the calling module apply it whenever we decide to
	 * switch to MP mode.
	 */
	private SetValueMotionProfile m_setValue = SetValueMotionProfile.Disable;
	/**
	 * How many trajectory points do we wait for before firing the motion profile.
	 */
	private static final int kMinPointsInTalon = 5;
	/**
	 * Just a state timeout to make sure we don't get stuck anywhere. Each loop is
	 * about 20ms.
	 */
	private static final int kNumLoopsTimeout = 10;

	/**
	 * Lets create a periodic task to funnel our trajectory points into our talon.
	 * It doesn't need to be very accurate, just needs to keep pace with the motion
	 * profiler executer. Now if you're trajectory points are slow, there is no need
	 * to do this, just call m_talon.processMotionProfileBuffer() in your teleop
	 * loop. Generally speaking you want to call it at least twice as fast as the
	 * duration of your trajectory points. So if they are firing every 20ms, you
	 * should call every 10ms.
	 */
	class PeriodicRunnable implements java.lang.Runnable {
		@Override
		public void run() {
			m_talon.processMotionProfileBuffer();
		}
	}

	Notifier m_notifer = new Notifier(new PeriodicRunnable());

	/**
	 * Called to clear Motion profile buffer and reset state info during disabled
	 * and when Talon is not in MP control mode.
	 */
	public void reset() {
		/*
		 * Let's clear the buffer just in case user decided to disable in the middle of
		 * an MP, and now we have the second half of a profile just sitting in memory.
		 */
		m_talon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		m_setValue = SetValueMotionProfile.Disable;
		/* When we do start running our state machine start at the beginning. */
		m_state = 0;
		m_loopTimeout = -1;
		/*
		 * If application wanted to start an MP before, ignore and wait for next button
		 * press
		 */
		m_bStart = false;
	}

	/**
	 * Called every loop.
	 */
	public void control(GeneratedMotionProfile generatedMotionProfile) {
		/* Get the motion profile status every loop */
		m_talon.getMotionProfileStatus(m_status);

		/*
		 * track time, this is rudimentary but that's okay, we just want to make sure
		 * things never get stuck.
		 */
		if (m_loopTimeout < 0) {
			/* do nothing, timeout is disabled */
		} else {
			/* our timeout is nonzero */
			if (m_loopTimeout == 0) {
				/*
				 * something is wrong. Talon is not present, unplugged, breaker tripped
				 */
				System.err.println("Can't see Talon: ");
			} else {
				--m_loopTimeout;
			}
		}
		/* first check if we are in MP mode */
		if (m_talon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around using
			 * gamepads or some other mode.
			 */
			m_state = 0;
			m_loopTimeout = -1;
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp progress,
			 * and possibly interrupting MPs if thats what you want to do.
			 */

			switch (m_state) {
			case 0: /* wait for application to tell us to start an MP */
				if (m_bStart) {
					m_bStart = false;

					m_setValue = SetValueMotionProfile.Disable;

					startFilling(generatedMotionProfile.m_points, generatedMotionProfile.m_numPoints);
					/*
					 * MP is being sent to CAN bus, wait a small amount of time
					 */
					m_state = 1;
					m_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 1: /*
					 * wait for MP to stream to Talon, really just the first few points
					 */
				/* do we have a minimum numberof points in Talon */

				if (m_status.btmBufferCnt > kMinPointsInTalon) {
					/* start (once) the motion profile */
					System.out.println("Setting the m_setValue to enabled");
					m_setValue = SetValueMotionProfile.Enable;
					/* MP will start once the control frame gets scheduled */
					m_state = 2;
					m_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 2: /* check the status of the MP */
				/*
				 * if talon is reporting things are good, keep adding to our timeout. Really
				 * this is so that you can unplug your talon in the middle of an MP and react to
				 * it.
				 */

				if (m_status.isUnderrun == false) {
					m_loopTimeout = kNumLoopsTimeout;
				}
				/*
				 * If we are executing an MP and the MP finished, start loading another. We will
				 * go into hold state so robot servo's position.
				 */
				if (m_status.activePointValid && m_status.isLast) {
					/*
					 * because we set the last point's isLast to true, we will get here when the MP
					 * is done
					 */
					System.out.println("Setting the m_setValue to Hold");
					m_setValue = SetValueMotionProfile.Hold;
					m_state = 0;
					m_loopTimeout = -1;
				}
				else {
					System.out.println("m_loopTimeout: " + m_loopTimeout);
					System.out.println("m_status.isUnderrun: " + m_status.isUnderrun);
				}
				break;
			}

			/* Get the motion profile status every loop */
			m_talon.getMotionProfileStatus(m_status);
		}
	}

	/**
	 * Find enum value if supported.
	 *
	 * @param durationMs
	 * @return enum equivalent of durationMs
	 */
	private TrajectoryDuration getTrajectoryDuration(int durationMs) {
		/* create return value */
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError(
					"Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
		}
		/* pass to caller */
		return retval;
	}

	/** Start filling the MPs to all of the involved Talons. */
	/*private void startFilling() {
		/* since this example only has one talon, just update that one

		startFilling(GeneratedMotionProfile.Points, GeneratedMotionProfile.kNumPoints);
	}
*/

	private void startFilling(double[][] profile, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();

		/* did we get an underrun condition since last time we checked ? */
		if (m_status.hasUnderrun) {
			/* better log it so we know about it */
			System.err.println("Ran out of points in the buffer!");
			/*
			 * clear the error. This flag does not auto clear, this way we never miss
			 * logging it.
			 */
			m_talon.clearMotionProfileHasUnderrun(0);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer points
		 * in memory, clear it.
		 */
		m_talon.clearMotionProfileTrajectories();

		/*
		 * set the base trajectory period to zero, use the individual trajectory period
		 * below
		 */
		m_talon.configMotionProfileTrajectoryPeriod(TalonMPConstants.kBaseTrajPeriodMs, TalonMPConstants.kTimeoutMs);

		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			double positionRot = profile[i][0];
			double velocityRPM = profile[i][1];
			/* for each point, fill our structure and pass it to API */
			point.position = positionRot * TalonMPConstants.kSensorUnitsPerRotation; // Convert Revolutions to Units
			point.velocity = velocityRPM * TalonMPConstants.kSensorUnitsPerRotation / 600.0; // Convert RPM to
																								// Units/100ms
			point.headingDeg = 0; /* future feature - not used in this example */
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			point.profileSlotSelect1 = 0; /*
											 * future feature - not used in this example - cascaded PID [0,1], leave
											 * zero
											 */
			point.timeDur = getTrajectoryDuration((int) profile[i][2]);
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt)
				point.isLastPoint = true; /* set this to true on the last point */

			m_talon.pushMotionProfileTrajectory(point);
		}
	}

	/**
	 * Called by application to signal Talon to start the buffered MP (when it's
	 * able to).
	 */
	public void startMotionProfile() {
		m_bStart = true;
	}

	/**
	 *
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
	public SetValueMotionProfile getSetValue() {
		return m_setValue;
	}

	public GeneratedMotionProfile generateTrajectory(double setpoint) {
		System.out.println("In generate Trajectory.");
		Kinematics myKinematics = new Kinematics();
		Path tempPath = new Path();
		tempPath = myKinematics.createTrajectory(TalonMPConstants.maxVelocity, TalonMPConstants.maxAcceleration, TalonMPConstants.maxJerk, setpoint);
		Vector<TrajectoryPoint4905> trajectoryPointsVector = new Vector<TrajectoryPoint4905>();
		System.out.println("setpoint: " + setpoint);
		System.out.println("tempPath.getSetpointVector().size(): " + tempPath.getSetpointVector().size());
		UnitCases.printTrajectory(tempPath);
		// this could be condensed into  a function that both the function and the print function in Kinematics tester uses because I copied most of this code from there.
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < tempPath.getSetpointVector().size(); i++) {
			System.out.println("tempPath.getSetpointVector().get(i).getEndDeltaTime(): " + tempPath.getSetpointVector().get(i).getEndDeltaTime());
			endDeltatTimeFromStartOfPath += tempPath.getSetpointVector().get(i).getEndDeltaTime();
		}
		System.out.println("endDeltatTimeFromStartOfPath: " + endDeltatTimeFromStartOfPath);
		for (double i = 0.0; i < endDeltatTimeFromStartOfPath; i += TalonMPConstants.trajectoryPointInterval) {

			TrajectoryPoint4905 currentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(tempPath, i, true);
			trajectoryPointsVector.add(currentPoint);
		}

		double[][] generatedMPArray = new double[trajectoryPointsVector.size()][3];
		int counter = 0;
		for (TrajectoryPoint4905 trajectoryPoint : trajectoryPointsVector) {
			generatedMPArray[counter][0] = trajectoryPoint.m_position/4;
			generatedMPArray[counter][1] = trajectoryPoint.m_currentVelocity/2;
			generatedMPArray[counter][2] = TalonMPConstants.trajectoryPointInterval*1000; // need to multiply this by 1000 because the trajectoryPointInterval is in seconds but this expects milliseconds
			counter++;
		}
		for (int a = 0; a < generatedMPArray.length; a++) {
			System.out.println("trajectoryBeforeCANTalonVelocity: " + generatedMPArray[a][1]);
		}
		System.out.println("");
		System.out.println("");
		System.out.println("");
		for (int a = 0; a < generatedMPArray.length; a++) {
			System.out.println("trajectoryBeforeCANTalonPosition: " + generatedMPArray[a][0]);
		}
		GeneratedMotionProfile generatedMotionProfile = new GeneratedMotionProfile(generatedMPArray, generatedMPArray.length)  ;
		return generatedMotionProfile;
	}

}

package kinematics;

import java.util.TimerTask;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MotionProfilingController extends SendableBase implements Sendable, Runnable {
	private double m_initialTimeStamp = 0.0;

	private PIDCalculator m_velocityPIDCalculator;
	private PIDCalculator m_positionPIDCalculator;

	private double m_velocityF = 0.0;

	private double m_maxVelocity = 0.0;
	private double m_maxAcceleration = 0.0;
	private double m_maxJerk = 0.0;

	private MPSource m_mpSource;
	private PIDOutput m_pidOutput;

	private boolean m_enableStatus = false;

	private double m_tolerance;
	private double m_currentSetpoint;
	private double m_outputRangeMin = 0.0;
	private double m_outputRangeMax = 1.0;

	private Kinematics m_kinematics = new Kinematics();
	private Path m_path = new Path();
	private TrajectoryPoint m_currentTrajectoryPoint = new TrajectoryPoint(0.0, 0.0, 0.0);

	private double m_deltaTime = 0.0;
	private double m_endDeltaTime = 0.0;
	private double m_initialPosition = 0.0;

	// This creates the thread that will run the run method
	private java.util.Timer m_controlLoop;
	// this tells the thread how long to wait in between cycles. This is 50
	// miliseconds
	private long kdefaultPeriod = 20;

	public MotionProfilingController(double positionPValue, double positionIValue, double positionDValue,
			double velocityPValue, double velocityIValue, double velocityDValue, double velocityFValue,
			double maxVelocity, double maxAcceleration, double maxJerk, MPSource mpSource, PIDOutput pidOutput) {

		m_positionPIDCalculator = new PIDCalculator();
		m_velocityPIDCalculator = new PIDCalculator();

		m_positionPIDCalculator.m_p = positionPValue;
		m_positionPIDCalculator.m_i = positionIValue;
		m_positionPIDCalculator.m_d = positionDValue;

		m_velocityPIDCalculator.m_p = velocityPValue;
		m_velocityPIDCalculator.m_i = velocityIValue;
		m_velocityPIDCalculator.m_d = velocityDValue;
		m_velocityF = velocityFValue;

		m_maxVelocity = maxVelocity;
		m_maxAcceleration = maxAcceleration;
		m_maxJerk = maxJerk;

		m_mpSource = mpSource;
		m_pidOutput = pidOutput;
		m_controlLoop = new java.util.Timer();
		MotionProfilingTask motionProfilingTask = new MotionProfilingTask(this);
		m_controlLoop.schedule(motionProfilingTask, 0, kdefaultPeriod);

	}

	public void disable() {
		m_enableStatus = false;
		m_pidOutput.pidWrite(0.0);
	}

	public void enable() {
		m_velocityPIDCalculator.reset();
		m_positionPIDCalculator.reset();

		m_initialTimeStamp = Timer.getFPGATimestamp();
		m_kinematics.createTrajectory(m_path, m_maxVelocity * 0.9, m_maxAcceleration, m_maxJerk);
		m_endDeltaTime = m_path.getSetpointVector().get(0).endDeltaTime;
		m_initialPosition = m_mpSource.getPosition();
		m_enableStatus = true;
	}

	public void run() {
		if (m_enableStatus) {

			double currentTimestamp = Timer.getFPGATimestamp();
			m_deltaTime = currentTimestamp - m_initialTimeStamp;
			TrajectoryPoint nextTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(m_path, m_deltaTime);
			double deltaPosition = getDeltaPosition(m_mpSource.getPosition());
			double positionPidOut = m_positionPIDCalculator.getPIDOut(m_currentTrajectoryPoint.m_position,
					deltaPosition);
			double nextVelocity = nextTrajectoryPoint.m_currentVelocity + positionPidOut;

			double velocity = m_mpSource.getVelocity();

			double velocityPIDOut = m_velocityPIDCalculator.getPIDOut(nextVelocity, velocity);
			double output = nextVelocity * m_velocityF + velocityPIDOut;

			m_pidOutput.pidWrite(output);

			// THIS DOES NOT WORK FOR MULTIPLE MOTIONPROFILING CONTROLLERS
			Trace.getInstance().addTrace(true, "MotionProfilingData", new TracePair("ActualVelocity", velocity),
					new TracePair("ProjectedVelocity", m_currentTrajectoryPoint.m_currentVelocity),
					new TracePair("ActualPosition", deltaPosition),
					new TracePair("ProjectedPosition", m_currentTrajectoryPoint.m_position),
					new TracePair("VelocityError", m_currentTrajectoryPoint.m_currentVelocity - velocity),
					new TracePair("PositionError", (m_currentTrajectoryPoint.m_position - deltaPosition) * 10));

			/*
			 * new TracePair("velocityPIDOut", (velocityPIDOut)), new
			 * TracePair("nextVelocity", (nextVelocity)))
			 * 
			 * new TracePair("Rate", rate), new TracePair("Ratio", velocity/rate));
			 */

			m_currentTrajectoryPoint = nextTrajectoryPoint;
		}
	}

	public boolean onTarget() {

		double currentPosition = getDeltaPosition(m_mpSource.getPosition());
		if (m_deltaTime >= m_endDeltaTime) {
			return (m_currentSetpoint - m_tolerance <= currentPosition)
					&& (currentPosition <= m_currentSetpoint + m_tolerance);
		} else {
			return false;
		}
	}

	public void setAbsoluteTolerance(double tolerance) {
		m_tolerance = tolerance;
	}

	public void setPercentTolerance(double percentTolerance) {
		m_tolerance = m_currentSetpoint * percentTolerance;
	}

	public void setSetpoint(double setpoint) {
		m_currentSetpoint = setpoint;
		try {
			m_kinematics.addPointToPath(m_path, new Point(setpoint));
		} catch (InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private double getDeltaPosition(double currentPosition) {
		return currentPosition - m_initialPosition;
	}

	private double getPositionP() {
		return m_positionPIDCalculator.m_p;
	}

	private void setPositionP(double value) {
		m_positionPIDCalculator.m_p = value;
	}

	private double getPositionI() {
		return m_positionPIDCalculator.m_i;
	}

	private void setPositionI(double value) {
		m_positionPIDCalculator.m_i = value;
	}

	private double getPositionD() {
		return m_positionPIDCalculator.m_d;
	}

	private void setPositionD(double value) {
		m_positionPIDCalculator.m_d = value;
	}

	private double getVelocityP() {
		return m_velocityPIDCalculator.m_p;
	}

	private void setVelocityP(double value) {
		m_velocityPIDCalculator.m_p = value;
	}

	private double getVelocityI() {
		return m_velocityPIDCalculator.m_i;
	}

	private void setVelocityI(double value) {
		m_velocityPIDCalculator.m_i = value;
	}

	private double getVelocityD() {
		return m_velocityPIDCalculator.m_d;
	}

	private void setVelocityD(double value) {
		m_velocityPIDCalculator.m_d = value;
	}

	private double getVelocityF() {
		return m_velocityF;
	}

	private void setVelocityF(double value) {
		m_velocityF = value;
	}

	private double getV() {
		return m_maxVelocity;
	}

	private void setV(double value) {
		m_maxVelocity = value;
	}

	private double getA() {
		return m_maxAcceleration;
	}

	private void setA(double value) {
		m_maxAcceleration = value;
	}

	private double getJ() {
		return m_maxJerk;
	}

	private void setJ(double value) {
		m_maxJerk = value;
	}

	private double getSetpoint() {
		return m_currentSetpoint;
	}

	private boolean getEnabled() {
		return m_enableStatus;
	}

	private void setEnabled(boolean status) {
		if (status) {
			enable();
		} else {
			disable();
		}
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("MotionProfilingController");
		builder.addDoubleProperty("PositionP", this::getPositionP, this::setPositionP);
		builder.addDoubleProperty("PositionI", this::getPositionI, this::setPositionI);
		builder.addDoubleProperty("PositionD", this::getPositionD, this::setPositionD);

		builder.addDoubleProperty("VelocityP", this::getVelocityP, this::setVelocityP);
		builder.addDoubleProperty("VelocityI", this::getVelocityI, this::setVelocityI);
		builder.addDoubleProperty("VelocityD", this::getVelocityD, this::setVelocityD);
		builder.addDoubleProperty("VelocityF", this::getVelocityF, this::setVelocityF);

		builder.addDoubleProperty("MaxV", this::getV, this::setV);
		builder.addDoubleProperty("MaxA", this::getA, this::setA);
		builder.addDoubleProperty("MaxJ", this::getJ, this::setJ);

		builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
		builder.addBooleanProperty("enabled", this::getEnabled, this::setEnabled);

	}

	private class MotionProfilingTask extends TimerTask {
		MotionProfilingController m_motionProfilingController;

		public MotionProfilingTask(MotionProfilingController motionProfilingController) {
			m_motionProfilingController = motionProfilingController;
		}

		@Override
		public void run() {
			m_motionProfilingController.run();

		}

	}
}

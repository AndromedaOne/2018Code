package kinematics;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class MotionProfilingController {
	private double m_initialTimeStamp = 0.0;
	private double m_pValue = 0.0;
	private double m_iValue = 0.0;
	private double m_dValue = 0.0;
	private double m_maxVelocity = 0.0;
	private double m_maxAcceleration = 0.0;
	private double m_maxJerk = 0.0;
	private PIDSource m_pidSource;
	private PIDOutput m_pidOutput;
	private boolean enableStatus;
	private double m_tolerance;
	private double m_currentSetpoint;
	private double m_outputRangeMin = 0.0;
	private double m_outputRangeMax = 1.0;
	private Kinematics m_kinematics = new Kinematics();
	private Path m_path = new Path();
	private double m_previousError = Double.NaN;
	private double m_pidIAccum = 0.0;
	private TrajectoryPoint m_currentTrajectoryPoint = new TrajectoryPoint(0.0, 0.0, 0.0);
	private double m_deltaTime = 0.0;
	private double m_endDeltaTime = 0.0;
	private double m_initialPosition = 0.0;

	public MotionProfilingController(double pValue, double iValue, double dValue, double maxVelocity,
			double maxAcceleration, double maxJerk, PIDSource pidSource, PIDOutput pidOutput) {
		m_pValue = pValue;
		m_iValue = iValue;
		m_dValue = dValue;
		m_maxVelocity = maxVelocity;
		m_maxAcceleration = maxAcceleration;
		m_maxJerk = maxJerk;

		m_pidSource = pidSource;
		m_pidOutput = pidOutput;
	}

	public void disable() {
		enableStatus = false;
		m_pidOutput.pidWrite(0.0);
		Trace.getInstance().flushTraceFiles();
	}

	public void enable() {
		m_initialTimeStamp = Timer.getFPGATimestamp();
		m_kinematics.createTrajectory(m_path, m_maxVelocity, m_maxAcceleration, m_maxJerk);
		m_endDeltaTime = m_path.getSetpointVector().get(0).endDeltaTime;
		m_initialPosition = m_pidSource.pidGet();
		enableStatus = true;
	}
	
	public void run() {
		System.out.println("enableStatus: " + enableStatus);
		if(enableStatus) {
			double currentTimestamp = Timer.getFPGATimestamp();
			m_deltaTime = currentTimestamp - m_initialTimeStamp;
			TrajectoryPoint nextTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(m_path, m_deltaTime);
			double deltaPosition = m_pidSource.pidGet() - m_initialPosition;
			
			double pidOut = getPIDOut(m_currentTrajectoryPoint.m_position, deltaPosition);
			double output = nextTrajectoryPoint.m_currentVelocity + pidOut;
			m_pidOutput.pidWrite(output);
			System.out.println("Robot.driveTrain.getVelocity(): " + Robot.driveTrain.getVelocity());
			Trace.getInstance().addTrace("MotionProfilingData", 
					new TracePair("ActualVelocity", Robot.driveTrain.getVelocity()),
					new TracePair("ProjectedVelocity", m_currentTrajectoryPoint.m_currentVelocity),
					new TracePair("ActualPosition", (deltaPosition)),
					new TracePair("ProjectedPosition", m_currentTrajectoryPoint.m_position),
					new TracePair("Error", (deltaPosition - m_currentTrajectoryPoint.m_position)/100),
					new TracePair("Zero", 0.0));
			m_currentTrajectoryPoint = nextTrajectoryPoint;
		}
	}

	public boolean onTarget() {

		// change this to work!!!!!!!!!!u
		if (m_deltaTime >= m_endDeltaTime) {
			return m_currentSetpoint - m_tolerance <= m_pidSource.pidGet()
					&& m_pidSource.pidGet() <= m_currentSetpoint + m_tolerance;
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

		try {
			m_kinematics.addPointToPath(m_path, new Point(setpoint));
		} catch (InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double getPIDOut(double actualPosition, double projectedPosition) {

		double error = projectedPosition - actualPosition;
		if (Double.isNaN(m_previousError)) {
			m_previousError = error;
		}
		m_pidIAccum += error;
		double pTerm = error * m_pValue;
		double iTerm = m_pidIAccum * m_iValue;
		double dTerm = (error - m_previousError) * m_dValue;

		m_previousError = error;
		return pTerm + iTerm + dTerm;
	}
}

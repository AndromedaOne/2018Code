package kinematics;

import java.util.TimerTask;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MotionProfilingController extends SendableBase implements Sendable, Runnable{
	private double m_initialTimeStamp = 0.0;
	private double m_pValue = 0.0;
	private double m_iValue = 0.0;
	private double m_dValue = 0.0;
	private double m_maxVelocity = 0.0;
	private double m_maxAcceleration = 0.0;
	private double m_maxJerk = 0.0;
	private PIDSource m_pidSource;
	private PIDOutput m_pidOutput;
	private boolean m_enableStatus = false;
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
	// This creates the thread that will run the run method
	private java.util.Timer m_controlLoop; 
	// this tells the thread how long to wait in between cycles. This is 50 miliseconds
	private long kdefaultPeriod = 50;

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
		m_controlLoop = new java.util.Timer();
		MotionProfilingTask motionProfilingTask = new MotionProfilingTask(this);
		m_controlLoop.schedule(motionProfilingTask, 0, kdefaultPeriod);
		
	}

	public void disable() {
		m_enableStatus = false;
		m_pidOutput.pidWrite(0.0);
	}

	public void enable() {
		m_initialTimeStamp = Timer.getFPGATimestamp();
		m_kinematics.createTrajectory(m_path, m_maxVelocity*0.9, m_maxAcceleration, m_maxJerk);
		m_endDeltaTime = m_path.getSetpointVector().get(0).endDeltaTime;
		m_initialPosition = m_pidSource.pidGet();
		m_enableStatus = true;
	}
	
	public void run() {
		if(m_enableStatus) {
			double currentTimestamp = Timer.getFPGATimestamp();
			m_deltaTime = currentTimestamp - m_initialTimeStamp;
			TrajectoryPoint nextTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(m_path, m_deltaTime);
			double deltaPosition = m_pidSource.pidGet() - m_initialPosition;
			
			double pidOut = getPIDOut(m_currentTrajectoryPoint.m_position, deltaPosition);
			double output = nextTrajectoryPoint.m_currentVelocity + pidOut;
			m_pidOutput.pidWrite(output);
			
			// THIS DOES NOT WORK FOR MULTIPLE MOTIONPROFILING CONTROLLERS
			Trace.getInstance().addTrace("MotionProfilingData", 
					new TracePair("ActualVelocity", Robot.driveTrain.getVelocity()),
					new TracePair("ProjectedVelocity", m_currentTrajectoryPoint.m_currentVelocity),
					new TracePair("ActualPosition", (deltaPosition)),
					new TracePair("ProjectedPosition", m_currentTrajectoryPoint.m_position),
					new TracePair("Error", (deltaPosition - m_currentTrajectoryPoint.m_position)),
					new TracePair("Output", output),
					new TracePair("Zero", 0.0));
			
			m_currentTrajectoryPoint = nextTrajectoryPoint;
		}
	}

	public boolean onTarget() {

		// change this to work!!!!!!!!!!
		double currentPosition = m_pidSource.pidGet() - m_initialPosition;
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
	
	private double getP() {return m_pValue;}
	private void setP(double value) {m_pValue = value;}
	private double getI() {return m_iValue;}
	private void setI(double value) {m_iValue = value;}
	private double getD() {return m_dValue;}
	private void setD(double value) {m_dValue = value;}
	private double getV() {return m_maxVelocity;}
	private void setV(double value) {m_maxVelocity = value;}
	private double getA() {return m_maxAcceleration;}
	private void setA(double value) {m_maxAcceleration = value;}
	private double getJ() {return m_maxJerk;}
	private void setJ(double value) {m_maxJerk = value;}
	private double getSetpoint() {return m_currentSetpoint;}
	private boolean getEnabled() {return m_enableStatus;}
	private void setEnabled(boolean status) {
		if(status) {
			enable();
		}else {
			disable();
		}
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("MotionProfilingController");
	    builder.addDoubleProperty("P", this::getP, this::setP);
	    builder.addDoubleProperty("I", this::getI, this::setI);
	    builder.addDoubleProperty("D", this::getD, this::setD);
	    builder.addDoubleProperty("MaxV", this::getV, this::setV);
	    builder.addDoubleProperty("MaxA", this::getA, this::setA);
	    builder.addDoubleProperty("MaxJ", this::getJ, this::setJ);
	    
	    builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
	    builder.addBooleanProperty("enabled", this::getEnabled, this::setEnabled);
		
	}
	
	private class MotionProfilingTask extends TimerTask{
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

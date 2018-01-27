package org.usfirst.frc4905.Galaktika.commands;

import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Trace;
import Utilities.TracePair;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunMotorAndLog extends Command {
	Vector<String> m_header = new Vector<String>();
	double m_initialTimeStamp = 0.0;
	double m_previousVelocity = 0.0;
	double m_previousTimeStamp = 0.0;
	double m_totalDeltaTimeFromLastExecute;
	double m_numberofDeltaTimesFromLastExecute;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public RunMotorAndLog() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		m_initialTimeStamp = Timer.getFPGATimestamp();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.setAllDriveControllersPercentVBus(1.0);
		
		Vector<Double> entry = new Vector<Double>();
		double currentTimeStamp = Timer.getFPGATimestamp();
		double deltaTimeFromLastExecute = currentTimeStamp - m_previousTimeStamp;

		double deltaTime = currentTimeStamp - m_previousTimeStamp;
		deltaTime /= 60;
		double currentVelocity = Robot.driveTrain.getVelocity();
		double currentAcceleration = currentVelocity - m_previousVelocity;
		currentAcceleration /= deltaTime;
		
		m_totalDeltaTimeFromLastExecute+=deltaTimeFromLastExecute;
		m_numberofDeltaTimesFromLastExecute++;
		
		Trace.getInstance().addTrace("MotionProfilingData", 
				new TracePair("Velocities", currentVelocity),
				new TracePair("Acceleration", currentAcceleration),
				new TracePair("Position", Robot.driveTrain.getEncoderPosition()));
		// 0 Sep 28 19:48 MotionProfilingData09-28-2017-19-48-27.csv

		m_previousTimeStamp = currentTimeStamp;
		m_previousVelocity = currentVelocity;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.setAllDriveControllersPercentVBus(0.0);

		Trace.getInstance().flushTraceFiles();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

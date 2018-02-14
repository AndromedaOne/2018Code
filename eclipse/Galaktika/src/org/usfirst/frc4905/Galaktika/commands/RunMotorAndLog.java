package org.usfirst.frc4905.Galaktika.commands;

import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
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
	double m_previousAcceleration = 0.0;
	double m_initialPosition = 0.0;
	boolean encoderMode = true;
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
		m_initialPosition = Robot.driveTrain.getEncoderPosition();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double initialTime = Timer.getFPGATimestamp();
		if (encoderMode) {
			Robot.driveTrain.gyroCorrectMove(1.0, 0.0, 1.0);
		} else {
			Robot.driveTrain.gyroCorrectMove(0.0, 1.0, 1.0);
		}
		double currentTimeStamp = Timer.getFPGATimestamp();
		double deltaTimeFromLastExecute = currentTimeStamp - m_previousTimeStamp;

		double deltaTime = currentTimeStamp - m_previousTimeStamp;
		deltaTime /= 60;
		double currentVelocity;
		if (encoderMode) {
			currentVelocity = Robot.driveTrain.getTalonVelocity();
		} else {
			currentVelocity = Robot.driveTrain.getGyroVelocity();
		}

		double currentAcceleration = (currentVelocity - m_previousVelocity) / deltaTime;

		double currentJerk = (currentAcceleration - m_previousAcceleration) / deltaTime;

		m_totalDeltaTimeFromLastExecute += deltaTimeFromLastExecute;
		m_numberofDeltaTimesFromLastExecute++;

		Trace.getInstance().addTrace(true, "KinematicLimits", 
				new TracePair("Acceleration", currentAcceleration / 500), new TracePair("Jerk", currentJerk / 100000),
				new TracePair("Position", Robot.driveTrain.getEncoderPosition() - m_initialPosition));

		m_previousTimeStamp = currentTimeStamp;
		m_previousVelocity = currentVelocity;
		m_previousAcceleration = currentAcceleration;
		double finalTime = Timer.getFPGATimestamp();
		double executeDeltaTime = finalTime - initialTime;
		Trace.getInstance().addTrace("DeltaTime", new TracePair("deltaTime", executeDeltaTime));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.move(0.0, 0.0);

		Trace.getInstance().flushTraceFiles();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

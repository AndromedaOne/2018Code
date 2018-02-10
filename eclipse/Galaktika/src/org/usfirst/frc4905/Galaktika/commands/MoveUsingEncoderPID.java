package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingEncoderPID extends Command {

	private double m_setpoint = 0;
	private boolean useMotionProfilng = true;

	public MoveUsingEncoderPID(double setpointInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		m_setpoint = setpointInches * DriveTrain.ENCODER_TICKS_PER_INCH;
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//debug("Initializing");
		System.out.println("In the Init command");
		if (!useMotionProfilng) {
			Robot.driveTrain.initializeEncoderPID();
			Robot.driveTrain.enableEncoderPID(m_setpoint);
		} else {
			Robot.driveTrain.initializePositionMP();
			Robot.driveTrain.enablePositionMP(m_setpoint);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if(useMotionProfilng) {
			System.out.println("Running");
			Robot.driveTrain.runPositionMP();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!useMotionProfilng) {
			return Robot.driveTrain.isDoneEncoderPID();
		} else {
			return Robot.driveTrain.isDonePositionMP();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		debug("Done");
		Robot.driveTrain.move(0, 0);
		if (!useMotionProfilng) {
			Robot.driveTrain.disableEncoderPID();
		} else {
			Robot.driveTrain.disablePositionMP();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("CALLING INTERUPTED!!!!!!!");
		end();
	}

	protected void debug(String information) {
		System.out.println("In MoveUsingEncoderPID.java Field Setup: Robot = " + Robot.getInitialRobotLocation()
				+ "Done Encoder Ticks " + m_setpoint + " " + information);
	}
}

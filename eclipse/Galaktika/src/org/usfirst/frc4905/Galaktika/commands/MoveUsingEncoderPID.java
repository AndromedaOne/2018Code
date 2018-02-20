package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingEncoderPID extends Command {

	private double m_setpoint = 0;
	private final boolean useMotionProfilng = true;

	public MoveUsingEncoderPID(double setpointInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
    		debug("top of constructor, inches = " + setpointInches);
		requires(Robot.driveTrain);
	    	// -1 multiplier so that positive input send us in a positive direction
	    	m_setpoint = setpointInches * DriveTrain.ENCODER_TICKS_PER_INCH * -1;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//debug("Initializing");
		System.out.println("In the Init command");
		if (!useMotionProfilng) {
			Robot.driveTrain.initializeEncoderPID();
			Robot.driveTrain.enableEncoderPID(m_setpoint);
		} else {
			Robot.driveTrain.initializeEncoderMP();
			Robot.driveTrain.enableEncoderMP(m_setpoint);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!useMotionProfilng) {
			return Robot.driveTrain.isDoneEncoderPID();
		} else {
			return Robot.driveTrain.isDoneEncoderMP();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		debug("Done");
		Robot.driveTrain.move(0, 0);
		if (!useMotionProfilng) {
			Robot.driveTrain.disableEncoderPID();
		} else {
			Robot.driveTrain.disableEncoderMP();
		}
		Trace.getInstance().flushTraceFiles();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("CALLING INTERUPTED!!!!!!!");
		end();
	}

	protected void debug(String information) {
		System.out.println("In MoveUsingEncoderPID.java " );
		System.out.flush();
	}
}

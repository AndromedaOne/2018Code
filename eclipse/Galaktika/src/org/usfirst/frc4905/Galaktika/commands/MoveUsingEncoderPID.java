package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingEncoderPID extends Command {

	private double m_setpoint = 0;

    public MoveUsingEncoderPID(double setpointInches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		debug("top of constructor, inches = " + setpointInches);
	    	requires(Robot.driveTrain);
	    	m_setpoint = setpointInches * DriveTrain.ENCODER_TICKS_PER_INCH;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		debug("Initializing");
	    	Robot.driveTrain.initializeEncoderPID();
	    	Robot.driveTrain.enableEncoderPID(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.isDoneEncoderPID();
    }

    // Called once after isFinished returns true
    protected void end() {
		debug("Done");
	    	Robot.driveTrain.move(0, 0);
	    	Robot.driveTrain.disableEncoderPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	    	end();
    }

    protected void debug(String information) {
		System.out.println("In MoveUsingEncoderPID.java " +
				"Done Encoder Ticks " + m_setpoint + " " +
				information);
		System.out.flush();
    }
}

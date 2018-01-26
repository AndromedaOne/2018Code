package org.usfirst.frc4905.Galaktika.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDelay extends Command {

    public AutoDelay() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		// TODO: Set time out
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		// TODO: Wait
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		// TODO: Check for time out
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}

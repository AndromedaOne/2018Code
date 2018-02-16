package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoTimedArmsClose extends TimedCommand {

    public AutoTimedArmsClose(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.jaws);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.jaws.contract();
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

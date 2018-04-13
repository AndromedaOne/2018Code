package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakeShouldBeUpCommand extends Command {

	private boolean m_state;
	
    public SetIntakeShouldBeUpCommand(boolean state) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.retractor);
    	m_state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("SetIntakeShouldBeUpCommand m_state: " + m_state);
    	Robot.retractor.setShouldIntakeBeUpBoolean(m_state);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

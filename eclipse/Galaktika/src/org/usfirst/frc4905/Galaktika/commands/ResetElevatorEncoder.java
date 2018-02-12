package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetElevatorEncoder extends Command {

	private boolean m_atBottom = false;
	
    public ResetElevatorEncoder() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.elevator.getBottomLimitSwitch()) {
    		m_atBottom = true;
    	}

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.elevator.getBottomLimitSwitch()) {
    		Robot.elevator.stopElevator();
    	} else {
    		Robot.elevator.moveElevator(0.1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return Robot.elevator.getBottomLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.resetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

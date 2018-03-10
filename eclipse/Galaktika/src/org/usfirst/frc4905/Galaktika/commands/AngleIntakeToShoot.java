package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AngleIntakeToShoot extends Command {

	private int counter;
	
	private int timeThreshold = 15;
	
    public AngleIntakeToShoot() {
        // Use requires() here to declare subsystem dependencies
       requires(Robot.retractor);
       counter= 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(counter<timeThreshold){
    		Robot.retractor.retractIntake();
    	}
    	else{
    		Robot.retractor.stopIntakeExtension();
    		Robot.retractor.setShouldIntakeBeStoppedBoolean(true);
    	}
    	
    	
    	counter= counter + 1;
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(counter>timeThreshold){
    		return true;
    	}else{
    		return false;
    	}
       
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

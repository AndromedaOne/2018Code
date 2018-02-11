package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.ButtonsEnumerated;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftRamps extends Command {

	
	Joystick driveController;
    public LiftRamps() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveController = Robot.oi.getDriveController();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	boolean isBackButtonPressed = ButtonsEnumerated.getBackButton(driveController);
    	boolean isStartButtonPressed = ButtonsEnumerated.getStartButton(driveController);
    	if (isBackButtonPressed) {
    		Robot.ramps.liftLeftRamp();
    	} 
    	if (isStartButtonPressed) {
    		Robot.ramps.liftRightRamp();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

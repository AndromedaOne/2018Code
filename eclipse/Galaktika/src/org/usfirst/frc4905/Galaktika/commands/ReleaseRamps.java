package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.ButtonsEnumerated;
import Utilities.ControllerButtons.POVDirectionNames;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseRamps extends Command {



	Joystick driveController;
	public ReleaseRamps() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.ramps);
	}



	// Called just before this Command runs the first time
	protected void initialize() {
		driveController = Robot.oi.getDriveController();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		boolean isLeftPOVPressed = POVDirectionNames.getPOVWest(driveController);
		boolean isRightPOVPressed = POVDirectionNames.getPOVEast(driveController);
		boolean safetiesEnabled = Robot.ramps.returnSafetyStatus();
		double timeRemaining = Robot.ramps.getTimeRemainingInMatchPeriod();


		if(safetiesEnabled){
			if (isLeftPOVPressed && timeRemaining < 30) {
				Robot.ramps.releaseLeftRamp();
			}
			if (isRightPOVPressed && timeRemaining < 30) {
				Robot.ramps.releaseRightRamp();
			}
		}
		else{
			if (isLeftPOVPressed) {
				Robot.ramps.releaseLeftRamp();
			}
			if (isRightPOVPressed) {
				Robot.ramps.releaseRightRamp();
			}
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

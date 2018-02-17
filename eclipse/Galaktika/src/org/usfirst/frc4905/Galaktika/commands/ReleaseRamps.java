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

	private boolean isLeftPOVPressed = false, isRightPOVPressed = false;
	private boolean safetiesEnabled;
	private double timeRemaining;
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

		isLeftPOVPressed = POVDirectionNames.getPOVWest(driveController);
		isRightPOVPressed = POVDirectionNames.getPOVEast(driveController);
		safetiesEnabled = Robot.ramps.returnSafetyStatus();
		timeRemaining = Robot.ramps.getTimeRemainingInMatchPeriod();

		System.out.println("hitting button to deploy ramps" + isLeftPOVPressed);
		

		if(safetiesEnabled){
			if (isLeftPOVPressed && timeRemaining < 31) {
				Robot.ramps.moveLeftServo(0);
				Robot.ramps.setLeftRampDeployed();
			}
			if (isRightPOVPressed && timeRemaining < 31) {
				Robot.ramps.moveRightServo(1);
				Robot.ramps.setRightRampDeployed();
			}
		}
		else{
			if (isLeftPOVPressed) {
				System.out.println("trying to move left servo");
				Robot.ramps.moveLeftServo(0);
				Robot.ramps.setLeftRampDeployed();
			}
			if (isRightPOVPressed) {
				Robot.ramps.moveRightServo(1);
				Robot.ramps.setRightRampDeployed();
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

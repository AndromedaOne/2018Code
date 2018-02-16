package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.ButtonsEnumerated;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JawsOpenClose extends Command {

	public JawsOpenClose() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.jaws);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Joystick joystick = Robot.oi.getSubsystemController();
		boolean isLeftBumperPressed = ButtonsEnumerated.getLeftButton(joystick);
		boolean isRightBumperPressed = ButtonsEnumerated.getRightButton(joystick);
		if(isRightBumperPressed && ! isLeftBumperPressed) {
			//contract
			Robot.intake.runIntake(RunIntakeIn.kIntakeSpeed);
			Robot.jaws.contract();

		}else if(isLeftBumperPressed && ! isRightBumperPressed) {
			//extend
			Robot.jaws.extend();

		}else {
			Robot.jaws.stop();
			Robot.intake.runIntake(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.jaws.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

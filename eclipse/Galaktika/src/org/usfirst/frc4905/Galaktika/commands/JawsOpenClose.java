package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.ButtonsEnumerated;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JawsOpenClose extends Command {

	Joystick subsystemController;
	
	public JawsOpenClose() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.jaws);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		subsystemController = Robot.oi.getSubsystemController();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		boolean isLeftBumperPressed = ButtonsEnumerated.getLeftButton(subsystemController);
		boolean isRightBumperPressed = ButtonsEnumerated.getRightButton(subsystemController);
		
		
		if(isRightBumperPressed && ! isLeftBumperPressed) {
			//contract
			//Robot.intake.runIntake(RunIntakeIn.kIntakeSpeed);
			Robot.jaws.setShouldJawsBeOpenBoolean(false);
			System.out.println("Trying to close");

		}else if(isLeftBumperPressed && ! isRightBumperPressed) {
			//extend
			Robot.jaws.setShouldJawsBeOpenBoolean(true);
			System.out.println("Trying to open");

		}
		
		Robot.jaws.setJawsToCorrectState();
		
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

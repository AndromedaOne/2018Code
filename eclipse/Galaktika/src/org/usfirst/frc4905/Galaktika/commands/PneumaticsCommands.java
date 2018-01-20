package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ButtonsEnumerated;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PneumaticsCommands extends Command {

    public PneumaticsCommands() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pneumatics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Joystick joystick = Robot.oi.getDriveController();
    	
    	boolean isLeftBumperPressed = ButtonsEnumerated.getLeftButton(joystick);
    	boolean isRightBumperPressed = ButtonsEnumerated.getRightButton(joystick);
    	if(isRightBumperPressed && ! isLeftBumperPressed) {
    		//extend
    		Robot.pneumatics.extend();

    	}else if(isLeftBumperPressed && ! isRightBumperPressed) {
    		//contract
    		Robot.pneumatics.contract();
    	}else {
    		//do nothing
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pneumatics.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

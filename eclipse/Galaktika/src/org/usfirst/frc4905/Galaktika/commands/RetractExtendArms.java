package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractExtendArms extends Command {

	Joystick subystemController;
	
    public RetractExtendArms() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.retractor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	subystemController = Robot.oi.getSubsystemController();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	boolean downPovPressed = Utilities.ControllerButtons.POVDirectionNames.getPOVSouth(subystemController);
    	boolean upPovPressed = Utilities.ControllerButtons.POVDirectionNames.getPOVNorth(subystemController);
    	
    	if(upPovPressed){
    		Robot.retractor.setShouldIntakeBeUpBoolean(true);
    	}
    	else if(downPovPressed){
    		Robot.retractor.setShouldIntakeBeUpBoolean(false);
    	}
    	else{
    		//do nothing
    	}
    	
    	Robot.retractor.setIntakeToCorrectState();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.retractor.stopIntakeExtension();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

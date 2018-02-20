package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.ControllerButtons.EnumeratedRawAxis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorManualControl extends Command {


	Joystick subsystemController;
    public ElevatorManualControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	subsystemController = Robot.oi.getSubsystemController();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	double forwardBackwardStickValue = EnumeratedRawAxis.getRightStickVertical(subsystemController);
    	
    	if(forwardBackwardStickValue < 0.02 && forwardBackwardStickValue > -0.02 && Robot.elevator.getPidEnabledStatus() == false){
    		//if there is no driver input and a pid loop isnt running, start one to maintain position
    		double positionToMaintain = Robot.elevator.getElevatorPosition();
    		Robot.elevator.setPIDControllerToMaintenanceMode();//maintain our position constants
    		
    		Robot.elevator.enableEncoderPID(positionToMaintain);
    		
    	}
    	else{
    		if(!(forwardBackwardStickValue < 0.02 && forwardBackwardStickValue > -0.02) && Robot.elevator.getPidEnabledStatus() == true){
    			//if we have driver input and the pid loop is still running, end it.
    			Robot.elevator.disableEncoderPID();
    		}
    		
    		if(forwardBackwardStickValue > 0.6){
    			//temp safety on the way down cuz moveelevatorsafely doesn't work as intended and I don't feel like fixing it yet
    			forwardBackwardStickValue = 0.6;
    		}
    		Robot.elevator.moveElevatorSafely(forwardBackwardStickValue);
    	}
     	
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.elevator.moveElevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	Robot.elevator.moveElevator(0);
    }
}

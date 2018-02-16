package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

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
    	//Robot.elevator.moveElevator(forwardBackwardStickValue);
    	//Changed from move safely to move for testing
    	
    	if(forwardBackwardStickValue < 0){
    		//slow down the elevator on the way down cuz it is sketchy.
    		forwardBackwardStickValue *= 0.2;
    	}
    	
    	if(forwardBackwardStickValue == 0){
    		Robot.elevator.enableEncoderPID(Robot.elevator.getElevatorPosition());
    	}
    	else{
    		Robot.elevator.disableEncoderPID();
    		Robot.elevator.moveElevator(forwardBackwardStickValue);

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
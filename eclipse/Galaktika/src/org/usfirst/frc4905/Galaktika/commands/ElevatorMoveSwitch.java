package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.EnumeratedRawAxis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorMoveSwitch extends Command {
	double driverInput = 0;
	Joystick subsystemcontroller;
	
	boolean driverInterrupt = false;
	
    public ElevatorMoveSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	if(Robot.elevator.getPidEnabledStatus() == true){
    		Robot.elevator.disableEncoderPID();
    	}
    	Robot.elevator.initializeEncoderPID();
    	Robot.elevator.setPIDControllerToTravelMode();
    	Robot.elevator.enableEncoderPID(1200);
    	subsystemcontroller = Robot.oi.getSubsystemController();
    	driverInterrupt = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	driverInput = EnumeratedRawAxis.getRightStickVertical(subsystemcontroller);
    	if(!(driverInput < 0.02 && driverInput > -0.02)){
    		//if driver starts moving, disable pid loop
    		Robot.elevator.disableEncoderPID();
    		driverInterrupt = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        
    	return Robot.elevator.isDoneEncoderPID() || driverInterrupt;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!driverInterrupt){
    		Robot.elevator.setPIDControllerToMaintenanceMode();
    	}
    	else{
    		//System.out.println("Ended button pid loop due to driver input");
    	}
    	
    	
    	//don't end the pid loop, instead change to maintenance mode to hold spot
    	//if some other elevator function is called, the first thing it does is disable a running pid loop anyways.
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	Robot.elevator.disableEncoderPID();
    }
}

package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.EnumeratedRawAxis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorMoveGroundLevel extends Command {
	double driverInput = 0;
	Joystick subsystemcontroller;
	
	boolean driverInterrupt = false;
	
	
    public ElevatorMoveGroundLevel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.elevator.getPidEnabledStatus() == true){
    		Robot.elevator.disableEncoderPID();
    	}
    	Robot.elevator.initializeEncoderPID();
    	Robot.elevator.setPIDControllerToTravelMode();
    	Robot.elevator.enableEncoderPID(0);
    	subsystemcontroller = Robot.oi.getSubsystemController();
    	driverInterrupt = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driverInput = EnumeratedRawAxis.getRightStickVertical(subsystemcontroller);
    	if(!(driverInput < 0.02 && driverInput > -0.02)){
    		//if driver starts moving, disable pid loop
    		Robot.elevator.disableEncoderPID();
    		driverInterrupt = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.elevator.isDoneEncoderPID() || driverInterrupt;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!driverInterrupt){
    		Robot.elevator.setPIDControllerToMaintenanceMode();
    		
    	}else{
    		//System.out.println("Ended button pid loop due to driver input");
    	}
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.disableEncoderPID();
    }
}

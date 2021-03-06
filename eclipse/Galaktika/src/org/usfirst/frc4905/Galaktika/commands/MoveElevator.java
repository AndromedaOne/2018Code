package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.EnumeratedRawAxis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {
	boolean m_driverInterrupt = false;
	double m_setPoint = 0;
	

    public MoveElevator(double setPoint) {
    	
    	this();
    	System.out.println("MOVEELEVATOR CONSTRUCTOR CALLED, SETPOINT = " + setPoint);
    	m_setPoint = setPoint;
    }
    public MoveElevator() {
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initializing ELevator PID");
    	if(Robot.elevator.getPidEnabledStatus() == true){
    		Robot.elevator.disableEncoderPID();
    	}
    	Robot.elevator.initializeEncoderPID();
    	Robot.elevator.setPIDControllerToTravelMode();
    	Robot.elevator.enableEncoderPID(m_setPoint);
    	System.out.println("Current Position: " + Robot.elevator.getElevatorEncoderPosition());
    	System.out.println("Trying to move elevator to: " + m_setPoint);
    	System.out.println("PID Actual error: " + Robot.elevator.getEncoderError());
    	m_driverInterrupt = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.AutonomousMode) {
    		double driverInput = EnumeratedRawAxis.getRightStickVertical(Robot.oi.subsystemController);
    		if(!isInDeadzone(driverInput)){
    			System.out.println("The Robot is not in the dead zone :p");
    			//if driver starts moving, disable pid loop
    			Robot.elevator.disableEncoderPID();
    			m_driverInterrupt = true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.elevator.isDoneEncoderPID() || m_driverInterrupt;
    }

    // Called once after isFinished returns true
    protected void end() {
    	boolean autoMode = Robot.AutonomousMode;
    	if(!m_driverInterrupt && !autoMode){
    		Robot.elevator.setPIDControllerToMaintenanceMode();
    	}
    	else{
    		Robot.elevator.disableEncoderPID();// there is driver input, let's just preempt the check in manual and disable the loop, for safety.
    	}
    	
    	
    	Robot.elevator.stopElevator();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.disableEncoderPID();
    }
	protected boolean isInDeadzone(double forwardBackwardStickValue) {
		return (forwardBackwardStickValue > -0.02 && forwardBackwardStickValue < 0.02);
	}
}

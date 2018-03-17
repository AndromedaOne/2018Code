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
	
	// Encoder Revolution Constants
	public static final double GROUND_LEVEL = 0;
	public static final double EXCHANGE_HEIGHT = 200;
	public static final double SWITCH_HEIGHT = 1200;
	public static final double LOW_SCALE_HEIGHT = 2000;
	public static final double HIGH_SCALE_HEIGHT = 3400;
	
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
    	if(Robot.elevator.getPidEnabledStatus() == true){
    		Robot.elevator.disableEncoderPID();
    	}
    	Robot.elevator.initializeEncoderPID();
    	Robot.elevator.setPIDControllerToTravelMode();
    	Robot.elevator.enableEncoderPID(m_setPoint);
    	System.out.println("Current Position: " + Robot.elevator.getElevatorEncoderPosition());
    	System.out.println("Trying to move elevator to: " + m_setPoint);
    	System.out.println("Error: " + Robot.elevator.getEncoderError());
    	m_driverInterrupt = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double driverInput = EnumeratedRawAxis.getRightStickVertical(Robot.oi.subsystemController);
    	if(!isInDeadzone(driverInput)){
    		//if driver starts moving, disable pid loop
    		Robot.elevator.disableEncoderPID();
    		m_driverInterrupt = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.elevator.isDoneEncoderPID() || m_driverInterrupt;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!m_driverInterrupt){
    		Robot.elevator.setPIDControllerToMaintenanceMode();
    	}
    	else{
    		Robot.elevator.disableEncoderPID();//there is driver input, let's just preempt the check in manual and disable the loop, for safety.
    	}
    	
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

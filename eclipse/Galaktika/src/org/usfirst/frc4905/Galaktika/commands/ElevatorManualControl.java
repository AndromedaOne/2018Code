package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.ControllerButtons.EnumeratedRawAxis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
// Extends MoveElevator to get isIndeadzone function and some other crap
public class ElevatorManualControl extends MoveElevator {

	//reverse oi, no mod on turning, fix intake wheels, auto put things down

	Joystick subsystemController;

	double m_topElevatorPosition = 4775;
	double m_topElevatorLimit = m_topElevatorPosition - 100;
	double m_bottomElevatorLimit = 100;



	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		subsystemController = Robot.oi.getSubsystemController();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double forwardBackwardStickValue = EnumeratedRawAxis.getRightStickVertical(subsystemController);

		if(Robot.elevator.getPidEnabledStatus()) {

			// If pid is enabled and stick is not in deadzone then disable the encoder pid
			if(!isInDeadzone(forwardBackwardStickValue)) {
				Robot.elevator.disableEncoderPID();
			}
		} else {
			// If pid is disabled and stick is in deadzone then maintain position
			if(isInDeadzone(forwardBackwardStickValue)) {
				double positionToMaintain = Robot.elevator.getElevatorPosition();

				if(positionToMaintain > Robot.elevator.getTopEncoderPosition() - 50) {
					positionToMaintain = Robot.elevator.getTopEncoderPosition() - 50;
				}
				if(positionToMaintain < m_bottomElevatorLimit) {
					Robot.elevator.disableEncoderPID();
				}else {
					Robot.elevator.setPIDControllerToMaintenanceMode();//maintain our position constants
					Robot.elevator.enableEncoderPID(positionToMaintain);
				}
			} else {
				double elevatorPosition = Robot.elevator.getElevatorPosition();

				if(elevatorPosition > m_topElevatorLimit && (forwardBackwardStickValue < 0 || isInDeadzone(forwardBackwardStickValue))) {
					Robot.elevator.setPIDControllerToMaintenanceMode();//maintain our position constants
					Robot.elevator.enableEncoderPID(m_topElevatorPosition);
				}

				Robot.elevator.moveElevatorSafely(forwardBackwardStickValue);
			}
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
		Robot.elevator.stopElevator();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.elevator.stopElevator();
	}

}

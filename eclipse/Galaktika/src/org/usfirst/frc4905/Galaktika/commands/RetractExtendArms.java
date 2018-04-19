package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

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

	private final double kDeadZone = 0.06;
	private final long kDelayTime = 100;
	private final long kHoldTime = 40;
	private long m_currentDelayTime = 0;
	private long m_currentHoldTime = 0;

	enum RetractorStates {
		Stop,
		BeginMovingUp,
		Moving,
		BeginMovingDown,
		InchingDelay
	}
	private RetractorStates m_currentState = RetractorStates.Stop;


	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		subystemController = Robot.oi.getSubsystemController();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		boolean downPovPressed = Utilities.ControllerButtons.POVDirectionNames.getPOVSouth(subystemController);
		boolean upPovPressed = Utilities.ControllerButtons.POVDirectionNames.getPOVNorth(subystemController);
		double leftJoystick = -(Utilities.ControllerButtons.EnumeratedRawAxis.getLeftStickVertical(subystemController));
		
		if(upPovPressed){
			System.out.println("UP");
			Robot.retractor.setShouldIntakeBeUpBoolean(true);
		}
		else if(downPovPressed){
			System.out.println("Down");
			Robot.retractor.setShouldIntakeBeUpBoolean(false);
		}
		else if(((kDeadZone < leftJoystick) || (-kDeadZone > leftJoystick))
				&& !Robot.jaws.getShouldJawsBeOpen()){
			long currentTime = System.currentTimeMillis();
			System.out.println("Current State = " + m_currentState + "   Current Time = " + currentTime 
					+ "   Left JoyStick = " + leftJoystick);
			switch (m_currentState) {
			case Stop:
				Robot.retractor.setIntakeRetractionShouldBeStopped();
				if(leftJoystick > 0) {
					m_currentState = RetractorStates.BeginMovingUp;
				}
				if(leftJoystick < 0) {
					m_currentState = RetractorStates.BeginMovingDown;
				}
				break;
			case BeginMovingUp:
				m_currentDelayTime = (long) (currentTime + kHoldTime + kDelayTime / leftJoystick);
				m_currentHoldTime = currentTime + kHoldTime;
				Robot.retractor.setShouldIntakeBeUpBoolean(true);
				m_currentState = RetractorStates.Moving;
				break;
			case Moving:
				if(currentTime > m_currentHoldTime) {
					Robot.retractor.setIntakeRetractionShouldBeStopped();
					m_currentState = RetractorStates.InchingDelay;
				}
				break;
			case BeginMovingDown:
				m_currentDelayTime = (long) (currentTime + kHoldTime + kDelayTime / -leftJoystick);
				m_currentHoldTime = currentTime + kHoldTime;
				Robot.retractor.setShouldIntakeBeUpBoolean(false);
				m_currentState = RetractorStates.Moving;
				break;
			case InchingDelay: 
				if(currentTime > m_currentDelayTime) {
					m_currentState = RetractorStates.Stop;
				}
				break;
			default: 
				m_currentState = RetractorStates.Stop;
			}

		} else {
			m_currentState = RetractorStates.Stop;
		}
		
		Robot.retractor.setIntakeToCorrectState();
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Robot.retractor.stopIntakeExtension();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

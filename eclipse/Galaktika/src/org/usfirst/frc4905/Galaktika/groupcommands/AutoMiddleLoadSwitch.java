package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {

	private static final double LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE = 120.3;
	private static final double LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE = 120.3;
	boolean m_useDelay;

	public AutoMiddleLoadSwitch(boolean useDelay) {
		if (useDelay) {
	        m_useDelay = useDelay;
		}
	}

	protected void prepareToStart() {
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
        closeJaws(false);
        lowerIntake();
		moveElevatorToSwitchHeight();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE/2);

		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == 'R') {
			turnToCompassHeading(90);
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
		} else {
			turnToCompassHeading(270);
			driveForward(LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE);	
		}
		turnToCompassHeading(0);
		driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
		shootCube(0.5);
		closeJaws(false); 
		delay(2);
		driveBackward(30);

	}

}

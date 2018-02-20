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
		char platePos = Robot.getSwitchPlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
		parallelJawsOpenClose();
		parallelRetractExtendArms();
		setJawsShouldBeOpenState(false);
		setRetractorShouldBeUp(false);

		moveElevatorToSwitchHeight();

		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE/2);

		if (platePos == 'R') {
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
			turnLeft();
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
		} else {
			turnLeft();
			driveForward(LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE);
			turnRight();
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
		}

		setJawsShouldBeOpenState(true);

		/*
		if (platePos == 'R') {

			driveBackward(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			moveElevatorToGroundHeight();
			turnLeft();
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
			turnRight();

		} else {
			driveBackward(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			moveElevatorToGroundHeight();
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
			turnLeft();

		}

*/

	}

}

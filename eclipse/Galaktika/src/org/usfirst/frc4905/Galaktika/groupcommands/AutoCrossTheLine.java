package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoCrossTheLine extends AutoCommand {

	public AutoCrossTheLine(boolean useDelay) {
	    if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
	}

	public AutoCrossTheLine() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        if (robotPos == 'M') {
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnRight();
            driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
            turnLeft();
            driveForwardToWall();
        } else {
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
        }
    }
}

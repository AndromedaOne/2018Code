package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoCrossTheLine extends AutoCommand {

	public AutoCrossTheLine(double delaySeconds) {
		if (delaySeconds > 0) {
			delay(delaySeconds);
		}
		char robotPos = Robot.getInitialRobotLocation();
		if (robotPos == 'M') {
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT);
			turnLeft();
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
		} else {
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		}
	}

	public AutoCrossTheLine() {
		this(5);
	}

}

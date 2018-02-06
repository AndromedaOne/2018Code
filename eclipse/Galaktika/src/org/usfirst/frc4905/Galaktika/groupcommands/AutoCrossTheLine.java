package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoCrossTheLine extends AutoCommand {

	static final double FORWARD_DISTANCE_TO_AUTO_LINE = 122;
	static final double LATERAL_DISTANCE_TO_EXCHANGE = 31.13;
    public AutoCrossTheLine() {

		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		turnAround();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
		turnRight();
		driveForward(LATERAL_DISTANCE_TO_EXCHANGE);
		turnLeft();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
		loadPowerCubeIntoExchange();
	}

	private void loadPowerCubeIntoExchange() {
		// TODO Auto-generated method stub

	}

    protected void prepareToStart() {
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
}

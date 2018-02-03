package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoCrossTheLine extends AutoCommand {

	public AutoCrossTheLine(boolean useDelay) {
	    if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
	    char robotPos = Robot.getInitialRobotLocation();
	    char scaleSide = '*';
	    char switchSide = '*';
	    if (robotPos == 'M') {
		    	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		    	turnRight(scaleSide, switchSide);
		    	driveForward(LATERAL_DISTANCE_TO_RIGHT, scaleSide, switchSide);
		    	turnLeft(scaleSide, switchSide);
		    	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
	    } else {
	    		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE, scaleSide, switchSide);
	    }
	}

	public AutoCrossTheLine() {
		this(false);
	}

}

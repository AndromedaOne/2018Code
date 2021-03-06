package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoCrossTheLine extends AutoCommand {
	boolean m_useDelay;

	public AutoCrossTheLine(boolean useDelay) {
	    if (useDelay) {
	        m_useDelay = useDelay;
        }
	}

	public AutoCrossTheLine() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
        lowerIntake();
       
        if (robotPos == 'M') {
        	moveElevatorToSwitchHeight();
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnRight();
            driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
            turnLeft();
            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
        } else {
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
        }
    }
}

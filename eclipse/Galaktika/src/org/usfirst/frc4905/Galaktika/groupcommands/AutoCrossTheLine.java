package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoCrossTheLine extends AutoCommand {
	boolean m_useDelay;

	public AutoCrossTheLine(boolean useDelay) {
	    if (useDelay) {
	        m_useDelay = useDelay;
        }
	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        if (m_useDelay) {
        	delay(Robot.getAutonomousDelay());
        }
        driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
    }
}

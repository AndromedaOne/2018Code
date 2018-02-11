package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class TestCompassDirections extends AutoCommand {

	public TestCompassDirections(boolean useDelay) {
	    if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
	}

	public TestCompassDirections() {
		this(false);

	}

    protected void prepareToStart() {
        driveForward(5);
        turnToCompassHeading(90.0);
        driveForward(5);
        turnToCompassHeading(270.0);
        driveForward(5);
        turnToCompassHeading(0.0);
        driveForward(5);
        turnToCompassHeading(180.0);
    }
}

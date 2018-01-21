package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {
	private static final double LATERAL_DISTANCE_TO_RIGHT = 0;
	private static final double LATERAL_DISTANCE_TO_LEFT = 0;

	AutoMiddleLoadSwitch(){

		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == 'R') {
			driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT);
			turnLeft();
			driveForward(AutoLoadSwitch.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			turnLeft();
			driveForward(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
		} else {
			driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
			turnLeft();
			driveForward(LATERAL_DISTANCE_TO_LEFT);
			turnRight();
			driveForward(AutoLoadSwitch.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			turnRight();
			driveForward(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
		}
	}

}

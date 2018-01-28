package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {
	public AutoMiddleLoadSwitch(double delaySeconds) {

		if (delaySeconds > 0) {
			delay(delaySeconds);
		}
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == 'R') {
			driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT);
			turnLeft();
			driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			turnLeft();
			driveForwardToWall(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
		} else {
			driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
			turnLeft();
			driveForward(LATERAL_DISTANCE_TO_LEFT);
			turnRight();
			driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			turnRight();
			driveForwardToWall(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
		}
	}

	public AutoMiddleLoadSwitch() {
		this(0);
	}

}

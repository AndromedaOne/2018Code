package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadScale extends CommandGroup {

	public AutoLoadScale() {

	}

	public void start() {
		char robotPos = Robot.getInitialRobotLocation();
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == robotPos) {
			nearScale(robotPos);
		} else {
			farScale(robotPos);
		}
	}

	public void nearScale(char robotPos) {

	}

	public void farScale(char robotPos) {

	}

}

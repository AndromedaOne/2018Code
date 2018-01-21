package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadScale extends AutoCommand {
	public AutoLoadScale() {

		char robotPos = Robot.getInitialRobotLocation();
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == robotPos) {
			loadNearScalePlate(robotPos);
		} else {
			loadFarScalePlate(robotPos);
		}
	}

	public void loadNearScalePlate(char robotPos) {
		driveForward(FORWARD_DISTANCE_TO_SCALE);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForward(LATERAL_DISTANCE_TO_SCALE);
		loadPowerCubeOntoScale();
	}

	private void loadPowerCubeOntoScale() {
		// TODO Auto-generated method stub
		
	}

	

	public void loadFarScalePlate(char robotPos) {
		driveForward(FORWARD_DISTANCE_TO_MIDDLE);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForward(LATERAL_DISTANCE_BETWEEN_PATHS);
		if (robotPos == 'R') {
			turnRight();
		} else {
			turnLeft();
		}
		driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE);
		if (robotPos == 'R') {
			turnRight();
		} else {
			turnLeft();
		}
		driveForward(LATERAL_DISTANCE_TO_SCALE);
		loadPowerCubeOntoScale();
	}

}

package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadScale extends CommandGroup {
	//We got these values from CAD
	private static final double FORWARD_DISTANCE_TO_SCALE = 304.25;
	private static final double LATERAL_DISTANCE_TO_SCALE = 15.08;

	public AutoLoadScale() {

	}

	public void start() {
		char robotPos = Robot.getInitialRobotLocation();
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == robotPos) {
			loadNearScale(robotPos);
		} else {
			loadFarScale(robotPos);
		}
	}

	public void loadNearScale(char robotPos) {
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

	private void turnRight() {
		// TODO Auto-generated method stub
		
	}

	private void turnLeft() {
		// TODO Auto-generated method stub
		
	}

	private void driveForward(double forwardDistanceToScale) {
		// TODO Auto-generated method stub
		
	}

	public void loadFarScale(char robotPos) {

	}

}

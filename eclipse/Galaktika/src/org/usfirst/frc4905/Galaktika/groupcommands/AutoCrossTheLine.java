package org.usfirst.frc4905.Galaktika.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCrossTheLine extends CommandGroup {
	
	static final double FORWARD_DISTANCE_TO_AUTO_LINE = 122;
	static final double LATERAL_DISTANCE_TO_EXCHANGE = 0;

	public AutoCrossTheLine() {
		
	}
	
	public void start() {
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		turnLeft();
		turnLeft();
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

	private void turnRight() {
		// TODO Auto-generated method stub
		
	}

	private void turnLeft() {
		// TODO Auto-generated method stub
		
	}

	private void driveForward(double distanceToAutoLine) {
		// TODO Auto-generated method stub
		
	}
}

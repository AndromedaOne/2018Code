package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadSwitch extends CommandGroup {
	
	//We need these values from CAD
		static final double FORWARD_DISTANCE_TO_SWITCH = 148.04;
		static final double LATERAL_DISTANCE_TO_SWITCH = 28.72;

		public AutoLoadSwitch() {

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
			driveForward(FORWARD_DISTANCE_TO_SWITCH);
			if (robotPos == 'R') {
				turnLeft();
			} else {
				turnRight();
			}
			driveForward(LATERAL_DISTANCE_TO_SWITCH);
			loadPowerCubeOntoSwitch();
		}

		private void loadPowerCubeOntoSwitch() {
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
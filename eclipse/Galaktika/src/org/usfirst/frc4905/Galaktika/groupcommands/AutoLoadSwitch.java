package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoLoadSwitch extends AutoCommand {
	
	//We need these values from CAD
		static final double FORWARD_DISTANCE_TO_SWITCH = 148.04;
		static final double LATERAL_DISTANCE_TO_SWITCH = 28.72;

		public AutoLoadSwitch() {

		}

		public void start() {
			char robotPos = Robot.getInitialRobotLocation();
			char platePos = Robot.getSwitchPlatePosition();
			if (platePos == robotPos) {
				loadNearSwitchPlate(robotPos);
			} else {
				loadFarSwitchPlate(robotPos);
			}
		}

		public void loadNearSwitchPlate(char robotPos) {
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

		public void loadFarSwitchPlate(char robotPos) {
			driveForward(FORWARD_DISTANCE_TO_MIDDLE);
			if (robotPos == 'R') {
				turnLeft();
			} else {
				turnRight();
			}
			driveForward(LATERAL_DISTANCE_BETWEEN_PATHS);
			if (robotPos == 'R') {
				turnLeft();
			} else {
				turnRight();
			}
			driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH);
			if (robotPos == 'R') {
				turnLeft();
			} else {
				turnRight();
			}
			driveForward(LATERAL_DISTANCE_TO_SWITCH);
			loadPowerCubeOntoSwitch();
		}

	}
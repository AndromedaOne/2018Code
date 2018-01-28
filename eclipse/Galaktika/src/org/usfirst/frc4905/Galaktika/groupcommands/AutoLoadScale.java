package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadScale extends AutoCommand {
	public AutoLoadScale() {
		// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
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

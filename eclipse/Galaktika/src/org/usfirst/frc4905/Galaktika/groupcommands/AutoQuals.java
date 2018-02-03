package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoQuals extends AutoCommand {
	static final double LATERAL_DISTANCE_TO_EXCHANGE = 31.13;

	public AutoQuals() {
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
		this(false);

	}

	public AutoQuals(boolean useDelay) {
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
		debug("top of AutoQuals constructor");
	    if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
		char robotPos = Robot.getInitialRobotLocation();
		char switchPlatePos = Robot.getSwitchPlatePosition();
		char scalePlatePos = Robot.getScalePlatePosition();
		if (switchPlatePos == robotPos) {
			loadNearSwitchPlate(robotPos);
		} else if (scalePlatePos == robotPos){
			loadNearScalePlate(robotPos);
		} else {
			crossAutoLine();
			returnToLoadExchange();
		}
		debug("bottom of AutoQuals constructor");
	}


	private void crossAutoLine() {
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
	}

	private void returnToLoadExchange() {
		turnLeft();
		turnLeft();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
		turnRight();
		driveForward(LATERAL_DISTANCE_TO_EXCHANGE);
		turnLeft();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
		loadPowerCubeIntoExchange();
	}


	public void loadNearSwitchPlate(char robotPos) {
		debug("top of AutoQuals loadNearSwitchPlate");
		driveForward(FORWARD_DISTANCE_TO_SWITCH);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForward(LATERAL_DISTANCE_TO_SWITCH);
		loadPowerCubeOntoSwitch();
		debug("bottom of AutoQuals loadNearSwitchPlate");
	}

	public void loadNearScalePlate(char robotPos) {
		debug("top of AutoQuals loadNearScalePlate");
		driveForward(FORWARD_DISTANCE_TO_SCALE);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForward(LATERAL_DISTANCE_TO_SCALE);
		loadPowerCubeOntoScale();
		debug("bottom of AutoQuals loadNearScalePlate");
	}

}

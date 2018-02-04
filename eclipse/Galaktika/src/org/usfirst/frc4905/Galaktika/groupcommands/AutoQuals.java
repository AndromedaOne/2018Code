package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoQuals extends AutoCommand {
	static final double LATERAL_DISTANCE_TO_EXCHANGE = 31.13;
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
	    debug("bottom of AutoQuals constructor");
	}

	protected void initialize() {
	    if (m_needsInitialization) {
			char robotPos = Robot.getInitialRobotLocation();
			loadNearSwitchPlate(robotPos);
			loadNearScalePlate(robotPos);
			crossAutoLine(robotPos);
			returnToLoadExchange(robotPos);
			m_needsInitialization = false;
	    }
		super.initialize();
	 }

	private void crossAutoLine(char robotPos) {
		char scalePos;
		char switchPos;
		if (robotPos == 'R') {
			scalePos = 'L';
			switchPos = 'L';
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE, scalePos, switchPos);
		} else if (robotPos == 'L') {
			scalePos = 'R';
			switchPos = 'R';
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE, scalePos, switchPos);
		}
	}

	private void returnToLoadExchange(char robotPos) {
		char scalePos;
		char switchPos;
		if (robotPos == 'R') {
			scalePos = 'L';
			switchPos = 'L';
			turnAround(scalePos, switchPos);
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scalePos, switchPos);
			turnRight(scalePos, switchPos);
			driveForward(LATERAL_DISTANCE_TO_EXCHANGE, scalePos, switchPos);
			turnLeft(scalePos, switchPos);
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scalePos, switchPos);
			loadPowerCubeIntoExchange(scalePos, switchPos);
		} else if (robotPos == 'L') {
			scalePos = 'R';
			switchPos = 'R';
			turnAround(scalePos, switchPos);
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scalePos, switchPos);
			turnLeft(scalePos, switchPos);
			driveForward(LATERAL_DISTANCE_TO_EXCHANGE, scalePos, switchPos);
			turnRight(scalePos, switchPos);
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scalePos, switchPos);
			loadPowerCubeIntoExchange(scalePos, switchPos);
		}
	}


	public void loadNearSwitchPlate(char robotPos) {
		debug("top of AutoQuals loadNearSwitchPlate");
		char scaleSide = '*';
		char switchSide = robotPos;
		driveForward(FORWARD_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		if (robotPos == 'R') {
			turnLeft(scaleSide, switchSide);
		} else {
			turnRight(scaleSide, switchSide);
		}
		driveForward(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		loadPowerCubeOntoSwitch(scaleSide, switchSide);
		debug("bottom of AutoQuals loadNearSwitchPlate");
	}

	public void loadNearScalePlate(char robotPos) {
		debug("top of AutoQuals loadNearScalePlate");
		char scaleSide = robotPos;
		char switchSide = '*';
		driveForward(FORWARD_DISTANCE_TO_SCALE, scaleSide, switchSide);
		if (robotPos == 'R') {
			turnLeft(scaleSide, switchSide);
		} else {
			turnRight(scaleSide, switchSide);
		}
		driveForward(LATERAL_DISTANCE_TO_SCALE, scaleSide, switchSide);
		loadPowerCubeOntoScale(scaleSide, switchSide);
		debug("bottom of AutoQuals loadNearScalePlate");
	}

}

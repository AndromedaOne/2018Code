package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPlayoffs extends AutoCommand {
    static final double LATERAL_DISTANCE_TO_EXCHANGE = 31.13;
    private static final double LATERAL_DISTANCE_TO_FIRST_CUBE = 50.75;
    private static final double LATERAL_DISTANCE_TO_EXCHANGE_L = 90;
    private static final double LATERAL_DISTANCE_TO_EXCHANGE_R = 154;

    public AutoPlayoffs(boolean useDelay) {
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
        if (useDelay) {
            delay(Robot.getAutonomousDelay());

        }
    }
	protected void initialize() {
	    if (m_needsInitialization) {
			char robotPos = Robot.getInitialRobotLocation();
	        char scaleSide = 'L';
	        char switchSide;

	        if (robotPos == 'L') {
		        	switchSide = '*';
		        	driveForward(FORWARD_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	loadPowerCubeOntoScale(scaleSide, switchSide);
		        	driveBackward(LATERAL_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);

		        	scaleSide = 'R';

		        	switchSide = 'L';
		        	driveForward(FORWARD_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	loadPowerCubeOntoSwitch(scaleSide, switchSide);
		        	driveBackward(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);

		        	switchSide = 'R';
		        	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	loadPowerCubeIntoExchange(scaleSide, switchSide);
		        	driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
	        } else if (robotPos == 'R') {
		        	scaleSide = 'R';
		        	switchSide = '*';
		        	driveForward(FORWARD_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	loadPowerCubeOntoScale(scaleSide, switchSide);
		        	driveBackward(LATERAL_DISTANCE_TO_SCALE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);

		        	scaleSide = 'L';

		        	switchSide = 'R';
		        	driveForward(FORWARD_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	loadPowerCubeOntoSwitch(scaleSide, switchSide);
		        	driveBackward(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);

		        	switchSide = 'L';
		        	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	loadPowerCubeIntoExchange(scaleSide, switchSide);
		        	driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
	        } else {
		        	// RobotPos = Middle
		        	scaleSide = '*';
		        	switchSide = 'R';
		        	driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_RIGHT, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0), scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);

		        	switchSide = 'L';
		        	driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		        	turnLeft(scaleSide, switchSide);
		        	driveForward(LATERAL_DISTANCE_TO_LEFT, scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0), scaleSide, switchSide);
		        	turnRight(scaleSide, switchSide);
		        	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
	        }
			m_needsInitialization = false;
	    }
		super.initialize();
	 }
}

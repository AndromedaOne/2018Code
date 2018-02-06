package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPlayoffs extends AutoCommand {
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
	public void start() {
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
		super.start();
	 }


	protected void prepareToStart() {
	    char robotPos = Robot.getInitialRobotLocation();
	    char switchPlatePos = Robot.getSwitchPlatePosition();
	    char scalePlatePos = Robot.getScalePlatePosition();

	    if (robotPos == 'L') {
	        if (scalePlatePos == 'L') {
	            driveForward(FORWARD_DISTANCE_TO_SCALE);
	            turnRight();
	            driveForward(LATERAL_DISTANCE_TO_SCALE);
	            loadPowerCubeOntoScale();
	            driveBackward(LATERAL_DISTANCE_TO_SCALE);
	            turnRight();
	            driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE);
	            turnLeft();
	            driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	            turnRight();
	        } else {
	            if (switchPlatePos == 'L') {
	                driveForward(FORWARD_DISTANCE_TO_SWITCH);
	                turnRight();
	                driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	                loadPowerCubeOntoSwitch();
	                driveBackward(LATERAL_DISTANCE_TO_SWITCH);
	                turnLeft();
	                driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH);
	                turnRight();
	                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	                turnRight();
	            } else {
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnRight();
	                driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L);
	                turnRight();
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                loadPowerCubeIntoExchange();
	                driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnRight();
	                driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L);
	                turnRight();
	                driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnRight();
	                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	                turnRight();
	            }
	        }
	    } else if (robotPos == 'R') {
	        if (scalePlatePos == 'R') {
	            driveForward(FORWARD_DISTANCE_TO_SCALE);
	            turnLeft();
	            driveForward(LATERAL_DISTANCE_TO_SCALE);
	            loadPowerCubeOntoScale();
	            driveBackward(LATERAL_DISTANCE_TO_SCALE);
	            turnLeft();
	            driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE);
	            turnRight();
	            driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	            turnLeft();
	        } else {
	            if (switchPlatePos == 'R') {
	                driveForward(FORWARD_DISTANCE_TO_SWITCH);
	                turnLeft();
	                driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	                loadPowerCubeOntoSwitch();
	                driveBackward(LATERAL_DISTANCE_TO_SWITCH);
	                turnRight();
	                driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH);
	                turnLeft();
	                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	                turnLeft();
	            } else {
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnLeft();
	                driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R);
	                turnLeft();
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                loadPowerCubeIntoExchange();
	                driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnLeft();
	                driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R);
	                turnLeft();
	                driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	                turnLeft();
	                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
	                turnLeft();
	            }
	        }
	    } else {
	        if (switchPlatePos == 'R') {
	            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	            turnRight();
	            driveForward(LATERAL_DISTANCE_TO_RIGHT);
	            turnLeft();
	            driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
	            turnLeft();
	            driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	        } else {
	            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	            turnLeft();
	            driveForward(LATERAL_DISTANCE_TO_LEFT);
	            turnRight();
	            driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
	            turnRight();
	            driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	        }
	    }
	}

/*
        if (robotPos == 'L') {
            if (scalePlatePos == 'L') {
                driveForward(FORWARD_DISTANCE_TO_SCALE);
                turnRight();
                driveForward(LATERAL_DISTANCE_TO_SCALE);
                loadPowerCubeOntoScale();
                driveBackward(LATERAL_DISTANCE_TO_SCALE);
                turnRight();
                driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE);
                turnLeft();
                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                turnRight();
            } else {
                if (switchPlatePos == 'L') {
                    driveForward(FORWARD_DISTANCE_TO_SWITCH);
                    turnRight();
                    driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
                    loadPowerCubeOntoSwitch();
                    driveBackward(LATERAL_DISTANCE_TO_SWITCH);
                    turnLeft();
                    driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH);
                    turnRight();
                    driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                    turnRight();
                } else {
                    driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnRight();
                    driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L);
                    turnRight();
                    driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    loadPowerCubeIntoExchange();
                    driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnRight();
                    driveForward(LATERAL_DISTANCE_TO_EXCHANGE_L);
                    turnRight();
                    driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnRight();
                    driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                    turnRight();
                }
            }
        } else if (robotPos == 'R') {
            if (scalePlatePos == 'R') {
                driveForward(FORWARD_DISTANCE_TO_SCALE);
                turnLeft();
                driveForward(LATERAL_DISTANCE_TO_SCALE);
                loadPowerCubeOntoScale();
                driveBackward(LATERAL_DISTANCE_TO_SCALE);
                turnLeft();
                driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_TO_MIDDLE);
                turnRight();
                driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                turnLeft();
            } else {
                if (switchPlatePos == 'R') {
                    driveForward(FORWARD_DISTANCE_TO_SWITCH);
                    turnLeft();
                    driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
                    loadPowerCubeOntoSwitch();
                    driveBackward(LATERAL_DISTANCE_TO_SWITCH);
                    turnRight();
                    driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_SWITCH);
                    turnLeft();
                    driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                    turnLeft();
                } else {
                    driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnLeft();
                    driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R);
                    turnLeft();
                    driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    loadPowerCubeIntoExchange();
                    driveBackward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnLeft();
                    driveForward(LATERAL_DISTANCE_TO_EXCHANGE_R);
                    turnLeft();
                    driveForward(FORWARD_DISTANCE_TO_MIDDLE - FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                    turnLeft();
                    driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
                    turnLeft();
                }
            }
        } else {
            if (switchPlatePos == 'R') {
                driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                turnRight();
                driveForward(LATERAL_DISTANCE_TO_RIGHT);
                turnLeft();
                driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
                turnLeft();
                driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
            } else {
                driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
                turnLeft();
                driveForward(LATERAL_DISTANCE_TO_LEFT);
                turnRight();
                driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
                turnRight();
                driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
            }
        }
    }

 */
}

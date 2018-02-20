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

	protected void prepareToStart() {
	    char robotPos = Robot.getInitialRobotLocation();
	    char switchPlatePos = Robot.getSwitchPlatePosition();
	    char scalePlatePos = Robot.getScalePlatePosition();

	    if (robotPos == 'L') {
	        if (scalePlatePos == 'L') {
	            driveForward(FORWARD_DISTANCE_TO_SCALE);
	            turnRight();
	            driveForward(LATERAL_DISTANCE_TO_SCALE);
	            moveElevatorToScaleHeight();
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
	                moveElevatorToSwitchHeight();
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
	                moveElevatorToExchangeHeight();
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
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
	            moveElevatorToScaleHeight();
	            driveForward(LATERAL_DISTANCE_TO_SCALE);
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
	                moveElevatorToSwitchHeight();
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
	                moveElevatorToExchangeHeight();
	                driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
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
	            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	            turnRight();
	            driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
	            turnLeft();
	            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
	        } else {
	            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
	            turnLeft();
	            driveForward(LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE);
	            turnRight();
	            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
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

package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPlayoffs extends AutoCommand {
	static final double LATERAL_DISTANCE_TO_EXCHANGE = 31.13;
	//TODO: get the correct number from CAD
	private static final double LATERAL_DISTANCE_TO_FIRST_CUBE = 100;
	
	public AutoPlayoffs() {
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
		this(0);
	
	}
	
	public AutoPlayoffs(double delaySeconds) {
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
		if (delaySeconds > 0) {
			delay(delaySeconds);
			
		}
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
					//TODO: Load Exchange then cross auto line
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
					//TODO: Load Exchange then cross auto line
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
				driveForwardToWall(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
			} else {
				driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
				turnLeft();
				driveForward(LATERAL_DISTANCE_TO_LEFT);
				turnRight();
				driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
				turnRight();
				driveForwardToWall(AutoLoadSwitch.LATERAL_DISTANCE_TO_SWITCH);
			}
		}
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
		driveForward(FORWARD_DISTANCE_TO_SWITCH);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForward(LATERAL_DISTANCE_TO_SWITCH);
		loadPowerCubeOntoSwitch();
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

}

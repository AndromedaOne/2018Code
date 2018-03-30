package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.AutoType;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCombinedLeftRight extends AutoCommand {
	enum Position {
		NEAR_SCALE,
		FAR_SCALE,
		NEAR_SWITCH,
		DROVE_FORWARD,

	}
	protected Position m_positionAfterFirstCube;
	private final boolean m_useDelay;
	protected final AutoType m_matchType;
	private AutoFollowOn m_followOn;
	public AutoCombinedLeftRight(boolean useDelay, AutoType matchType, AutoFollowOn followOn) {
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
		m_useDelay = useDelay;
		m_matchType = matchType;
		m_followOn = followOn;
		debug("bottom of AutoQuals constructor");
	}
	public AutoCombinedLeftRight(boolean useDelay, AutoType matchType) {
		this(useDelay, matchType, null);
	}


	protected void prepareToStart() {
		debug("top of prepareToStart");
		if (m_useDelay) {
			delay(Robot.getAutonomousDelay());
		}
		addAutoCombinedCommands(m_matchType);
		if (m_followOn != null) {
			m_followOn.addCommands(this);
		}
		debug("bottom of prepareToStart");
	}

	private void loadNearSwitchPlate(char robotPos) {
		debug("top of AutoQuals loadNearSwitchPlate");
		closeJaws(false);
		parallelJawsOpenClose();


		moveElevatorToSwitchHeight();

		driveForward(FORWARD_DISTANCE_TO_SWITCH);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
		lowerIntake();
		parallelRetractExtendArms();
		delay(1.5);
		openJaws();
		parallelJawsOpenClose();
		delay(1);
		driveBackward(20);
		m_positionAfterFirstCube = Position.NEAR_SWITCH;
		debug("bottom of AutoQuals loadNearSwitchPlate");
	}

	private void loadNearScalePlate(char robotPos) {
		debug("top of AutoQuals loadNearScalePlate");

		closeJaws(false);
		parallelJawsOpenClose();


		//240
		driveForward(FORWARD_DISTANCE_TO_SCALE);//empirical measurement subject to change

		if (robotPos == 'R') {
			turnDeltaAngle(-90);
		} else {
			turnDeltaAngle(90);
		}
		driveBackward(20);
		moveElevatorToScaleHeight();

		delay(3);
		lowerIntake();
		parallelRetractExtendArms();
		delay(1);

		shootCube(2);
		m_positionAfterFirstCube = Position.NEAR_SCALE;
		debug("bottom of AutoQuals loadNearScalePlate");
	}

	private void loadFarScalePlate(char robotPos){

		closeJaws(false);
		parallelJawsOpenClose();

		driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
		if(robotPos == 'L'){
			turnRight();
		}
		else{
			turnLeft();
		}

		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES);
		if(robotPos == 'L'){
			turnLeft();
		}
		else{
			turnRight();
		}
		moveElevatorToScaleHeight();
		delay(4.5);

		driveForward(12);//eyeballed
		shootCubeParallel(2);

		lowerIntake();
		parallelRetractExtendArms();

		driveBackward(12);
		moveElevatorToGroundHeight();
		raiseIntake();
		parallelRetractExtendArms();
		turnAround();
		m_positionAfterFirstCube = Position.FAR_SCALE;

	}

	protected void addAutoCombinedCommands(AutoType matchType) {
		char robotPos = Robot.getInitialRobotLocation();
		char switchPlatePos = Robot.getSwitchPlatePosition();
		char scalePlatePos = Robot.getScalePlatePosition();
		if (matchType == AutoType.SAFE_QUALIFIERS) {
			if (switchPlatePos == robotPos) {
				loadNearSwitchPlate(robotPos);
			} else if (scalePlatePos == robotPos){
				loadNearScalePlate(robotPos);
			} else {
				driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
			}
		} else if (matchType == AutoType.QUALIFIERS) {
			if (switchPlatePos == robotPos) {
				loadNearSwitchPlate(robotPos);
			} else if (scalePlatePos == robotPos){
				loadNearScalePlate(robotPos);
			} else {
				loadFarScalePlate(robotPos);
			}
		} else if (matchType == AutoType.SAFE_PLAYOFFS) {
			addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos, false);
		} else if (matchType == AutoType.PLAYOFFS) {
			addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos, true);
		} else if (matchType == AutoType.SAFE_DOUBLE_CUBE) {
			//11 & 12 on SmartDashboard, DO NOT CROSS FIELD
			addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos, false);
			if (m_positionAfterFirstCube == Position.NEAR_SCALE) {
				if (robotPos == switchPlatePos) {
					//AutoDoubleSwitch
				} else {
					//AutoDoubleScale
				}
			} else if (m_positionAfterFirstCube == Position.NEAR_SWITCH) {
				//AutoDoubleSwitch
			} else if (m_positionAfterFirstCube == Position.DROVE_FORWARD) {
				// do nothing
			}
		} else if (matchType == AutoType.DOUBLE_CUBE) {
			addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos, false);
			if (m_positionAfterFirstCube == Position.NEAR_SCALE) {
				if (robotPos == switchPlatePos) {
					//AutoDoubleSwitch
				} else {
					//AutoDoubleScale
				}
			} else if (m_positionAfterFirstCube == Position.NEAR_SWITCH) {
				//AutoDoubleSwitch
			} else if (m_positionAfterFirstCube == Position.FAR_SCALE) {
				//AutoDoubleScale
			}
		}
	}

	protected void addPlayoffPaths(char robotPos, char switchPlatePos, char scalePlatePos, boolean crossField) {
		closeJaws(false);
		parallelJawsOpenClose();
		if (robotPos == 'L') {
			if (scalePlatePos == 'L') {
				loadNearScalePlate('L');

			} else {
				if (switchPlatePos == 'L') {

					loadNearSwitchPlate('L');

				} else {
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			}
		} else if (robotPos == 'R') {
			if (scalePlatePos == 'R') {
				loadNearScalePlate('R');
			} else {
				if (switchPlatePos == 'R') {
					loadNearSwitchPlate('R');
				} else {
					if (crossField) {
						loadFarScalePlate(robotPos);
					} else {
						//lost the switch and scale, just go forwards
						driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
					}
				}
			}
		}
	}

	protected void pickupFirstCubeFromScale(double deltaAngle) {
	    driveBackward(LATERAL_DISTANCE_TO_SCALE);
		turnToCompassHeading(180);
		moveElevatorToGroundHeight();
		driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
		turnToCompassHeading(deltaAngle);
		driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
		turnToCompassHeading(180);
		driveForwardToWall(24.99);
		closeJaws(true);
	}

	protected void pickupFirstCubeFromLeftSwitchPlate() {
		driveBackward(CLEARANCE_TO_TURN);
		turnLeft();
		driveForward(80);
		turnRight();
		driveForward(40 + CLEARANCE_TO_TURN);
		turnRight();
		moveElevatorToGroundHeight();
		driveForward(33);
		closeJaws(true);
	}

	protected void pickupFarCubeFromLeftSwitchPlate() {
		driveBackward(CLEARANCE_TO_TURN);
		turnLeft();
		driveForward(80);
		turnRight();
		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES + CLEARANCE_TO_TURN + 40);
		turnRight();
		moveElevatorToGroundHeight();
		driveForward(33);
		closeJaws(true);
	}

	protected void pickupFirstCubeFromRightSwitchPlate() {
		driveBackward(CLEARANCE_TO_TURN);
		turnRight();
		driveForward(80);
		turnLeft();
		driveForward(40 + CLEARANCE_TO_TURN);
		turnLeft();
		moveElevatorToGroundHeight();
		driveForward(33);
		closeJaws(true);
	}

}

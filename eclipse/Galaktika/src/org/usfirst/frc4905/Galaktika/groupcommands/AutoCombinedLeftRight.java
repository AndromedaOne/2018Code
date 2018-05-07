package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.RunIntakeIn;
import org.usfirst.frc4905.Galaktika.commands.RunIntakeInAuto;
import org.usfirst.frc4905.Galaktika.commands.TimedShootCube;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.AutoType;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCombinedLeftRight extends AutoCommand {
	enum Position {
		CORNER_SCALE,
		FAR_SCALE,
		NEAR_SWITCH,
		DROVE_FORWARD,

	}
	
	public enum PathOption {
		NORMAL,
		IGNORE_SWITCH,
		IGNORE_FAR_SCALE,
	}
	
	enum PositionAfterSecondCube {
		SCALE,
		SWITCH,
	}
	
	protected Position m_positionAfterFirstCube;
	protected PositionAfterSecondCube m_positionAfterSecondCube;
	private final boolean m_useDelay;
	protected final AutoType m_matchType;
	private PathOption m_pathOption;
	public AutoCombinedLeftRight(boolean useDelay, AutoType matchType, PathOption pathOption) {
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
		// would require.S
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		debug("top of AutoQuals constructor");
		m_useDelay = useDelay;
		m_matchType = matchType;
		m_pathOption = pathOption;
		debug("bottom of AutoQuals constructor");
	}

	public AutoCombinedLeftRight(boolean useDelay, AutoType matchType) {
		this(useDelay, matchType, PathOption.NORMAL);
	}

	protected void prepareToStart() {
		debug("top of prepareToStart");
		if (m_useDelay) {
			delay(Robot.getAutonomousDelay());
		}
		addAutoCombinedCommands(m_matchType);
		debug("bottom of prepareToStart");
	}

	private void loadNearSwitchPlate(char robotPos) {
		debug("top of AutoQuals loadNearSwitchPlate");
		closeJaws(false);
		driveForward(FORWARD_DISTANCE_TO_SWITCH);
		moveElevatorToSwitchHeight();
		if (robotPos == 'R') {
			turnToCompassHeading(-90);
		} else {
			turnToCompassHeading(90);
		}
		driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
		lowerIntake();
		delay(0.5);
		shootCube(0.3);
		raiseIntake();
		delay(0.5);
		driveBackward(LATERAL_DISTANCE_TO_SWITCH);
		m_positionAfterFirstCube = Position.NEAR_SWITCH;
		debug("bottom of AutoQuals loadNearSwitchPlate");
	}

	private void loadNearScalePlate(char robotPos) {
		debug("top of AutoQuals loadNearScalePlate");

		closeJaws(false);
		parallelJawsOpenClose();


		//240
		driveForward(FORWARD_DISTANCE_TO_SCALE_FORTY_FIVE_DEGREE, false);

		if (robotPos == 'R') {
			turnDeltaAngle(-65);//18.4
		} else {
			turnDeltaAngle(65);
		}
		driveBackward(18);//changed after we hit the scale in auto that time
		moveElevatorToScaleHeightSequential();
		addSequential(new TimedShootCube());
		raiseIntake();
		delay(0.8);
		moveElevatorToGroundHeightParallel();
		m_positionAfterFirstCube = Position.CORNER_SCALE;
		debug("bottom of AutoQuals loadNearScalePlate");
	}

	private void loadFarScalePlate(char robotPos){

		closeJaws(false);
		parallelJawsOpenClose();

		driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE - 16);
		if(robotPos == 'L'){
			turnToCompassHeading(90);
		}
		else{
			turnToCompassHeading(270);
		}
		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES + 16);
		if(robotPos == 'L'){
			turnToCompassHeading(315);
		}
		else{
			turnToCompassHeading(45);
		}
		
		driveForward(20);//TODO Needs tuning
		moveElevatorToScaleHeightSequential();
		addSequential(new TimedShootCube());
		raiseIntake();
		moveElevatorToGroundHeight();
		driveBackward(24);
		turnToCompassHeading(180);
		lowerIntake();
		delay(1);
		openJaws();
		
		m_positionAfterFirstCube = Position.FAR_SCALE;

	}

		protected void addAutoCombinedCommands(AutoType autoType) {
		char robotPos = Robot.getInitialRobotLocation();
		char switchPlatePos = Robot.getSwitchPlatePosition();
		char scalePlatePos = Robot.getScalePlatePosition();
		switch (autoType) {
			case QUALIFIERS:
				if (switchPlatePos == robotPos && m_pathOption != PathOption.IGNORE_SWITCH) {
					loadNearSwitchPlate(robotPos);
				} else if (scalePlatePos == robotPos){
					loadNearScalePlate(robotPos);
					addDoubleCubeCommands(robotPos, switchPlatePos, scalePlatePos);
					//driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				} else if(m_pathOption != PathOption.IGNORE_FAR_SCALE) {
					loadFarScalePlate(robotPos);
				} else {
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			break;
			case PLAYOFFS:
				addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos);
			break;
			case DOUBLE_CUBE_FIELD:
				if(robotPos == switchPlatePos || robotPos == scalePlatePos) {
					addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos);
					addDoubleCubeCommands(robotPos, switchPlatePos, scalePlatePos);
				}
				else {
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
				
			break;
			case DOUBLE_CUBE_CROSS:
				//TODO: do nothing FOR NOW
			break;
			case TRIPLE_CUBE:
				addPlayoffPaths(robotPos, switchPlatePos, scalePlatePos);
				addDoubleCubeCommands(robotPos, switchPlatePos, scalePlatePos);
				addTripleCubeCommands(robotPos, switchPlatePos, scalePlatePos);
		}


	}
	protected void addPlayoffPaths(char robotPos, char switchPlatePos, char scalePlatePos) {
		closeJaws(false);
		parallelJawsOpenClose();
		
		if (robotPos == 'L') {
			if (scalePlatePos == 'L') {
				loadNearScalePlate('L');

			} else {
				if (switchPlatePos == 'L') {

					loadNearSwitchPlate('L');

				} else if (m_pathOption != PathOption.IGNORE_FAR_SCALE) {
					loadFarScalePlate(robotPos);
				} else {
					System.out.println("Driving forward because haven't got either");
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			}
		} else if (robotPos == 'R') {
			if (scalePlatePos == 'R') {
				loadNearScalePlate('R');
			} else {
				if (switchPlatePos == 'R') {
					loadNearSwitchPlate('R');

				} else if (m_pathOption != PathOption.IGNORE_FAR_SCALE) {
					loadFarScalePlate(robotPos);
				} else {
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			}
		}
	}

	protected void pickupFirstCubeFromScale(double deltaAngle) {
	    driveForward(LATERAL_DISTANCE_FROM_SCALE);
		turnToCompassHeading(180);
		moveElevatorToGroundHeight();
		driveForward(FORWARD_DISTANCE_TO_SCALE - FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
		turnToCompassHeading(deltaAngle);
		driveForward(LATERAL_DISTANCE_TO_FIRST_CUBE);
		turnToCompassHeading(180);
		driveForwardToWall(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE - FORWARD_DISTANCE_TO_CUBES);
		closeJaws(true);
	}
	
	protected void pickupFirstCubeFromCornerScale(char robotPos, double deltaAngle) {
		
		
		if (robotPos == 'R') {
			turnToCompassHeading(215);
		}else {
			turnToCompassHeading(135);
		}
		lowerIntake();
		delay(0.4);
		openJaws();
		driveForward(65);
		turnToCompassHeading(180);
			
		
		/*Possible alternative code for retrieving first cube
		turnDeltaAngle(-deltaAngle * 2);
		driveForward(DIAGONAL_DISTANCE_TO_FIRST_CUBE);
		turnDeltaAngle(deltaAngle);
		*/
		
		runIntakeInAuto();
		driveForward(12);
		closeJaws(true);
	}

	protected void pickupFirstCubeFromLeftSwitchPlate() {
		moveElevatorToGroundHeightParallel();
		turnToCompassHeading(0);
		driveForward(56);
		turnToCompassHeading(90);
		driveForward(34);
		lowerIntake();
		turnToCompassHeading(180);
		openJaws();
		runIntakeInAuto();
		driveForward(15);
		closeJaws(false);
		
	}

	protected void pickupFarCubeFromLeftSwitchPlate() {
		turnLeft();
		driveForward(65);
		turnRight();
		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES + 35);
		turnRight();
		moveElevatorToGroundHeight();
		runIntakeInAuto();
		driveForward(28);
		closeJaws(true);
	}

	protected void pickupFirstCubeFromRightSwitchPlate() {
		
		moveElevatorToGroundHeightParallel();
		turnToCompassHeading(0);
		driveForward(65);
		turnToCompassHeading(270);
		driveForward(42);
		lowerIntake();
		turnToCompassHeading(180);
		openJaws();
		driveForward(12);
		runIntakeInAuto();
		closeJaws(false);
	}

	protected void addDoubleCubeCommands(char robotPos, char switchPlatePos, char scalePlatePos) {
		switch (m_positionAfterFirstCube) {
			case CORNER_SCALE:
				if (robotPos == switchPlatePos) {
					addDoubleSwitchCommands(robotPos);
				} else {
					addDoubleScaleCommands(robotPos);
				}
			break;
			case FAR_SCALE:
				addDoubleScaleCommands(robotPos);
			case NEAR_SWITCH:
				addDoubleSwitchCommands(robotPos);
			break;
			case DROVE_FORWARD:
				System.out.println("Double cube not supported after driving forward.");
			break;
		}
	}

	protected void addDoubleSwitchCommands(char robotPos) {
		double deltaAngle;

	    //Only for when robotPos is 'L' or 'R'
	    switch (m_positionAfterFirstCube) {
		    case CORNER_SCALE:
		    	if (robotPos == 'L') {
		    		deltaAngle = -45;
		    	} else {
		    		deltaAngle = 45;
		    	}
			    pickupFirstCubeFromCornerScale(robotPos, deltaAngle);
			    dropCubeOntoSwitch();
	        break;
		    case FAR_SCALE:
		    	if (robotPos == 'L') {
		    		//Dummy numbers
		    		deltaAngle = 90;
		    		System.out.println("Done left far side Scale :D");
		    	} else {
		    		deltaAngle = -90;
		    		System.out.println("Done right far side Scale :D");
		    	}
		    	pickupFirstCubeFromScale(deltaAngle);
		    	dropCubeOntoSwitch();
	        break;
		    case NEAR_SWITCH:
		    	if (robotPos == 'L') {
		    		pickupFirstCubeFromLeftSwitchPlate();
		    	} else {
		    		pickupFirstCubeFromRightSwitchPlate();
		    	}
	    		dropCubeOntoSwitch();
	    		break;
		    case DROVE_FORWARD:
		    	System.out.println("Double cube not supported after driving forward.");
	    }
	}
	
	protected void addDoubleScaleCommands(char robotPos) {
		double deltaAngle;


		//Only for when robotPos is 'L' or 'R'
		switch (m_positionAfterFirstCube) {
	    case CORNER_SCALE:
	    	if (robotPos == 'L') {
	    		deltaAngle = -45;
	    	} else {
	    		deltaAngle = 45;
	    	}
		    pickupFirstCubeFromCornerScale(robotPos, deltaAngle);
//		    driveBackward(FORWARD_DISTANCE_TO_SCALE_PLATE_FROM_CUBE / 2);
//		    moveElevatorToScaleHeight();
//	    	turnToCompassHeading(0);
//	    	driveForward(FORWARD_DISTANCE_TO_SCALE_PLATE_FROM_CUBE / 2);
//	    	raiseIntake();
//	    	addSequential(new TimedShootCube());
//	    	m_positionAfterSecondCube = PositionAfterSecondCube.SCALE;
	    	
        break;
	    case FAR_SCALE:
	    	if (robotPos == 'L') {
	    		//Dummy numbers
	    		deltaAngle = 90;
	    		System.out.println("Done left far side Scale :D");
	    	} else {
	    		deltaAngle = -90;
	        System.out.println("Done right far side Scale :D");
	    	}
	    	pickupFirstCubeFromScale(deltaAngle);
	    	moveElevatorToScaleHeight();
	    	turnToCompassHeading(0);
	    	driveForward(AutoCommand.FORWARD_DISTANCE_TO_SIDE_OF_SCALE);
	    	openJaws();
	    	
        break;
	    case NEAR_SWITCH:
	    	if (robotPos == 'L') {
	    		pickupFirstCubeFromLeftSwitchPlate();
	    	} else {
	    		pickupFirstCubeFromRightSwitchPlate();
	    	}
	    	driveBackward(52);
	    	turnAround();
    		// Could be moved v
	    	moveElevatorToScaleHeight();
	    	driveForward(52);
	    	openJaws();
    		break;
	    case DROVE_FORWARD:
	    	System.out.println("Double cube not supported after driving forward.");
		}
	}
	
	protected void addTripleCubeCommands(char robotPos, char switchPlatePos, char scalePlatePos) {
		if (m_positionAfterSecondCube == PositionAfterSecondCube.SCALE) {
			addTripleScaleCommands(robotPos);
		} else if (m_positionAfterSecondCube == PositionAfterSecondCube.SWITCH) {
			addTripleSwitchCommands(robotPos);
		}
	}
	
	protected void addTripleSwitchCommands(char robotPos) {
        char switchPlatePos = Robot.getSwitchPlatePosition();
		if (robotPos == 'L' && switchPlatePos == 'L') {
			driveBackward(CLEARANCE_TO_TURN);
			turnLeft();
			driveForward(13.4);
			turnRight();
			driveForward(13 + CLEARANCE_TO_TURN);
			closeJaws(true);
			dropCubeOntoSwitch();
			System.out.println("Done :D");
	    } else if (robotPos == 'R' && switchPlatePos == 'R') {
	    	driveBackward(CLEARANCE_TO_TURN);
	    	turnRight();
	    	driveForward(13.4);
	    	turnLeft();
	    	driveForward(13 + CLEARANCE_TO_TURN);
	    	closeJaws(true);
	    	dropCubeOntoSwitch();
	    	System.out.println("Done :D");
	    }
	}
	
	protected void addTripleScaleCommands(char robotPos) {
        char switchPlatePos = Robot.getSwitchPlatePosition();
        char scalePlatePos = Robot.getScalePlatePosition();
        //Only for when robotPos is 'L' or 'R'
        if (robotPos == 'L' && switchPlatePos == 'L' && scalePlatePos == 'L') {
        	driveBackward(53);
        	moveElevatorToGroundHeight();
        	turnAround();
        	driveForward(53);
        	closeJaws(true);
        	moveElevatorToSwitchHeightSequential();
        	openJaws();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && switchPlatePos == 'R' && scalePlatePos == 'R') {
        	driveBackward(53);
        	moveElevatorToGroundHeight();
        	turnAround();
        	driveForward(53);
        	closeJaws(true);
        	moveElevatorToSwitchHeightSequential();
        	openJaws();
            System.out.println("Done :D");
        } else {
        	driveBackward(53);
    		moveElevatorToGroundHeight();
    		//turnAround();
    		if (scalePlatePos == 'L') {
    			turnRight();
    			driveForward(LATERAL_DISTANCE_BETWEEN_CUBE_POSITIONS);
    			turnRight();
    		}
    		driveForward(53);
    		closeJaws(true);
    		driveBackward(53);
    		turnAround();
    		moveElevatorToScaleHeight();
    		driveForward(53);
    		openJaws();
    		
        }
	}

	protected void dropCubeOntoSwitch() {
		moveElevatorToSwitchHeightSequential();
		lowerIntake();
		driveForwardToWall(11);
		shootCube(2.0);
		
	}

}

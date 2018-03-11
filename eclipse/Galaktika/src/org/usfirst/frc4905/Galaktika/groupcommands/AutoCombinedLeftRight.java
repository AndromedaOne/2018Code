package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MatchType;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCombinedLeftRight extends AutoCommand {
	private final boolean m_useDelay;
	protected final MatchType m_matchType;
	public AutoCombinedLeftRight(boolean useDelay, MatchType matchType) {
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
	    debug("bottom of AutoQuals constructor");
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
        lowerIntake();

		moveElevatorToSwitchHeight();

		driveForward(FORWARD_DISTANCE_TO_SWITCH);
		if (robotPos == 'R') {
			turnLeft();
		} else {
			turnRight();
		}
		driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
		openJaws();

		debug("bottom of AutoQuals loadNearSwitchPlate");
	}

	private void loadNearScalePlate(char robotPos) {
		debug("top of AutoQuals loadNearScalePlate");

        closeJaws(false);
        lowerIntake();

		moveElevatorToScaleHeight();
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);

		if (robotPos == 'R') {
			turnDeltaAngle(-4.8);
		} else {
			turnDeltaAngle(4.8);
		}

		driveForward(177.6);

		openJaws();

		debug("bottom of AutoQuals loadNearScalePlate");
	}

	protected void addAutoCombinedCommands(MatchType matchType) {
		char robotPos = Robot.getInitialRobotLocation();
	    char switchPlatePos = Robot.getSwitchPlatePosition();
	    char scalePlatePos = Robot.getScalePlatePosition();
	    if (matchType == MatchType.QUALIFIERS) {
		    	if (switchPlatePos == robotPos) {
		    		loadNearSwitchPlate(robotPos);
		    	} else if (scalePlatePos == robotPos){
		    		loadNearScalePlate(robotPos);
		    	} else {
		    		crossAutoLine(robotPos);
		    	}
	    } else {
		    closeJaws(false);
		    lowerIntake();

			if (robotPos == 'L') {
				if (scalePlatePos == 'L') {

					moveElevatorToScaleHeight();
		            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		            turnDeltaAngle(-4.8);

		            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
		            openJaws();

				} else {
					if (switchPlatePos == 'L') {

						moveElevatorToSwitchHeight();
						driveForward(FORWARD_DISTANCE_TO_SWITCH);
						turnRight();
						driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
						openJaws();

					} else {
						driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
					}
				}
			} else if (robotPos == 'R') {
				if (scalePlatePos == 'R') {
					moveElevatorToScaleHeight();
		            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		            turnDeltaAngle(4.8);

		            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
		            openJaws();
				} else {
					if (switchPlatePos == 'R') {

						moveElevatorToSwitchHeight();
						driveForward(FORWARD_DISTANCE_TO_SWITCH);
						turnLeft();
						driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
						openJaws();

					} else {
						//lost the switch and scale, just go forwards
						driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
					}
				}
			}
	    }
	}

	protected void pickupFirstCubeFromScale(double deltaAngle) {
		turnDeltaAngle(deltaAngle);
		moveElevatorToGroundHeight();
		driveForward(53);
		turnToCompassHeading(180);
		driveForwardToWall(40);
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

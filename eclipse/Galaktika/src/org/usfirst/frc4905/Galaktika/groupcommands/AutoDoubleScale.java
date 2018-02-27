package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MatchType;

public class AutoDoubleScale extends AutoCombinedLeftRight {

	public AutoDoubleScale(boolean useDelay, MatchType matchType) {
		super(useDelay, matchType);
	}

    protected void prepareToStart() {
        super.prepareToStart();
        addAutoDoubleScaleCommands(m_matchType);
    }

    protected void addAutoDoubleScaleCommands(MatchType matchType) {
	    addAutoCombinedCommands(matchType);
		char robotPos = Robot.getInitialRobotLocation();
	    char scalePlatePos = Robot.getScalePlatePosition();
	    char switchPlatePos = Robot.getSwitchPlatePosition();

	    //Only for when robotPos is 'L' or 'R'
	    if (robotPos == 'L' && scalePlatePos == 'L') {
	    	//Dummy numbers
	    		double deltaAngle = 17;
	        pickupFirstCubeFromScale(deltaAngle);
	        moveElevatorToScaleHeight();
	        turnToCompassHeading(0);
	        driveForward(50);
	        openJaws();
	        System.out.println("Done left near side Scale :D");
	    } else if (robotPos == 'R' && scalePlatePos == 'R') {
	    		double deltaAngle = -17;
	    		pickupFirstCubeFromScale(deltaAngle);
	        moveElevatorToScaleHeight();
	        turnToCompassHeading(0);
	        driveForward(50);
	        openJaws();
	        System.out.println("Done right near side Scale :D");
	    } else if (robotPos == 'L' && scalePlatePos == 'R') {
	    		if (switchPlatePos == 'L') {
	    			pickupFarCubeFromLeftSwitchPlate();
	    		} else {
		    		driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE - FORWARD_DISTANCE_TO_AUTO_LINE);
		    		turnRight();
		    		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES);
		    		turnLeft();
		    		moveElevatorToScaleHeight();
		    		driveForward(71.18);
		    		openJaws();
		    		driveBackward(52);
		    		moveElevatorToGroundHeight();
		    		turnAround();
		    		driveForwardToWall(52);
		    		closeJaws(true);
	    		}
	    		driveBackward(52);
	    		turnAround();
	    		// Could be moved v
	    		moveElevatorToScaleHeight();
	    		driveForward(52);
	    		openJaws();
	    		System.out.println("Done left far side Scale :D");
	    } else {
	        	// RobotPos = R and ScalePos = L
	        	driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
	    		turnLeft();
	    		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES);
	    		turnRight();
	    		moveElevatorToScaleHeight();
	    		driveForward(71.18);
	    		openJaws();
	    		driveBackward(52);
	    		moveElevatorToGroundHeight();
	    		turnAround();
	    		driveForwardToWall(52);
	    		closeJaws(true);
	    		driveBackward(52);
	    		turnAround();
	    		// Could be moved v
	    		moveElevatorToScaleHeight();
	    		driveForward(52);
	    		openJaws();
	    		System.out.println("Done right far side Scale :D");
	    }
	}


}

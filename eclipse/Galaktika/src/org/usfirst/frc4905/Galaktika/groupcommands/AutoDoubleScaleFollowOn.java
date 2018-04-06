package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoDoubleScaleFollowOn extends AutoFollowOn {

	@Override
	public void addCommands(AutoCombinedLeftRight autoCommand) {
		char robotPos = Robot.getInitialRobotLocation();
		double deltaAngle;


    //Only for when robotPos is 'L' or 'R'
	    switch (autoCommand.m_positionAfterFirstCube) {
		    case NEAR_SCALE:
		    	if (robotPos == 'L') {
		    		//Dummy numbers
		    		deltaAngle = -90;
		    		System.out.println("Done left near side Scale :D");
		    	} else {
		    		deltaAngle = 90;
		        System.out.println("Done right near side Scale :D");
		    	}
		    	autoCommand.pickupFirstCubeFromScale(deltaAngle);
		    	autoCommand.moveElevatorToScaleHeight();
		    	autoCommand.turnToCompassHeading(0);
		    	autoCommand.driveForward(AutoCommand.FORWARD_DISTANCE_TO_SIDE_OF_SCALE);
		    	autoCommand.openJaws();
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
		    	autoCommand.pickupFirstCubeFromScale(deltaAngle);
		    	autoCommand.moveElevatorToScaleHeight();
		    	autoCommand.turnToCompassHeading(0);
		    	autoCommand.driveForward(AutoCommand.FORWARD_DISTANCE_TO_SIDE_OF_SCALE);
		    	autoCommand.openJaws();
	        break;
		    case NEAR_SWITCH:
		    	if (robotPos == 'L') {
		    		autoCommand.pickupFirstCubeFromLeftSwitchPlate();
		    	} else {
		    		autoCommand.pickupFirstCubeFromRightSwitchPlate();
		    	}
		    	autoCommand.driveBackward(52);
		    	autoCommand.turnAround();
	    		// Could be moved v
		    	autoCommand.moveElevatorToScaleHeight();
		    	autoCommand.driveForward(52);
		    	autoCommand.openJaws();
	    		break;
		    case DROVE_FORWARD:
		    	System.out.println("Double cube not supported after driving forward.");
	    }
	}
}

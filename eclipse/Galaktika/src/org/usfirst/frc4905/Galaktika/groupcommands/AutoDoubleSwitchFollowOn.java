package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoDoubleSwitchFollowOn extends AutoFollowOn {

	@Override
	public void addCommands(AutoCombinedLeftRight autoCommand) {
		char robotPos = Robot.getInitialRobotLocation();
		double deltaAngle;

	    //Only for when robotPos is 'L' or 'R'
	    switch (autoCommand.m_positionAfterFirstCube) {
		    case CORNER_SCALE:
		    	if (robotPos == 'L') {
		    		//Dummy numbers
		    		deltaAngle = -90;
		    		System.out.println("Done left near side Scale :D");
		    	} else {
		    		deltaAngle = 90;
		        System.out.println("Done right near side Scale :D");
		    	}
		    	autoCommand.pickupFirstCubeFromScale(deltaAngle);
		    dropCubeOntoSwitch(autoCommand);
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
		    dropCubeOntoSwitch(autoCommand);
	        break;
		    case NEAR_SWITCH:
		    	if (robotPos == 'L') {
		    		autoCommand.pickupFirstCubeFromLeftSwitchPlate();
		    	} else {
		    		autoCommand.pickupFirstCubeFromRightSwitchPlate();
		    	}
	    		dropCubeOntoSwitch(autoCommand);
	    		break;
		    case DROVE_FORWARD:
		    	System.out.println("Double cube not supported after driving forward.");
	    }
	}

	protected void dropCubeOntoSwitch(AutoCombinedLeftRight autoCommand) {
		autoCommand.moveElevatorToSwitchHeightSequential();
		autoCommand.driveForwardToWall(13);
		autoCommand.openJaws();
	}
}

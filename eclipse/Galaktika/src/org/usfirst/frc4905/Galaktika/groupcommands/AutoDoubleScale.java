package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.AutoType;


public class AutoDoubleScale extends AutoCombinedLeftRight {

	public AutoDoubleScale(boolean useDelay, AutoType matchType) {
		super(useDelay, matchType);
	}

    protected void prepareToStart() {
        super.prepareToStart();
        addAutoDoubleScaleCommands(m_matchType);
    }

    protected void addAutoDoubleScaleCommands(AutoType matchType) {
	    addAutoCombinedCommands(matchType);
		char robotPos = Robot.getInitialRobotLocation();
    		double deltaAngle;


	    //Only for when robotPos is 'L' or 'R'
	    switch (m_positionAfterFirstCube) {
		    case NEAR_SCALE:
		    	if (robotPos == 'L') {
		    		//Dummy numbers
		    		deltaAngle = -90;
		    		System.out.println("Done left near side Scale :D");
		    	} else {
		    		deltaAngle = 90;
		        System.out.println("Done right near side Scale :D");
		    	}
	    		pickupFirstCubeFromScale(deltaAngle);
	        moveElevatorToScaleHeight();
	        turnToCompassHeading(0);
	        driveForward(49.99);
	        openJaws();
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
	        driveForward(49.99);
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


}

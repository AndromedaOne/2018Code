package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.AutoType;

public class AutoDoubleSwitch extends AutoCombinedLeftRight {

	public AutoDoubleSwitch(boolean useDelay, AutoType matchType) {
	    super(useDelay, matchType);
	}

    protected void prepareToStart() {
        super.prepareToStart();
        addAutoDoubleSwitchCommands(m_matchType);
    }

    protected void addAutoDoubleSwitchCommands(AutoType matchType) {
		char robotPos = Robot.getInitialRobotLocation();
		double deltaAngle;

	    addAutoCombinedCommands(matchType);
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

	protected void dropCubeOntoSwitch() {
		moveElevatorToSwitchHeightSequential();
		driveForwardToWall(13);
		openJaws();
	}
}

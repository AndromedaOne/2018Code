package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MatchType;

public class AutoDoubleSwitch extends AutoCombinedLeftRight {

	public AutoDoubleSwitch(boolean useDelay, MatchType matchType) {
	    super(useDelay, matchType);
	}

    protected void prepareToStart() {
        super.prepareToStart();
        addAutoDoubleSwitchCommands(m_matchType);
    }

    protected void addAutoDoubleSwitchCommands(MatchType matchType) {
		char robotPos = Robot.getInitialRobotLocation();
	    char switchPlatePos = Robot.getSwitchPlatePosition();
	    char scalePlatePos = Robot.getScalePlatePosition();
	    addAutoCombinedCommands(matchType);
	    //Only for when robotPos is 'L' or 'R'
	    if (robotPos == 'L' && switchPlatePos == 'L' &&
	    		(matchType == MatchType.QUALIFIERS || scalePlatePos == 'R')) {
	    		if ((matchType == MatchType.QUALIFIERS && switchPlatePos == 'L') ||
	    				(matchType == MatchType.PLAYOFFS && scalePlatePos == 'R')) {
	    			pickupFirstCubeFromLeftSwitchPlate();
		    	} else {
		    		pickupFirstCubeFromScale(17);
		    	}
	    		moveElevatorToSwitchHeightSequential();
	    		driveForwardToWall(13);
	    		openJaws();
	        System.out.println("Done left side :D");
	    } else if (robotPos == 'R' && switchPlatePos == 'R' &&
	    		(matchType == MatchType.QUALIFIERS || scalePlatePos == 'L')) {
	    		if ((matchType == MatchType.QUALIFIERS && switchPlatePos == 'R') ||
	    			(matchType == MatchType.PLAYOFFS && scalePlatePos == 'L')) {
		    		pickupFirstCubeFromRightSwitchPlate();
	    		} else {
	    			pickupFirstCubeFromScale(-17);
	    		}
	    		moveElevatorToSwitchHeightSequential();
	    		driveForwardToWall(13);
	    		openJaws();
			System.out.println("Done right side :D");
	    }
	}
}

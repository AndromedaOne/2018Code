package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MatchType;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoDoubleScale;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoDoubleSwitch;

public class AutoScaleSwitchTwoCube extends AutoCombinedLeftRight {

	public AutoScaleSwitchTwoCube(boolean useDelay, MatchType matchType) {
		super(useDelay, matchType);
	}

    protected void prepareToStart() {
        super.prepareToStart();
        addAutoScaleSwitchTwoCubeCommands(m_matchType);
    }

    protected void addAutoScaleSwitchTwoCubeCommands(MatchType matchType) {
	    addAutoCombinedCommands(matchType);
		char robotPos = Robot.getInitialRobotLocation();
	    char scalePlatePos = Robot.getScalePlatePosition();
	    char switchPlatePos = Robot.getSwitchPlatePosition();

	    //Only for when robotPos is 'L' or 'R'
	    if (robotPos == 'R') {
		    	if (switchPlatePos == 'R' && scalePlatePos == 'R') {
		    		// path 13.1
		    	} else if (switchPlatePos == 'R' && scalePlatePos == 'L') {
		    		// path 13.2
		    	} else if (scalePlatePos == 'R') {
		    		// path 13.3
		    	} else if (switchPlatePos == 'L' && scalePlatePos == 'L') {
		    		// path 13.4
		    	}
	    }
	    if (robotPos == 'R') {
		    	if (switchPlatePos == 'R' && scalePlatePos == 'R') {
		    		// path 14.1
		    	} else if (switchPlatePos == 'R' && scalePlatePos == 'L') {
		    		// path 14.2
		    	} else if (scalePlatePos == 'R') {
		    		// path 14.3
		    	} else if (switchPlatePos == 'L' && scalePlatePos == 'L') {
		    		// path 14.4
		    	}
	    }
    }


}

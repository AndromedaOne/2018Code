package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoTripleFromSwitch extends AutoDoubleSwitch {

	public AutoTripleFromSwitch(boolean useDelay, AutoType matchType) {
	    super(useDelay, matchType);
	}

    protected void prepareToStart() {
    		super.prepareToStart();
    		// TRIPLE SWITCH = NOT LIKELY TO HAVE TIME
        char robotPos = Robot.getInitialRobotLocation();
        char switchPlatePos = Robot.getSwitchPlatePosition();
        //Only for when robotPos is 'L' or 'R'

        if (robotPos == 'L' && switchPlatePos == 'L') {
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnLeft();
	    		driveForward(15.4);
	    		turnRight();
	    		driveForward(13);
	    		closeJaws(true);
	    		dropCubeOntoSwitch();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && switchPlatePos == 'R') {
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnRight();
	    		driveForward(15.4);
	    		turnLeft();
	    		driveForward(13);
	    		closeJaws(true);
	    		dropCubeOntoSwitch();
    			System.out.println("Done :D");
        }
    }
}

package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoTripleSwitch extends AutoCommand {

	boolean m_useDelay;

	public AutoTripleSwitch(boolean useDelay) {
	    if (useDelay) {
	        m_useDelay = useDelay;
        }
	}

	public AutoTripleSwitch() {
		this(false);

	}

    protected void prepareToStart() {
    		// TRIPLE SWITCH = NOT LIKELY TO HAVE TIME
        char robotPos = Robot.getInitialRobotLocation();
        char switchPlatePos = Robot.getSwitchPlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
        autoDoubleSwitch(false);
        //Only for when robotPos is 'L' or 'R'

        if (robotPos == 'L' && switchPlatePos == 'L') {
	    		setJawsShouldBeOpenState(true);
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnLeft();
	    		driveForward(15.4);
	    		turnRight();
	    		driveForward(13);
	    		setJawsShouldBeOpenState(false);
	    		moveElevatorToSwitchHeightSequential();
	    		setJawsShouldBeOpenState(true);
            System.out.println("Done :D");
        } else if (robotPos == 'R' && switchPlatePos == 'R') {
	    		setJawsShouldBeOpenState(true);
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnRight();
	    		driveForward(15.4);
	    		turnLeft();
	    		driveForward(13);
	    		setJawsShouldBeOpenState(false);
	    		moveElevatorToSwitchHeightSequential();
	    		setJawsShouldBeOpenState(true);
    			System.out.println("Done :D");
        }
    }
}

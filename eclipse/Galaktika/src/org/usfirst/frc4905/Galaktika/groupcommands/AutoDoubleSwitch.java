package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoDoubleSwitch extends AutoCommand {

	boolean m_useDelay;

	public AutoDoubleSwitch(boolean useDelay) {
	    if (useDelay) {
	        m_useDelay = useDelay;
        }
	}

	public AutoDoubleSwitch() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        char switchPlatePos = Robot.getSwitchPlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
        parallelJawsOpenClose();
        parallelRetractExtendArms();
        setJawsShouldBeOpenState(false);
        setRetractorShouldBeUp(false);
        //Only for when robotPos is 'L' or 'R'
        if (robotPos == 'L' && switchPlatePos == 'L') {
	        	setJawsShouldBeOpenState(false);
	    		setRetractorShouldBeUp(false);

	    		moveElevatorToSwitchHeight();

	    		driveForward(FORWARD_DISTANCE_TO_SWITCH);
	    			turnRight();
	    		driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	    		setJawsShouldBeOpenState(true);
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnLeft();
	    		driveForward(80);
	    		turnRight();
	    		driveForward(40);
	    		turnRight();
	    		moveElevatorToGroundHeight();
	    		driveForward(33);
	    		setJawsShouldBeOpenState(false);
	    		moveElevatorToSwitchHeightSequential();
	    		driveForwardToWall(13);
	    		setJawsShouldBeOpenState(true);
            System.out.println("Done :D");
        } else if (robotPos == 'R' && switchPlatePos == 'R') {
	        	setJawsShouldBeOpenState(false);
	    		setRetractorShouldBeUp(false);
	    		moveElevatorToSwitchHeight();
	    		driveForward(FORWARD_DISTANCE_TO_SWITCH);
	    		turnLeft();
	    		driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
	    		setJawsShouldBeOpenState(true);
	    		driveBackward(CLEARANCE_TO_TURN);
	    		turnRight();
	    		driveForward(80);
	    		turnLeft();
	    		driveForward(40);
	    		turnLeft();
	    		moveElevatorToGroundHeight();
	    		driveForward(33);
	    		setJawsShouldBeOpenState(false);
	    		moveElevatorToSwitchHeightSequential();
	    		driveForwardToWall(13);
	    		setJawsShouldBeOpenState(true);
    			System.out.println("Done :D");
        }
    }
}

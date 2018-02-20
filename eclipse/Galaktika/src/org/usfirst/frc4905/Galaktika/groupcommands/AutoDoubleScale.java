package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoDoubleScale extends AutoCommand {

	public AutoDoubleScale(boolean useDelay) {
	    if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
	}

	public AutoDoubleScale() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        char scalePlatePos = Robot.getScalePlatePosition();
        parallelJawsOpenClose();
        parallelRetractExtendArms();
        setJawsShouldBeOpenState(false);
        setRetractorShouldBeUp(false);

        if (robotPos == 'L' && scalePlatePos == 'L') {
        	//robot position should only be L or R
        	moveElevatorToScaleHeight();
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
            turnDeltaAngle(-4.8);

            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
            setJawsShouldBeOpenState(true);
            turnDeltaAngle(17);
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnAround();
            driveForward(53);

            setJawsShouldBeOpenState(false);
            delay(0.5);//make sure jaws close, could be changed
            moveElevatorToScaleHeight();
            driveBackward(53);
            turnAround();
            driveForward(53);
            setJawsShouldBeOpenState(true);
            System.out.println("Done :D");



        } else if (robotPos == 'R' && scalePlatePos == 'R') {
        	//robot position should only be L or R
        	moveElevatorToScaleHeight();
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
            turnDeltaAngle(4.8);

            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
            setJawsShouldBeOpenState(true);
            turnDeltaAngle(-17);
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnAround();
            driveForward(53);

            setJawsShouldBeOpenState(false);
            delay(0.5);//make sure jaws close, could be changed
            moveElevatorToScaleHeight();
            driveBackward(53);
            turnAround();
            driveForward(53);
            setJawsShouldBeOpenState(true);
            System.out.println("Done :D");
        } else if (robotPos == 'L' && scalePlatePos == 'R') {
        		driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
        		turnRight();
        		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES);
        		turnLeft();
        		moveElevatorToScaleHeight();
        		driveForward(71.18);
        		setJawsShouldBeOpenState(true);
        		driveBackward(52);
        		moveElevatorToGroundHeight();
        		turnAround();
        		driveForwardToWall(52);
        		setJawsShouldBeOpenState(false);
        		delay(0.5);
        		driveBackward(52);
        		turnAround();
        		// Could be moved v
        		moveElevatorToScaleHeight();
        		driveForward(52);
        		setJawsShouldBeOpenState(true);
        } else {
	        	// RobotPos = R and Scale Pos = L
	        	driveForward(FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE);
	    		turnLeft();
	    		driveForward(LATERAL_DISTANCE_TO_SCALE_PLATES);
	    		turnRight();
	    		moveElevatorToScaleHeight();
	    		driveForward(71.18);
	    		setJawsShouldBeOpenState(true);
	    		driveBackward(52);
	    		moveElevatorToGroundHeight();
	    		turnAround();
	    		driveForwardToWall(52);
	    		setJawsShouldBeOpenState(false);
	    		delay(0.5);
	    		driveBackward(52);
	    		turnAround();
	    		// Could be moved v
	    		moveElevatorToScaleHeight();
	    		driveForward(52);
	    		setJawsShouldBeOpenState(true);
        }
    }
}
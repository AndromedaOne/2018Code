package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT = 120.3;

    public AutoMiddleLoadSwitch(boolean useDelay) {
        if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
    }

    protected void prepareToStart() {
        char platePos = Robot.getSwitchPlatePosition();

        if (platePos == 'R') {
            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnRight();
            driveForward(LATERAL_DISTANCE_TO_RIGHT);
            turnLeft();
            driveForward(FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
            turnLeft();
            driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
        } else {
            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnLeft();
            driveForward(LATERAL_DISTANCE_TO_LEFT);
            turnRight();
            driveForward(FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
            turnRight();
            driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
        }
    }

	public void start() {
	    if (m_needsInitialization) {
		    	char robotPos = Robot.getInitialRobotLocation();
		    	char scaleSide = '*';
		    	char switchSide = 'R';
		    	driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		    	turnRight(scaleSide, switchSide);
		    	driveForward(LATERAL_DISTANCE_TO_RIGHT, scaleSide, switchSide);
		    	turnLeft(scaleSide, switchSide);
		    	driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0), scaleSide, switchSide);
		    	turnLeft(scaleSide, switchSide);
		    	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
		    	char platePos = 'L';
		    	driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0, scaleSide, switchSide);
		    	turnLeft(scaleSide, switchSide);
		    	driveForward(LATERAL_DISTANCE_TO_LEFT, scaleSide, switchSide);
		    	turnRight(scaleSide, switchSide);
		    	driveForward(AutoCommand.FORWARD_DISTANCE_TO_SWITCH - (AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0), scaleSide, switchSide);
		    	turnRight(scaleSide, switchSide);
		    	driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH, scaleSide, switchSide);
			m_needsInitialization = false;
	    }
		super.start();
	 }
}

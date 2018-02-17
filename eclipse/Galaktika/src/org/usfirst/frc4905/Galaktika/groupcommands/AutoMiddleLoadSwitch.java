package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT_PLATE = 100;
    private static final double LATERAL_DISTANCE_TO_RIGHT_PLATE = 100;

    public AutoMiddleLoadSwitch(boolean useDelay) {
        if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
    }

    protected void prepareToStart() {
        char platePos = Robot.getSwitchPlatePosition();
        closeArmsInAuto(5);
        extendIntakeAuto();
        resetElevatorInAuto();
        moveElevatorToSwitchHeight();
        driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
        if (platePos == 'R') {
            turnRight();
            driveForward(LATERAL_DISTANCE_TO_RIGHT_PLATE);
            turnLeft();
        } else {
            turnLeft();
            driveForward(LATERAL_DISTANCE_TO_LEFT_PLATE);
            turnRight();
        }
        driveForwardToWall();
        openArmsInAuto();
    }

}

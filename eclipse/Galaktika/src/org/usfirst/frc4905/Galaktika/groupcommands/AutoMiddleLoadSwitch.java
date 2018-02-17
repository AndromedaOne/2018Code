package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE = 120.3;

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
            driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
            turnLeft();
            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
        } else {
            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnLeft();
            driveForward(LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE);
            turnRight();
            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
        }
    }

}

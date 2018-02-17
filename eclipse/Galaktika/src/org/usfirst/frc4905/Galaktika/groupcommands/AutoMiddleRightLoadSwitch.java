package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.AutoJawsClose;
import org.usfirst.frc4905.Galaktika.commands.AutoJawsOpen;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleRightLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT_PLATE = 100;
    private static final double LATERAL_DISTANCE_TO_RIGHT_PLATE = 100;

    public AutoMiddleRightLoadSwitch(boolean useDelay) {
        if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
    }

    protected void prepareToStart() {
        char platePos = Robot.getSwitchPlatePosition();
        closeArmsInAuto();
        extendIntakeAuto();
        resetElevatorInAuto();
        moveElevatorToSwitchHeight();
        if (platePos == 'L') {
            driveForward(AutoCrossTheLine.FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnLeft();
            driveForward(LATERAL_DISTANCE_TO_LEFT_PLATE + LATERAL_DISTANCE_TO_RIGHT_PLATE);
            turnRight();
        }
        driveForwardToWall();
        openArmsInAuto();
       
    }

}

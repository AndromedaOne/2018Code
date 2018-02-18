package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.AutoJawsClose;
import org.usfirst.frc4905.Galaktika.commands.AutoJawsOpen;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleRightLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT_PLATE = 100;
    private static final double LATERAL_DISTANCE_TO_RIGHT_PLATE = 100;
    private static final double LATERAL_DISTANCE_BETWEEN_PLATES = 78;

    public AutoMiddleRightLoadSwitch(boolean useDelay) {
        if (useDelay) {
            delay(Robot.getAutonomousDelay());
        }
    }

    protected void prepareToStart() {
        char platePos = Robot.getSwitchPlatePosition();
        closeArmsInAuto(5);
        extendIntakeAuto();
        //resetElevatorInAuto();
        //moveElevatorToSwitchHeight();
        //if (platePos == 'L') {
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnLeft();
            driveForward(LATERAL_DISTANCE_BETWEEN_PLATES);
            turnRight();
            //driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
            
        //} else {
            //driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES);
        //}
        openArmsInAuto();
       
    }

}

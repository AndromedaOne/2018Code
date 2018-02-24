package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMiddleRightLoadSwitch extends AutoCommand {

    private static final double LATERAL_DISTANCE_TO_LEFT_PLATE = 100;
    private static final double LATERAL_DISTANCE_TO_RIGHT_PLATE = 100;
    private static final double LATERAL_DISTANCE_BETWEEN_PLATES = 78;
	boolean m_useDelay;

    public AutoMiddleRightLoadSwitch(boolean useDelay) {
        if (useDelay) {
	        m_useDelay = useDelay;
        }
    }

    protected void prepareToStart() {
        char platePos = Robot.getSwitchPlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
    		}
        parallelJawsOpenClose();
        parallelRetractExtendArms();
        setJawsShouldBeOpenState(false);
        setRetractorShouldBeUp(false);
        

        if (platePos == 'L') {
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 2.0);
            turnLeft();
            driveForward(LATERAL_DISTANCE_BETWEEN_PLATES);
            turnRight();
            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));

        } else {
            driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES);
        }

        setJawsShouldBeOpenState(true);


        /*
		if (platePos == 'R') {

			driveBackward(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			moveElevatorToGroundHeight();
			turnLeft();
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
			turnRight();

		} else {
			driveBackward(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			moveElevatorToGroundHeight();
			turnRight();
			driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
			turnLeft();

		}

*/



    }

}

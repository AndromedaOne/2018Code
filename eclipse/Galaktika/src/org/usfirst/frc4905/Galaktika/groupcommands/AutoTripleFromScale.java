package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoTripleFromScale extends AutoDoubleScale {

	public AutoTripleFromScale(boolean useDelay, AutoType matchType) {
	    super(useDelay, matchType);
	}

    protected void prepareToStart() {
    		super.prepareToStart();
        char robotPos = Robot.getInitialRobotLocation();
        char scalePlatePos = Robot.getScalePlatePosition();
        //Only for when robotPos is 'L' or 'R'
        if (robotPos == 'L' && scalePlatePos == 'L') {
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnDeltaAngle(175);
            driveForward(53);
            closeJaws(true);
            moveElevatorToSwitchHeightSequential();
            openJaws();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && scalePlatePos == 'R') {
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnDeltaAngle(-175);
            driveForward(53);
            closeJaws(true);
            moveElevatorToSwitchHeightSequential();
            openJaws();
            System.out.println("Done :D");
        }
    }
}

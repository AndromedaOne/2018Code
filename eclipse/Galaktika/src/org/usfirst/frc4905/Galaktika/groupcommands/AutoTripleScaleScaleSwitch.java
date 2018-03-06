package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoTripleScaleScaleSwitch extends AutoCommand {

	boolean m_useDelay;

	public AutoTripleScaleScaleSwitch(boolean useDelay) {
	    if (useDelay) {
	        m_useDelay = useDelay;
        }
	}

	public AutoTripleScaleScaleSwitch() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        char scalePlatePos = Robot.getScalePlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
        autoDoubleScale(false);
        //Only for when robotPos is 'L' or 'R'
        if (robotPos == 'L' && scalePlatePos == 'L') {
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnDeltaAngle(175);
            driveForward(53);
            setJawsShouldBeOpenState(false);
            moveElevatorToSwitchHeightSequential();
            setJawsShouldBeOpenState(true);
            System.out.println("Done :D");
        } else if (robotPos == 'R' && scalePlatePos == 'R') {
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnDeltaAngle(-175);
            driveForward(53);
            setJawsShouldBeOpenState(false);
            moveElevatorToSwitchHeightSequential();
            setJawsShouldBeOpenState(true);
            System.out.println("Done :D");
        }
    }
}

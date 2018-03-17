package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoScaleThenSwitch extends AutoCombinedLeftRight {

	boolean m_useDelay;

	public AutoScaleThenSwitch(boolean useDelay) {

	    // MATCH TYPE DOES NOT MATTER
	    super(useDelay, MatchType.QUALIFIERS);
        m_useDelay = useDelay;
	}

	public AutoScaleThenSwitch() {
		this(false);

	}

    protected void prepareToStart() {
        char robotPos = Robot.getInitialRobotLocation();
        char switchPlatePos = Robot.getSwitchPlatePosition();
        char scalePlatePos = Robot.getScalePlatePosition();
        if (m_useDelay) {
        		delay(Robot.getAutonomousDelay());
        }
        closeJaws(false);
        lowerIntake();

        //Only for when robotPos is 'L' or 'R'
         if (robotPos == 'L' && scalePlatePos == 'L' && switchPlatePos == 'L') {
            // pickup cube from scale position
            pickupFirstCubeFromScale(16.99);
            moveElevatorToSwitchHeightSequential();
            driveForwardToWall(12);
            openJaws();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && scalePlatePos == 'R' && switchPlatePos == 'R') {
            // pickup cube from scale position
            pickupFirstCubeFromScale(-16.99);
            moveElevatorToSwitchHeightSequential();
            driveForwardToWall(12);
            openJaws();
        }
    }
}

package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;

public class AutoScaleThenSwitch extends AutoCommand {

	boolean m_useDelay;

	public AutoScaleThenSwitch(boolean useDelay) {
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
        	moveElevatorToScaleHeight();
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
            turnDeltaAngle(-4.8);
            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
            openJaws();
            turnDeltaAngle(17);
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnAround();
            driveForward(53);
            closeJaws(true);
            moveElevatorToSwitchHeightSequential();
            openJaws();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && scalePlatePos == 'R' && switchPlatePos == 'R') {
        		moveElevatorToScaleHeight();
            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
            turnDeltaAngle(4.8);
            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
            openJaws();
            turnDeltaAngle(-17);
            driveBackward(53);
            moveElevatorToGroundHeight();
            turnAround();
            driveForward(53);
            closeJaws(true);
            moveElevatorToSwitchHeightSequential();
            openJaws();
        }
    }
}

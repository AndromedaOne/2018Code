package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.TimedShootCube;

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
		raiseIntake();
		closeJaws(false);
		parallelJawsOpenClose();
		
		if (platePos == 'L') {
			System.out.println("Plate on the left!");
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE / 3);
			delay(0.4);
			turnLeft();
			delay(0.4);
			driveForward(LATERAL_DISTANCE_BETWEEN_PLATES + 12);
			delay(0.4);
			turnDeltaAngle(95);
			delay(0.4);
			moveElevatorToSwitchHeight();
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 3) - 12.0);
			
			/*lowerIntake();
			parallelRetractExtendArms();
			delay(1);
			
			openJaws();*/
			
			addSequential(new TimedShootCube());
			parallelJawsOpenClose();
			

		} else {
			moveElevatorToSwitchHeight();
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES);
			lowerIntake();
			delay(1);
			
			openJaws();
			parallelJawsOpenClose();
			
		}
		delay(2);
		driveBackward(30);

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
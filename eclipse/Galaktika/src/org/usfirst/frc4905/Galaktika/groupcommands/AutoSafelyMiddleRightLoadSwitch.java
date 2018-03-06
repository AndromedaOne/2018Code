package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSafelyMiddleRightLoadSwitch extends AutoCommand {

	private static final double LATERAL_DISTANCE_TO_LEFT_PLATE = 100;
	private static final double LATERAL_DISTANCE_TO_RIGHT_PLATE = 100;
	private static final double LATERAL_DISTANCE_BETWEEN_PLATES = 78;
	boolean m_useDelay;

	public AutoSafelyMiddleRightLoadSwitch(boolean useDelay) {
		if (useDelay) {
			m_useDelay = useDelay;
		}
	}

	protected void prepareToStart() {
		if (m_useDelay) {
			delay(Robot.getAutonomousDelay());
		}
		raiseIntake();
		closeJaws(false);



		// TO BE USED ONLY IF GYRO IS NOT FUNCTIONAL IN ANY WAY/SHAPE/FORM
        char platePos = Robot.getSwitchPlatePosition();
		if (platePos == 'L') {
			System.out.println("We're ont he left!");
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES);
		} else {
			moveElevatorToSwitchHeight();
			driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES);
			lowerIntake();
			delay(1);

			openJaws();

		}


	}

}

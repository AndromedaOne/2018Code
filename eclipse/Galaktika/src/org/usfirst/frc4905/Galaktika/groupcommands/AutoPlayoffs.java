package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.commands.SetShouldJawsBeOpenStateCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPlayoffs extends AutoCommand {
	boolean m_useDelay;

	public AutoPlayoffs(boolean useDelay) {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		if (useDelay) {
	        m_useDelay = useDelay;
		}
	}

	protected void prepareToStart() {
		char robotPos = Robot.getInitialRobotLocation();
		char switchPlatePos = Robot.getSwitchPlatePosition();
		char scalePlatePos = Robot.getScalePlatePosition();
        if (m_useDelay) {
    			delay(Robot.getAutonomousDelay());
        }
		parallelJawsOpenClose();
		parallelRetractExtendArms();
		setJawsShouldBeOpenState(false);
		setRetractorShouldBeUp(false);




		if (robotPos == 'L') {
			if (scalePlatePos == 'L') {


				moveElevatorToScaleHeight();
	            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
	            turnDeltaAngle(-4.8);

	            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
	            setJawsShouldBeOpenState(true);

			} else {
				if (switchPlatePos == 'L') {

					moveElevatorToSwitchHeight();
					driveForward(FORWARD_DISTANCE_TO_SWITCH);
					turnRight();
					driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
					setJawsShouldBeOpenState(true);

				} else {
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			}
		} else if (robotPos == 'R') {
			if (scalePlatePos == 'R') {
				moveElevatorToScaleHeight();
	            driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
	            turnDeltaAngle(4.8);

	            driveForward(177.6);//assumed distance from pythagorean theorem to approach plate
	            setJawsShouldBeOpenState(true);
			} else {
				if (switchPlatePos == 'R') {

					moveElevatorToSwitchHeight();
					driveForward(FORWARD_DISTANCE_TO_SWITCH);
					turnLeft();
					driveForwardToWall(LATERAL_DISTANCE_TO_SWITCH);
					setJawsShouldBeOpenState(true);

				} else {
					//lost the switch and scale, just go forwards
					driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
				}
			}
		} else {
			//we are in the middle

			moveElevatorToSwitchHeight();
			driveForward(FORWARD_DISTANCE_TO_AUTO_LINE/2);

			if (switchPlatePos == 'R') {
				turnRight();
				driveForward(LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE);
				turnLeft();
				driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));


			} else {
				turnLeft();
				driveForward(LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE);
				turnRight();
				driveForwardToWall(FORWARD_DISTANCE_TO_SWITCH_PLATES - (FORWARD_DISTANCE_TO_AUTO_LINE / 2.0));
			}

			setJawsShouldBeOpenState(true);
		}
	}


}

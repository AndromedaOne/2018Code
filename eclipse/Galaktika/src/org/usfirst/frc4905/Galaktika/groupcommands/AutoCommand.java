package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.commands.GyroPIDTurnDeltaAngle;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingEncoderPID;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MoveToWall;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public abstract class AutoCommand extends CommandGroup {
@Deprecated // ("Use the new Ultrasonic command")
    public class MoveToWall extends Command {

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

	}

private boolean m_preparedToStart;
protected double LATERAL_DISTANCE_TO_RIGHT;

	public AutoCommand() {
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
    }

	protected static final double FORWARD_DISTANCE_TO_SCALE = 304.25;
	protected static final double LATERAL_DISTANCE_TO_SCALE = 15.08;
	protected static final double FORWARD_DISTANCE_TO_MIDDLE = 212;
	protected static final double LATERAL_DISTANCE_BETWEEN_PATHS = 236.6;

	protected void turnRight() {
		addSequential(new GyroPIDTurnDeltaAngle(90));

	}

	protected void turnLeft() {
		addSequential(new GyroPIDTurnDeltaAngle(-90));

	}

	protected void turnAround() {
		addSequential(new GyroPIDTurnDeltaAngle(180));
	}

	protected void driveForward(double forwardDistanceInches) {
		addSequential(new MoveUsingEncoderPID(forwardDistanceInches));
	}

	protected void driveForwardToWall(double forwardDistanceInches) {
		driveForward(forwardDistanceInches);
		addSequential(new MoveToWall());
	}

    public void start() {
        if (m_preparedToStart) {
            prepareToStart();
            m_preparedToStart = false;
        }
        super.start();
     }

    private void prepareToStart() {
        // TODO Auto-generated method stub

    }
}

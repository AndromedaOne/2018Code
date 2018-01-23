package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.commands.AutoDriveForward;
import org.usfirst.frc4905.Galaktika.commands.AutoTurnLeft;
import org.usfirst.frc4905.Galaktika.commands.AutoTurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public abstract class AutoCommand extends CommandGroup {

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
	protected static final double FORWARD_DISTANCE_TO_MIDDLE = 0;
	protected static final double LATERAL_DISTANCE_BETWEEN_PATHS = 0;

	protected void turnRight() {
		addSequential(new AutoTurnRight());
		
	}

	protected void turnLeft() {
		addSequential(new AutoTurnLeft());
		
	}

	protected void driveForward(double forwardDistanceInches) {
		addSequential(new AutoDriveForward(forwardDistanceInches));
	}
}

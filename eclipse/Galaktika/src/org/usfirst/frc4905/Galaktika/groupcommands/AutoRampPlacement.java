package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.MoveForwardUntilScaleBump;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingFrontUltrasonic;
import org.usfirst.frc4905.Galaktika.commands.TurnToCompassHeading;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRampPlacement extends CommandGroup {

    public AutoRampPlacement() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new TurnToCompassHeading(90));
    	addSequential(new MoveForwardUntilScaleBump());
    	addSequential(new TurnToCompassHeading(0));
    	addSequential(new MoveUsingFrontUltrasonic(5));
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
}

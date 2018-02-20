package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.commands.GyroPIDTurnDeltaAngle;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingEncoderPID;
import org.usfirst.frc4905.Galaktika.commands.TurnToCompassHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MPTesting extends CommandGroup {

    public MPTesting() {
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
    	
    		addSequential(new MoveUsingEncoderPID(20.0));
    		addSequential(new GyroPIDTurnDeltaAngle(-45));
    		addSequential(new MoveUsingEncoderPID(50.0));
    		addSequential(new GyroPIDTurnDeltaAngle(45.0));
    		addSequential(new MoveUsingEncoderPID(5.0));
    }
}

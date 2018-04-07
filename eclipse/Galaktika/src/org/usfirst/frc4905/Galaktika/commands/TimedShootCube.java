package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TimedShootCube extends CommandGroup {
	
	private double m_delay = 0.3;
    public TimedShootCube() {
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
    	
    		addSequential(new SetIntakeShouldBeUpCommand(false));
    		addParallel(new RetractExtendArms());
    		addSequential(new Delay(m_delay));
    		addSequential(new ShootCubeInAuto(1.2));
    		addSequential(new ReinitializeIntakeSystems());
    		
    }
}

package org.usfirst.frc4905.Galaktika.commands;


import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import kinematics.InvalidDimentionException;
import kinematics.Path;
import kinematics.Point;

/**
 *
 */
public class MotionProfilingTest extends CommandGroup {

    public MotionProfilingTest() {
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
    	
    		Path myFirstPath = new Path();
		try {
			Robot.kinematics.addPointToPath(myFirstPath, new Point(100000));
		} catch (InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CHECK TO SEE IF THE GRAPH'S CURRENT POSITION REPRESENTATION IS BEHIND THE PROJECTED POSITION.
		Robot.kinematics.createTrajectory(myFirstPath, (12460), (4.97)*Math.pow(10, 7), 63460.0*1000000.0);
		

		addSequential(new MoveUsingEncoderMotionProfiling(myFirstPath));
    }
}
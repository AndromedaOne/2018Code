package org.usfirst.frc4905.Galaktika.commands;


import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import kinematics.Kinematics.InvalidDimentionException;
import kinematics.Kinematics.Path;
import kinematics.Kinematics.Point;

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
    	
    	Path myFirstPath = Robot.kinematics.new Path();
		try {
			Robot.kinematics.addPointToPath(myFirstPath, Robot.kinematics.new Point(12));
		} catch (InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CHECK TO SEE IF THE GRAPH'S CURRENT POSITION REPRESENTATION IS BEHIND THE PROJECTED POSITION.
		Robot.kinematics.createTrajectory(myFirstPath, (200), (100000),0.25);
		

		addSequential(new MoveUsingEncoderMotionProfiling(myFirstPath));
    }
}

package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestDriveToWall extends Command {
    private static final double BUMPER_WIDTH = 3;

    public TestDriveToWall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		debug("top of constructor");
	    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		debug("Initializing");
    		Robot.driveTrain.intializeUltrasonicPIDFront(BUMPER_WIDTH);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.doneUltrasonicFrontPID();
    }

    // Called once after isFinished returns true
    protected void end() {
		debug("Done");
		Robot.driveTrain.stop();
        Robot.driveTrain.stopUltrasonicFrontPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
	    	end();
    }

    protected void debug(String information) {
		System.out.println("In TestDriveToWall.java " +
				information);
		System.out.flush();
    }
}

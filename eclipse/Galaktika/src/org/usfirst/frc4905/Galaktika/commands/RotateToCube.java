package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateToCube extends Command {
	
	private DigitalInput cubeDetectorLeft = RobotMap.cubeDetectorLeft;
    private DigitalInput cubeDetectorRight = RobotMap.cubeDetectorRight;
	private static final double TURNSPEED = 0.5;
    public RotateToCube() {
    	requires(Robot.driveTrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (cubeDetectorLeft.get() && !cubeDetectorRight.get()){
    		Robot.driveTrain.move(0, -TURNSPEED);
    	} else if (cubeDetectorRight.get() && !cubeDetectorLeft.get()) {
    		Robot.driveTrain.move(0, TURNSPEED);
    	} else {
    		Robot.driveTrain.move(0, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cubeDetectorRight.get() == cubeDetectorLeft.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

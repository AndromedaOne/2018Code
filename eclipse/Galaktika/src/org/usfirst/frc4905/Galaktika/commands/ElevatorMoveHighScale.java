package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorMoveHighScale extends Command {

    public ElevatorMoveHighScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.elevator.initializeEncoderPID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.elevator.enableEncoderPID(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {	
        return true;
    	//return Robot.elevator.isDoneEncoderPID();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.elevator.disableEncoderPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Robot.elevator.disableEncoderPID();
    }
}

package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevatorUsingEncoderPID extends Command {

	private double m_setpoint = 0;

    public MoveElevatorUsingEncoderPID(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    	m_setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	Robot.elevator.initializeEncoderPID();
    	Robot.elevator.enableEncoderPID(m_setpoint);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return Robot.elevator.isDoneEncoderPID();
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.elevator.disableEncoderPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingEncoderPID extends Command {
	
	private double m_setpoint = 0;

    public MoveUsingEncoderPID(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_setpoint = setpoint;
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.initializeEncoderPID();
    	Robot.driveTrain.enableEncoderPID(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.isDoneEncoderPID();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.move(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

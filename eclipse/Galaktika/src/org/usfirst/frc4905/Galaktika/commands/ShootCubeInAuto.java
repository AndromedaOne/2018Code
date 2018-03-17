package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ShootCubeInAuto extends TimedCommand {

    public ShootCubeInAuto(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
       	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.ejectIntake(1);
    }

    // Called once after timeout
    protected void end() {
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoJawsClose extends TimedCommand {

    public AutoJawsClose(double timeout) {
        super(timeout);
        requires(Robot.jaws);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.intake.runIntake(RunIntakeIn.kIntakeSpeed);
		Robot.jaws.closeJaws();
    }

    // Called once after timeout
    protected void end() {
		Robot.intake.runIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

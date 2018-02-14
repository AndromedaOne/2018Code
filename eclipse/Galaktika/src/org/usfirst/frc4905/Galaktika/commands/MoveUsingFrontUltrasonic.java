package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingFrontUltrasonic extends Command {

	private double m_distanceToDriveTo = 0;
	private final boolean useMotionProfilng = true;

	public MoveUsingFrontUltrasonic(double distanceToDriveTo) {
		requires(Robot.driveTrain);
		m_distanceToDriveTo = distanceToDriveTo;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (!useMotionProfilng) {
			Robot.driveTrain.intializeUltrasonicPIDFront(m_distanceToDriveTo);
		} else {
			Robot.driveTrain.initializeUltrasonicMP();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!useMotionProfilng) {
			return Robot.driveTrain.doneUltrasonicFrontPID();
		} else {
			return Robot.driveTrain.isDoneUltrasonicMP();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
		if (!useMotionProfilng) {
			Robot.driveTrain.stopUltrasonicFrontPID();
		} else {
			Robot.driveTrain.disableUltrasonicMP();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

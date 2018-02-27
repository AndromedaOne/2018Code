package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroPIDTurnDeltaAngle extends Command {

	protected double m_deltaAngleToTurn = 0.0;
	private final boolean useMotionProfilng = true;
	private double m_initialEncoderValue;


	public GyroPIDTurnDeltaAngle() {
		this(90);
	}

	public GyroPIDTurnDeltaAngle(double deltaAngleToTurn) {
		debug("top of constructor");
		m_deltaAngleToTurn = deltaAngleToTurn;
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		debug("Initializing");
		if (!useMotionProfilng) {
			Robot.driveTrain.initGyroPIDDeltaAngle();
			Robot.driveTrain.enableGyroPID(m_deltaAngleToTurn);
		} else {
			m_initialEncoderValue = Robot.driveTrain.getEncoderPosition();
			Robot.driveTrain.initializeGyroMP();
			Robot.driveTrain.enableGyroMP(m_deltaAngleToTurn);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (!useMotionProfilng) {
			return Robot.driveTrain.gyroPIDIsDone();
		} else {
			return Robot.driveTrain.isDoneGyroMP();
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		double finalEncderPosition = Robot.driveTrain.getEncoderPosition();
		debug("Delta Pos:" + (finalEncderPosition - m_initialEncoderValue));
		debug("Done");
		Robot.driveTrain.stop();
		if (!useMotionProfilng) {
			Robot.driveTrain.stopGyroPid();
		} else {
			Robot.driveTrain.disableGyroMP();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	protected void debug(String information) {
		System.out
				.println("In GyroPIDTurnDeltaAngle.java Angle in Degrees = " + m_deltaAngleToTurn + " " + information);
	}
}

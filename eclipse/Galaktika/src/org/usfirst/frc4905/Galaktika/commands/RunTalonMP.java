package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import com.ctre.phoenix.motion.SetValueMotionProfile;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.command.Command;
import kinematics.GeneratedMotionProfile;
import kinematics.MotionProfilingControllerCanTalon;

/**
 *
 */
public class RunTalonMP extends Command {

	MotionProfilingControllerCanTalon m_example = new MotionProfilingControllerCanTalon(
			Robot.driveTrain.getLeftTopTalon());
	double m_setpoint;
	double m_initialEncoderPos;
	GeneratedMotionProfile m_generatedMotionProfile;

	public RunTalonMP(double setpoint) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		m_setpoint = setpoint;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		m_example.reset();
		m_example.startMotionProfile();
		m_generatedMotionProfile = m_example.generateTrajectory(m_setpoint);
		m_initialEncoderPos = Robot.driveTrain.getEncoderPosition();
		Robot.driveTrain.pauseDifferentialDrive();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SetValueMotionProfile setOutput = m_example.getSetValue();
		Robot.driveTrain.setLeftTopTalonMPMode(setOutput.value);
		m_example.control(m_generatedMotionProfile);

		Trace.getInstance().addTrace(true, "MotionProfilingData",
				new TracePair("ActualVelocity", Robot.driveTrain.getTalonVelocity()),
				new TracePair("ProjectedVelocity",
						Robot.driveTrain.getLeftTopTalon().getActiveTrajectoryVelocity() + 0.0),
				new TracePair("ActualPosition", Robot.driveTrain.getEncoderPosition() - m_initialEncoderPos),
				new TracePair("ProjectedPosition",
						(Robot.driveTrain.getLeftTopTalon().getActiveTrajectoryPosition() + 0.0) / 1000),
				// new TracePair("VelocityError", m_currentTrajectoryPoint.m_currentVelocity -
				// velocity),
				// new TracePair("PositionError", (m_currentTrajectoryPoint.m_position -
				// deltaPosition) * 10),
				new TracePair("getMotorOutputPercent", Robot.driveTrain.getLeftTopTalon().getMotorOutputPercent() * 1000),
				new TracePair("setOutputValue", setOutput.value * 1000.0));

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.gyroCorrectMove(0.0, 0.0, 1.0, false);
		m_example.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

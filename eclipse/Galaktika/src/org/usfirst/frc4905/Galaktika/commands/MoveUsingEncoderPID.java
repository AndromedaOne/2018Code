package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import Utilities.Tracing.Trace;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingEncoderPID extends Command {

	private double m_setpoint = 0;
	private final boolean useMotionProfilng = false;
	private boolean m_useDelay = true;
	
	public MoveUsingEncoderPID(double setpointInches, boolean useDelay) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
    		debug("top of constructor, inches = " + setpointInches);
		requires(Robot.driveTrain);
	    	
	    	m_setpoint = setpointInches * DriveTrain.ENCODER_TICKS_PER_INCH;
	    	m_useDelay = useDelay;
	}
	
	public MoveUsingEncoderPID(double setPointInches) {
		this(setPointInches, true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//debug("Initializing");
		System.out.println("In the Init command");
		if (!useMotionProfilng) {
			Robot.driveTrain.initializeEncoderPID(m_useDelay);
			Robot.driveTrain.enableEncoderPID(m_setpoint);
		} else {
			Robot.driveTrain.initializeEncoderMP();
			Robot.driveTrain.enableEncoderMP(m_setpoint);
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
			return Robot.driveTrain.isDoneEncoderPID();
		} else {
			return Robot.driveTrain.isDoneEncoderMP();
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		debug("Done");
		Robot.driveTrain.move(0, 0);
		if (!useMotionProfilng) {
			Robot.driveTrain.disableEncoderPID();
		} else {
			Robot.driveTrain.disableEncoderMP();
		}
		Trace.getInstance().flushTraceFiles();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		System.out.println("CALLING INTERUPTED!!!!!!!");
		end();
	}

	protected void debug(String information) {
		System.out.println("In MoveUsingEncoderPID.java " );
		System.out.flush();
	}
}

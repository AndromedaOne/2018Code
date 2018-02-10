package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveUsingFrontUltrasonic extends Command {
	
	private double m_distanceToDriveTo=0;

    public MoveUsingFrontUltrasonic(double distanceToDriveTo) {
    	requires(Robot.driveTrain);
		m_distanceToDriveTo = distanceToDriveTo;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.driveTrain.intializeUltrasonicPIDFront(m_distanceToDriveTo);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Trace.getInstance().addTrace("GyroTrace",
				new TracePair("ZVelocity", (double) RobotMap.navX.getAHRS().getVelocityZ()),
				new TracePair("ZPos", (double) RobotMap.navX.getAHRS().getDisplacementZ()),
				new TracePair("ZAccel", (double) RobotMap.navX.getAHRS().getWorldLinearAccelZ()));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return Robot.driveTrain.doneUltrasonicFrontPID();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.driveTrain.stop();
		Robot.driveTrain.stopUltrasonicFrontPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

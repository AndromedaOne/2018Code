package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroPIDTurnDeltaAngle extends Command {

	protected double m_deltaAngleToTurn = 0.0;

	public GyroPIDTurnDeltaAngle() {
		requires(Robot.driveTrain);
		m_deltaAngleToTurn = 90;
	}

    public GyroPIDTurnDeltaAngle(double deltaAngleToTurn) {
	    	m_deltaAngleToTurn = deltaAngleToTurn;
	    	requires(Robot.driveTrain);
    }


    // Called just before this Command runs the first time
    protected void initialize() {
		debug("Initializing");
	    	Robot.driveTrain.initGyroPIDDeltaAngle();
	    	Robot.driveTrain.enableGyroPID(m_deltaAngleToTurn);
    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.gyroPIDIsDone();
    }

    // Called once after isFinished returns true
    protected void end() {
		debug("Done");
	    	Robot.driveTrain.stop();
	    	Robot.driveTrain.stopGyroPid();
    }

	// Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }

    protected void debug(String information) {
		System.out.println("In GyroPIDTurnDeltaAngle.java Angle in Degrees = " +
    m_deltaAngleToTurn + " " +
    information);
	}
}

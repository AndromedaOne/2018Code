package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.Command;




public class MoveDownAndShoot extends Command {

	private final long kWaitToShootDelay = 300;
	private final long kRunMotorTime  = 1000;
	private long m_timeToShoot = 0;
	private long m_timeToRunIntake = 0;
	
    public MoveDownAndShoot() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.retractor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	long currentTime = System.currentTimeMillis();
    	m_timeToShoot = currentTime + kWaitToShootDelay;
    	m_timeToRunIntake = m_timeToShoot + kRunMotorTime;
    	Robot.retractor.setShouldIntakeBeUpBoolean(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	long currentTime = System.currentTimeMillis();
    	Robot.retractor.setIntakeToCorrectState();
    	if((currentTime > m_timeToShoot) && (currentTime < m_timeToRunIntake)) {
    		Robot.intake.ejectIntake(0.75);
    	} else if(currentTime > m_timeToRunIntake) {
    		Robot.intake.stopIntake();
    	} 
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

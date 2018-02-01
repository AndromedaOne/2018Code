package org.usfirst.frc4905.Galaktika.commands;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Delay extends Command {
    double m_delaySeconds;
    long m_timeoutMillis;

    public Delay() {
        this(5);
    }

    public Delay(double delaySeconds) {
            m_delaySeconds = delaySeconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
            long now = System.currentTimeMillis();
            m_timeoutMillis = now + (long) (m_delaySeconds*1000);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
            long now = System.currentTimeMillis() ;
        try {
            TimeUnit.MILLISECONDS.sleep(m_timeoutMillis - now);
        } catch (InterruptedException e) {
            // continue to check isFinished
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
            long now = System.currentTimeMillis();
            if (m_timeoutMillis > now) {
                return false;
            }
            else {
                return true;
            }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

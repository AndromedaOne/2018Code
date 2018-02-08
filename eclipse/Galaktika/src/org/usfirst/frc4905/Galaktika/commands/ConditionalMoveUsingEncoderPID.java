package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConditionalMoveUsingEncoderPID extends MoveUsingEncoderPID {

	private final char m_scalePosition;
	private final char m_switchPosition;
	private boolean m_disabled;

    public ConditionalMoveUsingEncoderPID(double setpointInches,
    		char scalePosition, char switchPosition) {
    		super(setpointInches);
    		m_scalePosition = scalePosition;
    		m_switchPosition = switchPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		debug("Initializing");
		char currentScalePosition = Robot.getScalePlatePosition();
		char currentSwitchPosition = Robot.getSwitchPlatePosition();
		m_disabled = ((currentScalePosition == 'L' && m_scalePosition == 'R') ||
				(currentScalePosition == 'R' && m_scalePosition == 'L') &&
				((currentSwitchPosition == 'L' && m_switchPosition == 'R') ||
				(currentSwitchPosition == 'R' && m_switchPosition == 'L'))
				);
		if (m_disabled) {
			return;
		}
	    super.initialize();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_disabled || super.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    		debug("Done");
		if (m_disabled) {
			return;
		}
	    super.end();
    }

    protected void debug(String information) {
    		super.debug("For Field Setup: Scale Position = " + m_scalePosition +
				" Switch Position = " + m_switchPosition +
			    " "  + information);
    }
}

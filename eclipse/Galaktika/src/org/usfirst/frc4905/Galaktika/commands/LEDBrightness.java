package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.subsystems.LEDs;

import Utilities.LEDColor;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDBrightness extends Command {
	private static final double kColorInc = 0.01;
	private LEDs m_led = new LEDs();
	private LEDColor m_color;
	private double m_brightness = 0;
	private boolean m_inc = true;

	public LEDBrightness(LEDColor color) {
		m_color = color;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		m_led.clearColor();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (m_inc) {
			m_brightness += kColorInc;
			if (m_brightness > 1.0) {
				m_inc = false;
				m_brightness = 1.0;
			}
		} else {
			m_brightness -= kColorInc;
			if (m_brightness < 0) {
				m_inc = true;
				m_brightness = 0;
			}
		}
		if (m_color == LEDColor.RED) {
			m_led.setRed(m_brightness);
		}
		else if(m_color == LEDColor.BLUE) {
			m_led.setBlue(m_brightness);
		}
		else {
			m_led.setGreen(m_brightness);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}

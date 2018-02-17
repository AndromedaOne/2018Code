package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.subsystems.LEDs;

import Utilities.LEDColor;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDCommand extends Command {
	private LEDColor m_color;
    public LEDCommand(LEDColor color) {
    	m_color = color;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	LEDs led = new LEDs();
    	 if (m_color == LEDColor.RED) {

         	led.setRed(1);
       }
       else if(m_color == LEDColor.BLUE) {
       	led.setBlue(1);
       }
       else {
     	  led.setGreen(1);
       }

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return true;
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

package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDs extends Subsystem {


	private static DigitalOutput red = RobotMap.redVal;
	private static DigitalOutput blue = RobotMap.blueVal;
	private static DigitalOutput green = RobotMap.greenVal;
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	private double validateBrightness(double brightness) {
		if (brightness > 1.0) {
			brightness = 1.0;
		} else if (brightness < 0) {
			brightness = 0;
		}
		return brightness;
	}

	public void setPurple(double brightness)  {
		clearColor();
		brightness = validateBrightness(brightness);
		red.updateDutyCycle(brightness);
		blue.updateDutyCycle(brightness);
	}

	public void setRed(double brightness)  {
		clearColor();
		brightness = validateBrightness(brightness);
		red.updateDutyCycle(brightness);
	}
	public void setBlue(double brightness)  {
		clearColor();
		brightness = validateBrightness(brightness);
		blue.updateDutyCycle(brightness);
	}
	public void setGreen(double brightness)  {
		clearColor();
		brightness = validateBrightness(brightness);
		green.updateDutyCycle(brightness);
	}

	public void setColor(int Red, int Green, int Blue) {
		clearColor();
/*		red.setRaw(Red);
		blue.setRaw(Blue);
		green.setRaw(Green); */
	}

	public void clearColor() {
		red.updateDutyCycle(0);
		blue.updateDutyCycle(0);
		green.updateDutyCycle(0);
	}

	public void setStatusGood() {
		clearColor();
		green.updateDutyCycle(1);
	}

	public void setStatusBad() {
		clearColor();
		red.updateDutyCycle(1);
	}


}

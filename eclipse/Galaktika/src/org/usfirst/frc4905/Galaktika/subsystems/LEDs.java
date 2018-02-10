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
	public void setPurple()  {
		clearColor();
		red.updateDutyCycle(1);
		blue.updateDutyCycle(1);
	}

	public void setRed()  {
		clearColor();
		red.updateDutyCycle(1);
	}
	public void setBlue()  {
		clearColor();
		blue.updateDutyCycle(1);
	}
	public void setGreen()  {
		clearColor();
		red.updateDutyCycle(1);
		green.updateDutyCycle(1);
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

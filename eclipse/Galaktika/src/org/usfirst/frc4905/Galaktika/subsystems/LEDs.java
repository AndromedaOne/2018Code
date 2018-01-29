package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDs extends Subsystem {


	private static PWM red = RobotMap.redVal;
	private static PWM blue = RobotMap.blueVal;
	private static PWM green = RobotMap.greenVal;
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	public void setPurple()  {
		clearColor();
		red.setRaw(255);
		blue.setRaw(255);
	}
	public void setRed()  {
		clearColor();
		red.setRaw(255);
	}
	public void setBlue()  {
		clearColor();
		blue.setRaw(255);
	}
	public void setYellow()  {
		clearColor();
		red.setRaw(255);
		green.setRaw(255);
	}
	public void setColor(int Red, int Green, int Blue) {
		clearColor();
		red.setRaw(Red);
		blue.setRaw(Blue);
		green.setRaw(Green);
	}

	public void clearColor() {
		red.setRaw(0);
		blue.setRaw(0);
		green.setRaw(0);
	}
	
	public void setStatusGood() {
		clearColor();
		green.setRaw(255);
	}
	
	public void setStatusBad() {
		clearColor();
		red.setRaw(255);
	}

}

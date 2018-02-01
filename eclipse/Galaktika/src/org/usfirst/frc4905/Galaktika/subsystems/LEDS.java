package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDS extends Subsystem {

	
	private static PWM red = RobotMap.redVal;
	private static PWM blue = RobotMap.blueVal;
	private static PWM green = RobotMap.greenVal;
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
}
	public void setPurple()  {
		red.setRaw(255);
		blue.setRaw(255);
	}
	public void setRed()  {
		red.setRaw(255);
	}
	public void setBlue()  {
		blue.setRaw(255);
	}
	public void setYellow()  {
		red.setRaw(255);
		green.setRaw(255);
	}
	public void setColor(int Red, int Green, int Blue) {
		red.setRaw(Red);
		blue.setRaw(Blue);
		green.setRaw(Green);
	}
	
}

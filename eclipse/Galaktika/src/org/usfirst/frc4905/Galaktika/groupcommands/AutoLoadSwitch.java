package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadSwitch extends CommandGroup {
	
	public AutoLoadSwitch() {
		
	}
	
	public void start() {
		char robotPos = Robot.getInitialRobotLocation();
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == robotPos) {
			nearSwitch(robotPos);
		} else {
			farSwitch(robotPos);
		}
	}
	
	public void nearSwitch(char robotPos) {
		
	}
	
	public void farSwitch(char robotPos) {
		
	}
	
}

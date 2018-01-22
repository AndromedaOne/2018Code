package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.TeleOpDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLoadSwitch extends CommandGroup {

	//WALLTOSWITCH has a temporary value! Correct the measurement before use!
	public static final double WALLTOSWITCH = 140;
	
	public AutoLoadSwitch() {
		
	}
	
	public void start() {
		char robotPos = Robot.getInitialRobotLocation();
		char platePos = Robot.getSwitchPlatePosition();
		if (platePos == robotPos) {
			loadNearSwitch(robotPos);
		} else {
			loadFarSwitch(robotPos);
		}
	}
	
	public void loadNearSwitch(char robotPos) {
		//addSequential(new goForward(1));
	}
	
	public void loadFarSwitch(char robotPos) {
		
	}
	
}

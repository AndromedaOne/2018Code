package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.RobotMap;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Jaws extends Subsystem {
	
   public void initDefaultCommand() {
    	setDefaultCommand(new JawsOpenClose());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void extend() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kForward);
	   }
	   public void contract() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kReverse);
	   	
	   }
	   public void stop() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kOff);
	  
	   }

 
}


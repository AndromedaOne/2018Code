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
    //	setDefaultCommand(new JawsOpenClose());
	   // CHANGE THIS BACK!!! I ONLY CHANGED THIS BECAUSE I NEEDED TO COMMENT OUT THE SOLENOID TO GET RID OF ERRORS
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
   
   	private boolean shouldJawsBeOpen = false;
   
   
	public void extend() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kForward);
	   }
	   public void contract() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kReverse);
	   	
	   }
	   public void stop() {
	    RobotMap.jawsSolenoid.set(DoubleSolenoid.Value.kOff);
	  
	   }
	   
	   public void setShouldJawsBeOpenBoolean(boolean state){
		   //true = they should be open, false = they should not be open
		   shouldJawsBeOpen = state;
	   }
	   
	   public void setJawsToCorrectState(){
		   if(shouldJawsBeOpen){
			   extend();
		   }
		   else{
			   contract();
		   }
	   }

 
}


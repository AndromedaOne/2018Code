package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.RobotMap;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Retractor extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.


	private final DoubleSolenoid retractor = RobotMap.retractIntake;
	private boolean shouldIntakeBeUp = true;
	private boolean shouldIntakeBeStopped= false;

	@Override
	public void initDefaultCommand() {
		setDefaultCommand (new RetractExtendArms());
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void retractIntake(){
		retractor.set(DoubleSolenoid.Value.kReverse);
	}

	public void extendIntake(){
		retractor.set(DoubleSolenoid.Value.kForward);
	}

	public void stopIntakeExtension(){
		retractor.set(DoubleSolenoid.Value.kOff);
	}

	public void setShouldIntakeBeUpBoolean(boolean state){
		shouldIntakeBeUp = state;
	}

	public void setShouldIntakeBeStoppedBoolean(boolean state){
		shouldIntakeBeStopped = state;
	}

	public boolean getShouldIntakeBeUpBoolean() {
		return shouldIntakeBeUp;
	}

	public boolean getShouldIntakeBeStoppedBoolean(){
		return shouldIntakeBeStopped;
	}

	public void setIntakeToCorrectState(){
		if(shouldIntakeBeStopped){
			stopIntakeExtension();
		}
		else{
			if(shouldIntakeBeUp){
				retractIntake();
			}
			else if(!shouldIntakeBeUp){
				extendIntake();
			}
		}
	}

}


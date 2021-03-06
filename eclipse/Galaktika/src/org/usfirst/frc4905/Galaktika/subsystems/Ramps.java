// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4905.Galaktika.subsystems;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;
import org.usfirst.frc4905.Galaktika.commands.ReleaseRamps;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Solenoid;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Ramps extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final DoubleSolenoid solenoid1 = RobotMap.rampsSolenoid1;
    private final DoubleSolenoid solenoid2 = RobotMap.rampsSolenoid2;
    private final DoubleSolenoid solenoid3 = RobotMap.rampsSolenoid3;
    private final DoubleSolenoid solenoid4 = RobotMap.rampsSolenoid4;
    private final Servo leftDeployServo = RobotMap.leftDeployServo;
    private final Servo rightDeployServo = RobotMap.rightDeployServo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private boolean leftRampDeployed = false, rightRampDeployed = false;
    private static boolean safetiesEnabled = false;
    
    
    private final DoubleSolenoid leftRampSolenoid = RobotMap.leftRampSolenoid;
    private final DoubleSolenoid rightRampSolenoid = RobotMap.rightRampSolenoid;
    
    
    
    
    public void liftLeftRamp() {
    	/*
    	solenoid1.set(Value.kForward);
    	solenoid2.set(Value.kForward);
    	*/
    	leftRampSolenoid.set(Value.kForward);
    }
    public void liftRightRamp() {
    	/*
    	solenoid3.set(Value.kForward);
    	solenoid4.set(Value.kForward);
    	*/
    	rightRampSolenoid.set(Value.kForward);
    }
    

    @Override
    public void initDefaultCommand() {
       setDefaultCommand (new ReleaseRamps());
    	
  
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }
    
    public void moveLeftServo(double LeftServoPosition) {
    	leftDeployServo.set(LeftServoPosition);
    }
    
    public void moveRightServo(double RightServoPosition ) {
    	rightDeployServo.set(RightServoPosition);
    }

    public double getTimeRemainingInMatchPeriod(){
    	double time = DriverStation.getInstance().getMatchTime();
    	return time;
    }
    
    public void setLeftRampDeployed(){
    	leftRampDeployed = true;
    }
    
    public void setRightRampDeployed(){
    	rightRampDeployed = true;
    }
    
    public boolean getLeftRampDeployedStatus(){
    	return leftRampDeployed;
    }

    public boolean getRightRampDeployedStatus(){
    	return rightRampDeployed;
    }
    
    public void setSafetyBooleanStatus(boolean state){
    	safetiesEnabled = state;
    }
   
    public boolean returnSafetyStatus(){
    	 return safetiesEnabled;
    }
    
 
    
    public void lockRampsIn(){
    	Robot.ramps.moveLeftServo(0.4);
        Robot.ramps.moveRightServo(0.5);
    }

}
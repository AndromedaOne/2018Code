
    public void moveRightServo(double RightServoPosition ) {
    	rightDeployServo.set(RightServoPosition);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}


import org.usfirst.frc4905.Galaktika.commands.ReleaseRamps;

import org.usfirst.frc4905.Galaktika.Robot;

    public void holdRamps() {
    	solenoid1.set(Value.kReverse);
    	solenoid2.set(Value.kReverse);
    	solenoid3.set(Value.kReverse);
    	solenoid4.set(Value.kReverse);
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
    public void moveRightServo(double RightServoPosition ) {
    	rightDeployServo.set(RightServoPosition);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}


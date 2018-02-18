// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4905.Galaktika.commands;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.ControllerButtons.EnumeratedRawAxis;

/**
 *
 */
public class RunIntakeIn extends Command {
	Joystick intakeController;
    private static final double kDeadzone = 0;
	public static final double kIntakeSpeed = 0.75;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public RunIntakeIn() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.intake);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	intakeController = Robot.oi.getSubsystemController();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    	double leftTriggerValue = EnumeratedRawAxis.getLeftTriggerValue(intakeController);
    	double rightTriggerValue = EnumeratedRawAxis.getRightTriggerValue(intakeController);
    	
    	
    	/*
    	if(leftTriggerValue < kDeadzone) {
			leftTriggerValue = 0;
		}

		if(rightTriggerValue < kDeadzone) {
			rightTriggerValue = 0;
		}
		*/
		if (leftTriggerValue == 0 && rightTriggerValue == 0) {
			Robot.intake.stopIntake();
		}
		else if (leftTriggerValue < rightTriggerValue) {
			Robot.intake.runIntake(kIntakeSpeed);
			//System.out.println("running intake in");
		}
		
		else {
			Robot.intake.reverseIntake(kIntakeSpeed);
			//System.out.println("Running intake out");
		}
		
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.intake.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}

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
import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.EnumeratedRawAxis;
import Utilities.PDPLogging;
import Utilities.Trace;
import Utilities.TracePair;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleOpDrive extends Command {
	public static final double kDeadzone = 0.15;

	private int m_slowmodedelaycounter = 0;

	private boolean slowMoEnabled = false;

	private double mod = 1;

	private static final double kProportion = .1;

	private double courseCorrectionDelay = 0;
	
	private double SavedAngle = 0;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public TeleOpDrive() {

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
		requires(Robot.driveTrain);

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}








	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Joystick drivecontroller = Robot.oi.getDriveController();
		double forwardBackwardStickValue = EnumeratedRawAxis.getLeftStickVertical(drivecontroller);
		double rotateStickValue = EnumeratedRawAxis.getRightStickHorizontal(drivecontroller);
		if(forwardBackwardStickValue < kDeadzone && forwardBackwardStickValue > -kDeadzone) {
			forwardBackwardStickValue = 0;
		}

		if(rotateStickValue < kDeadzone && rotateStickValue > -kDeadzone) {
			rotateStickValue = 0;
		}

		
		
		
		
		
		
		//24=about half a second
		if(m_slowmodedelaycounter > 24 && Utilities.ButtonsEnumerated.getLeftButton(drivecontroller)) {
			m_slowmodedelaycounter = 0;
			if (!slowMoEnabled) {
				mod = 0.6;
				slowMoEnabled = true;
				System.out.println("Slow Mode IS enabled!");
			}
			else {
				mod = 1;
				slowMoEnabled = false;
				System.out.println("SLOWMODE HAS ENDED!");
			}
		}
		m_slowmodedelaycounter++;

		double robotAngle = RobotMap.navX.getRobotAngle();
		double correctionEquation = (SavedAngle - robotAngle)*kProportion;
		int correctionMode = -1;
		if (forwardBackwardStickValue == 0 && rotateStickValue == 0) {
			correctionMode = 0;
			SavedAngle = robotAngle;
		}
		else if (rotateStickValue != 0) {
			courseCorrectionDelay = 0;
			correctionMode = 1;
			SavedAngle = robotAngle;
			Robot.driveTrain.move(forwardBackwardStickValue*mod, rotateStickValue*mod);
		}
		else if(courseCorrectionDelay > 25) {
			//disable correction for half a second after releasing the turn stick, to allow the driver
			//to let the machine drift naturally, and not correct back to the gyro reading from
			//the instant the driver released the turn stick.
			//PROBLEM, corrects every 25 cycles cuz I'm dumb...
			
			//reassign the correctionEquation to the latest direction that we've been "free driving" in
			correctionEquation = (SavedAngle - robotAngle)*kProportion;
			correctionMode = 2;
			Robot.driveTrain.move(forwardBackwardStickValue*mod, correctionEquation*mod);
		}
		else {
			//should all cases fail, just drive normally
			Robot.driveTrain.move(forwardBackwardStickValue*mod, rotateStickValue*mod);
		}
		
		
		if(courseCorrectionDelay == 24){
			//take the most recent course and make that our angle
			SavedAngle = robotAngle;
		}
		
		
		
		
		
		Trace.getInstance().addTrace("GyroCorrection",
				new TracePair("forwardBackwardStickValue", forwardBackwardStickValue),
				new TracePair("SavedAngle", SavedAngle),
				new TracePair("robotAngle", robotAngle),
				new TracePair("kProportion", kProportion),
				new TracePair("correctionEquation", correctionEquation),
				new TracePair("correctionMode", (double)correctionMode));
		
		courseCorrectionDelay++;
    }
		



	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		PDPLogging.pdpLog();
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

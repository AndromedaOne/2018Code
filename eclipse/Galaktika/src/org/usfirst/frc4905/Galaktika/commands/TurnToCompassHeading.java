package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToCompassHeading extends Command {

	private double m_compassHeading = 0;
	
    public TurnToCompassHeading(double compassHeading) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	m_compassHeading = compassHeading;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//calculates the delta angle
    	double currentAngle = RobotMap.navX.getRobotAngle();
    	double modAngle = currentAngle % 360;
    	System.out.println("currentangle =" +currentAngle + "," + "mod angle =" + modAngle);
    	//corrects negative Modulus
    	if(modAngle < 0) {
    		modAngle += 360;
    	}
    	
    	double deltaAngle = m_compassHeading - modAngle;
    	
    	//fixes long turns if delta is over 180: when tested it didn't work the first time.
    	// ******TEST BEFORE FINALIZING!******
    	System.out.println("delta angle = " + deltaAngle);

    	if(deltaAngle > 180) {
    		deltaAngle = -(360 - deltaAngle);
    		System.out.println("Angle corrected for shortest method!");
    	}
    	
    	Robot.driveTrain.initGyroPIDDeltaAngle();
    	System.out.println("Delta angle = " + deltaAngle);

    	Robot.driveTrain.enableGyroPID(deltaAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.driveTrain.gyroPIDIsDone();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.driveTrain.stopGyroPid();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

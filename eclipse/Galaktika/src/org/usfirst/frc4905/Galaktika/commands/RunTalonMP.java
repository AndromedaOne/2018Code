package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;

import com.ctre.phoenix.motion.SetValueMotionProfile;

import edu.wpi.first.wpilibj.command.Command;
import kinematics.MotionProfilingControllerCanTalon;

/**
 *
 */
public class RunTalonMP extends Command {

	MotionProfilingControllerCanTalon m_example = new MotionProfilingControllerCanTalon(Robot.driveTrain.getLeftTopTalon());
	int counter = 0; // I think I need this so that there is a delay between the MP trajectory point buffer filling and the MP starting...
    public RunTalonMP() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		m_example.control();
    		SetValueMotionProfile setOutput = m_example.getSetValue();

		Robot.driveTrain.setLeftTopTalonMPMode(setOutput.value);
		if(counter == 50) {
			m_example.startMotionProfile();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.driveTrain.gyroCorrectMove(0.0, 0.0, 1.0, false);
    		m_example.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}

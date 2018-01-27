package org.usfirst.frc4905.Galaktika.commands;

import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Trace;
import Utilities.TracePair;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import kinematics.Kinematics;
import kinematics.Kinematics.*;


/**
 *
 */
public class MoveUsingEncoder extends Command {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
		// org.usfirst.frc.team4905.robot.Robot
		Path m_path;
		double timeThruLoop = 0.0;
		double m_initialTimeStamp;
		double m_currentTimeStamp;
		double deltaTime;
		double m_velocityToMotorOutputRatio;
		double m_accelerationToMotorOutputRatio;
		double m_PIDOut;
		double m_positionToEncoderRevolutionsRatio = 5.0;
		double m_initialEncoderPosition;
		Kinematics m_kinematics = new Kinematics();

		Kinematics.TrajectoryPoint currentTrajectoryPoint = Robot.kinematics.new TrajectoryPoint();
		Kinematics.TrajectoryPoint nextTrajectoryPoint = Robot.kinematics.new TrajectoryPoint();

		Vector<String> m_header = new Vector<String>();

		public MoveUsingEncoder() {

		}
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR

		public MoveUsingEncoder(Path path) {
			m_path = path;
			// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
			// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

			// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
			// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

	    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
		}

		// Called just before this Command runs the first time
		protected void initialize() {
			

			m_velocityToMotorOutputRatio = 1.0 / m_path.getMaxVelocity();
			m_accelerationToMotorOutputRatio = 1.0 / m_path.getMaxAcceleration();
			
			Robot.driveTrain.initializePositionPID();
			
			m_initialTimeStamp = Timer.getFPGATimestamp();
			m_initialEncoderPosition = Robot.driveTrain.getEncoderTicks();
		}

		// Called repeatedly when this Command is scheduled to run
		protected void execute() {
			
			Vector<Double> entry = new Vector<Double>();
			currentTrajectoryPoint = nextTrajectoryPoint;
			m_currentTimeStamp = Timer.getFPGATimestamp();
			deltaTime = m_currentTimeStamp - m_initialTimeStamp;
			nextTrajectoryPoint = Robot.kinematics.getTrajectoryPoint(m_path,
					(deltaTime / 60));
			
			m_PIDOut = Robot.driveTrain.getPositionPIDOut(currentTrajectoryPoint.m_position + m_initialEncoderPosition);
			entry.add(Robot.driveTrain.getVelocity());
			entry.add(currentTrajectoryPoint.m_currentVelocity);
			entry.add((Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition));
			entry.add(currentTrajectoryPoint.m_position);
			entry.addElement((currentTrajectoryPoint.m_position-(Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition))*100);
			entry.add(0.0);
			entry.add(Robot.driveTrain.getDTerm()*10);
			Trace.getInstance().addTrace("MoveWithEncoderData", 
					new TracePair("ActualVelocity", Robot.driveTrain.getVelocity()),
					new TracePair("ProjectedVelocity", currentTrajectoryPoint.m_currentVelocity),
					new TracePair("ActualPosition", (Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition)),
					new TracePair("ProjectedPosition", currentTrajectoryPoint.m_position),
					new TracePair("Error", (currentTrajectoryPoint.m_position-(Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition))*100),
					new TracePair("Zero", 0.0));
			Robot.driveTrain.setAllDriveControllersVelocity((nextTrajectoryPoint.m_currentVelocity) + m_PIDOut);
			
		}

		// Make this return true when this Command no longer needs to run execute()
		protected boolean isFinished() {
			
			return false;
		}

		// Called once after isFinished returns true
		protected void end() {
			Robot.driveTrain.setAllDriveControllersVelocity(0.0);
			Trace.getInstance().flushTraceFiles();
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		protected void interrupted() {
			end();
		}
}

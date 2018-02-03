package org.usfirst.frc4905.Galaktika.commands;

import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Trace;
import Utilities.TracePair;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import kinematics.Kinematics;
import kinematics.Kinematics.*;
import kinematics.KinematicsTester;

/**
 *
 */
public class MoveUsingEncoderMotionProfiling extends Command {

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
		double m_positionToEncoderRevolutionsRatio = 1.0;
		double m_initialEncoderPosition;
		Kinematics m_kinematics = new Kinematics();

		TrajectoryPoint currentTrajectoryPoint = m_kinematics.new TrajectoryPoint();
		TrajectoryPoint nextTrajectoryPoint = m_kinematics.new TrajectoryPoint();

		Vector<String> m_header = new Vector<String>();

		public MoveUsingEncoderMotionProfiling() {

		}
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR

		public MoveUsingEncoderMotionProfiling(Path path) {
			m_path = path;
			// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
			// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

			// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
			// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

	    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
		}

		// Called just before this Command runs the first time
		protected void initialize() {
			
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
			nextTrajectoryPoint = m_kinematics.getTrajectoryPoint(m_path.getTrajectoryVector(),
					(deltaTime));
			
			m_PIDOut = Robot.driveTrain.getPositionPIDOut(currentTrajectoryPoint.m_position + m_initialEncoderPosition);
			Trace.getInstance().addTrace("MotionProfilingData", 
					new TracePair("ActualVelocity", Robot.driveTrain.getVelocity()),
					new TracePair("ProjectedVelocity", currentTrajectoryPoint.m_currentVelocity),
					new TracePair("ActualPosition", (Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition)),
					new TracePair("ProjectedPosition", currentTrajectoryPoint.m_position),
					new TracePair("Error", (currentTrajectoryPoint.m_position-(Robot.driveTrain.getEncoderPosition() - m_initialEncoderPosition))),
					new TracePair("MotorOutput", (nextTrajectoryPoint.m_currentVelocity*Robot.driveTrain.m_velocityToMotorOutputRatio)*10000),
					new TracePair("Zero", 0.0));
			Robot.driveTrain.moveVelocity(nextTrajectoryPoint.m_currentVelocity + m_PIDOut);
			System.out.println("(nextTrajectoryPoint.m_currentVelocity): " + (nextTrajectoryPoint.m_currentVelocity));
			System.out.println("deltaTime: " + deltaTime);
			System.out.println("(nextTrajectoryPoint.m_position): " + nextTrajectoryPoint.m_position);
			System.out.println("End delta time: " + m_path.getSetpointVector().get(0).getEndDeltaTime());
			System.out.println("Final Position: " + m_kinematics.getTrajectoryPoint(m_path.getTrajectoryVector(), m_path.getSetpointVector().get(0).getEndDeltaTime()).m_position);
			System.out.println("m_path.getSetpointVector().get(0): " + m_path.getSetpointVector().get(0).getm_X());
		}

		// Make this return true when this Command no longer needs to run execute()
		protected boolean isFinished() {
			
			return false;
		}
		
		// Called once after isFinished returns true
		protected void end() {
			Robot.driveTrain.move(0.0,0.0);
			Trace.getInstance().flushTraceFiles();
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		protected void interrupted() {
			end();
		}
}

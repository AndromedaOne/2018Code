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
import org.usfirst.frc4905.Galaktika.Ultrasonic;
import org.usfirst.frc4905.Galaktika.commands.TeleOpDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Utilities.Signal;
import Utilities.SignalProcessor;
import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import kinematics.MPSource;
import kinematics.MotionProfilingController;

/**
 *
 */
public class DriveTrain extends Subsystem {
	// Left Talon = 1673.4 Encoder Ticks / inch
	// Right Talon = 1657.04 Encoder Ticks / inch
	// Average = 1665.22, rounded to 1665 ET/i
	public static final double ENCODER_TICKS_PER_INCH = 216;
	public static double ULTRASONIC_RANGE_IN_INCHES = 0;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX leftTopTalon = RobotMap.driveTrainLeftTopTalon;
	private final WPI_TalonSRX leftBottomTalon = RobotMap.driveTrainLeftBottomTalon;
	private final SpeedControllerGroup leftSpeedController = RobotMap.driveTrainLeftSpeedController;
	private final WPI_TalonSRX rightTopTalon = RobotMap.driveTrainRightTopTalon;
	private final WPI_TalonSRX rightBottomTalon = RobotMap.driveTrainRightBottomTalon;
	private final SpeedControllerGroup rightSpeedController = RobotMap.driveTrainRightSpeedController;
	private final DifferentialDrive differentialDrive = RobotMap.driveTrainDifferentialDrive;
	private final Compressor compressor = RobotMap.driveTrainCompressor;
	private final Ultrasonic frontUltrasonic = RobotMap.driveTrainFrontUltrasonic;

	private static final double kEncoderMaxVelocity = 95000.0; // Encoder ticks per second
	private static final double kEncoderMaxAcceleration = 1000000; // Encoder ticks per second^2
	private static final double kEncoderMaxJerk = 400000; // Encoder ticks per second^3
	public int m_encoderTicksPassed = 0;

	public static double getMaxVelocity() {
		return kEncoderMaxVelocity;
		
	}

	public static double getMaxAcceleration() {
		return kEncoderMaxAcceleration;
	}

	public static double getMaxJerk() {
		return kEncoderMaxJerk;
	}

	private double m_encoderMPPositionkp = 6.0;//5.0;
	private double m_encoderMPPositionki = 0.5;//1.0;
	private double m_encoderMPPositionkd = 0.0;

	private double m_encoderMPVelocitykp = 0.000015;
	private double m_encoderMPVelocityki = 1.0e-6;
	private double m_encoderMPVelocitykd = 0.0;
	private double m_encoderMPVelocitykf = 1.0 / kEncoderMaxVelocity;

	private double kEncoderMPTolerance = 50;
	private MotionProfilingController m_encoderMotionProfilingController;

	public MotionProfilingController getEncoderMPController() {
		return m_encoderMotionProfilingController;
	}

	private static final double kGyroMaxVelocity = 221.0; // degrees per second
	private static final double kGyroMaxAcceleration = 2500.0; // degrees per second^2
	private static final double kGyroMaxJerk = 2000.0; // degrees per second^3

	public static double getGyroMaxVelocity() {
		return kGyroMaxVelocity;
	}

	public static double getGyroMaxAcceleration() {
		return kGyroMaxAcceleration;
	}

	public static double getGyroMaxJerk() {
		return kGyroMaxJerk;
	}

	private double m_gyroMPPositionkp = 5.5;
	private double m_gyroMPPositionki = 0.001;
	private double m_gyroMPPositionkd = 0.1;
	private double m_gyroMPiInitialPosition = 0.0;

	private double m_gyroMPVelocitykp = 0.006;
	private double m_gyroMPVelocityki = 0.0;
	private double m_gyroMPVelocitykd = 0.0;
	private double m_gyroMPVelocitykf = 1.0 / kGyroMaxVelocity;

	private double kGyroMPTolerance = 2.5;
	private MotionProfilingController m_gyroMotionProfilingController;

	public MotionProfilingController getGyroMPController() {
		return m_gyroMotionProfilingController;
	}
	
	private double KDEGREES_PER_ENCODER_TICK = 0.002452021205189;

	private double m_gyroPreviousPosition = Double.NaN;
	private double m_gyroPreviousTime = 0.0;
	
	// these constants might be the exact same as the encoder MP ones.
	private double m_ultrasonicMPPositionkp = 10.0;
	private double m_ultrasonicMPPositionki = 0.0;
	private double m_ultrasonicMPPositionkd = 0.0;
	private double m_ultrasonicMPInitialPosition = 0.0;

	private double kUltrasonicMPTolerance = 1.0;
	private MotionProfilingController m_ultrasonicMotionProfilingController;

	public MotionProfilingController getUltrasonicMPController() {
		return m_ultrasonicMotionProfilingController;
	}

	// Encoder PID
	private double m_encoderPIDP = 0.0004;
	private double m_encoderPIDI = 0;
	private double m_encoderPIDD = 0;
	private double m_encoderPIDF = 0;
	private double m_encoderPIDOutputMax = 0.4;
	private double m_encoderPIDTolerance = 1000;
	private double m_encoderPIDMaxAllowableDelta = 0.1;

	private PIDController m_ultrasonicPID;
	private double m_P = .2;
	private double m_I = .00000;
	private double m_D = .0;
	private double m_maxSpeed = 1;
	private double m_f = 0;
	private double m_tolerance = 1;
	private double m_noiseTolerance = 64;
	private double m_pingDelay = 0.02;
	private int m_timesDistanceAveraged = 5;
	// Gyro Correction for move
	private static final double kProportion = .05;

	private double courseCorrectionDelay = 0;

	private double m_savedAngle = 0;

	
	public DriveTrain() {
		leftBottomTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		leftBottomTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		frontUltrasonic.setEnabled(true);
		frontUltrasonic.setAutomaticMode(true);
		UltrasonicPIDOutputFront ultraPIDOutput = new UltrasonicPIDOutputFront();
		UltrasonicPIDin pdIn = new UltrasonicPIDin();
		m_ultrasonicPID = new PIDController(m_P, m_I, m_D, m_f, pdIn, ultraPIDOutput);
		m_ultrasonicPID.setAbsoluteTolerance(m_tolerance);
		m_ultrasonicPID.setOutputRange(-m_maxSpeed, m_maxSpeed);
		frontUltrasonic.SetUltrasonicNoiseTolerance(m_noiseTolerance);
		frontUltrasonic.SetUltrasonicPingDelay(m_pingDelay);
		frontUltrasonic.SetUltrasonicAveragedAmount(m_timesDistanceAveraged);
		LiveWindow.add(m_ultrasonicPID);
		m_ultrasonicPID.setName("Ultrasonic DriveTrain PID","Ultrasonic PID");

		initializeEncoderPID();
		initGyroPIDDeltaAngle();

		//initializeEncoderMP();
		//initializeGyroMP();
	}

	// Ultrasonic Code - Begins

	public double getDistanceFromFront() {
		return frontUltrasonic.getRangeInches();
	}

	private class UltrasonicPIDin implements PIDSource {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
		}

		@Override
		public PIDSourceType getPIDSourceType() {

			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return getDistanceFromFront();
		}

	}

	public boolean doneUltrasonicFrontPID() {
		debug("top of doneUltrasonicFrontPID");
		Trace.getInstance().addTrace(false, "MoveWithUltrasonic",
				new TracePair("Current Distance", getDistanceFromFront()),
				new TracePair("PID Error", m_ultrasonicPID.getError()),
				new TracePair("PID Output", m_ultrasonicPID.get()));
		boolean done = m_ultrasonicPID.onTarget();
		debug("bottom of doneUltrasonicFrontPID returning " + done);
		return done;
	}

	private void debug(String information) {
		System.out.println("In DriveTrain.java " + information);
		System.out.flush();
	}

	public void stopUltrasonicFrontPID() {
		m_ultrasonicPID.disable();

	}

	public void moveWithUltrasonicPID(double distanceToGoTo) {
		m_ultrasonicPID.setSetpoint(distanceToGoTo);
		m_ultrasonicPID.enable();

	}

	private class UltrasonicPIDOutputFront implements PIDOutput {

		@Override
		public void pidWrite(double output) {
		}
	}

	public void intializeUltrasonicPIDFront(double distanceToDriveTo) {
		debug("top of intializeUltrasonicPIDFront");
		moveWithUltrasonicPID(distanceToDriveTo);
		debug("bottom of intializeUltrasonicPIDFront");
	}

	// Ultrasonic Code - Ends

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TeleOpDrive());
	}

	@Override
	public void periodic() {
		// Put code here to be run every loop

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private PIDController m_encoderPID;
	private class EncoderPIDIn extends SensorBase implements PIDSource, Sendable {


		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			SmartDashboard.putNumber("Encoder Ticks: ", getEncoderTicks());
			return getEncoderTicks();
		}
		@Override
		public void initSendable(SendableBuilder builder) {
			// TODO Auto-generated method stub
			builder.setSmartDashboardType("Counter");
			builder.addDoubleProperty("Value", this::pidGet, null);
		}

	}
	static public double calculateOutput(double output, double previousOutput,
			double maxAllowableDelta) {
		double deltaOutput = output - previousOutput;
		if (deltaOutput > 0 && deltaOutput > maxAllowableDelta) {
			deltaOutput = maxAllowableDelta;
			output = previousOutput + deltaOutput;
		}
		if (deltaOutput < 0 && deltaOutput < -maxAllowableDelta) {
			deltaOutput = -maxAllowableDelta;
			output = previousOutput + deltaOutput;
		}
		return output;
	
	}
	private class EncoderPIDOut implements PIDOutput {
		private double m_previousOutput = 0;
		private double m_maxAllowableDelta;

		public EncoderPIDOut(double maxAllowableDelta) {
			m_maxAllowableDelta = maxAllowableDelta;
		}
		@Override
		public void pidWrite(double output) {
			
			output = -output;
			/*
			if(output != 0.0) {
				output = calculateOutput(output, m_previousOutput, m_maxAllowableDelta);
			}
			if(output < 0.3 && output > 0) {
				output = 0.3;
				
			}
			if(output > -0.3 && output < 0) {
				output = -0.3;
			}
			*/
			// Negation causes forward movement for positive values
			gyroCorrectMove(output, 0.0, 1.0, true,false);
			m_previousOutput = output;

		}
	}

	public void initializeEncoderPID() {
		EncoderPIDIn encoderPIDIn = new EncoderPIDIn();
		EncoderPIDOut encoderPIDOut = new EncoderPIDOut(m_encoderPIDMaxAllowableDelta);
		m_encoderPID = new PIDController(m_encoderPIDP, m_encoderPIDI, m_encoderPIDD, m_encoderPIDF, encoderPIDIn,
				encoderPIDOut);
		m_encoderPID.setOutputRange(-m_encoderPIDOutputMax, m_encoderPIDOutputMax);
		m_encoderPID.setAbsoluteTolerance(m_encoderPIDTolerance);
		LiveWindow.add(encoderPIDIn);
		encoderPIDIn.setName("DriveTrain", "Encoder");
		
		// grab a saved angle to correct to when using the encoder pid
		m_savedAngle = RobotMap.navX.getRobotAngle();
		LiveWindow.add(m_encoderPID);
		m_encoderPID.setName("DriveTrain","Encoder PID");
	}

	public void enableEncoderPID(double setpoint) {
		double currentEncoderPosition = getEncoderTicks();
		m_encoderPID.setSetpoint(setpoint + currentEncoderPosition);
		System.out.println("Current position: " + currentEncoderPosition);
		if (setpoint + currentEncoderPosition > currentEncoderPosition) {
			System.out.println("Moving backwards, distance = " + Math.abs(setpoint));
		} else {
			System.out.println("Moving forwards, distance = " + Math.abs(setpoint));
		}
		m_encoderPID.enable();
	}

	public boolean isDoneEncoderPID() {
		return m_encoderPID.onTarget();
	}

	public void disableEncoderPID() {
		m_encoderPID.disable();
	}

	private PIDController m_gyroPIDSource;
	private class GyroPIDIn extends SensorBase implements PIDSource, Sendable {
		@Override
		public void setPIDSourceType(PIDSourceType PIDSource) {

		}
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return RobotMap.navX.getRobotAngle();
		}
		@Override
		public void initSendable(SendableBuilder builder) {
			builder.setSmartDashboardType("Counter");
			builder.addDoubleProperty("Value", this::pidGet, null);
		}
	}

	private class GyroPIDOut implements PIDOutput {

		private double m_previousOutput = 0;
		private double m_maxAllowableDelta;

		public GyroPIDOut(double maxAllowableDelta) {
			m_maxAllowableDelta = maxAllowableDelta;
		}

		@Override
		public void pidWrite(double output) {
			if(output != 0.0) {
				output = calculateOutput(output, m_previousOutput, m_maxAllowableDelta);
			}
			move(0,output,false);
			m_previousOutput = output;
		}
	}

	public void initGyroPIDDeltaAngle() {
		double gyroPIDP = 0.028;
		double gyroPIDI = 0.00;
		double gyroPIDD = 0.1;
		double gyroPIDF = 0.0;
		double gyroPIDOutputRange = 1.0;
		double gyroPIDAbsTolerance = 5;
		double maxAllowableDelta = 0.2;
		GyroPIDIn gyroPIDIn = new GyroPIDIn();
		GyroPIDOut gyroPIDOut = new GyroPIDOut(maxAllowableDelta);
		m_gyroPIDSource = new PIDController(gyroPIDP, gyroPIDI, gyroPIDD, gyroPIDF, gyroPIDIn, gyroPIDOut);
		m_gyroPIDSource.setOutputRange(-gyroPIDOutputRange, gyroPIDOutputRange);
		m_gyroPIDSource.setAbsoluteTolerance(gyroPIDAbsTolerance);
		
		LiveWindow.add(gyroPIDIn);
		gyroPIDIn.setName("Gyro", "GyroAngle");
		
		LiveWindow.add(m_gyroPIDSource);
		m_gyroPIDSource.setName("Gyro", "Gyro PID");
	}

	public void enableGyroPID(double setPoint) {
		double endAngle = RobotMap.navX.getRobotAngle() + setPoint;
		m_gyroPIDSource.setSetpoint(endAngle);
		m_gyroPIDSource.enable();

	}

	public boolean gyroPIDIsDone() {
		Trace.getInstance().addTrace(true, "GyroPID", new TracePair("Avg Error", m_gyroPIDSource.getError()),
				new TracePair("Target", m_gyroPIDSource.getSetpoint()),
				new TracePair("Robot Angle", RobotMap.navX.getRobotAngle()),
				new TracePair("Avg Error", m_gyroPIDSource.getError()),
				new TracePair("Output", m_gyroPIDSource.get()));
		return m_gyroPIDSource.onTarget();
	}

	public void stopGyroPid() {
		m_gyroPIDSource.disable();
		m_savedAngle = RobotMap.navX.getRobotAngle();
		System.out.println("Done Turning");
	}

	public double getEncoderTicks() {
		// TODO Auto-generated method stub
		return leftBottomTalon.getSelectedSensorPosition(0);
	}

	public void move(double forwardBackSpeed, double rotateAmount) {
		// Rotation was inverted, -rotation fixes that
		move(forwardBackSpeed, rotateAmount, true);
	}

	public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInput) {
		// Rotation was inverted, -rotation fixes that
		
		differentialDrive.arcadeDrive(forwardBackSpeed, -rotateAmount, squaredInput);
	}

	public void stop() {
		//differentialDrive.stopMotor();
		differentialDrive.arcadeDrive(0.0, 0.0);
	}

	public void gyroCorrectMove(double forwardBackwardStickValue, double rotateStickValue, double mod,
			boolean useDelay) {
		gyroCorrectMove(forwardBackwardStickValue, rotateStickValue, mod, useDelay, true);
	}

	public void gyroCorrectMove(double forwardBackwardStickValue, double rotateStickValue, double mod, boolean useDelay,
			boolean squaredInput) {
		double robotAngle = RobotMap.navX.getRobotAngle();
		double correctionEquation = (m_savedAngle - robotAngle)*kProportion;

		double newForwardBackwardStickValue = 0;
		double newRotateStickValue = 0;

		if (!useDelay) {
			newForwardBackwardStickValue = forwardBackwardStickValue * mod;
			newRotateStickValue = correctionEquation;

			
		} else if (forwardBackwardStickValue == 0 && rotateStickValue == 0) {

			m_savedAngle = robotAngle;
			newForwardBackwardStickValue = 0;
			newRotateStickValue = 0;
		} else if (rotateStickValue != 0) {
			courseCorrectionDelay = 0;

			m_savedAngle = robotAngle;
			newForwardBackwardStickValue = forwardBackwardStickValue * mod;
			newRotateStickValue = rotateStickValue * mod;
		} else if (courseCorrectionDelay > 25) {
			// disable correction for half a second after releasing the turn stick, to allow
			// the driver
			// to let the machine drift naturally, and not correct back to the gyro reading
			// from
			// the instant the driver released the turn stick.

			// reassign the correctionEquation to the latest direction that we've been "free
			correctionEquation = (m_savedAngle - robotAngle)*kProportion;
			newForwardBackwardStickValue = forwardBackwardStickValue * mod;
			newRotateStickValue = correctionEquation;

		} else {
			// should all cases fail, just drive normally
			newForwardBackwardStickValue = forwardBackwardStickValue * mod;
			newRotateStickValue = rotateStickValue;
		}

		if(courseCorrectionDelay == 24 && useDelay){
			// take the most recent course after half a second and make that our angle
			m_savedAngle = robotAngle;
		}
		Trace.getInstance().addTrace(false, "GyroCorrection",
				new TracePair("forwardBackwardStickValue", newForwardBackwardStickValue),
				new TracePair("SavedAngle", m_savedAngle),
				new TracePair("kProportion", kProportion),
				new TracePair("robotAngle", robotAngle),
				new TracePair("correctionEquation", correctionEquation));

		Robot.driveTrain.move(forwardBackwardStickValue*mod, newRotateStickValue*0.7, squaredInput);
		courseCorrectionDelay++;
		
	}

	private class EncoderMPOut implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			gyroCorrectMove(output, 0.0, 1.0, true, false);
		}
	}

	private class EncoderMPIn implements MPSource {

		@Override
		public double getPosition() {
			return getEncoderTicks();
		}

		@Override
		public double getVelocity() {
			return getTalonVelocity();
		}

	}

	public void initializeEncoderMP() {
		EncoderMPIn encoderMPIn = new EncoderMPIn();
		EncoderMPOut encoderPIDOut = new EncoderMPOut();
		m_encoderMotionProfilingController = new MotionProfilingController(m_encoderMPPositionkp, m_encoderMPPositionki,
				m_encoderMPPositionkd, m_encoderMPVelocitykp, m_encoderMPVelocityki, m_encoderMPVelocitykd,
				m_encoderMPVelocitykf, kEncoderMaxVelocity, kEncoderMaxAcceleration, kEncoderMaxJerk, encoderMPIn,
				encoderPIDOut);
		m_encoderMotionProfilingController.setAbsoluteTolerance(kEncoderMPTolerance);
		LiveWindow.add(m_encoderMotionProfilingController);
		m_encoderMotionProfilingController.setName("EncoderMP", "EncoderMP");
	}

	public void enableEncoderMP(double setpoint) {
		m_encoderMotionProfilingController.setSetpoint(setpoint);
		m_encoderMotionProfilingController.enable();
	}

	public boolean isDoneEncoderMP() {

		return m_encoderMotionProfilingController.onTarget();
	}

	public void disableEncoderMP() {
		m_encoderMotionProfilingController.disable();
	}

	public double getTalonVelocity() {
		return leftBottomTalon.getSelectedSensorVelocity(0) * 10;
	}

	public double getEncoderPosition() {
		return leftBottomTalon.getSelectedSensorPosition(0);
	}

	private class GyroMPOut implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			gyroCorrectMove(0.0, output, 1.0, true, false);
		}

	}

	private class GyroMPIn implements MPSource {
		double outlierCount = 25.0;
		@Override
		public double getPosition() {
			return getGyroPosition();
		}

		@Override
		public double getVelocity() {
			double encoderVelocity = getTalonVelocity();
			double rotationalVelocity = -1.0 * (encoderVelocity * KDEGREES_PER_ENCODER_TICK);
			return rotationalVelocity;
		}
	}

	public void initializeGyroMP() {
		GyroMPIn gyroMPIn = new GyroMPIn();
		GyroMPOut gyroMPOut = new GyroMPOut();
		m_gyroMotionProfilingController = new MotionProfilingController(m_gyroMPPositionkp, m_gyroMPPositionki,
				m_gyroMPPositionkd, m_gyroMPVelocitykp, m_gyroMPVelocityki, m_gyroMPVelocitykd, m_gyroMPVelocitykf,
				kGyroMaxVelocity, kGyroMaxAcceleration, kGyroMaxJerk, gyroMPIn, gyroMPOut);
		m_gyroMotionProfilingController.setAbsoluteTolerance(kGyroMPTolerance);
		LiveWindow.add(m_gyroMotionProfilingController);
		m_gyroMotionProfilingController.setName("GyroMP", "GyroMP");
	}

	public void enableGyroMP(double setpoint) {
		m_gyroMotionProfilingController.setSetpoint(setpoint);
		m_gyroMotionProfilingController.enable();
	}

	public boolean isDoneGyroMP() {
		return m_gyroMotionProfilingController.onTarget();
	}

	public void disableGyroMP() {
		m_gyroMotionProfilingController.disable();
	}
	

	public double getGyroPosition() {

		return RobotMap.navX.getRobotAngle();
	}
	
	public void resetRobotState() {
		courseCorrectionDelay = 0;
		m_savedAngle = RobotMap.navX.getRobotAngle();
	}

}

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

import org.usfirst.frc4905.Galaktika.RobotMap;
import org.usfirst.frc4905.Galaktika.Ultrasonic;
import org.usfirst.frc4905.Galaktika.commands.TeleOpDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import org.usfirst.frc4905.Galaktika.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {
	//TODO: Find value of encoder ticks per inch by rolling the robot
	//TODO: and measuring the distance in inches and ticks.
	//TODO: The following value is simply a guess from Hardware.
	public static final double ENCODER_TICKS_PER_INCH = 1000;




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

    private double m_positionPIDkp = 0;
	private double m_positionPIDki = 0;
	private double m_positionPIDkd = 0;
	private double m_positionPIDIAccum = 0.0;
	private double m_positionPIDPreviousPositionError = Double.NaN;
	private double m_positionPIDPreviousPosition = Double.NaN;
	private double iTerm = 0.0;
	private double dTerm = 0.0;
	private double pTerm = 0.0;
	private double m_positionError;
	private double m_positionPIDError;
	public double getPositionPIDError() {return m_positionPIDError;}
	public double getPreviousPositionError() {return m_positionPIDPreviousPositionError;}
	public double getPositionError() {return m_positionError;}
	public double getPTerm() {return pTerm;}
	public double getITerm() {return iTerm;}
	public double getDTerm() {return dTerm;}

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Encoder PID
    private double m_encoderPIDP = 0.0001;
    private double m_encoderPIDI = 0;
    private double m_encoderPIDD = 0;
    private double m_encoderPIDF = 0;
    private double m_encoderPIDOutputMax = 1;
    private double m_encoderPIDTolerance = 1000;

   	// Ultrasonic PID
	private PIDController m_ultrasonicPID;
	private double m_P=.2;
	private double m_I=.00000;
	private double m_D=.0;
	private double m_maxSpeed=1;
	private double m_f=0;
	private double m_tolerance=1;
	private double m_noiseTolerance = 64;
	private double m_pingDelay = 0.02;
	private int m_timesDistanceAveraged = 5;

    public DriveTrain() {
    	leftBottomTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
    	leftBottomTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	frontUltrasonic.setEnabled(true);
    	frontUltrasonic.setAutomaticMode(true);
		UltrasonicPIDOutputFront ultraPIDOutput = new UltrasonicPIDOutputFront();
		UltrasonicPIDin pdIn = new UltrasonicPIDin();
		m_ultrasonicPID = new PIDController(m_P, m_I, m_D, m_f,
				pdIn, ultraPIDOutput);
		m_ultrasonicPID.setAbsoluteTolerance(m_tolerance);
		m_ultrasonicPID.setOutputRange(-m_maxSpeed, m_maxSpeed);
		LiveWindow.add(m_ultrasonicPID);
		m_ultrasonicPID.setName("DriveTrain","Ultrasonic PID");
		frontUltrasonic.SetUltrasonicNoiseTolerance(m_noiseTolerance);
		frontUltrasonic.SetUltrasonicPingDelay(m_pingDelay);
		frontUltrasonic.SetUltrasonicAveragedAmount(m_timesDistanceAveraged);
    }

	//Ultrasonic Code - Begins

	public double getDistanceFromFront(){
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
		Trace.getInstance().addTrace("MoveWithUltrasonic",
				new TracePair("Current Distance", getDistanceFromFront()),
				new TracePair("PID Error", m_ultrasonicPID.getError()),
				new TracePair("PID Output", m_ultrasonicPID.get()));
		return m_ultrasonicPID.onTarget();
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
			move(output, 0);
		}
	}

	public void intializeUltrasonicPIDFront(double distanceToDriveTo) {
		moveWithUltrasonicPID(distanceToDriveTo);

	}

	//Ultrasonic Code - Ends

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
    private class EncoderPIDIn implements PIDSource{

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
			return getEncoderTicks();
		}

    }
    private class EncoderPIDOut implements PIDOutput{
    	public void pidWrite(double output) {
    		// Negation causes forward movement for positive values
    		move(-output, 0);
    	}
    }

   public void initializeEncoderPID(){
	    EncoderPIDIn encoderPIDIn = new EncoderPIDIn();
		EncoderPIDOut encoderPIDOut = new EncoderPIDOut();
		m_encoderPID = new PIDController(m_encoderPIDP, m_encoderPIDI, m_encoderPIDD, m_encoderPIDF, encoderPIDIn, encoderPIDOut);
		m_encoderPID.setOutputRange(-m_encoderPIDOutputMax, m_encoderPIDOutputMax);
		m_encoderPID.setAbsoluteTolerance(m_encoderPIDTolerance);
   }

   public void enableEncoderPID(double setpoint) {
	   double currentEncoderPosition = getEncoderTicks();
	   m_encoderPID.setSetpoint(setpoint + currentEncoderPosition);
	   m_encoderPID.enable();
   }

   public boolean isDoneEncoderPID() {
	   return m_encoderPID.onTarget();
   }

   public void disableEncoderPID() {
	   m_encoderPID.disable();
   }
    private PIDController m_gyroPIDSource;
    private class GyroPIDIn implements PIDSource {
    	public void setPIDSourceType(PIDSourceType PIDSource) {

    	}
    	public PIDSourceType getPIDSourceType() {
    		return PIDSourceType.kDisplacement;
    	}
		public double pidGet() {
			return RobotMap.navX.getRobotAngle();
		}
    }
    private class GyroPIDOut implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			move(0,output);
		}
    }
    public void initGyroPIDDeltaAngle() {
    	GyroPIDIn gyroPIDIn = new GyroPIDIn();
    	GyroPIDOut gyroPIDOut = new GyroPIDOut();
    	double gyroPIDP = 0.1;
    	double gyroPIDI = 0.0;
    	double gyroPIDD = 0.0;
    	double gyroPIDF = 0.0;
    	double gyroPIDOutputRange = 0.5;
    	double gyroPIDAbsTolerance = 1;
    	m_gyroPIDSource = new PIDController(gyroPIDP, gyroPIDI, gyroPIDD, gyroPIDF, gyroPIDIn, gyroPIDOut);
    	m_gyroPIDSource.setOutputRange(-gyroPIDOutputRange, gyroPIDOutputRange);
    	m_gyroPIDSource.setAbsoluteTolerance(gyroPIDAbsTolerance);

    }
    public void enableGyroPID(double setPoint) {
    	double endAngle = RobotMap.navX.getRobotAngle() + setPoint;
    	m_gyroPIDSource.setSetpoint(endAngle);
    	m_gyroPIDSource.enable();

    }
    public boolean gyroPIDIsDone() {
    	Trace.getInstance().addTrace("GyroPID",
				new TracePair("Target", m_gyroPIDSource.getSetpoint()),
				new TracePair("Robot Angle", RobotMap.navX.getRobotAngle()),
				new TracePair("Avg Error", m_gyroPIDSource.getError()),
				new TracePair("Output", m_gyroPIDSource.get()));
    	return m_gyroPIDSource.onTarget();
    }

    public void stopGyroPid() {
    	m_gyroPIDSource.disable();
    }


	public double getEncoderTicks() {
		// TODO Auto-generated method stub
		return leftBottomTalon.getSelectedSensorPosition(0);
	}
    public void move(double forwardBackSpeed, double rotateAmount) {
    		//Rotation was inverted, -rotation fixes that
    		differentialDrive.arcadeDrive(forwardBackSpeed, -rotateAmount);
    }
    public void stop() {
    		differentialDrive.stopMotor();
    }

    public void initializePositionPID() {
		m_positionPIDPreviousPositionError = Double.NaN;
		m_positionPIDIAccum = 0.0;
		m_positionPIDPreviousPosition = Double.NaN;
	}

    public double getPositionPIDOut(double setpoint) {
		m_positionError = setpoint - getEncoderTicks();

		if (Double.isNaN(m_positionPIDPreviousPosition)) {
			m_positionPIDPreviousPositionError = m_positionError;
			dTerm = 0.0;
		} else {
			m_positionPIDPreviousPositionError = setpoint - m_positionPIDPreviousPosition;
		}
		m_positionPIDIAccum += m_positionError;
		pTerm = m_positionError*m_positionPIDkp;
		iTerm = m_positionPIDIAccum*m_positionPIDki;
		if (m_positionError - m_positionPIDPreviousPositionError != 0) {

			dTerm = (m_positionError - m_positionPIDPreviousPositionError)*m_positionPIDkd;

		} else {

		}
		m_positionPIDPreviousPosition = getEncoderTicks();
		m_positionPIDError = m_positionError;
		return pTerm + iTerm + dTerm;
	}

    public double getVelocity() {
		return leftBottomTalon.getSelectedSensorVelocity(0)*10;
	}

    public double getEncoderPosition() {
		return leftBottomTalon.getSelectedSensorPosition(0);
	}

    public void setAllDriveControllersVelocity(double value) {
    		System.out.println("THe value being put into the speed controllers is: " + value);
	    	leftBottomTalon.set(ControlMode.Velocity, value);
	    	rightBottomTalon.set(ControlMode.Velocity, -value);
	    	leftTopTalon.set(ControlMode.Velocity, value);
	    	rightTopTalon.set(ControlMode.Velocity, -value);

	}
    public void setAllDriveControllersPercentVBus(double value) {
	    	leftBottomTalon.set(ControlMode.PercentOutput, value);
	    	rightBottomTalon.set(ControlMode.PercentOutput, -value);
	    	leftTopTalon.set(ControlMode.PercentOutput, value);
	    	rightTopTalon.set(ControlMode.PercentOutput, -value);
    }
}


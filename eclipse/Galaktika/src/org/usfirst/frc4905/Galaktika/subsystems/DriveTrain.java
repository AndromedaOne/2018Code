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
import org.usfirst.frc4905.Galaktika.commands.TeleOpDrive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {

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

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private double m_encoderPIDP = 0;
    private double m_encoderPIDI = 0;
    private double m_encoderPIDD = 0;
    private double m_encoderPIDF = 0;
    private double m_encoderPIDOutputMax = 1;
    private double m_encoderPIDTolerance = 10;
    
    
    public DriveTrain() {
    	leftBottomTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
    	leftBottomTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    }
    
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
    		move(output, 0);
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
    public void rotateToAngle(double angle) {
    	RobotMap.navX.turnWithGyroPID(angle);
    }
}


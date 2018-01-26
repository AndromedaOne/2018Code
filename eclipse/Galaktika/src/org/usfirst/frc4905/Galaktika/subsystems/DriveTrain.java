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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Utilities.Trace;
import Utilities.TracePair;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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

    public DriveTrain() {
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
    }

   	// Ultrasonic PID
	private PIDController m_ultrasonicPID;
	private double m_P=.02;
	private double m_I=.00000;
	private double m_D=.0;
	private double m_maxSpeed=1;
	private double m_f=0;
	private double m_tolerance=1;

	//Ultrasonic Code - Begins
	private double m_oldDistance;
	public double getDistanceFromFront(){
		double currentDistance = frontUltrasonic.getRangeInches();
		if((currentDistance - m_oldDistance) > 200) {
			return(m_oldDistance);
		}
		m_oldDistance = currentDistance;
		return currentDistance;
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
			
	    	differentialDrive.arcadeDrive(output, 0, false);
				
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


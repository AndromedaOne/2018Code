// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4905.Galaktika;

import java.io.IOException;

import org.usfirst.frc4905.Galaktika.Sensors.VL6180;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import Utilities.NavxGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static WPI_TalonSRX driveTrainLeftTopTalon;
    public static WPI_TalonSRX driveTrainLeftBottomTalon;
    public static SpeedControllerGroup driveTrainLeftSpeedController;
    public static WPI_TalonSRX driveTrainRightTopTalon;
    public static WPI_TalonSRX driveTrainRightBottomTalon;
    public static SpeedControllerGroup driveTrainRightSpeedController;
    public static DifferentialDrive driveTrainDifferentialDrive;
    public static Compressor driveTrainCompressor;
    public static Ultrasonic driveTrainFrontUltrasonic;
    public static SpeedController intakeLeftController;
    public static SpeedController intakeRightController;
    public static Solenoid rampsSolenoid1;
    public static Solenoid rampsSolenoid2;
    public static Solenoid rampsSolenoid3;
    public static Solenoid rampsSolenoid4;
    public static WPI_TalonSRX elevatorElevatorContoller;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static VL6180 rangeFinder;

    public static NavxGyro navX;
    public static DoubleSolenoid jawsSolenoid;

    public static DigitalOutput redVal;
	public static DigitalOutput greenVal;
	public static DigitalOutput blueVal;


    private static class LiveWindow {

		public static void addActuator(String subsystem, String name,
				Sendable sendable) {
			sendable.setName(subsystem, name);
		}

		public static void addSensor(String moduleType, String channel, Sendable component) {
			component.setName(moduleType, channel);;
		}

    }

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainLeftTopTalon = new WPI_TalonSRX(1);


        driveTrainLeftBottomTalon = new WPI_TalonSRX(2);


        driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftTopTalon, driveTrainLeftBottomTalon  );
        LiveWindow.addActuator("DriveTrain", "LeftSpeedController", driveTrainLeftSpeedController);

        driveTrainRightTopTalon = new WPI_TalonSRX(3);


        driveTrainRightBottomTalon = new WPI_TalonSRX(4);


        driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightTopTalon, driveTrainRightBottomTalon );
        LiveWindow.addActuator("DriveTrain", "RightSpeedController", driveTrainRightSpeedController);

        driveTrainDifferentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);
        LiveWindow.addActuator("DriveTrain", "DifferentialDrive", driveTrainDifferentialDrive);
        driveTrainDifferentialDrive.setSafetyEnabled(true);
        driveTrainDifferentialDrive.setExpiration(0.1);
        driveTrainDifferentialDrive.setMaxOutput(1.0);

        // driveTrainCompressor = new Compressor(0);
        // LiveWindow.addActuator("DriveTrain", "Compressor", driveTrainCompressor);

        driveTrainFrontUltrasonic = new Ultrasonic(0, 1);
        LiveWindow.addSensor("DriveTrain", "FrontUltrasonic", driveTrainFrontUltrasonic);

        /*intakeLeftController = new Spark(0);
        LiveWindow.addActuator("Intake", "LeftController", (Spark) intakeLeftController);
        intakeLeftController.setInverted(false);
        intakeRightController = new Spark(1);
        LiveWindow.addActuator("Intake", "RightController", (Spark) intakeRightController);
        intakeRightController.setInverted(false);
        /*rampsSolenoid1 = new Solenoid(0, 0);
        LiveWindow.addActuator("Ramps", "Solenoid1", rampsSolenoid1);

        rampsSolenoid2 = new Solenoid(0, 1);
        LiveWindow.addActuator("Ramps", "Solenoid2", rampsSolenoid2);

        rampsSolenoid3 = new Solenoid(0, 2);
        LiveWindow.addActuator("Ramps", "Solenoid3", rampsSolenoid3);

        rampsSolenoid4 = new Solenoid(0, 3);
        LiveWindow.addActuator("Ramps", "Solenoid4", rampsSolenoid4);*/

        //elevatorElevatorContoller = new WPI_TalonSRX(5);



    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        try {
			rangeFinder = new VL6180(I2C.Port.kOnboard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LiveWindow.addSensor("DriveTrain", "RangeFinder", rangeFinder);
        navX = new NavxGyro();
        // jawsSolenoid = new DoubleSolenoid(0, 4, 5);

        //LED PWM Port assignments
        redVal = new DigitalOutput(2);
        redVal.enablePWM(0);
        blueVal = new DigitalOutput(3);
        blueVal.enablePWM(0);
        greenVal = new DigitalOutput(4);
        greenVal.enablePWM(0);
    }
}

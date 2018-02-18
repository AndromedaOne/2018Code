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
import org.usfirst.frc4905.Galaktika.commands.ElevatorManualControl;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Elevator extends Subsystem {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX elevatorController = RobotMap.elevatorElevatorController;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private DigitalInput elevatorBottomLimitSwitch = RobotMap.elevatorBottomLimitSwitch;
	//private DigitalInput elevatorTopLimitSwitch = RobotMap.elevatorTopLimitSwitch;

	private double m_encoderPIDP = 0.0001;
	private double m_encoderPIDI = 0;
	private double m_encoderPIDD = 0;
	private double m_encoderPIDF = 0;
	private double m_encoderPIDOutputMax = 0.5;
	private double m_encoderPIDTolerance = 1000;

	private double m_encoderZeroPostion =  0.0;
	// TODO We need to find the top level postion in encoder ticks
	private static double m_encoderTopPosition = 15;

	public Elevator() {
		initializeEncoderPID();
		
	}

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ElevatorManualControl());
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
			return elevatorController.getSelectedSensorPosition(0);
		}

		@Override
		public void initSendable(SendableBuilder builder) {
			// TODO Auto-generated method stub
			builder.setSmartDashboardType("Counter");
			builder.addDoubleProperty("Value", this::pidGet, null);
		}

	}
	private class EncoderPIDOut implements PIDOutput{
		@Override
		public void pidWrite(double output) {
			moveElevatorSafely(output);
		}
	}

	public void initializeEncoderPID(){
		EncoderPIDIn encoderPIDIn = new EncoderPIDIn();
		EncoderPIDOut encoderPIDOut = new EncoderPIDOut();
		m_encoderPID = new PIDController(m_encoderPIDP, m_encoderPIDI, m_encoderPIDD, m_encoderPIDF, encoderPIDIn, encoderPIDOut);
		m_encoderPID.setOutputRange(-m_encoderPIDOutputMax, m_encoderPIDOutputMax);
		m_encoderPID.setAbsoluteTolerance(m_encoderPIDTolerance);
		LiveWindow.add(encoderPIDIn);
		encoderPIDIn.setName("Elevator", "Encoder");
		LiveWindow.add(m_encoderPID);
		m_encoderPID.setName("Elevator","Encoder PID");

	}

	public void enableEncoderPID(double setpoint) {
		double currentEncoderPosition = elevatorController.getSelectedSensorPosition(0);
		m_encoderPID.setSetpoint(setpoint + currentEncoderPosition);
		m_encoderPID.enable();
	}

	public boolean isDoneEncoderPID() {
		return m_encoderPID.onTarget();
	}

	public void disableEncoderPID() {
		m_encoderPID.disable();
	}

	/*public boolean getTopLimitSwitch() {
		return elevatorTopLimitSwitch.get();
	}*/
	public boolean getBottomLimitSwitch() {
		return elevatorBottomLimitSwitch.get();
	}
	public void moveElevator(double velocity) {
		elevatorController.set(velocity);
		System.out.println("Elevator velocity: "+ velocity + " Encoder Position: " + getElevatorEncoderPosition());
	}
	// Disabled for now until sensors are hooked up

	   public void moveElevatorSafely(double velocity) {
		if((getElevatorPosition() > m_encoderTopPosition) == true && (velocity > 0)) {
		moveElevator(0);

		//if the pid loop drives somewhere unsafe, we should probably end the PID loop if its running
		disableEncoderPID();
		}
		else if ((Robot.elevator.getBottomLimitSwitch() == true) && (velocity < 0)) {
		moveElevator(0);
		resetEncoder();
		//if the pid loop is driving us somewhere unsafe, probably wanna disable it man...
		disableEncoderPID();

		}
		else {
		moveElevator(velocity);
		}
		
		System.out.println("Elevator velocity: "+ velocity + " Encoder Position: " + getElevatorEncoderPosition());
		
	}
	public void stopElevator() {
		moveElevator(0);
	}
	
	public double getElevatorEncoderPosition(){
		return elevatorController.getSelectedSensorPosition(0);
	}

	public void resetEncoder() {
		if (getBottomLimitSwitch()) {
			elevatorController.setSelectedSensorPosition(0, 0, 10);
		}
	}
	public double getElevatorPosition() {

		return elevatorController.getSelectedSensorPosition(0) -
				m_encoderZeroPostion;
	}

	public boolean getPidEnabledStatus(){
		return m_encoderPID.isEnabled();
	}

}
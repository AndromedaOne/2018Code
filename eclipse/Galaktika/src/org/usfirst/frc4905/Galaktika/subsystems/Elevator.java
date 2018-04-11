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

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
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
	// Encoder Revolution Constants
	public static final double GROUND_LEVEL = 10.0;
	public static final double EXCHANGE_HEIGHT = 200.0;
	public static final double SWITCH_HEIGHT = 2500.0;
	public static final double LOW_SCALE_HEIGHT = 3000.0;
	public static final double HIGH_SCALE_HEIGHT = 4300.0;
	public static final double MAX_HEIGHT = 4775.0;
	
	private boolean noisyDebug = false;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX elevatorController = RobotMap.elevatorElevatorController;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private DigitalInput elevatorBottomLimitSwitch = RobotMap.elevatorBottomLimitSwitch;

	private DigitalInput elevatorTopLimitSwitch = RobotMap.elevatorTopLimitSwitch;

	private double m_encoderPIDP_maintanence = 0.0;//0.00003;//p constant for maintaining position, way too big for distance traveling
	private double m_encoderPIDP_travel = 0.01;//p constant for traveling up or down on the elevator
	private double m_encoderPIDI_travel = 0.0005;
	private double m_encoderPIDI_maintanence = 0.0;
	private double m_encoderPIDD = 0;
	private double m_encoderPIDF = 0;
	private double m_encoderPIDOutputMax = 1.0;
	private double m_encoderPIDTolerance = 10;
	
	private double m_encoderPIDOutputRange = 0.25;
	
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

			// Negated because encoder and motor count in opposite directions
			output *= -1;
			double kMinOutput = 0.15;
			if(output != 0.0) {
				boolean outputLessThanZero = (output < 0.0) ? true : false;
				output = (Math.abs(output)*(1-kMinOutput))+kMinOutput;
				if(outputLessThanZero) {
					output = -output;
				}
			}
			if (Math.abs(output) >= m_encoderPIDOutputMax) {
				m_encoderPID.setI(0.0);
			}else {
				if(Robot.AutonomousMode) {
					m_encoderPID.setI(m_encoderPIDI_travel);
				}
			}
			moveElevatorSafely(output);
			if(noisyDebug) {
				System.out.println("In Elevator pidWrite output = " + output +
						" Current error = " + m_encoderPID.getError() + "Current Position: " + getElevatorEncoderPosition());
			}
			Trace.getInstance().addTrace(true, "ElevatorPID", 
					new TracePair("Position", getElevatorEncoderPosition()),
					new TracePair("Setpoint", m_encoderPID.getSetpoint()),
					new TracePair("Average Error", m_encoderPID.getError()),
					new TracePair("Output", output * 1000));
		}
	}

	public void initializeEncoderPID(){
		EncoderPIDIn encoderPIDIn = new EncoderPIDIn();
		EncoderPIDOut encoderPIDOut = new EncoderPIDOut();
		m_encoderPID = new PIDController(m_encoderPIDP_maintanence, 0.0, m_encoderPIDD, m_encoderPIDF, encoderPIDIn, encoderPIDOut);
		m_encoderPID.setOutputRange(-m_encoderPIDOutputMax, m_encoderPIDOutputMax);
		m_encoderPID.setAbsoluteTolerance(m_encoderPIDTolerance);
		LiveWindow.add(encoderPIDIn);
		encoderPIDIn.setName("Elevator", "Encoder");
		LiveWindow.add(m_encoderPID);
		m_encoderPID.setName("Elevator","Encoder PID");

	}

	public void enableEncoderPID(double setpoint) {
		double currentEncoderPosition = elevatorController.getSelectedSensorPosition(0);
		m_encoderPID.setSetpoint(setpoint);
		if (setpoint > currentEncoderPosition) {
			m_encoderPID.setAbsoluteTolerance(300);
		} else if (setpoint < currentEncoderPosition) {
			m_encoderPID.setAbsoluteTolerance(20);
		}
		if (noisyDebug) {
			System.out.println("In Elevator enableEncoderPID setpoint = " + setpoint);
		}
		m_encoderPID.enable();
	}

	public boolean isDoneEncoderPID() {
		boolean onTarget = m_encoderPID.onTarget();
		if(onTarget) {
			System.out.println("Elevator Encoder PID ON Target!!!");
			System.out.println("Current Encoder pos: " + getElevatorEncoderPosition());
			
		}
		if((!getBottomLimitSwitch()) && (m_encoderPID.get() < 0.0)) {
			System.out.println("m_encoderPID.get(): " + m_encoderPID.get());
			return true;
		}
		return onTarget;
	}

	public void disableEncoderPID() {
		m_encoderPID.reset();
		m_encoderPID.disable();
		
	}
	
	public void freeEncoderPID() {
		m_encoderPID.free();
	}


	public boolean getBottomLimitSwitch() {
		return elevatorBottomLimitSwitch.get();
	}

	public boolean getTopLimitSwitch() {
		return elevatorTopLimitSwitch.get();
	}

	private void moveElevator(double velocity) {
		elevatorController.set(velocity);
	}


	public void moveElevatorSafely(double speed) {
		//negative velocities  drive the elevator up, positive velocities drive it down...

		if(getTopLimitSwitch() == false && (speed < 0)) {
			//trying to go upwards when speed < 0, false on the limit switch means it is triggered
			if(getPidEnabledStatus() == true){
				
				
				
				//stop a little short of the top, has to be debugged.
				disableEncoderPID();
			}
			//System.out.println("HITTING TOP LIMIT SWITCH");
			moveElevator(0);
			//if the pid loop drives somewhere unsafe, we should probably end the PID loop if its running
		}
		else if ((getBottomLimitSwitch() == false) && (speed >= 0)) {
			//if the limit switch is pressed, then it returns a false
			//speeds > 0 are going down
			if(getPidEnabledStatus() == true){
				//hold ourselves a little off the bottom.
				
				disableEncoderPID();
			}
			resetEncoder();
			//System.out.println("HITTING BOTTOM LIMIT SWITCH");
			moveElevator(0);

		}
		else {
			if(speed > 0) {
				speed = speed * 0.6;
			}
			moveElevator(speed);
		}

	}

	public void stopElevator() {
		moveElevator(0.0);
	}

	public double getElevatorEncoderPosition(){
		return elevatorController.getSelectedSensorPosition(0);
	}

	public void resetEncoder() {

		elevatorController.setSelectedSensorPosition(0, 0, 10);

	}
	public double getElevatorPosition() {
		return elevatorController.getSelectedSensorPosition(0); 				
	}
	public boolean getPidEnabledStatus(){
		
		return m_encoderPID.isEnabled();
	}

	public void setPidLoopTolerance(double tolerance){
		m_encoderPID.setAbsoluteTolerance(tolerance);
	}

	public void setPIDControllerToTravelMode(){
		m_encoderPID.setPID(m_encoderPIDP_travel, m_encoderPIDI_travel, m_encoderPIDD);
		setToleranceForTravelMode();
		if(noisyDebug) {
			System.out.println("Switching to Travel mode");
		}
		//have a lower tolerance, just want to get close, then we'll switch to maintanence mode and more aggressive correction constants
	}

	public void setPIDControllerToMaintenanceMode(){
		m_encoderPID.setPID(m_encoderPIDP_maintanence, m_encoderPIDI_maintanence, m_encoderPIDD);
		setToleranceForMaintenanceMode();
		if(noisyDebug) {
			System.out.println("Switching to Maintenance mode");
		}
	}

	public void setToleranceForTravelMode(){
		m_encoderPID.setAbsoluteTolerance(200);

	}

	public void setToleranceForMaintenanceMode(){
		m_encoderPID.setAbsoluteTolerance(0);
	}

	public double getTopEncoderPosition() {
		return MAX_HEIGHT;
	}
	
	public double getEncoderError(){
		return m_encoderPID.getError();
	}
	
}
package Utilities;

import java.util.TimerTask;

import com.kauailabs.navx.frc.AHRS;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavxGyro {

	// gyroEncoder PID controller

	// gyroEncoder PID controller variables
	private static final double gyroEncoderKp = 0.012;
	private static final double gyroEncoderKi = 0.000;
	private static final double gyroEncoderKd = 0.000;
	private static final double gyroEncoderKf = 0.000;
	private static final double gyroEncoderTolerance = 2.0;
	private static final double gyroEncoderOutputMax = 0.6 ;
	private boolean angleReadingSet;
	private double m_initialAngleReading = 0;

	private static String m_traceFileName = "GyroValues";

	private AHRS m_navX;
	// Timer for control loop to initialize navX gyro
	private java.util.Timer m_controlLoop;
	private long kDefaultPeriod = 50;
	private long kDelay = 3000; // 3 second delay

	public NavxGyro() {
		try {
			/* Communicate w/navX MXP via the MXP SPI Bus.                                     */
			/* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
			/* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
			m_navX = new AHRS(SPI.Port.kMXP);
			angleReadingSet = false;
			System.out.println("Created NavX instance");
			// New thread to initialize the initial angle
			m_controlLoop = new java.util.Timer();
			SetInitialAngleReading task = new SetInitialAngleReading(this);
			m_controlLoop.schedule(task, kDelay, kDefaultPeriod);
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(),
					true);
		}
	}

	private class SetInitialAngleReading extends TimerTask {

		private NavxGyro m_navxGyro;
		public SetInitialAngleReading(NavxGyro navxGyro) {
			m_navxGyro = navxGyro;
		}

		@Override
		public void run() {
			System.out.println("In run");
			if (!isCalibrating()) {
				m_navxGyro.setInitialAngleReading();
				cancel();
			}
		}
	}

	public AHRS getAHRS() {
		return m_navX;
	}
	private void setInitialAngleReading() {
		if (angleReadingSet) {
			System.out.println("Angle already set, not resetting");
			return;
		}else {
			m_initialAngleReading = m_navX.getAngle();
			System.out.println("Initial angle set to: " + m_initialAngleReading);
			angleReadingSet = true;
		}
	}



	private int m_getRobotAngleCount = 0;

	public double getRobotAngle() {
		double correctedAngle = m_navX.getAngle() - m_initialAngleReading;
		if((m_getRobotAngleCount % 10) == 0) {
			SmartDashboard.putNumber("Raw Anlge", m_navX.getAngle());
			SmartDashboard.putNumber("Get Robot Angle", correctedAngle);
		}
		Trace.getInstance().addTrace(true, m_traceFileName,
				new TracePair("Raw Angle", m_navX.getAngle()),
				new TracePair("Corrected Angle", correctedAngle),
				new TracePair("X Accel", (double) m_navX.getWorldLinearAccelX()),
				new TracePair("Y Accel", (double) m_navX.getWorldLinearAccelY()),
				new TracePair("Z Accel", (double) m_navX.getWorldLinearAccelZ()));

		return correctedAngle;
	}

	private class GyroPIDin implements PIDSource {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return getRobotAngle();
		}

	}





	public boolean isCalibrating() {

		return m_navX.isCalibrating();
	}


}







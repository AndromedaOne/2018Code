package Utilities;

import com.kauailabs.navx.frc.AHRS;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
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
	private static final double gyroEncoderTolerance = 1.0;
	private static final double gyroEncoderOutputMax = 0.6;
	private boolean angleReadingSet;
	private double m_initialAngleReading = 0;

	private static String m_traceFileName = "GyroValues";

	private AHRS m_navX;

	public NavxGyro() {
		try {
			/* Communicate w/navX MXP via the MXP SPI Bus. */
			/* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
			/*
			 * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
			 * details.
			 */
			m_navX = new AHRS(SPI.Port.kMXP);
			angleReadingSet = false;
			System.out.println("Created NavX instance");
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}

	public AHRS getAHRS() {
		return m_navX;
	}

	public void setInitialAngleReading() {
		if (angleReadingSet) {
			System.out.println("Angle already set, not resetting");
			return;
		} else {
			m_initialAngleReading = m_navX.getAngle();
			System.out.println("Setting initial angle to " + m_initialAngleReading);
			angleReadingSet = true;
			if (m_initialAngleReading == 0.0) {
				System.out.println("Warning: angle probably not correct");
			}
		}
	}

	private int m_getRobotAngleCount = 0;

	public double getRobotAngle() {
		double correctedAngle = m_navX.getAngle() - m_initialAngleReading;
		if ((m_getRobotAngleCount % 10) == 0) {
			SmartDashboard.putNumber("Raw Anlge", m_navX.getAngle());
			SmartDashboard.putNumber("Get Robot Angle", correctedAngle);
		}
		Trace.getInstance().addTrace(false, m_traceFileName, new TracePair("Raw Angle", m_navX.getAngle()),
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

	public double getRotationalVelocity() {
		return m_navX.getRate() * m_navX.getActualUpdateRate();
	}

}

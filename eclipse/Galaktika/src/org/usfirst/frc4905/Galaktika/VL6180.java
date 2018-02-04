package org.usfirst.frc4905.Galaktika;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Infrared rangefinder class using the ST VL6180 time of flight sensor. Sends out infrared light which then reflects and comes back to the sensor,
 * allowing for time of flight to be determined.
 * @author owen
 */

public class VL6180 extends SensorBase implements PIDSource, Sendable {

	private static final byte kAddress = 0x29;
	private static final short kModelID = 0x000;
	private double m_fakeReading = .1234;
	private boolean m_readOnce = true;
	private I2C m_i2c;

	private void readingChange() {
		m_fakeReading = m_fakeReading + .0001;
	}

	private double getSensorReading() {
		ByteBuffer id = ByteBuffer.allocate(1);
		ByteBuffer index = ByteBuffer.allocate(2);
		index.putShort(kModelID);
		boolean readSuccessful;
		readingChange();
		if (m_readOnce) {
			readSuccessful = m_i2c.transaction(index, 2, id, 1);
			System.out.println(String.format("0x%02X, readSuccessful = %b", id.get(0), readSuccessful));
			if (readSuccessful = false) {
				m_readOnce = false;
			}
		}
		return m_fakeReading;
	}

	private double readFromSensor() {
	}
	
	public VL6180(I2C.Port port) {
		HAL.report(tResourceType.kResourceType_Ultrasonic, 1);
		m_i2c = new I2C(port, kAddress);
	}



	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Ultrasonic");
		builder.addDoubleProperty("Value", this::getSensorReading, null);
		// TODO Auto-generated method stub

	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}

}

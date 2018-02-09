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
	private static final short kFreshOutReset = 0x016;
	private static final short kSYSRangeStart = 0x018;
	private static final short kSYSRangeIgnore = 0x026;
	private static final short kResultRange = 0x062;

	private I2C m_i2c;

	private double getSensorReading() {
		byte distance = readByteFromSensor(kResultRange);
		return distance;
	}

	private void writeByteToSensor(short index, byte value) {
		ByteBuffer writeBuff = ByteBuffer.allocate(3);
		writeBuff.putShort(index);
		writeBuff.put(value);
		m_i2c.writeBulk(writeBuff, 3);
	}

	private byte readByteFromSensor(short index) {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(1);
		indexBuff.putShort(index);
		m_i2c.transaction(indexBuff, 2, resultBuff, 1);
		return resultBuff.get();
	}

	private void writeWordToSensor(short index, short value) {
		ByteBuffer writeBuff = ByteBuffer.allocate(4);
		writeBuff.putShort(index);
		writeBuff.putShort(value);
		m_i2c.writeBulk(writeBuff, 4);
	}

	private short readWordFromSensor(short index) {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(2);
		indexBuff.putShort(index);
		m_i2c.transaction(indexBuff, 2, resultBuff, 2);
		return resultBuff.getShort();
	}

	private void enableSensor() {
		if (readByteFromSensor(kFreshOutReset) == 1) {
			writeByteToSensor(kFreshOutReset, (byte) 0);
			writeByteToSensor(kSYSRangeStart, (byte) 3);
		}
	}

	private void disableSensor() {
		if (readByteFromSensor(kFreshOutReset) == 0) {
			writeByteToSensor(kSYSRangeStart, (byte) 3);
			writeByteToSensor(kFreshOutReset, (byte) 1);
		}
	}

	public VL6180(I2C.Port port) {
		HAL.report(tResourceType.kResourceType_Ultrasonic, 1);
		m_i2c = new I2C(port, kAddress);
		System.out.println(String.format("modelID: 0x%02X", readByteFromSensor(kModelID)));
		System.out.println(String.format("freshOutReset: 0x%02X", readByteFromSensor(kFreshOutReset)));
		writeByteToSensor(kFreshOutReset, (byte) 0);
		System.out.println(String.format("newFreshOutReset: 0x%02X", readByteFromSensor(kFreshOutReset)));
		writeByteToSensor(kSYSRangeStart, (byte) 3);
		System.out.println(String.format("SYSRangeStart: 0x%02X", readByteFromSensor(kSYSRangeStart)));
		System.out.println(String.format("SYSRangeIgnore: 0x%04X", readWordFromSensor(kSYSRangeIgnore)));
		disableSensor();
		enableSensor();

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

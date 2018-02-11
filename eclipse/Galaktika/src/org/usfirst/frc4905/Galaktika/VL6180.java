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
	private static final short kSystemInterruptClear = 0x015;
	private static final short kFreshOutReset = 0x016;
	private static final short kSYSRangeStart = 0x018;
	private static final short kSYSRangeCrossTalkCompensationRate = 0x01E;
	private static final short kSYSRangeIgnore = 0x026;
	private static final short kResultInterruptStatusGPIO = 0x04F;
	private static final short kResultRange = 0x062;

	private I2C m_i2c;
	private byte m_distance;

	private double getSensorReading() {
		//System.out.println(String.format("NewSampleThresholdPoll: 0x%02X", readByteFromSensor((short) 0x4f)));
		if (readByteFromSensor(kResultInterruptStatusGPIO) == 4) {
			m_distance = readByteFromSensor(kResultRange);
			writeByteToSensor(kSystemInterruptClear, (byte) 0x07);
			disableSensor();
			enableSensor();
		}
		return m_distance;
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

	private void writeIntToSensor(short index, int value) {
		ByteBuffer writeBuff = ByteBuffer.allocate(6);
		writeBuff.putShort(index);
		writeBuff.putInt(value);
		m_i2c.writeBulk(writeBuff, 6);
	}

	private int readIntFromSensor(short index) {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(4);
		indexBuff.putShort(index);
		m_i2c.transaction(indexBuff, 2, resultBuff, 4);
		return resultBuff.getInt();
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
		disableSensor();
		while (readByteFromSensor((short)0x4D) == 0) {
			System.out.println(String.format("resultRangeStatus; 0x%02x", readByteFromSensor((short)0x4D)));
		}
		System.out.println(String.format("modelID: 0x%02X", readByteFromSensor(kModelID)));
		System.out.println(String.format("freshOutReset: 0x%02X", readByteFromSensor(kFreshOutReset)));
		System.out.println(String.format("newFreshOutReset: 0x%02X", readByteFromSensor(kFreshOutReset)));
		// Following code taken from the AN4545 VL6180 Application Note
		writeByteToSensor((short) 0x0207, (byte) 0x01);
		writeByteToSensor((short) 0x0208, (byte) 0x01);
		writeByteToSensor((short) 0x0096, (byte) 0x00);
		writeByteToSensor((short) 0x0097, (byte) 0xfd);
		writeByteToSensor((short) 0x00e3, (byte) 0x00);
		writeByteToSensor((short) 0x00e4, (byte) 0x04);
		writeByteToSensor((short) 0x00e5, (byte) 0x02);
		writeByteToSensor((short) 0x00e6, (byte) 0x01);
		writeByteToSensor((short) 0x00e7, (byte) 0x03);
		writeByteToSensor((short) 0x00f5, (byte) 0x02);
		writeByteToSensor((short) 0x00d9, (byte) 0x05);
		writeByteToSensor((short) 0x00db, (byte) 0xce);
		writeByteToSensor((short) 0x00dc, (byte) 0x03);
		writeByteToSensor((short) 0x00dd, (byte) 0xf8);
		writeByteToSensor((short) 0x009f, (byte) 0x00);
		writeByteToSensor((short) 0x00a3, (byte) 0x3c);
		writeByteToSensor((short) 0x00b7, (byte) 0x00);
		writeByteToSensor((short) 0x00bb, (byte) 0x3c);
		writeByteToSensor((short) 0x00b2, (byte) 0x09);
		writeByteToSensor((short) 0x00ca, (byte) 0x09);
		writeByteToSensor((short) 0x0198, (byte) 0x01);
		writeByteToSensor((short) 0x01b0, (byte) 0x17);
		writeByteToSensor((short) 0x01ad, (byte) 0x00);
		writeByteToSensor((short) 0x00ff, (byte) 0x05);
		writeByteToSensor((short) 0x0100, (byte) 0x05);
		writeByteToSensor((short) 0x0199, (byte) 0x05);
		writeByteToSensor((short) 0x01a6, (byte) 0x1b);
		writeByteToSensor((short) 0x01ac, (byte) 0x3e);
		writeByteToSensor((short) 0x01a7, (byte) 0x1f);
		writeByteToSensor((short) 0x0030, (byte) 0x00);
		writeByteToSensor((short) 0x0011, (byte) 0x10);
		writeByteToSensor((short) 0x010a, (byte) 0x30);
		writeByteToSensor((short) 0x003f, (byte) 0x46);
		writeByteToSensor((short) 0x0031, (byte) 0xFF);
		writeByteToSensor((short) 0x0040, (byte) 0x63);
		writeByteToSensor((short) 0x002e, (byte) 0x01);
		writeByteToSensor((short) 0x001b, (byte) 0x09);
		writeByteToSensor((short) 0x003e, (byte) 0x31);
		writeByteToSensor((short) 0x0014, (byte) 0x24);
		System.out.println(String.format("SYSRangeStart: 0x%02X", readByteFromSensor(kSYSRangeStart)));
		System.out.println(String.format("SYSRangeIgnore: 0x%08X", readIntFromSensor(kSYSRangeIgnore)));
		System.out.println(String.format("SYSRangeCrossTalkCompensationRate: 0x%04X", readWordFromSensor(kSYSRangeCrossTalkCompensationRate)));
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

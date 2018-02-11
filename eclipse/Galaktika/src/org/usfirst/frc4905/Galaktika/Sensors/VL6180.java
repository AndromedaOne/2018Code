package org.usfirst.frc4905.Galaktika.Sensors;

import java.io.IOException;
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
	private int m_distance;

	private double getSensorReading() throws IOException {
		if (readByteFromSensor(kResultInterruptStatusGPIO) == 4) {
			m_distance = Byte.toUnsignedInt(readByteFromSensor(kResultRange));
			writeByteToSensor(kSystemInterruptClear, (byte) 0x07);
		}
		return m_distance;
	}

	private void writeByteToSensor(short index, byte value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(3);
		writeBuff.putShort(index);
		writeBuff.put(value);
		if (m_i2c.writeBulk(writeBuff, 3)) {
			// Transaction was aborted
			throw new IOException("writeByteToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private byte readByteFromSensor(short index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(1);
		indexBuff.putShort(index);
		if (m_i2c.transaction(indexBuff, 2, resultBuff, 1)) {
			// Transaction was aborted
			throw new IOException("readByteFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.get();
	}

	private void writeWordToSensor(short index, short value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(4);
		writeBuff.putShort(index);
		writeBuff.putShort(value);
		if (m_i2c.writeBulk(writeBuff, 4)) {
			// Transaction was aborted
			throw new IOException("writeWordToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private short readWordFromSensor(short index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(2);
		indexBuff.putShort(index);
		if (m_i2c.transaction(indexBuff, 2, resultBuff, 2)) {
			// Transaction was aborted
			throw new IOException("readWordFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.getShort();
	}

	private void writeIntToSensor(short index, int value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(6);
		writeBuff.putShort(index);
		writeBuff.putInt(value);
		if (m_i2c.writeBulk(writeBuff, 6)) {
			// Transaction was aborted
			throw new IOException("writeIntToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private int readIntFromSensor(short index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(2);
		ByteBuffer resultBuff = ByteBuffer.allocate(4);
		indexBuff.putShort(index);
		if (m_i2c.transaction(indexBuff, 2, resultBuff, 4)) {
			// Transaction was aborted
			throw new IOException("readIntFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.getInt();
	}

	private void enableSensor() throws IOException {
		if (readByteFromSensor(kFreshOutReset) == 1) {
			writeByteToSensor(kFreshOutReset, (byte) 0);
			writeByteToSensor(kSYSRangeStart, (byte) 3);
		}
	}

	private void disableSensor() throws IOException {
		if (readByteFromSensor(kFreshOutReset) == 0) {
			writeByteToSensor(kSYSRangeStart, (byte) 3);
			writeByteToSensor(kFreshOutReset, (byte) 1);
		}
	}

	public VL6180(I2C.Port port) throws IOException {
		HAL.report(tResourceType.kResourceType_Ultrasonic, 1);
		m_i2c = new I2C(port, kAddress);
		disableSensor();
		while (readByteFromSensor((short)0x4D) == 0) {
			System.out.println(String.format("resultRangeStatus; 0x%02x", readByteFromSensor((short)0x4D)));
		}
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
		enableSensor();

	}



	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Ultrasonic");
		builder.addDoubleProperty("Value", this::pidGet, null);

	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (!pidSource.equals(PIDSourceType.kDisplacement)) {
			throw new IllegalArgumentException("Only displacement PID is allowed for VL6180");
		}
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		try {
			return getSensorReading();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

}

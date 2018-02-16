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

public class VL53L0X extends SensorBase implements PIDSource, Sendable {

	private static final byte kAddress = 0x29;
	private I2C m_i2c;
	private int m_distance;

	private double getSensorReading() throws IOException {
		return m_distance;
	}

	private void writeByteToSensor(byte index, byte value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(3);
		writeBuff.put(index);
		writeBuff.put(value);
		if (m_i2c.writeBulk(writeBuff, 2)) {
			// Transaction was aborted
			throw new IOException("writeByteToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private byte readByteFromSensor(byte index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(1);
		ByteBuffer resultBuff = ByteBuffer.allocate(1);
		indexBuff.putShort(index);
		if (m_i2c.transaction(indexBuff, 1, resultBuff, 1)) {
			// Transaction was aborted
			throw new IOException("readByteFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.get();
	}

	private void writeWordToSensor(byte index, byte value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(2);
		writeBuff.put(index);
		writeBuff.put(value);
		if (m_i2c.writeBulk(writeBuff, 2)) {
			// Transaction was aborted
			throw new IOException("writeWordToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private short readWordFromSensor(byte index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(1);
		ByteBuffer resultBuff = ByteBuffer.allocate(1);
		indexBuff.put(index);
		if (m_i2c.transaction(indexBuff, 1, resultBuff, 1)) {
			// Transaction was aborted
			throw new IOException("readWordFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.get();
	}

	private void writeIntToSensor(byte index, int value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(4);
		writeBuff.put(index);
		writeBuff.putInt(value);
		if (m_i2c.writeBulk(writeBuff, 4)) {
			// Transaction was aborted
			throw new IOException("writeIntToSensor: VL6180 i2c writeBulk aborted");
		}
	}

	private int readIntFromSensor(byte index) throws IOException {
		ByteBuffer indexBuff = ByteBuffer.allocate(1);
		ByteBuffer resultBuff = ByteBuffer.allocate(3);
		indexBuff.put(index);
		if (m_i2c.transaction(indexBuff, 1, resultBuff, 3)) {
			// Transaction was aborted
			throw new IOException("readIntFromSensor: VL6180 i2c transaction aborted");
		}
		return resultBuff.getInt();
	}

	private void enableSensor() throws IOException {
		/*if (readByteFromSensor(kFreshOutReset) == 1) {
			writeByteToSensor(kFreshOutReset, (byte) 0);
			writeByteToSensor(kSYSRangeStart, (byte) 3);
			
		}*/
	}

	private void disableSensor() throws IOException {
	/*	if (readByteFromSensor(kFreshOutReset) == 0) {
			writeByteToSensor(kSYSRangeStart, (byte) 3);
			writeByteToSensor(kFreshOutReset, (byte) 1);
			
		}*/
	}

	public VL53L0X(I2C.Port port) throws IOException {
		HAL.report(tResourceType.kResourceType_Ultrasonic, 1);
		m_i2c = new I2C(port, kAddress);
		disableSensor();		
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

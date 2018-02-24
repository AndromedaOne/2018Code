package org.usfirst.frc4905.Galaktika.Sensors;

import java.io.IOException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;

public class I2CSensor {
	private I2C m_i2c;

	public I2CSensor(I2C.Port port, byte sensorAddress) {
		m_i2c = new I2C(port, sensorAddress);
	}
	
	private void writeToSensor(ByteBuffer buf) throws IOException {
		if (m_i2c.writeBulk(buf, buf.capacity())) {
			// Transaction was aborted
			throw new IOException("writeToSensor: VL6180 i2c writeBulk aborted");
		}
	}
	
	public void writeByteToSensor(byte offset, byte value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(2);
		writeBuff.put(offset);
		writeBuff.put(value);
		writeToSensor(writeBuff);
	}

	public void writeByteToSensor(short offset, byte value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(3);
		writeBuff.putShort(offset);
		writeBuff.put(value);
		writeToSensor(writeBuff);
	}
	
	public void writeShortToSensor(byte offset, short value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(3);
		writeBuff.put(offset);
		writeBuff.putShort(value);
		writeToSensor(writeBuff);
	}

	public void writeShortToSensor(short offset, short value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(4);
		writeBuff.putShort(offset);
		writeBuff.putShort(value);
		writeToSensor(writeBuff);
	}
	
	public void writeIntToSensor(byte offset, int value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(5);
		writeBuff.put(offset);
		writeBuff.putInt(value);
		writeToSensor(writeBuff);
	}

	public void writeIntToSensor(short offset, int value) throws IOException {
		ByteBuffer writeBuff = ByteBuffer.allocate(6);
		writeBuff.putShort(offset);
		writeBuff.putInt(value);
		writeToSensor(writeBuff);
	}
	
	private void readFromSensor(ByteBuffer offsetBuf, ByteBuffer resultBuf) throws IOException {
		if (m_i2c.transaction(offsetBuf, offsetBuf.capacity(), resultBuf, resultBuf.capacity())) {
			// Transaction was aborted
			throw new IOException("readFromSensor: VL6180 i2c transaction aborted");
		}
	}

	public byte readByteFromSensor(byte offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(1);
		ByteBuffer resultBuf = ByteBuffer.allocate(1);
		offsetBuf.put(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.get();
	}

	public byte readByteFromSensor(short offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(2);
		ByteBuffer resultBuf = ByteBuffer.allocate(1);
		offsetBuf.putShort(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.get();
	}

	public short readShortFromSensor(byte offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(1);
		ByteBuffer resultBuf = ByteBuffer.allocate(2);
		offsetBuf.put(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.getShort();
	}

	public short readShortFromSensor(short offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(2);
		ByteBuffer resultBuf = ByteBuffer.allocate(2);
		offsetBuf.putShort(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.getShort();
	}

	public int readIntFromSensor(byte offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(1);
		ByteBuffer resultBuf = ByteBuffer.allocate(4);
		offsetBuf.put(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.getInt();
	}

	public int readIntFromSensor(short offset) throws IOException {
		ByteBuffer offsetBuf = ByteBuffer.allocate(2);
		ByteBuffer resultBuf = ByteBuffer.allocate(4);
		offsetBuf.putShort(offset);
		readFromSensor(offsetBuf, resultBuf);
		return resultBuf.getInt();
	}
}

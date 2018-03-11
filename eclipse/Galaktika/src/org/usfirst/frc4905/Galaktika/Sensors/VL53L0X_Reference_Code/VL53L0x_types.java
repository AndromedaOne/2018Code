package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;

public class VL53L0x_types {
	/** use where fractional values are expected
	 *
	 * Given a floating point value f it's .16 bit point is (int)(f*(1<<16))
	 */
	static public class FixPoint1616_t {
		FixPoint1616_t(int initialValue) {
			value = initialValue;
		}
		public int value;
	}
	
	/** use to return a pointers to an int
	 * 
	 */
	static public class IntPointer {
		IntPointer(int initialValue) {
			value = initialValue;
		}
		public int value;
	}

	/** use to return a pointers to a short
	 * 
	 */
	static public class ShortPointer {
		ShortPointer(int initialValue) {
			value = (short)initialValue;
		}
		public short value;
	}

	/** use to return a pointers to a byte
	 * 
	 */
	static public class BytePointer {
		BytePointer(int initialValue) {
			value = (byte)initialValue;
		}
		public byte value;
	}

}

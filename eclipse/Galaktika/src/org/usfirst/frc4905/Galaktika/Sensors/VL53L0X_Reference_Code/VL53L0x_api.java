package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;

import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_def.*;
import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_types.*;
import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.Vl53L0x_platform.VL53L0X_DEV;

public class VL53L0x_api {

	public static void VL53L0X_SetXTalkCompensationEnable(VL53L0X_DEV Dev, int XTalkCompensationEnable) {
	}
	
	public static void VL53L0X_SetLimitCheckEnable(VL53L0X_DEV Dev, int LimitCheckId,	int LimitCheckEnable) {
	}
	public static void VL53L0X_PerformSingleRangingMeasurement(VL53L0X_DEV Dev,
			VL53L0X_RangingMeasurementData_t pRangingMeasurementData) {
	}
	public static void VL53L0X_SetXTalkCompensationRateMegaCps(VL53L0X_DEV Dev,
			FixPoint1616_t XTalkCompensationRateMegaCps) {
	}
	public static void VL53L0X_SetOffsetCalibrationDataMicroMeter(VL53L0X_DEV Dev,
			int OffsetCalibrationDataMicroMeter) {
	}
	public static void VL53L0X_GetSequenceStepEnable(VL53L0X_DEV Dev,
			VL53L0X_SequenceStepId SequenceStepId, BytePointer pSequenceStepEnabled) {
	}
	public static void VL53L0X_SetSequenceStepEnable(VL53L0X_DEV Dev,
			VL53L0X_SequenceStepId SequenceStepId, byte SequenceStepEnabled) {
	}
	public static void VL53L0X_GetOffsetCalibrationDataMicroMeter(VL53L0X_DEV Dev,
			IntPointer pOffsetCalibrationDataMicroMeter) {
	}
}

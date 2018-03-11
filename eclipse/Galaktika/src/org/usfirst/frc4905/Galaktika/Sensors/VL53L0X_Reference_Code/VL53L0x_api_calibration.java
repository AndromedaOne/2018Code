package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;

import java.security.InvalidParameterException;

import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_def.*;
import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_types.*;
import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.Vl53L0x_platform.*;
import static org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_api.*;
import static org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_def.*;
import static org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_device.*;
import static org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_api_core.*;
import static org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.Vl53L0x_platform.*;

public class VL53L0x_api_calibration {
	/*******************************************************************************
	 Copyright � 2016, STMicroelectronics International N.V.
	 All rights reserved.

	 Redistribution and use in source and binary forms, with or without
	 modification, are permitted provided that the following conditions are met:
	 * Redistributions of source code must retain the above copyright
	 notice, this list of conditions and the following disclaimer.
	 * Redistributions in binary form must reproduce the above copyright
	 notice, this list of conditions and the following disclaimer in the
	 documentation and/or other materials provided with the distribution.
	 * Neither the name of STMicroelectronics nor the
	 names of its contributors may be used to endorse or promote products
	 derived from this software without specific prior written permission.

	 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
	 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
	 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND
	 NON-INFRINGEMENT OF INTELLECTUAL PROPERTY RIGHTS ARE DISCLAIMED.
	 IN NO EVENT SHALL STMICROELECTRONICS INTERNATIONAL N.V. BE LIABLE FOR ANY
	 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
	 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
	 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
	 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	 ******************************************************************************/
	public static final int REF_ARRAY_SPAD_0 = 0;
	public static final int REF_ARRAY_SPAD_5 = 5;
	public static final int REF_ARRAY_SPAD_10 = 10;

	public static int refArrayQuadrants[] = {REF_ARRAY_SPAD_10, REF_ARRAY_SPAD_5,
			REF_ARRAY_SPAD_0, REF_ARRAY_SPAD_5 };

	public static void VL53L0X_perform_xtalk_calibration(VL53L0X_DEV Dev,
				FixPoint1616_t XTalkCalDistance,
				FixPoint1616_t pXTalkCompensationRateMegaCps)
	{
		short sum_ranging = 0;
		short sum_spads = 0;
		FixPoint1616_t sum_signalRate = new FixPoint1616_t(0);
		FixPoint1616_t total_count = new FixPoint1616_t(0);
		byte xtalk_meas = 0;
		VL53L0X_RangingMeasurementData_t RangingMeasurementData = new VL53L0X_RangingMeasurementData_t();
		FixPoint1616_t xTalkStoredMeanSignalRate = new FixPoint1616_t(0);
		FixPoint1616_t xTalkStoredMeanRange = new FixPoint1616_t(0);
		FixPoint1616_t xTalkStoredMeanRtnSpads = new FixPoint1616_t(0);
		int signalXTalkTotalPerSpad;
		int xTalkStoredMeanRtnSpadsAsInt;
		int xTalkCalDistanceAsInt;
		FixPoint1616_t XTalkCompensationRateMegaCps = new FixPoint1616_t(0);

		if (XTalkCalDistance.value <= 0) {
			throw new InvalidParameterException("VL53L0X_ERROR_INVALID_PARAMS");
		}

		/* Disable the XTalk compensation */
		VL53L0X_SetXTalkCompensationEnable(Dev, 0);

		/* Disable the RIT */
		VL53L0X_SetLimitCheckEnable(Dev,	VL53L0X_CHECKENABLE_RANGE_IGNORE_THRESHOLD, 0);

		/* Perform 50 measurements and compute the averages */
		sum_ranging = 0;
		sum_spads = 0;
		sum_signalRate.value = 0;
		total_count.value = 0;
		for (xtalk_meas = 0; xtalk_meas < 50; xtalk_meas++) {
			VL53L0X_PerformSingleRangingMeasurement(Dev, RangingMeasurementData);

			/* The range is valid when RangeStatus = 0 */
			if (RangingMeasurementData.RangeStatus == 0) {
				sum_ranging = (short) (sum_ranging + RangingMeasurementData.RangeMilliMeter);
				sum_signalRate.value = sum_signalRate.value + RangingMeasurementData.SignalRateRtnMegaCps.value;
				sum_spads = (short) (sum_spads + RangingMeasurementData.EffectiveSpadRtnCount	/ 256);
				total_count.value = total_count.value + 1;
			}
		}

		/* no valid values found */
		if (total_count.value == 0)
			throw new IllegalStateException("VL53L0X_ERROR_RANGE_ERROR");

		/* FixPoint1616_t / short = FixPoint1616_t */
		xTalkStoredMeanSignalRate.value = sum_signalRate.value / total_count.value;
		xTalkStoredMeanRange.value = (sum_ranging << 16) / total_count.value;
		xTalkStoredMeanRtnSpads.value = (sum_spads << 16) / total_count.value;

		/* Round Mean Spads to Whole Number.
		 * Typically the calculated mean SPAD count is a whole number
		 * or very close to a whole
		 * number, therefore any truncation will not result in a
		 * significant loss in accuracy.
		 * Also, for a grey target at a typical distance of around
		 * 400mm, around 220 SPADs will
		 * be enabled, therefore, any truncation will result in a loss
		 * of accuracy of less than
		 * 0.5%.
		 */
		xTalkStoredMeanRtnSpadsAsInt = (xTalkStoredMeanRtnSpads.value + 0x8000) >> 16;

		/* Round Cal Distance to Whole Number.
		 * Note that the cal distance is in mm, therefore no resolution
		 * is lost.*/
		xTalkCalDistanceAsInt = (XTalkCalDistance.value + 0x8000) >> 16;

		if (xTalkStoredMeanRtnSpadsAsInt == 0 ||
				xTalkCalDistanceAsInt == 0 ||
				xTalkStoredMeanRange.value >= XTalkCalDistance.value) {
			XTalkCompensationRateMegaCps.value = 0;
		} else {
			/* Round Cal Distance to Whole Number.
			 * Note that the cal distance is in mm, therefore no
			 * resolution is lost.*/
			xTalkCalDistanceAsInt = ((XTalkCalDistance.value + 0x8000) >> 16);

			/* Apply division by mean spad count early in the
			 * calculation to keep the numbers small.
			 * This ensures we can maintain a 32bit calculation.
			 * Fixed1616 / int := Fixed1616 */
			signalXTalkTotalPerSpad = xTalkStoredMeanSignalRate.value / xTalkStoredMeanRtnSpadsAsInt;

			/* Complete the calculation for total Signal XTalk per
			 * SPAD
			 * Fixed1616 * (Fixed1616 - Fixed1616/int) :=
			 * (2^16 * Fixed1616)
			 */
			signalXTalkTotalPerSpad *= ((1 << 16) - (xTalkStoredMeanRange.value / xTalkCalDistanceAsInt));

			/* Round from 2^16 * Fixed1616, to Fixed1616. */
			XTalkCompensationRateMegaCps.value = (signalXTalkTotalPerSpad + 0x8000) >> 16;
		}

		pXTalkCompensationRateMegaCps.value = XTalkCompensationRateMegaCps.value;

		/* Enable the XTalk compensation */
		VL53L0X_SetXTalkCompensationEnable(Dev, 1);

		/* Enable the XTalk compensation */
		VL53L0X_SetXTalkCompensationRateMegaCps(Dev, XTalkCompensationRateMegaCps);
}

	public static void VL53L0X_perform_offset_calibration(VL53L0X_DEV Dev,
				FixPoint1616_t CalDistanceMilliMeter,
				IntPointer pOffsetMicroMeter)
	{
		short sum_ranging = 0;
		FixPoint1616_t total_count = new FixPoint1616_t(0);
		VL53L0X_RangingMeasurementData_t RangingMeasurementData = new VL53L0X_RangingMeasurementData_t();
		FixPoint1616_t StoredMeanRange = new FixPoint1616_t(0);
		int StoredMeanRangeAsInt;
		int CalDistanceAsInt_mm;
		BytePointer SequenceStepEnabled = new BytePointer(0);
		int meas = 0;

		if (CalDistanceMilliMeter.value <= 0)
			throw new InvalidParameterException("VL53L0X_ERROR_INVALID_PARAMS");

		VL53L0X_SetOffsetCalibrationDataMicroMeter(Dev, 0);


		/* Get the value of the TCC */
		VL53L0X_GetSequenceStepEnable(Dev, VL53L0X_SequenceStepId.VL53L0X_SEQUENCESTEP_TCC, SequenceStepEnabled);


		/* Disable the TCC */
		VL53L0X_SetSequenceStepEnable(Dev, VL53L0X_SequenceStepId.VL53L0X_SEQUENCESTEP_TCC, (byte)0);


		/* Disable the RIT */
		VL53L0X_SetLimitCheckEnable(Dev, VL53L0X_CHECKENABLE_RANGE_IGNORE_THRESHOLD, 0);

		/* Perform 50 measurements and compute the averages */
		sum_ranging = 0;
		total_count.value = 0;
		for (meas = 0; meas < 50; meas++) {
			VL53L0X_PerformSingleRangingMeasurement(Dev, RangingMeasurementData);

			/* The range is valid when RangeStatus = 0 */
			if (RangingMeasurementData.RangeStatus == 0) {
				sum_ranging = (short) (sum_ranging + RangingMeasurementData.RangeMilliMeter);
				total_count.value = total_count.value + 1;
			}
		}

		/* no valid values found */
		if (total_count.value == 0)
			throw new IllegalStateException("VL53L0X_ERROR_RANGE_ERROR");


			/* FixPoint1616_t / short = FixPoint1616_t */
		StoredMeanRange.value = ((sum_ranging << 16) / total_count.value);

		StoredMeanRangeAsInt = (StoredMeanRange.value + 0x8000) >> 16;

		/* Round Cal Distance to Whole Number.
		 * Note that the cal distance is in mm, therefore no resolution
		 * is lost.*/
		CalDistanceAsInt_mm = (CalDistanceMilliMeter.value + 0x8000) >> 16;

		pOffsetMicroMeter.value = (CalDistanceAsInt_mm - StoredMeanRangeAsInt) * 1000;

		/* Apply the calculated offset */
		Dev.Data.CurrentParameters.RangeOffsetMicroMeters = pOffsetMicroMeter.value;
		VL53L0X_SetOffsetCalibrationDataMicroMeter(Dev, pOffsetMicroMeter.value);



		/* Restore the TCC */
		if (SequenceStepEnabled.value != 0)
			VL53L0X_SetSequenceStepEnable(Dev, VL53L0X_SequenceStepId.VL53L0X_SEQUENCESTEP_TCC, (byte) 1);

	}

	public static void VL53L0X_set_offset_calibration_data_micro_meter(VL53L0X_DEV Dev,
			int OffsetCalibrationDataMicroMeter)
	{
		int cMaxOffsetMicroMeter = 511000;
		int cMinOffsetMicroMeter = -512000;
		short cOffsetRange = 4096;
		int encodedOffsetVal;

		if (OffsetCalibrationDataMicroMeter > cMaxOffsetMicroMeter)
			OffsetCalibrationDataMicroMeter = cMaxOffsetMicroMeter;
		else if (OffsetCalibrationDataMicroMeter < cMinOffsetMicroMeter)
			OffsetCalibrationDataMicroMeter = cMinOffsetMicroMeter;

		/* The offset register is 10.2 format and units are mm
		 * therefore conversion is applied by a division of
		 * 250.
		 */
		if (OffsetCalibrationDataMicroMeter >= 0) {
			encodedOffsetVal =
				OffsetCalibrationDataMicroMeter/250;
		} else {
			encodedOffsetVal =
				cOffsetRange +
				OffsetCalibrationDataMicroMeter/250;
		}

		VL53L0X_WrWord(Dev, VL53L0X_REG_ALGO_PART_TO_PART_RANGE_OFFSET_MM, (short)encodedOffsetVal);

	}

	public static void VL53L0X_get_offset_calibration_data_micro_meter(VL53L0X_DEV Dev,
			IntPointer pOffsetCalibrationDataMicroMeter)
	{
		ShortPointer RangeOffsetRegister = new ShortPointer(0);
		short cMaxOffset = 2047;
		short cOffsetRange = 4096;

		/* Note that offset has 10.2 format */

		VL53L0X_RdWord(Dev, 	VL53L0X_REG_ALGO_PART_TO_PART_RANGE_OFFSET_MM, RangeOffsetRegister);

		RangeOffsetRegister.value = (short) (RangeOffsetRegister.value & 0x0fff);

		/* Apply 12 bit 2's compliment conversion */
		if (RangeOffsetRegister.value > cMaxOffset)
			pOffsetCalibrationDataMicroMeter.value =
			(short)((RangeOffsetRegister.value - cOffsetRange) * 250);
		else
			pOffsetCalibrationDataMicroMeter.value =
			(short)(RangeOffsetRegister.value * 250);

	}


	public static void VL53L0X_apply_offset_adjustment(VL53L0X_DEV Dev)
	{
		IntPointer CorrectedOffsetMicroMeters = new IntPointer(0);
		IntPointer CurrentOffsetMicroMeters = new IntPointer(0);

		/* if we run on this function we can read all the NVM info
		 * used by the API */
		VL53L0X_get_info_from_device(Dev, 7);

		/* Read back current device offset */
		VL53L0X_GetOffsetCalibrationDataMicroMeter(Dev, CurrentOffsetMicroMeters);


		/* Apply Offset Adjustment derived from 400mm measurements */
		/* Store initial device offset */
		//PALDevDataSet(Dev, Part2PartOffsetNVMMicroMeter,	CurrentOffsetMicroMeters);
		Dev.Data.Part2PartOffsetNVMMicroMeter = CurrentOffsetMicroMeters.value;

		CorrectedOffsetMicroMeters.value =
				CurrentOffsetMicroMeters.value + Dev.Data.Part2PartOffsetAdjustmentNVMMicroMeter;

		VL53L0X_SetOffsetCalibrationDataMicroMeter(Dev, CorrectedOffsetMicroMeters.value);

		/* store current, adjusted offset */
		//VL53L0X_SETPARAMETERFIELD(Dev, RangeOffsetMicroMeters, CorrectedOffsetMicroMeters);
		Dev.Data.CurrentParameters.RangeOffsetMicroMeters = CorrectedOffsetMicroMeters.value;
	}

	public static void get_next_good_spad(byte goodSpadArray[], int size,
										 int curr, IntPointer next)
	{
		int startIndex;
		int fineOffset;
		int cSpadsPerByte = 8;
		int coarseIndex;
		int fineIndex;
		byte dataByte;
		byte success = 0;

		/*
		 * Starting with the current good spad, loop through the array to find
		 * the next. i.e. the next bit set in the sequence.
		 *
		 * The coarse index is the byte index of the array and the fine index is
		 * the index of the bit within each byte.
		 */

		next.value = -1;

		startIndex = curr / cSpadsPerByte;
		fineOffset = curr % cSpadsPerByte;

		for (coarseIndex = startIndex; ((coarseIndex < size) && (success == 0));
					coarseIndex++) {
			fineIndex = 0;
			dataByte = goodSpadArray[coarseIndex];

			if (coarseIndex == startIndex) {
				/* locate the bit position of the provided current
				 * spad bit before iterating */
				dataByte >>= fineOffset;
				fineIndex = fineOffset;
			}

			while (fineIndex < cSpadsPerByte) {
				if ((dataByte & 0x1) == 1) {
					success = 1;
					next.value = coarseIndex * cSpadsPerByte + fineIndex;
					break;
				}
				dataByte >>= 1;
				fineIndex++;
			}
		}
	}


	public static byte is_aperture(int spadIndex)
	{
		/*
		 * This function reports if a given spad index is an aperture SPAD by
		 * deriving the quadrant.
		 */
		int quadrant;
		byte isAperture = 1;
		quadrant = spadIndex >> 6;
		if (refArrayQuadrants[quadrant] == REF_ARRAY_SPAD_0)
			isAperture = 0;

		return isAperture;
	}


	public static void enable_spad_bit(byte spadArray[], int size, int spadIndex)
	{
		int cSpadsPerByte = 8;
		int coarseIndex;
		int fineIndex;

		coarseIndex = spadIndex / cSpadsPerByte;
		fineIndex = spadIndex % cSpadsPerByte;
		if (coarseIndex >= size)
			throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");
		else
			spadArray[coarseIndex] |= (1 << fineIndex);
	}

	public static void count_enabled_spads(byte spadArray[],
			int byteCount, int maxSpads,
			IntPointer pTotalSpadsEnabled, BytePointer pIsAperture)
	{
		int cSpadsPerByte = 8;
		int lastByte;
		int lastBit;
		int byteIndex = 0;
		int bitIndex = 0;
		byte tempByte;
		byte spadTypeIdentified = 0;

		/* The entire array will not be used for spads, therefore the last
		 * byte and last bit is determined from the max spads value.
		 */

		lastByte = maxSpads / cSpadsPerByte;
		lastBit = maxSpads % cSpadsPerByte;

		/* Check that the max spads value does not exceed the array bounds. */
		if (lastByte >= byteCount)
			throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");

		pTotalSpadsEnabled.value = 0;

		/* Count the bits enabled in the whole bytes */
		for (byteIndex = 0; byteIndex <= (lastByte - 1); byteIndex++) {
			tempByte = spadArray[byteIndex];

			for (bitIndex = 0; bitIndex <= cSpadsPerByte; bitIndex++) {
				if ((tempByte & 0x01) == 1) {
					pTotalSpadsEnabled.value++;

					if (spadTypeIdentified == 0) {
						pIsAperture.value = 1;
						if ((byteIndex < 2) && (bitIndex < 4))
								pIsAperture.value = 0;
						spadTypeIdentified = 1;
					}
				}
				tempByte >>= 1;
			}
		}

		/* Count the number of bits enabled in the last byte accounting
		 * for the fact that not all bits in the byte may be used.
		 */
		tempByte = spadArray[lastByte];

		for (bitIndex = 0; bitIndex <= lastBit; bitIndex++) {
			if ((tempByte & 0x01) == 1)
				pTotalSpadsEnabled.value++;
		}
	}

	public static void set_ref_spad_map(VL53L0X_DEV Dev, byte refSpadArray[] )
	{
		VL53L0X_WriteMulti(Dev, VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_0, refSpadArray, 6);
	}

	public static void get_ref_spad_map(VL53L0X_DEV Dev, byte refSpadArray[] )
	{
		VL53L0X_ReadMulti(Dev, VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_0, 	refSpadArray, 6);
	}

	public static void enable_ref_spads(VL53L0X_DEV Dev,
									   int needAptSpads,
									   byte goodSpadArray[],
									   byte spadArray[],
									   int size,
									   int start,
									   int offset,
									   int spadCount,
									   IntPointer lastSpad)
	{
		int index;
		int i;
		IntPointer nextGoodSpad = new IntPointer(offset);
		int currentSpad;
		byte[] checkSpadArray = new byte[6];

		/*
		 * This function takes in a spad array which may or may not have SPADS
		 * already enabled and appends from a given offset a requested number
		 * of new SPAD enables. The 'good spad map' is applied to
		 * determine the next SPADs to enable.
		 *
		 * This function applies to only aperture or only non-aperture spads.
		 * Checks are performed to ensure this.
		 */

		currentSpad = offset;
		for (index = 0; index < spadCount; index++) {
			get_next_good_spad(goodSpadArray, size, currentSpad, nextGoodSpad);

			if (nextGoodSpad.value == -1) {
				throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");
			}

			/* Confirm that the next good SPAD is non-aperture */
			if (is_aperture(start + nextGoodSpad.value) != needAptSpads) {
				/* if we can't get the required number of good aperture
				 * spads from the current quadrant then this is an error
				 */
				throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");
			}
			currentSpad = nextGoodSpad.value;
			enable_spad_bit(spadArray, size, currentSpad);
			currentSpad++;
		}
		lastSpad.value = currentSpad;

		set_ref_spad_map(Dev, spadArray);


		get_ref_spad_map(Dev, checkSpadArray);

		i = 0;

		/* Compare spad maps. If not equal report error. */
		while (i < size) {
			if (spadArray[i] != checkSpadArray[i]) {
				throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");
			}
			i++;
		}
	}


	public static void perform_ref_signal_measurement(VL53L0X_DEV Dev, ShortPointer refSignalRate)
	{
		VL53L0X_RangingMeasurementData_t rangingMeasurementData = new VL53L0X_RangingMeasurementData_t();

		byte SequenceConfig = 0;

		/* store the value of the sequence config,
		 * this will be reset before the end of the function
		 */

		//SequenceConfig = PALDevDataGet(Dev, SequenceConfig);
		SequenceConfig = Dev.Data.SequenceConfig;

		/*
		 * This function performs a reference signal rate measurement.
		 */
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, (byte)0xC0);

		VL53L0X_PerformSingleRangingMeasurement(Dev,	rangingMeasurementData);

		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x01);

		VL53L0X_RdWord(Dev, 	VL53L0X_REG_RESULT_PEAK_SIGNAL_RATE_REF,	refSignalRate);

		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x00);

		/* restore the previous Sequence Config */
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, SequenceConfig);

		//PALDevDataSet(Dev, SequenceConfig, SequenceConfig);
		Dev.Data.SequenceConfig = SequenceConfig;
	}

	public static void VL53L0X_perform_ref_spad_management(VL53L0X_DEV Dev,
														  IntPointer refSpadCount,
														  BytePointer isApertureSpads)
	{
		byte lastSpadArray[] = new byte[6];
		byte startSelect = (byte)0xB4;
		int minimumSpadCount = 3;
		int maxSpadCount = 44;
		int currentSpadIndex = 0;
		IntPointer lastSpadIndex = new IntPointer(0);
		IntPointer nextGoodSpad = new IntPointer(0);
		short targetRefRate = 0x0A00; /* 20 MCPS in 9:7 format */
		ShortPointer peakSignalRateRef = new ShortPointer(0);
		int needAptSpads = 0;
		int index = 0;
		int spadArraySize = 6;
		int signalRateDiff = 0;
		int lastSignalRateDiff = 0;
		byte complete = 0;
		BytePointer VhvSettings = new BytePointer(0);
		BytePointer PhaseCal = new BytePointer(0);
		int refSpadCount_int = 0;
		byte	 isApertureSpads_int = 0;

		/*
		 * The reference SPAD initialization procedure determines the minimum
		 * amount of reference spads to be enables to achieve a target reference
		 * signal rate and should be performed once during initialization.
		 *
		 * Either aperture or non-aperture spads are applied but never both.
		 * Firstly non-aperture spads are set, begining with 5 spads, and
		 * increased one spad at a time until the closest measurement to the
		 * target rate is achieved.
		 *
		 * If the target rate is exceeded when 5 non-aperture spads are enabled,
		 * initialization is performed instead with aperture spads.
		 *
		 * When setting spads, a 'Good Spad Map' is applied.
		 *
		 * This procedure operates within a SPAD window of interest of a maximum
		 * 44 spads.
		 * The start point is currently fixed to 180, which lies towards the end
		 * of the non-aperture quadrant and runs in to the adjacent aperture
		 * quadrant.
		 */


		//targetRefRate = PALDevDataGet(Dev, targetRefRate);
		targetRefRate = Dev.Data.targetRefRate;

		/*
		 * Initialize Spad arrays.
		 * Currently the good spad map is initialised to 'All good'.
		 * This is a short term implementation. The good spad map will be
		 * provided as an input.
		 * Note that there are 6 bytes. Only the first 44 bits will be used to
		 * represent spads.
		 */
		for (index = 0; index < spadArraySize; index++)
			Dev.Data.SpadData.RefSpadEnables[index] = 0;


		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x01);

		VL53L0X_WrByte(Dev, 	VL53L0X_REG_DYNAMIC_SPAD_REF_EN_START_OFFSET, (byte)0x00);

		VL53L0X_WrByte(Dev, 	VL53L0X_REG_DYNAMIC_SPAD_NUM_REQUESTED_REF_SPAD, (byte)0x2C);

		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x00);

		VL53L0X_WrByte(Dev, 	VL53L0X_REG_GLOBAL_CONFIG_REF_EN_START_SELECT, startSelect);

		VL53L0X_WrByte(Dev, 	VL53L0X_REG_POWER_MANAGEMENT_GO1_POWER_FORCE, (byte)0);

		/* Perform ref calibration */
		VL53L0X_perform_ref_calibration(Dev, VhvSettings, PhaseCal, (byte)0);

		/* Enable Minimum NON-APERTURE Spads */
		currentSpadIndex = 0;
		lastSpadIndex.value = currentSpadIndex;
		needAptSpads = 0;
		enable_ref_spads(Dev,
						needAptSpads,
						Dev.Data.SpadData.RefGoodSpadMap,
						Dev.Data.SpadData.RefSpadEnables,
						spadArraySize,
						startSelect,
						currentSpadIndex,
						minimumSpadCount,
						lastSpadIndex);

		currentSpadIndex = lastSpadIndex.value;

		perform_ref_signal_measurement(Dev, peakSignalRateRef);

		if (peakSignalRateRef.value > targetRefRate) {
			/* Signal rate measurement too high,
			 * switch to APERTURE SPADs */

			for (index = 0; index < spadArraySize; index++)
				Dev.Data.SpadData.RefSpadEnables[index] = 0;


			/* Increment to the first APERTURE spad */
			while ((is_aperture(startSelect + currentSpadIndex)
					== 0) && (currentSpadIndex < maxSpadCount)) {
				currentSpadIndex++;
			}

			needAptSpads = 1;

			enable_ref_spads(Dev,
					needAptSpads,
					Dev.Data.SpadData.RefGoodSpadMap,
					Dev.Data.SpadData.RefSpadEnables,
					spadArraySize,
					startSelect,
					currentSpadIndex,
					minimumSpadCount,
					lastSpadIndex);

			currentSpadIndex = lastSpadIndex.value;
			perform_ref_signal_measurement(Dev, peakSignalRateRef);

			if (peakSignalRateRef.value > targetRefRate) {
				/* Signal rate still too high after
				 * setting the minimum number of
				 * APERTURE spads. Can do no more
				 * therefore set the min number of
				 * aperture spads as the result.
				 */
				isApertureSpads_int = 1;
				refSpadCount_int = minimumSpadCount;
			}
		} else {
			needAptSpads = 0;
		}


		if (peakSignalRateRef.value < targetRefRate) {
			/* At this point, the minimum number of either aperture
			 * or non-aperture spads have been set. Proceed to add
			 * spads and perform measurements until the target
			 * reference is reached.
			 */
			isApertureSpads_int = (byte)needAptSpads;
			refSpadCount_int	= minimumSpadCount;

			//memcpy(lastSpadArray, Dev.Data.SpadData.RefSpadEnables, spadArraySize);
			System.arraycopy(Dev.Data.SpadData.RefSpadEnables, 0, lastSpadArray, 0, spadArraySize);
			lastSignalRateDiff = Math.abs(peakSignalRateRef.value -	targetRefRate);
			complete = 0;

			while (complete == 0) {
				get_next_good_spad(Dev.Data.SpadData.RefGoodSpadMap,	spadArraySize, currentSpadIndex,	nextGoodSpad);

				if (nextGoodSpad.value == -1) {
					throw new InvalidParameterException("VL53L0X_ERROR_REF_SPAD_INIT");
				}

				/* Cannot combine Aperture and Non-Aperture spads, so
				 * ensure the current spad is of the correct type.
				 */
				if (is_aperture((int)startSelect + nextGoodSpad.value) != needAptSpads) {
					/* At this point we have enabled the maximum
					 * number of Aperture spads.
					 */
					complete = 1;
					break;
				}

				(refSpadCount_int)++;

				currentSpadIndex = nextGoodSpad.value;
				enable_spad_bit(Dev.Data.SpadData.RefSpadEnables, spadArraySize, currentSpadIndex);

				currentSpadIndex++;
				/* Proceed to apply the additional spad and
				 * perform measurement. */
				set_ref_spad_map(Dev, Dev.Data.SpadData.RefSpadEnables);

				perform_ref_signal_measurement(Dev, peakSignalRateRef);

				signalRateDiff = Math.abs(peakSignalRateRef.value - targetRefRate);

				if (peakSignalRateRef.value > targetRefRate) {
					/* Select the spad map that provides the
					 * measurement closest to the target rate,
					 * either above or below it.
					 */
					if (signalRateDiff > lastSignalRateDiff) {
						/* Previous spad map produced a closer
						 * measurement, so choose this. */
						set_ref_spad_map(Dev, lastSpadArray);
						//memcpy(Dev.Data.SpadData.RefSpadEnables, lastSpadArray, spadArraySize);
						System.arraycopy(lastSpadArray, 0, Dev.Data.SpadData.RefSpadEnables, 0, spadArraySize);

						(refSpadCount_int)--;
					}
					complete = 1;
				} else {
					/* Continue to add spads */
					lastSignalRateDiff = signalRateDiff;
					//memcpy(lastSpadArray, Dev.Data.SpadData.RefSpadEnables, spadArraySize);
					System.arraycopy(Dev.Data.SpadData.RefSpadEnables, 0, lastSpadArray, 0, spadArraySize);
				}

			} /* while */
		}

		refSpadCount.value = refSpadCount_int;
		isApertureSpads.value = isApertureSpads_int;

		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, RefSpadsInitialised, 1);
		Dev.Data.DeviceSpecificParameters.RefSpadsInitialised = 1;
		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadCount, (byte)(refSpadCount.value));
		Dev.Data.DeviceSpecificParameters.ReferenceSpadCount = (byte)refSpadCount.value;
		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadType, isApertureSpads.value);
		Dev.Data.DeviceSpecificParameters.ReferenceSpadType = isApertureSpads.value;

	}

	public static void VL53L0X_set_reference_spads(VL53L0X_DEV Dev, int count, byte isApertureSpads)
	{
		int currentSpadIndex = 0;
		byte startSelect = (byte)0xB4;
		int spadArraySize = 6;
		int maxSpadCount = 44;
		IntPointer lastSpadIndex = new IntPointer(0);
		int index;

		/*
		 * This function applies a requested number of reference spads, either
		 * aperture or
		 * non-aperture, as requested.
		 * The good spad map will be applied.
		 */

		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x01);
		VL53L0X_WrByte(Dev, 	VL53L0X_REG_DYNAMIC_SPAD_REF_EN_START_OFFSET, (byte)0x00);
		VL53L0X_WrByte(Dev, 	VL53L0X_REG_DYNAMIC_SPAD_NUM_REQUESTED_REF_SPAD, (byte)0x2C);
		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x00);
		VL53L0X_WrByte(Dev, 	VL53L0X_REG_GLOBAL_CONFIG_REF_EN_START_SELECT, startSelect);

		for (index = 0; index < spadArraySize; index++)
			Dev.Data.SpadData.RefSpadEnables[index] = 0;

		if (isApertureSpads != 0) {
			/* Increment to the first APERTURE spad */
			while ((is_aperture(startSelect + currentSpadIndex) == 0) &&
				  (currentSpadIndex < maxSpadCount)) {
				currentSpadIndex++;
			}
		}
		enable_ref_spads(Dev, 
						isApertureSpads,
						Dev.Data.SpadData.RefGoodSpadMap,
						Dev.Data.SpadData.RefSpadEnables,
						spadArraySize,
						startSelect,
						currentSpadIndex,
						count,
						lastSpadIndex);

		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, RefSpadsInitialised, 1);
		Dev.Data.DeviceSpecificParameters.RefSpadsInitialised = 1;
		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadCount, (byte)(count));
		Dev.Data.DeviceSpecificParameters.ReferenceSpadCount = (byte)count;
		//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadType, isApertureSpads);
		Dev.Data.DeviceSpecificParameters.ReferenceSpadType = isApertureSpads;
	}

	public static void VL53L0X_get_reference_spads(VL53L0X_DEV Dev, IntPointer pSpadCount, BytePointer pIsApertureSpads)
	{
		byte refSpadsInitialised;
		byte[] refSpadArray = new byte[6];
		int cMaxSpadCount = 44;
		int cSpadArraySize = 6;
		IntPointer spadsEnabled = new IntPointer(0);
		BytePointer isApertureSpads = new BytePointer(0);

		//refSpadsInitialised = VL53L0X_GETDEVICESPECIFICPARAMETER(Dev, RefSpadsInitialised);
		refSpadsInitialised = Dev.Data.DeviceSpecificParameters.RefSpadsInitialised;

		if (refSpadsInitialised == 1) {

			//*pSpadCount = (int)VL53L0X_GETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadCount);
			//*pIsApertureSpads = VL53L0X_GETDEVICESPECIFICPARAMETER(Dev,ReferenceSpadType);
			pSpadCount.value = Dev.Data.DeviceSpecificParameters.ReferenceSpadCount;
			pIsApertureSpads.value = Dev.Data.DeviceSpecificParameters.ReferenceSpadType;
		} else {

			/* obtain spad info from device.*/
			get_ref_spad_map(Dev, refSpadArray);

			/* count enabled spads within spad map array and
			 * determine if Aperture or Non-Aperture.
			 */
			count_enabled_spads(refSpadArray,
							    cSpadArraySize,
							    cMaxSpadCount,
							    spadsEnabled,
							    isApertureSpads);
			pSpadCount.value = spadsEnabled.value;
			pIsApertureSpads.value = isApertureSpads.value;

			//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, RefSpadsInitialised, 1);
			//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadCount, (byte)spadsEnabled);
			//VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, ReferenceSpadType, isApertureSpads);
			Dev.Data.DeviceSpecificParameters.RefSpadsInitialised = 1;
			Dev.Data.DeviceSpecificParameters.ReferenceSpadCount = (byte)spadsEnabled.value;
			Dev.Data.DeviceSpecificParameters.ReferenceSpadType = isApertureSpads.value;
		}
	}


	public static void VL53L0X_perform_single_ref_calibration(VL53L0X_DEV Dev, byte vhv_init_byte)
	{
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSRANGE_START, (byte)(VL53L0X_REG_SYSRANGE_MODE_START_STOP | vhv_init_byte));

		VL53L0X_measurement_poll_for_completion(Dev);

		VL53L0X_ClearInterruptMask(Dev, 0);

		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSRANGE_START, (byte)0x00);
	}


	public static void VL53L0X_ref_calibration_io(VL53L0X_DEV Dev, byte read_not_write,
												 byte VhvSettings, byte PhaseCal,
												 BytePointer pVhvSettings, BytePointer pPhaseCal,
												 final byte vhv_enable, final byte phase_enable)
	{
		BytePointer PhaseCalint = new BytePointer(0);

		/* Read VHV from device */
		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x01);
		VL53L0X_WrByte(Dev, (byte)0x00, (byte)0x00);
		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x00);

		if (read_not_write != 0) {
			if (vhv_enable != 0)
				VL53L0X_RdByte(Dev, (byte)0xCB, pVhvSettings);
			if (phase_enable != 0)
				VL53L0X_RdByte(Dev, (byte)0xEE, PhaseCalint);
		} else {
			if (vhv_enable != 0)
				VL53L0X_WrByte(Dev, (byte)0xCB, VhvSettings);
			if (phase_enable != 0)
				VL53L0X_UpdateByte(Dev, (byte)0xEE, (byte)0x80, PhaseCal);
		}

		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x01);
		VL53L0X_WrByte(Dev, (byte)0x00, (byte)0x01);
		VL53L0X_WrByte(Dev, (byte)0xFF, (byte)0x00);

		pPhaseCal.value = (byte)(PhaseCalint.value & 0xEF);
	}


	public static void VL53L0X_perform_vhv_calibration(VL53L0X_DEV Dev,
		BytePointer pVhvSettings, final byte get_data_enable,
		final byte restore_config)
	{
		byte SequenceConfig = 0;
		byte VhvSettings = 0;
		byte PhaseCal = 0;
		BytePointer PhaseCalInt = new BytePointer(0);

		/* store the value of the sequence config,
		 * this will be reset before the end of the function
		 */

		if (restore_config != 0)
			SequenceConfig = Dev.Data.SequenceConfig;

		/* Run VHV */
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, (byte)0x01);

		VL53L0X_perform_single_ref_calibration(Dev, (byte)0x40);

		/* Read VHV from device */
		if (get_data_enable == 1) {
			VL53L0X_ref_calibration_io(Dev, (byte)1, VhvSettings, PhaseCal, /* Not used here */
									   pVhvSettings, PhaseCalInt, (byte)1, (byte)0);
		} else
			pVhvSettings.value = 0;


		if (restore_config != 0) {
			/* restore the previous Sequence Config */
			VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, SequenceConfig);
			Dev.Data.SequenceConfig = SequenceConfig;

		}
	}

	public static void VL53L0X_perform_phase_calibration(VL53L0X_DEV Dev,
		BytePointer pPhaseCal, final byte get_data_enable,
		final byte restore_config)
	{
		byte SequenceConfig = 0;
		byte VhvSettings = 0;
		byte PhaseCal = 0;
		BytePointer VhvSettingsint = new BytePointer(0);

		/* store the value of the sequence config,
		 * this will be reset before the end of the function
		 */

		if (restore_config != 0)
			SequenceConfig = Dev.Data.SequenceConfig;

		/* Run PhaseCal */
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, (byte)0x02);

		VL53L0X_perform_single_ref_calibration(Dev, (byte)0x0);

		/* Read PhaseCal from device */
		if (get_data_enable == 1) {
			VL53L0X_ref_calibration_io(Dev, (byte)1, VhvSettings, PhaseCal, /* Not used here */
				VhvSettingsint, pPhaseCal, (byte)0, (byte)1);
		} else
			pPhaseCal.value = 0;


		if (restore_config != 0) {
			/* restore the previous Sequence Config */
			VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, 	SequenceConfig);
			Dev.Data.SequenceConfig = SequenceConfig;

		}
	}

	public static void VL53L0X_perform_ref_calibration(VL53L0X_DEV Dev,
		BytePointer pVhvSettings, BytePointer pPhaseCal, byte get_data_enable)
	{
		byte SequenceConfig = 0;

		/* store the value of the sequence config,
		 * this will be reset before the end of the function
		 */

		//SequenceConfig = PALDevDataGet(Dev, SequenceConfig);
		SequenceConfig = Dev.Data.SequenceConfig;

		/* In the following function we don't save the config to optimize
		 * writes on device. Config is saved and restored only once. */
		VL53L0X_perform_vhv_calibration(	Dev, pVhvSettings, get_data_enable, (byte)0);

		VL53L0X_perform_phase_calibration(Dev, pPhaseCal, get_data_enable, (byte)0);

		/* restore the previous Sequence Config */
		VL53L0X_WrByte(Dev, VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, 	SequenceConfig);
		Dev.Data.SequenceConfig = SequenceConfig;
	}

	public static void VL53L0X_set_ref_calibration(VL53L0X_DEV Dev,	byte VhvSettings, byte PhaseCal)
	{
		BytePointer pVhvSettings = new BytePointer(0);
		BytePointer pPhaseCal = new BytePointer(0);

		VL53L0X_ref_calibration_io(Dev, (byte)0, VhvSettings, PhaseCal, pVhvSettings, pPhaseCal, (byte)1, (byte)1);
	}

	public static void VL53L0X_get_ref_calibration(VL53L0X_DEV Dev,
			BytePointer pVhvSettings, BytePointer pPhaseCal)
	{
		byte VhvSettings = 0;
		byte PhaseCal = 0;

		VL53L0X_ref_calibration_io(Dev, (byte)1, VhvSettings, PhaseCal, pVhvSettings, pPhaseCal, (byte)1, (byte)1);
	}
}

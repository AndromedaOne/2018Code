package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;

import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_device.*;
import org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code.VL53L0x_types.*;

public class VL53L0x_def {
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
	 *******************************************************************************/

	/** @defgroup VL53L0X_globaldefine_group VL53L0X Defines
	 *	@brief	  VL53L0X Defines
	 *	@{
	 */


	/** PAL SPECIFICATION major version */
	public static final int VL53L0X10_SPECIFICATION_VER_MAJOR   = 1;
	/** PAL SPECIFICATION minor version */
	public static final int VL53L0X10_SPECIFICATION_VER_MINOR   = 2;
	/** PAL SPECIFICATION sub version */
	public static final int VL53L0X10_SPECIFICATION_VER_SUB	   = 7;
	/** PAL SPECIFICATION sub version */
	public static final int VL53L0X10_SPECIFICATION_VER_REVISION = 1440;

	/** VL53L0X PAL IMPLEMENTATION major version */
	public static final int VL53L0X10_IMPLEMENTATION_VER_MAJOR	= 1;
	/** VL53L0X PAL IMPLEMENTATION minor version */
	public static final int VL53L0X10_IMPLEMENTATION_VER_MINOR	= 0;
	/** VL53L0X PAL IMPLEMENTATION sub version */
	public static final int VL53L0X10_IMPLEMENTATION_VER_SUB		= 9;
	/** VL53L0X PAL IMPLEMENTATION sub version */
	public static final int VL53L0X10_IMPLEMENTATION_VER_REVISION	 = 3673;

	/** PAL SPECIFICATION major version */
	public static final int VL53L0X_SPECIFICATION_VER_MAJOR	 = 1;
	/** PAL SPECIFICATION minor version */
	public static final int VL53L0X_SPECIFICATION_VER_MINOR	 = 2;
	/** PAL SPECIFICATION sub version */
	public static final int VL53L0X_SPECIFICATION_VER_SUB	 = 7;
	/** PAL SPECIFICATION sub version */
	public static final int VL53L0X_SPECIFICATION_VER_REVISION = 1440;

	/** VL53L0X PAL IMPLEMENTATION major version */
	public static final int VL53L0X_IMPLEMENTATION_VER_MAJOR	  = 1;
	/** VL53L0X PAL IMPLEMENTATION minor version */
	public static final int VL53L0X_IMPLEMENTATION_VER_MINOR	  = 0;
	/** VL53L0X PAL IMPLEMENTATION sub version */
	public static final int VL53L0X_IMPLEMENTATION_VER_SUB	  = 2;
	/** VL53L0X PAL IMPLEMENTATION sub version */
	public static final int VL53L0X_IMPLEMENTATION_VER_REVISION	  = 4823;
	public static final int VL53L0X_DEFAULT_MAX_LOOP = 2000;
	public static final int VL53L0X_MAX_STRING_LENGTH = 32;

	/****************************************
	 * PRIVATE define do not edit
	 ****************************************/

	/** @brief Defines the parameters of the Get Version Functions
	 */
	public static class VL53L0X_Version_t {
		int	 revision; /*!< revision number */
		byte		 major;	   /*!< major number */
		byte		 minor;	   /*!< minor number */
		byte		 build;	   /*!< build number */
	}


	/** @brief Defines the parameters of the Get Device Info Functions
	 */
	public static class VL53L0X_DeviceInfo_t {
		String Name;
		/*!< Name of the Device e.g. Left_Distance */
		String Type;
		/*!< Type of the Device e.g VL53L0X */
		String ProductId;
		/*!< Product Identifier String	*/
		byte ProductType;
		/*!< Product Type, VL53L0X = 1, VL53L1 = 2 */
		byte ProductRevisionMajor;
		/*!< Product revision major */
		byte ProductRevisionMinor;
		/*!< Product revision minor */
	}


	/** @defgroup VL53L0X_define_Error_group Error and Warning code returned by API
	 *	The following DEFINE are used to identify the PAL ERROR
	 *	@{
	 */

	public static enum VL53L0X_Error {
		VL53L0X_ERROR_NONE		(0),
		VL53L0X_ERROR_CALIBRATION_WARNING	(-1),
		/*!< Warning invalid calibration data may be in used
			\a	VL53L0X_InitData()
			\a VL53L0X_GetOffsetCalibrationData
			\a VL53L0X_SetOffsetCalibrationData */
		VL53L0X_ERROR_MIN_CLIPPED			(-2),
		/*!< Warning parameter passed was clipped to min before to be applied */

		VL53L0X_ERROR_UNDEFINED				( -3),
		/*!< Unqualified error */
		VL53L0X_ERROR_INVALID_PARAMS			( -4),
		/*!< Parameter passed is invalid or out of range */
		VL53L0X_ERROR_NOT_SUPPORTED			( -5),
		/*!< Function is not supported in current mode or configuration */
		VL53L0X_ERROR_RANGE_ERROR			( -6),
		/*!< Device report a ranging error interrupt status */
		VL53L0X_ERROR_TIME_OUT				( -7),
		/*!< Aborted due to time out */
		VL53L0X_ERROR_MODE_NOT_SUPPORTED			( -8),
		/*!< Asked mode is not supported by the device */
		VL53L0X_ERROR_BUFFER_TOO_SMALL			( -9),
		/*!< ... */
		VL53L0X_ERROR_GPIO_NOT_EXISTING			( -10),
		/*!< User tried to setup a non-existing GPIO pin */
		VL53L0X_ERROR_GPIO_FUNCTIONALITY_NOT_SUPPORTED  ( -11),
		/*!< unsupported GPIO functionality */
		VL53L0X_ERROR_INTERRUPT_NOT_CLEARED		( -12),
		/*!< Error during interrupt clear */
		VL53L0X_ERROR_CONTROL_INTERFACE			( -20),
		/*!< error reported from IO functions */
		VL53L0X_ERROR_INVALID_COMMAND			( -30),
		/*!< The command is not allowed in the current device state
		 *	(power down) */
		VL53L0X_ERROR_DIVISION_BY_ZERO			( -40),
		/*!< In the function a division by zero occurs */
		VL53L0X_ERROR_REF_SPAD_INIT			( -50),
		/*!< Error during reference SPAD initialization */
		VL53L0X_ERROR_NOT_IMPLEMENTED			( -99);
		/*!< Tells requested functionality has not been implemented yet or
		 * not compatible with the device */
		/** @} VL53L0X_define_Error_group */
		private final int value;

		VL53L0X_Error(int value) {
			this.value = value;
		}
	}

	/** @defgroup VL53L0X_define_DeviceModes_group Defines Device modes
	 *	Defines all possible modes for the device
	 *	@{
	 */
	public static enum VL53L0X_DeviceModes {

		VL53L0X_DEVICEMODE_SINGLE_RANGING	(  0),
		VL53L0X_DEVICEMODE_CONTINUOUS_RANGING	(  1),
		VL53L0X_DEVICEMODE_SINGLE_HISTOGRAM	(  2),
		VL53L0X_DEVICEMODE_CONTINUOUS_TIMED_RANGING ( 3),
		VL53L0X_DEVICEMODE_SINGLE_ALS		( 10),
		VL53L0X_DEVICEMODE_GPIO_DRIVE		( 20),
		VL53L0X_DEVICEMODE_GPIO_OSC		( 21);

		private final int value;

		VL53L0X_DeviceModes(int value) {
			this.value = value;
		}
	}
	/* ... Modes to be added depending on device */
	/** @} VL53L0X_define_DeviceModes_group */



	/** @defgroup VL53L0X_define_HistogramModes_group Defines Histogram modes
	 *	Defines all possible Histogram modes for the device
	 *	@{
	 */
	public static enum VL53L0X_HistogramModes {

		VL53L0X_HISTOGRAMMODE_DISABLED		(0),
		/*!< Histogram Disabled */
		VL53L0X_HISTOGRAMMODE_REFERENCE_ONLY	(1),
		/*!< Histogram Reference array only */
		VL53L0X_HISTOGRAMMODE_RETURN_ONLY	(2),
		/*!< Histogram Return array only */
		VL53L0X_HISTOGRAMMODE_BOTH		(3);
		/*!< Histogram both Reference and Return Arrays */
		private final int value;

		VL53L0X_HistogramModes(int value) {
			this.value = value;
		}
	}

	/* ... Modes to be added depending on device */
	/** @} VL53L0X_define_HistogramModes_group */


	/** @defgroup VL53L0X_define_PowerModes_group List of available Power Modes
	 *	List of available Power Modes
	 *	@{
	 */

	public static enum VL53L0X_PowerModes {

		VL53L0X_POWERMODE_STANDBY_LEVEL1 (0),
		/*!< Standby level 1 */
		VL53L0X_POWERMODE_STANDBY_LEVEL2 (1),
		/*!< Standby level 2 */
		VL53L0X_POWERMODE_IDLE_LEVEL1	(2),
		/*!< Idle level 1 */
		VL53L0X_POWERMODE_IDLE_LEVEL2	(3);
		/*!< Idle level 2 */
		private final int value;

		VL53L0X_PowerModes(int value) {
			this.value = value;
		}
	}

	/** @} VL53L0X_define_PowerModes_group */


	/** @brief Defines all parameters for the device
	 */
	public static class VL53L0X_DeviceParameters_t {

		VL53L0X_DeviceModes DeviceMode;
		/*!< Defines type of measurement to be done for the next measure */
		VL53L0X_HistogramModes HistogramMode;
		/*!< Defines type of histogram measurement to be done for the next
		 *	measure */
		int MeasurementTimingBudgetMicroSeconds;
		/*!< Defines the allowed total time for a single measurement */
		int InterMeasurementPeriodMilliSeconds;
		/*!< Defines time between two consecutive measurements (between two
		 *	measurement starts). If set to 0 means back-to-back mode */
		byte XTalkCompensationEnable;
		/*!< Tells if Crosstalk compensation shall be enable or not	 */
		short XTalkCompensationRangeMilliMeter;
		/*!< CrossTalk compensation range in millimeter	 */
		FixPoint1616_t XTalkCompensationRateMegaCps;
		/*!< CrossTalk compensation rate in Mega counts per seconds.
		 *	Expressed in 16.16 fixed point format.	*/
		int RangeOffsetMicroMeters;
		/*!< Range offset adjustment (mm).	*/

		byte[] LimitChecksEnable = new byte[VL53L0x_device.VL53L0X_CHECKENABLE_NUMBER_OF_CHECKS];
		/*!< This Array store all the Limit Check enable for this device. */
		byte[] LimitChecksStatus = new byte[VL53L0x_device.VL53L0X_CHECKENABLE_NUMBER_OF_CHECKS];
		/*!< This Array store all the Status of the check linked to last
		 * measurement. */
		FixPoint1616_t [] LimitChecksValue = new FixPoint1616_t[VL53L0x_device.VL53L0X_CHECKENABLE_NUMBER_OF_CHECKS];
		/*!< This Array store all the Limit Check value for this device */

		byte WrapAroundCheckEnable;
		/*!< Tells if Wrap Around Check shall be enable or not */
	} 


	/** @defgroup VL53L0X_define_State_group Defines the current status of the device
	 *	Defines the current status of the device
	 *	@{
	 */

	public static enum VL53L0X_State {

		VL53L0X_STATE_POWERDOWN		 ( 0),
		/*!< Device is in HW reset	*/
		VL53L0X_STATE_WAIT_STATICINIT ( 1),
		/*!< Device is initialized and wait for static initialization  */
		VL53L0X_STATE_STANDBY		 ( 2),
		/*!< Device is in Low power Standby mode   */
		VL53L0X_STATE_IDLE			 ( 3),
		/*!< Device has been initialized and ready to do measurements  */
		VL53L0X_STATE_RUNNING		 ( 4),
		/*!< Device is performing measurement */
		VL53L0X_STATE_UNKNOWN		 ( 98),
		/*!< Device is in unknown state and need to be rebooted	 */
		VL53L0X_STATE_ERROR			 ( 99);
		/*!< Device is in error state and need to be rebooted  */
		private final int value;

		VL53L0X_State(int value) {
			this.value = value;
		}
	}

	/** @} VL53L0X_define_State_group */


	/** @brief Structure containing the Dmax computation parameters and data
	 */
	public static class VL53L0X_DMaxData_t {
		int AmbTuningWindowFactor_K;
		/*!<  internal algo tuning (*1000) */
		int RetSignalAt0mm;
		/*!< intermediate dmax computation value caching */
	}

	/**
	 * @struct VL53L0X_RangeData_t
	 * @brief Range measurement data.
	 */
	public static class VL53L0X_RangingMeasurementData_t {
		int TimeStamp;		/*!< 32-bit time stamp. */
		int MeasurementTimeUsec;
		/*!< Give the Measurement time needed by the device to do the
		 * measurement.*/


		short RangeMilliMeter;	/*!< range distance in millimeter. */

		short RangeDMaxMilliMeter;
		/*!< Tells what is the maximum detection distance of the device
		 * in current setup and environment conditions (Filled when
		 *	applicable) */

		FixPoint1616_t SignalRateRtnMegaCps;
		/*!< Return signal rate (MCPS)\n these is a 16.16 fix point
		 *	value, which is effectively a measure of target
		 *	 reflectance.*/
		FixPoint1616_t AmbientRateRtnMegaCps;
		/*!< Return ambient rate (MCPS)\n these is a 16.16 fix point
		 *	value, which is effectively a measure of the ambien
		 *	t light.*/

		short EffectiveSpadRtnCount;
		/*!< Return the effective SPAD count for the return signal.
		 *	To obtain Real value it should be divided by 256 */

		byte ZoneId;
		/*!< Denotes which zone and range scheduler stage the range
		 *	data relates to. */
		byte RangeFractionalPart;
		/*!< Fractional part of range distance. Final value is a
		 *	FixPoint168 value. */
		byte RangeStatus;
		/*!< Range Status for the current measurement. This is device
		 *	dependent. Value = 0 means value is valid.
		 *	See \ref RangeStatusPage */
	}


	public static final int VL53L0X_HISTOGRAM_BUFFER_SIZE = 24;

	/**
	 * @struct VL53L0X_HistogramData_t
	 * @brief Histogram measurement data.
	 */
	public static class VL53L0X_HistogramMeasurementData_t {
		/* Histogram Measurement data */
		int[] HistogramData = new int[VL53L0X_HISTOGRAM_BUFFER_SIZE];
		/*!< Histogram data */
		byte HistogramType; /*!< Indicate the types of histogram data :
		Return only, Reference only, both Return and Reference */
		byte FirstBin; /*!< First Bin value */
		byte BufferSize; /*!< Buffer Size - Set by the user.*/
		byte NumberOfBins;
		/*!< Number of bins filled by the histogram measurement */

		VL53L0X_DeviceError ErrorStatus;
		/*!< Error status of the current measurement. \n
		see @a ::VL53L0X_DeviceError @a VL53L0X_GetStatusErrorString() */
	}

	public static final int VL53L0X_REF_SPAD_BUFFER_SIZE = 6;

	/**
	 * @struct VL53L0X_SpadData_t
	 * @brief Spad Configuration Data.
	 */
	public static class VL53L0X_SpadData_t {
		byte[] RefSpadEnables = new byte[VL53L0X_REF_SPAD_BUFFER_SIZE];
		/*!< Reference Spad Enables */
		byte[] RefGoodSpadMap = new byte[VL53L0X_REF_SPAD_BUFFER_SIZE];
		/*!< Reference Spad Good Spad Map */
	}

	public static class VL53L0X_DeviceSpecificParameters_t {
		FixPoint1616_t OscFrequencyMHz; /* Frequency used */

		short LastEncodedTimeout;
		/* last encoded Time out used for timing budget*/

		VL53L0X_GpioFunctionality Pin0GpioFunctionality;
		/* store the functionality of the GPIO: pin0 */

		int FinalRangeTimeoutMicroSecs;
		/*!< Execution time of the final range*/
		byte FinalRangeVcselPulsePeriod;
		/*!< Vcsel pulse period (pll clocks) for the final range measurement*/
		int PreRangeTimeoutMicroSecs;
		/*!< Execution time of the final range*/
		byte PreRangeVcselPulsePeriod;
		/*!< Vcsel pulse period (pll clocks) for the pre-range measurement*/

		short SigmaEstRefArray;
		/*!< Reference array sigma value in 1/100th of [mm] e.g. 100 = 1mm */
		short SigmaEstEffPulseWidth;
		/*!< Effective Pulse width for sigma estimate in 1/100th
		 * of ns e.g. 900 = 9.0ns */
		short SigmaEstEffAmbWidth;
		/*!< Effective Ambient width for sigma estimate in 1/100th of ns
		 * e.g. 500 = 5.0ns */


		byte ReadDataFromDeviceDone; /* Indicate if read from device has
		been done (==1) or not (==0) */
		byte ModuleId; /* Module ID */
		byte Revision; /* test Revision */
		char[] ProductId = new char[VL53L0X_MAX_STRING_LENGTH];
		/* Product Identifier String  */
		byte ReferenceSpadCount; /* used for ref spad management */
		byte ReferenceSpadType;	/* used for ref spad management */
		byte RefSpadsInitialised; /* reports if ref spads are initialised. */
		int PartUIDUpper; /*!< Unique Part ID Upper */
		int PartUIDLower; /*!< Unique Part ID Lower */
		FixPoint1616_t SignalRateMeasFixed400mm; /*!< Peek Signal rate
		at 400 mm*/

	}

	/**
	 * @struct VL53L0X_DevData_t
	 *
	 * @brief VL53L0X PAL device ST private data structure \n
	 * End user should never access any of these field directly
	 *
	 * These must never access directly but only via macro
	 */
	public static class VL53L0X_DevData_t {
		VL53L0X_DMaxData_t DMaxData;
		/*!< Dmax Data */
		int	 Part2PartOffsetNVMMicroMeter;
		/*!< backed up NVM value */
		int	 Part2PartOffsetAdjustmentNVMMicroMeter;
		/*!< backed up NVM value representing additional offset adjustment */
		VL53L0X_DeviceParameters_t CurrentParameters;
		/*!< Current Device Parameter */
		VL53L0X_RangingMeasurementData_t LastRangeMeasure;
		/*!< Ranging Data */
		VL53L0X_HistogramMeasurementData_t LastHistogramMeasure;
		/*!< Histogram Data */
		VL53L0X_DeviceSpecificParameters_t DeviceSpecificParameters;
		/*!< Parameters specific to the device */
		VL53L0X_SpadData_t SpadData;
		/*!< Spad Data */
		byte SequenceConfig;
		/*!< Internal value for the sequence config */
		byte RangeFractionalEnable;
		/*!< Enable/Disable fractional part of ranging data */
		VL53L0X_State PalState;
		/*!< Current state of the PAL for this device */
		VL53L0X_PowerModes PowerMode;
		/*!< Current Power Mode	 */
		short SigmaEstRefArray;
		/*!< Reference array sigma value in 1/100th of [mm] e.g. 100 = 1mm */
		short SigmaEstEffPulseWidth;
		/*!< Effective Pulse width for sigma estimate in 1/100th
		 * of ns e.g. 900 = 9.0ns */
		short SigmaEstEffAmbWidth;
		/*!< Effective Ambient width for sigma estimate in 1/100th of ns
		 * e.g. 500 = 5.0ns */
		byte StopVariable;
		/*!< StopVariable used during the stop sequence */
		short targetRefRate;
		/*!< Target Ambient Rate for Ref spad management */
		FixPoint1616_t SigmaEstimate;
		/*!< Sigma Estimate - based on ambient & VCSEL rates and
		 * signal_total_events */
		FixPoint1616_t SignalEstimate;
		/*!< Signal Estimate - based on ambient & VCSEL rates and cross talk */
		FixPoint1616_t LastSignalRefMcps;
		/*!< Latest Signal ref in Mcps */
		public class PTuningSettingsPointer {byte[] TuningSettings;};
		PTuningSettingsPointer pTuningSettingsPointer;
		/*!< Pointer for Tuning Settings table */
		byte UseInternalTuningSettings;
		/*!< Indicate if we use	 Tuning Settings table */
		short LinearityCorrectiveGain;
		/*!< Linearity Corrective Gain value in x1000 */
		short DmaxCalRangeMilliMeter;
		/*!< Dmax Calibration Range millimeter */
		FixPoint1616_t DmaxCalSignalRateRtnMegaCps;
		/*!< Dmax Calibration Signal Rate Return MegaCps */

	}


	/** @defgroup VL53L0X_define_InterruptPolarity_group Defines the Polarity
	 * of the Interrupt
	 *	Defines the Polarity of the Interrupt
	 *	@{
	 */
	public static enum VL53L0X_InterruptPolarity {

		VL53L0X_INTERRUPTPOLARITY_LOW	   (0),
		/*!< Set active low polarity best setup for falling edge. */
		VL53L0X_INTERRUPTPOLARITY_HIGH	   (1);
		/*!< Set active high polarity best setup for rising edge. */
		private final int value;

		VL53L0X_InterruptPolarity(int value) {
			this.value = value;
		}
	}

	/** @} VL53L0X_define_InterruptPolarity_group */


	/** @defgroup VL53L0X_define_VcselPeriod_group Vcsel Period Defines
	 *	Defines the range measurement for which to access the vcsel period.
	 *	@{
	 */
	public static enum VL53L0X_VcselPeriod {

		VL53L0X_VCSEL_PERIOD_PRE_RANGE	(0),
		/*!<Identifies the pre-range vcsel period. */
		VL53L0X_VCSEL_PERIOD_FINAL_RANGE (1);
		/*!<Identifies the final range vcsel period. */
		private final int value;

		VL53L0X_VcselPeriod(int value) {
			this.value = value;
		}
	}

	/** @} VL53L0X_define_VcselPeriod_group */

	/** @defgroup VL53L0X_define_SchedulerSequence_group Defines the steps
	 * carried out by the scheduler during a range measurement.
	 *	@{
	 *	Defines the states of all the steps in the scheduler
	 *	i.e. enabled/disabled.
	 */
	public static class VL53L0X_SchedulerSequenceSteps_t {
		byte		 TccOn;	   /*!<Reports if Target Centre Check On  */
		byte		 MsrcOn;	   /*!<Reports if MSRC On  */
		byte		 DssOn;		   /*!<Reports if DSS On  */
		byte		 PreRangeOn;   /*!<Reports if Pre-Range On	*/
		byte		 FinalRangeOn; /*!<Reports if Final-Range On  */
	}

	/** @} VL53L0X_define_SchedulerSequence_group */

	/** @defgroup VL53L0X_define_SequenceStepId_group Defines the Polarity
	 *	of the Interrupt
	 *	Defines the the sequence steps performed during ranging..
	 *	@{
	 */
	public static enum VL53L0X_SequenceStepId {

		VL53L0X_SEQUENCESTEP_TCC		 (0),
		/*!<Target CentreCheck identifier. */
		VL53L0X_SEQUENCESTEP_DSS		 (1),
		/*!<Dynamic Spad Selection function Identifier. */
		VL53L0X_SEQUENCESTEP_MSRC		 (2),
		/*!<Minimum Signal Rate Check function Identifier. */
		VL53L0X_SEQUENCESTEP_PRE_RANGE	 (3),
		/*!<Pre-Range check Identifier. */
		VL53L0X_SEQUENCESTEP_FINAL_RANGE (4);
		/*!<Final Range Check Identifier. */
		private final int value;

		VL53L0X_SequenceStepId(int value) {
			this.value = value;
		}
	}

	public static final int	 VL53L0X_SEQUENCESTEP_NUMBER_OF_CHECKS			= 5;
	/*!<Number of Sequence Step Managed by the API. */

	/** @} VL53L0X_define_SequenceStepId_group */


	/* MACRO Definitions */
	/** @defgroup VL53L0X_define_GeneralMacro_group General Macro Defines
	 *	General Macro Defines
	 *	@{
	 */

	/* Defines */
	/*
	#define VL53L0X_SETPARAMETERFIELD(Dev, field, value) \
		PALDevDataSet(Dev, CurrentParameters.field, value)

	#define VL53L0X_GETPARAMETERFIELD(Dev, field, variable) \
		variable = PALDevDataGet(Dev, CurrentParameters).field


	#define VL53L0X_SETARRAYPARAMETERFIELD(Dev, field, index, value) \
		PALDevDataSet(Dev, CurrentParameters.field[index], value)

	#define VL53L0X_GETARRAYPARAMETERFIELD(Dev, field, index, variable) \
		variable = PALDevDataGet(Dev, CurrentParameters).field[index]


	#define VL53L0X_SETDEVICESPECIFICPARAMETER(Dev, field, value) \
			PALDevDataSet(Dev, DeviceSpecificParameters.field, value)

	#define VL53L0X_GETDEVICESPECIFICPARAMETER(Dev, field) \
			PALDevDataGet(Dev, DeviceSpecificParameters).field


	#define VL53L0X_FIXPOINT1616TOFIXPOINT97(Value) \
		(short)((Value>>9)&0xFFFF)
	#define VL53L0X_FIXPOINT97TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<9)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT88(Value) \
		(short)((Value>>8)&0xFFFF)
	#define VL53L0X_FIXPOINT88TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<8)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT412(Value) \
		(short)((Value>>4)&0xFFFF)
	#define VL53L0X_FIXPOINT412TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<4)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT313(Value) \
		(short)((Value>>3)&0xFFFF)
	#define VL53L0X_FIXPOINT313TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<3)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT08(Value) \
		(byte)((Value>>8)&0x00FF)
	#define VL53L0X_FIXPOINT08TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<8)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT53(Value) \
		(byte)((Value>>13)&0x00FF)
	#define VL53L0X_FIXPOINT53TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<13)

	#define VL53L0X_FIXPOINT1616TOFIXPOINT102(Value) \
		(short)((Value>>14)&0x0FFF)
	#define VL53L0X_FIXPOINT102TOFIXPOINT1616(Value) \
		(FixPoint1616_t)(Value<<12)

	#define VL53L0X_MAKEUINT16(lsb, msb) (short)((((short)msb)<<8) + \
			(short)lsb)
	 */
}

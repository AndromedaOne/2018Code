package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;
//#include "vl53l0x_types.h"

public class VL53L0x_device {
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

	/**
	 * Device specific defines. To be adapted by implementer for the targeted
	 * device.
	 */



	/** @defgroup VL53L0X_DevSpecDefines_group VL53L0X cut1.1 Device Specific Defines
	 *  @brief VL53L0X cut1.1 Device Specific Defines
	 *  @{
	 */


	/** @defgroup VL53L0X_DeviceError_group Device Error
	 *  @brief Device Error code
	 *
	 *  This enum is Device specific it should be updated in the implementation
	 *  Use @a VL53L0X_GetStatusErrorString() to get the string.
	 *  It is related to Status Register of the Device.
	 *  @{
	 */
	public enum VL53L0X_DeviceError {

	 VL53L0X_DEVICEERROR_NONE                        (0),
		/*!< 0  NoError  */
	 VL53L0X_DEVICEERROR_VCSELCONTINUITYTESTFAILURE  (1),
	 VL53L0X_DEVICEERROR_VCSELWATCHDOGTESTFAILURE    (2),
	 VL53L0X_DEVICEERROR_NOVHVVALUEFOUND             (3),
	 VL53L0X_DEVICEERROR_MSRCNOTARGET                (4),
	 VL53L0X_DEVICEERROR_SNRCHECK                    (5),
	 VL53L0X_DEVICEERROR_RANGEPHASECHECK             (6),
	 VL53L0X_DEVICEERROR_SIGMATHRESHOLDCHECK         (7),
	 VL53L0X_DEVICEERROR_TCC                         (8),
	 VL53L0X_DEVICEERROR_PHASECONSISTENCY            (9),
	 VL53L0X_DEVICEERROR_MINCLIP                     (10),
	 VL53L0X_DEVICEERROR_RANGECOMPLETE               (11),
	 VL53L0X_DEVICEERROR_ALGOUNDERFLOW               (12),
	 VL53L0X_DEVICEERROR_ALGOOVERFLOW                (13),
	 VL53L0X_DEVICEERROR_RANGEIGNORETHRESHOLD        (14);
		private final int value;

		VL53L0X_DeviceError(int value) {
			this.value = value;
		}
	}

	/** @} end of VL53L0X_DeviceError_group */


	/** @defgroup VL53L0X_CheckEnable_group Check Enable list
	 *  @brief Check Enable code
	 *
	 *  Define used to specify the LimitCheckId.
	 *  Use @a VL53L0X_GetLimitCheckInfo() to get the string.
	 *  @{
	 */

	public static int VL53L0X_CHECKENABLE_SIGMA_FINAL_RANGE           = 0;
	public static int VL53L0X_CHECKENABLE_SIGNAL_RATE_FINAL_RANGE     = 1;
	public static int VL53L0X_CHECKENABLE_SIGNAL_REF_CLIP             = 2;
	public static int VL53L0X_CHECKENABLE_RANGE_IGNORE_THRESHOLD      = 3;
	public static int VL53L0X_CHECKENABLE_SIGNAL_RATE_MSRC            = 4;
	public static int VL53L0X_CHECKENABLE_SIGNAL_RATE_PRE_RANGE       = 5;

	public static int VL53L0X_CHECKENABLE_NUMBER_OF_CHECKS            = 6;

	/** @}  end of VL53L0X_CheckEnable_group */


	/** @defgroup VL53L0X_GpioFunctionality_group Gpio Functionality
	 *  @brief Defines the different functionalities for the device GPIO(s)
	 *  @{
	 */
	public enum VL53L0X_GpioFunctionality {

	 VL53L0X_GPIOFUNCTIONALITY_OFF                     		(  0), /*!< NO Interrupt  */
	 VL53L0X_GPIOFUNCTIONALITY_THRESHOLD_CROSSED_LOW   		(  1), /*!< Level Low (value < thresh_low)  */
	 VL53L0X_GPIOFUNCTIONALITY_THRESHOLD_CROSSED_HIGH   		(  2), /*!< Level High (value > thresh_high) */
	 VL53L0X_GPIOFUNCTIONALITY_THRESHOLD_CROSSED_OUT    		(  3), /*!< Out Of Window (value < thresh_low OR value > thresh_high)  */
	 VL53L0X_GPIOFUNCTIONALITY_NEW_MEASURE_READY        		(  4); /*!< New Sample Ready  */
		private final int value;

		VL53L0X_GpioFunctionality(int value) {
			this.value = value;
		}
	}

	/** @} end of VL53L0X_GpioFunctionality_group */


	/* Device register map */

	/** @defgroup VL53L0X_DefineRegisters_group Define Registers
	 *  @brief List of all the defined registers
	 *  @{
	 */
	public static byte VL53L0X_REG_SYSRANGE_START                        = 0x000;
		/** mask existing bit in #VL53L0X_REG_SYSRANGE_START*/
		public static byte VL53L0X_REG_SYSRANGE_MODE_MASK          = 0x0F;
		/** bit 0 in #VL53L0X_REG_SYSRANGE_START write 1 toggle state in
		 * continuous mode and arm next shot in single shot mode */
		public static byte VL53L0X_REG_SYSRANGE_MODE_START_STOP    = 0x01;
		/** bit 1 write 0 in #VL53L0X_REG_SYSRANGE_START set single shot mode */
		public static byte VL53L0X_REG_SYSRANGE_MODE_SINGLESHOT    = 0x00;
		/** bit 1 write 1 in #VL53L0X_REG_SYSRANGE_START set back-to-back
		 *  operation mode */
		public static byte VL53L0X_REG_SYSRANGE_MODE_BACKTOBACK    = 0x02;
		/** bit 2 write 1 in #VL53L0X_REG_SYSRANGE_START set timed operation
		 *  mode */
		public static byte VL53L0X_REG_SYSRANGE_MODE_TIMED         = 0x04;
		/** bit 3 write 1 in #VL53L0X_REG_SYSRANGE_START set histogram operation
		 *  mode */
		public static byte VL53L0X_REG_SYSRANGE_MODE_HISTOGRAM     = 0x08;


	public static byte VL53L0X_REG_SYSTEM_THRESH_HIGH               = 0x000C;
	public static byte VL53L0X_REG_SYSTEM_THRESH_LOW                = 0x000E;


	public static byte VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG		= 0x0001;
	public static byte VL53L0X_REG_SYSTEM_RANGE_CONFIG			= 0x0009;
	public static byte VL53L0X_REG_SYSTEM_INTERMEASUREMENT_PERIOD	 = 0x0004;


	public static byte VL53L0X_REG_SYSTEM_INTERRUPT_CONFIG_GPIO               = 0x000A;
		public static byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_DISABLED	 = 0x00;
		public static byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_LEVEL_LOW	= 0x01;
		public static byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_LEVEL_HIGH	= 0x02;
		public static byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_OUT_OF_WINDOW	= 0x03;
		public static byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_NEW_SAMPLE_READY	 = 0x04;

	public static byte VL53L0X_REG_GPIO_HV_MUX_ACTIVE_HIGH          = (byte)0x0084;


	public static byte VL53L0X_REG_SYSTEM_INTERRUPT_CLEAR           = 0x000B;

	/* Result registers */
	public static byte VL53L0X_REG_RESULT_INTERRUPT_STATUS          = 0x0013;
	public static byte VL53L0X_REG_RESULT_RANGE_STATUS              = 0x0014;

	public static byte VL53L0X_REG_RESULT_CORE_PAGE  = 1;
	public static byte VL53L0X_REG_RESULT_CORE_AMBIENT_WINDOW_EVENTS_RTN   = (byte)0x00BC;
	public static byte VL53L0X_REG_RESULT_CORE_RANGING_TOTAL_EVENTS_RTN    = (byte)0x00C0;
	public static byte VL53L0X_REG_RESULT_CORE_AMBIENT_WINDOW_EVENTS_REF   = (byte)0x00D0;
	public static byte VL53L0X_REG_RESULT_CORE_RANGING_TOTAL_EVENTS_REF    = (byte)0x00D4;
	public static byte VL53L0X_REG_RESULT_PEAK_SIGNAL_RATE_REF             = (byte)0x00B6;

	/* Algo register */

	public static byte VL53L0X_REG_ALGO_PART_TO_PART_RANGE_OFFSET_MM       = 0x0028;

	public static byte VL53L0X_REG_I2C_SLAVE_DEVICE_ADDRESS                = (byte)0x008a;

	/* Check Limit registers */
	public static byte VL53L0X_REG_MSRC_CONFIG_CONTROL                     = 0x0060;

	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_MIN_SNR                      = 0x0027;
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_VALID_PHASE_LOW              = 0x0056;
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_VALID_PHASE_HIGH             = 0x0057;
	public static byte VL53L0X_REG_PRE_RANGE_MIN_COUNT_RATE_RTN_LIMIT            = 0x0064;

	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_MIN_SNR                    = 0x0067;
	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_VALID_PHASE_LOW            = 0x0047;
	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_VALID_PHASE_HIGH           = 0x0048;
	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_MIN_COUNT_RATE_RTN_LIMIT   = 0x0044;


	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_SIGMA_THRESH_HI              = 0x0061;
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_SIGMA_THRESH_LO              = 0x0062;

	/* PRE RANGE registers */
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_VCSEL_PERIOD                 = 0x0050;
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_TIMEOUT_MACROP_HI            = 0x0051;
	public static byte VL53L0X_REG_PRE_RANGE_CONFIG_TIMEOUT_MACROP_LO            = 0x0052;

	public static byte VL53L0X_REG_SYSTEM_HISTOGRAM_BIN                          = (byte)0x0081;
	public static byte VL53L0X_REG_HISTOGRAM_CONFIG_INITIAL_PHASE_SELECT         = 0x0033;
	public static byte VL53L0X_REG_HISTOGRAM_CONFIG_READOUT_CTRL                 = 0x0055;

	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_VCSEL_PERIOD               = 0x0070;
	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_TIMEOUT_MACROP_HI          = 0x0071;
	public static byte VL53L0X_REG_FINAL_RANGE_CONFIG_TIMEOUT_MACROP_LO          = 0x0072;
	public static byte VL53L0X_REG_CROSSTALK_COMPENSATION_PEAK_RATE_MCPS         = 0x0020;

	public static byte VL53L0X_REG_MSRC_CONFIG_TIMEOUT_MACROP                    = 0x0046;


	public static byte VL53L0X_REG_SOFT_RESET_GO2_SOFT_RESET_N	                 = (byte)0x00bf;
	public static byte VL53L0X_REG_IDENTIFICATION_MODEL_ID                       = (byte)0x00c0;
	public static byte VL53L0X_REG_IDENTIFICATION_REVISION_ID                    = (byte)0x00c2;

	public static byte VL53L0X_REG_OSC_CALIBRATE_VAL                             = (byte)0x00f8;


	public static int VL53L0X_SIGMA_ESTIMATE_MAX_VALUE                          = 65535;
	/* equivalent to a range sigma of 655.35mm */

	public static byte VL53L0X_REG_GLOBAL_CONFIG_VCSEL_WIDTH          = 0x032;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_0   = (byte)0x0B0;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_1   = (byte)0x0B1;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_2   = (byte)0x0B2;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_3   = (byte)0x0B3;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_4   = (byte)0x0B4;
	public static byte VL53L0X_REG_GLOBAL_CONFIG_SPAD_ENABLES_REF_5   = (byte)0x0B5;

	public static byte VL53L0X_REG_GLOBAL_CONFIG_REF_EN_START_SELECT   = (byte)0xB6;
	public static byte VL53L0X_REG_DYNAMIC_SPAD_NUM_REQUESTED_REF_SPAD = 0x4E; /* 0x14E */
	public static byte VL53L0X_REG_DYNAMIC_SPAD_REF_EN_START_OFFSET    = 0x4F; /* 0x14F */
	public static byte VL53L0X_REG_POWER_MANAGEMENT_GO1_POWER_FORCE    = (byte)0x80;

	/*
	 * Speed of light in um per 1E-10 Seconds
	 */

	public static int VL53L0X_SPEED_OF_LIGHT_IN_AIR = 2997;

	public static byte VL53L0X_REG_VHV_CONFIG_PAD_SCL_SDA__EXTSUP_HV     = (byte)0x0089;

	public static byte VL53L0X_REG_ALGO_PHASECAL_LIM                         = 0x0030; /* 0x130 */
	public static byte VL53L0X_REG_ALGO_PHASECAL_CONFIG_TIMEOUT              = 0x0030;

	/** @} VL53L0X_DefineRegisters_group */

	/** @} VL53L0X_DevSpecDefines_group */
}

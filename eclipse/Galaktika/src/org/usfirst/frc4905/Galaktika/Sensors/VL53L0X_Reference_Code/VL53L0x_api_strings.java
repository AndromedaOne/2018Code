package org.usfirst.frc4905.Galaktika.Sensors.VL53L0X_Reference_Code;

public class VL53L0x_api_strings {
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


	public static final String VL53L0X_STRING_DEVICE_INFO_NAME         = "VL53L0X cut1.0";
	public static final String VL53L0X_STRING_DEVICE_INFO_NAME_TS0     = "VL53L0X TS0";
	public static final String VL53L0X_STRING_DEVICE_INFO_NAME_TS1     = "VL53L0X TS1";
	public static final String VL53L0X_STRING_DEVICE_INFO_NAME_TS2     = "VL53L0X TS2";
	public static final String VL53L0X_STRING_DEVICE_INFO_NAME_ES1     = "VL53L0X ES1 or later";
	public static final String VL53L0X_STRING_DEVICE_INFO_TYPE         = "VL53L0X";

	/* PAL ERROR strings */
	public static final String VL53L0X_STRING_ERROR_NONE = "No Error";
	public static final String  VL53L0X_STRING_ERROR_CALIBRATION_WARNING = "Calibration Warning Error";
	public static final String  VL53L0X_STRING_ERROR_MIN_CLIPPED = "Min clipped error";
	public static final String  VL53L0X_STRING_ERROR_UNDEFINED = "Undefined error";
	public static final String  VL53L0X_STRING_ERROR_INVALID_PARAMS = "Invalid parameters error";
	public static final String  VL53L0X_STRING_ERROR_NOT_SUPPORTED =				"Not supported error";
	public static final String  VL53L0X_STRING_ERROR_RANGE_ERROR =				"Range error";
	public static final String  VL53L0X_STRING_ERROR_TIME_OUT =				"Time out error";
	public static final String  VL53L0X_STRING_ERROR_MODE_NOT_SUPPORTED =				"Mode not supported error";
	public static final String  VL53L0X_STRING_ERROR_BUFFER_TOO_SMALL =				"Buffer too small";
	public static final String  VL53L0X_STRING_ERROR_GPIO_NOT_EXISTING =				"GPIO not existing";
	public static final String  VL53L0X_STRING_ERROR_GPIO_FUNCTIONALITY_NOT_SUPPORTED =				"GPIO funct not supported";
	public static final String  VL53L0X_STRING_ERROR_INTERRUPT_NOT_CLEARED =				"Interrupt not Cleared";
	public static final String  VL53L0X_STRING_ERROR_CONTROL_INTERFACE =				"Control Interface Error";
	public static final String  VL53L0X_STRING_ERROR_INVALID_COMMAND =				"Invalid Command Error";
	public static final String  VL53L0X_STRING_ERROR_DIVISION_BY_ZERO =				"Division by zero Error";
	public static final String  VL53L0X_STRING_ERROR_REF_SPAD_INIT =				"Reference Spad Init Error";
	public static final String  VL53L0X_STRING_ERROR_NOT_IMPLEMENTED =				"Not implemented error";

	public static final String  VL53L0X_STRING_UNKNOW_ERROR_CODE =				"Unknown Error Code";



	/* Range Status */
	public static final String  VL53L0X_STRING_RANGESTATUS_NONE                 = "No Update";
	public static final String  VL53L0X_STRING_RANGESTATUS_RANGEVALID           = "Range Valid";
	public static final String  VL53L0X_STRING_RANGESTATUS_SIGMA                = "Sigma Fail";
	public static final String  VL53L0X_STRING_RANGESTATUS_SIGNAL               = "Signal Fail";
	public static final String  VL53L0X_STRING_RANGESTATUS_MINRANGE             = "Min Range Fail";
	public static final String  VL53L0X_STRING_RANGESTATUS_PHASE                = "Phase Fail";
	public static final String  VL53L0X_STRING_RANGESTATUS_HW                   = "Hardware Fail";


	/* Range Status */
	public static final String  VL53L0X_STRING_STATE_POWERDOWN               = "POWERDOWN State";
	public static final String  VL53L0X_STRING_STATE_WAIT_STATICINIT =				"Wait for staticinit State";
	public static final String  VL53L0X_STRING_STATE_STANDBY                 = "STANDBY State";
	public static final String  VL53L0X_STRING_STATE_IDLE                    = "IDLE State";
	public static final String  VL53L0X_STRING_STATE_RUNNING                 = "RUNNING State";
	public static final String  VL53L0X_STRING_STATE_UNKNOWN                 = "UNKNOWN State";
	public static final String  VL53L0X_STRING_STATE_ERROR                   = "ERROR State";


	/* Device Specific */
	public static final String  VL53L0X_STRING_DEVICEERROR_NONE                   = "No Update";
	public static final String  VL53L0X_STRING_DEVICEERROR_VCSELCONTINUITYTESTFAILURE =				"VCSEL Continuity Test Failure";
	public static final String  VL53L0X_STRING_DEVICEERROR_VCSELWATCHDOGTESTFAILURE =				"VCSEL Watchdog Test Failure";
	public static final String  VL53L0X_STRING_DEVICEERROR_NOVHVVALUEFOUND =				"No VHV Value found";
	public static final String  VL53L0X_STRING_DEVICEERROR_MSRCNOTARGET =				"MSRC No Target Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_SNRCHECK =				"SNR Check Exit";
	public static final String  VL53L0X_STRING_DEVICEERROR_RANGEPHASECHECK =				"Range Phase Check Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_SIGMATHRESHOLDCHECK =				"Sigma Threshold Check Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_TCC =				"TCC Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_PHASECONSISTENCY =				"Phase Consistency Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_MINCLIP =				"Min Clip Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_RANGECOMPLETE =				"Range Complete";
	public static final String  VL53L0X_STRING_DEVICEERROR_ALGOUNDERFLOW =				"Range Algo Underflow Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_ALGOOVERFLOW =				"Range Algo Overlow Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_RANGEIGNORETHRESHOLD =				"Range Ignore Threshold Error";
	public static final String  VL53L0X_STRING_DEVICEERROR_UNKNOWN =				"Unknown error code";

	/* Check Enable */
	public static final String  VL53L0X_STRING_CHECKENABLE_SIGMA_FINAL_RANGE =				"SIGMA FINAL RANGE";
	public static final String  VL53L0X_STRING_CHECKENABLE_SIGNAL_RATE_FINAL_RANGE =				"SIGNAL RATE FINAL RANGE";
	public static final String  VL53L0X_STRING_CHECKENABLE_SIGNAL_REF_CLIP =				"SIGNAL REF CLIP";
	public static final String  VL53L0X_STRING_CHECKENABLE_RANGE_IGNORE_THRESHOLD =				"RANGE IGNORE THRESHOLD";
	public static final String  VL53L0X_STRING_CHECKENABLE_SIGNAL_RATE_MSRC =				"SIGNAL RATE MSRC";
	public static final String  VL53L0X_STRING_CHECKENABLE_SIGNAL_RATE_PRE_RANGE =				"SIGNAL RATE PRE RANGE";

	/* Sequence Step */
	public static final String  VL53L0X_STRING_SEQUENCESTEP_TCC                 =  "TCC";
	public static final String  VL53L0X_STRING_SEQUENCESTEP_DSS                 =  "DSS";
	public static final String  VL53L0X_STRING_SEQUENCESTEP_MSRC                =  "MSRC";
	public static final String  VL53L0X_STRING_SEQUENCESTEP_PRE_RANGE           =  "PRE RANGE";
	public static final String  VL53L0X_STRING_SEQUENCESTEP_FINAL_RANGE         =  "FINAL RANGE";
}

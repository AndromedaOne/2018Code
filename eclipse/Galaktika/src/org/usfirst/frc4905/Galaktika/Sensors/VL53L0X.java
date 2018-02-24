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
	private static final byte DefaultTuningSettings = 0;
	private static final byte VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_NEW_SAMPLE_READY = 0x04;
	private static final byte VL53L0X_INTERRUPTPOLARITY_LOW = 0x00;
	private I2CSensor m_i2cSensor;
	private int m_distance;
	private byte m_sequenceConfig;
	private byte m_vhvSettings;
	private byte m_phaseCal;

	private double getSensorReading() throws IOException {
		return m_distance;
	}

	public VL53L0X(I2C.Port port) throws IOException {
		HAL.report(tResourceType.kResourceType_Counter, 1);
		m_i2cSensor = new I2CSensor(port, kAddress);
		
		staticInit();
		performRefCalibration(1);
		performRefSpadManagement();
		setDeviceMode();
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Counter");
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

	private class VL53L0X_DeviceParameters_t {

	}

	 private void staticInit(){
			VL53L0X_DeviceParameters_t CurrentParameters = new VL53L0X_DeviceParameters_t();
			byte pTuningSettingBuffer;
			short tempword = 0;
			byte tempbyte = 0;
			byte UseInternalTuningSettings = 0;
			int count = 0;
			byte isApertureSpads = 0;
			int refSpadCount = 0;
			byte ApertureSpads = 0;
			byte vcselPulsePeriodPCLK;
			int seqTimeoutMicroSecs;

			VL53L0X_get_info_from_device(1);

			/* set the ref spad from NVM */
			count= m_referenceSpadCount;
			ApertureSpads = m_referenceSpadType;
			/* NVM value invalid */
			if ((ApertureSpads > 1) ||
				((ApertureSpads == 1) && (count > 32)) ||
				((ApertureSpads == 0) && (count > 12)))
				VL53L0X_perform_ref_spad_management();
			else
				VL53L0X_set_reference_spads();


			/* Initialize tuning settings buffer to prevent compiler warning. */
			pTuningSettingBuffer = DefaultTuningSettings;

				UseInternalTuningSettings = PALDevDataGet(
					UseInternalTuningSettings);

				if (UseInternalTuningSettings == 0)
					pTuningSettingBuffer = PALDevDataGet(
						pTuningSettingsPointer);
				else
					pTuningSettingBuffer = DefaultTuningSettings;

				VL53L0X_load_tuning_settings();


			/* Set interrupt config to new sample ready */
				VL53L0X_SetGpioConfig( 0, 0,
				VL53L0X_REG_SYSTEM_INTERRUPT_GPIO_NEW_SAMPLE_READY,
				VL53L0X_INTERRUPTPOLARITY_LOW);

				VL53L0X_WrByte( 0xFF, 0x01);
				VL53L0X_RdWord( 0x84, &tempword);
				VL53L0X_WrByte( 0xFF, 0x00);

				VL53L0X_SETDEVICESPECIFICPARAMETER( OscFrequencyMHz,
					VL53L0X_FIXPOINT412TOFIXPOINT1616(tempword));

			/* After static init, some device parameters may be changed,
			 * so update them */
				VL53L0X_GetDeviceParameters( &CurrentParameters);


				VL53L0X_GetFractionEnable( &tempbyte);

				PALDevDataSet( RangeFractionalEnable, tempbyte);

				PALDevDataSet( CurrentParameters, CurrentParameters);

			/* read the sequence config and save it */
				VL53L0X_RdByte(
				VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, &tempbyte);

				PALDevDataSet( SequenceConfig, tempbyte);

			/* Disable MSRC and TCC by default */
				VL53L0X_SetSequenceStepEnable(
					VL53L0X_SEQUENCESTEP_TCC, 0);


				VL53L0X_SetSequenceStepEnable(
				VL53L0X_SEQUENCESTEP_MSRC, 0);


			/* Set PAL State to standby */
				PALDevDataSet( PalState, VL53L0X_STATE_IDLE);



			/* Store pre-range vcsel period */
				VL53L0X_GetVcselPulsePeriod(
					VL53L0X_VCSEL_PERIOD_PRE_RANGE,
					&vcselPulsePeriodPCLK);

					VL53L0X_SETDEVICESPECIFICPARAMETER(
						PreRangeVcselPulsePeriod,
						vcselPulsePeriodPCLK);

			/* Store final-range vcsel period */
				VL53L0X_GetVcselPulsePeriod(
					VL53L0X_VCSEL_PERIOD_FINAL_RANGE,
					&vcselPulsePeriodPCLK);

					VL53L0X_SETDEVICESPECIFICPARAMETER(
						FinalRangeVcselPulsePeriod,
						vcselPulsePeriodPCLK);

			/* Store pre-range timeout */
				get_sequence_step_timeout(
					VL53L0X_SEQUENCESTEP_PRE_RANGE,
					&seqTimeoutMicroSecs);

				VL53L0X_SETDEVICESPECIFICPARAMETER(
					PreRangeTimeoutMicroSecs,
					seqTimeoutMicroSecs);

			/* Store final-range timeout */
				get_sequence_step_timeout(
					VL53L0X_SEQUENCESTEP_FINAL_RANGE,
					&seqTimeoutMicroSecs);

				VL53L0X_SETDEVICESPECIFICPARAMETER(
					FinalRangeTimeoutMicroSecs,
					seqTimeoutMicroSecs);
			}

			LOG_FUNCTION_END(Status);
			return Status;

	}

	 private void VL53L0X_SetGpioConfig(int i, int j, byte vl53l0xRegSystemInterruptGpioNewSampleReady,
			byte vl53l0xInterruptpolarityLow) {
		// TODO Auto-generated method stub

	}

	private void VL53L0X_load_tuning_settings() {
		// TODO Auto-generated method stub

	}

	private byte PALDevDataGet(byte useInternalTuningSettings) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void VL53L0X_set_reference_spads() {
		// TODO Auto-generated method stub

	}

	private void VL53L0X_perform_ref_spad_management() {
		// TODO Auto-generated method stub

	}

	private void VL53L0X_get_info_from_device(int i) {
		// TODO Auto-generated method stub

	}

	 private void performRefCalibration(int get_data_enable) throws IOException {
			byte SequenceConfig;

			/* store the value of the sequence config, this will be reset before the end of the function*/

			SequenceConfig = m_sequenceConfig;

			/* In the following function we don't save the config to optimize writes on device. Config is saved and restored only once. */
			VL53L0X_perform_vhv_calibration(get_data_enable, 0);

			VL53L0X_perform_phase_calibration( get_data_enable, 0);

			/* restore the previous Sequence Config */
			writeByteToSensor(VL53L0X_REG_SYSTEM_SEQUENCE_CONFIG, SequenceConfig);
			m_sequenceConfig = SequenceConfig;
	 }
	 
	private void VL53L0X_perform_phase_calibration(int get_data_enable, int i) {
		// TODO Auto-generated method stub
		
	}

	private void VL53L0X_perform_vhv_calibration(int get_data_enable, int i) {
		// TODO Auto-generated method stub
		 
	}

	public void performRefSpadManagement() {

	 }

	 public void setDeviceMode() {

	 }

}

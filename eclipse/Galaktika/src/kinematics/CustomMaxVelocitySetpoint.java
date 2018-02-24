package kinematics;

/**
 * This class is used in the Unit Cases class to define a setpoint that has a
 * max velocity different from the max velocity specified by the create
 * trajectory method.
 * 
 * @author seandoyle
 *
 */
public class CustomMaxVelocitySetpoint {
	private double m_setpoint;
	private double m_customMaxVelocity = 0.0;

	public CustomMaxVelocitySetpoint(double setpoint, double customMaxVelocity) {
		m_setpoint = setpoint;
		m_customMaxVelocity = customMaxVelocity;
	}

	public CustomMaxVelocitySetpoint(double setpoint) {
		m_setpoint = setpoint;
	}

	public double getSetpoint() {
		return m_setpoint;
	}

	public double getCustomMaxVelocity() {
		return m_customMaxVelocity;
	}
}

package kinematics;

public class PIDCalculator {
	public double m_p = 0.0;
	public double m_i = 0.0;
	public double m_d = 0.0;
	public double m_iAccum = 0.0;
	public double m_previousError = Double.NaN;
	
	public double getPIDOut(double setpoint, double currentPosition) {
		double error = setpoint - currentPosition;

		if (Double.isNaN(m_previousError)) {
			m_previousError = error;
		}
		m_iAccum += error;
		double pTerm = error * m_p;
		double iTerm = m_iAccum * m_i;
		double dTerm = (error - m_previousError) * m_d;

		m_previousError = error;
		return pTerm + iTerm + dTerm;
	}
	
	public void reset() {
		m_iAccum = 0.0;
		m_previousError = Double.NaN;
	}
}

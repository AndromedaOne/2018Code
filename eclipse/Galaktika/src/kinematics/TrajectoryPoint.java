package kinematics;

public class TrajectoryPoint {
	public TrajectoryPoint(double currentVelocity, double position, double timestamp) {
		m_currentVelocity = currentVelocity;
		m_timestamp = timestamp;
		m_position = position;
	}

	public TrajectoryPoint() {

	}

	public double m_currentVelocity, m_timestamp, m_position = 0.0;

}

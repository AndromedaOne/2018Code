package kinematics;

public class Point {
	public Point(double x) {
		m_x = x;
		m_values = new double[1];
		m_values[0] = x;
		m_dim = 1;
	}

	public Point(double x, double y) {
		m_x = x;
		m_y = y;
		m_values = new double[2];
		m_values[0] = x;
		m_values[1] = y;
		m_dim = 2;
	}

	public int getDim() {
		return m_dim;
	}

	public double getm_X() {
		return m_x;
	}

	public double getm_Y() {
		return m_y;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	double startVelocityCruisingDeltaTime;
	double endVelocityCruisingDeltaTime;

	double firstStartAccelerationCruisingDeltaTime;
	double firstEndAccelerationCruisingDeltaTime;
	double secondStartAccelerationCruisingDeltaTime;
	double secondEndAccelerationCruisingDeltaTime;

	double endDeltaTime;
	double maxVelocity;

	double maxAcceleration;

	public double getEndDeltaTime() {
		return endDeltaTime;
	}

	double m_x;
	double m_y = 0.0;
	private double[] m_values;
	private int m_dim;

	public double vi = 0.0;
	public double vf = 0.0;

	double ai = 0.0;
	double af = 0.0;

	public double getaf() {
		return af;
	}
}

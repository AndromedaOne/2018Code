package kinematics;

public interface KinematicsGenerator {

	/**
	 * This method takes the setpoint vector and turns it into a trajectory vector
	 * by time parameterizing each setpoint
	 */
	Path createTrajectory(double maxVelocity, double maxAcceleration, double maxJerk, double... setpoints);

}
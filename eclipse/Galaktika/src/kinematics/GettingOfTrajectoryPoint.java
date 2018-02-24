package kinematics;

/**
 * This class hold the methods used to get trajectory points for a specific time
 * on a path.
 * 
 * @author seandoyle
 *
 */

public class GettingOfTrajectoryPoint {

	/**
	 * This method takes a delta time from the beginning of a trajectory path and
	 * returns the point where that time is
	 */
	public static TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector) {
		return getTrajectoryPoint(Key, deltaTimeFromStartOfSetpointVector, false);
	}

	public static TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector,
			Boolean debugMode) {
		double velocity = 0.0;
		double deltaPosition = Double.NaN;
		double time = deltaTimeFromStartOfSetpointVector;
		BeforeWhatTime beforeWhatTime = null;
		Point setpoint = new Point(0, 0);
		double previousEndDeltaTimes = 0.0;
		double previousPositions = 0.0;
		int setpointIndex = -1;
		// Go through as many setpoints in the setpoint vector needed in order to find
		// the setpoint during which point in time being looked for falls.
		for (int i = 0; i < Key.setpointVector.size(); i++) {
			setpoint = Key.setpointVector.get(i);
			setpointIndex++;
			// If the time being asked for is before the end time of the setpoint that means
			// you have found the setpoint in which the trajectory point falls in so you can
			// break.
			// Else see if there is a setpoint beyond the current setpoint... If there is
			// not break, if there is accumulate the previous positions and end Delta times
			// for later use.
			if (setpoint.endDeltaTime >= deltaTimeFromStartOfSetpointVector) {
				break;
			} else {
				try {
					Key.setpointVector.get(i + 1);
				} catch (ArrayIndexOutOfBoundsException a) {

					break;
				}
				previousPositions += setpoint.m_x;
				previousEndDeltaTimes += setpoint.endDeltaTime;
			}
		}

		double deltaTimeFromSetpoint = deltaTimeFromStartOfSetpointVector - previousEndDeltaTimes;

		// This logic goes through and calculates the velocity of the point in time
		// being looked at as well as defines what BeforeWhatTime should be equal to

		// If the delta time is 0 that means that velocity and delta position from the
		// setpoint must be 0
		// THIS IS WRONG!!!! THE VELOCITY IS NOT NECCESARILY 0!!!
		if (deltaTimeFromSetpoint <= 0) {
			velocity = 0.0;
			deltaPosition = 0.0;

		}
		// Else if the delta time is before the firstStartAccelerationCruisingDeltaTime
		// that means that velocity can be calculated by using the kinematic equation
		// distance = vi*t + 1/2*a*t^2. In this instance distance is actually equal to
		// velocity covered, vi is equal to the initial acceleration, a is equal to the
		// jerk, and t is equal to deltaTimeFromSetpoint.
		else if (deltaTimeFromSetpoint < setpoint.firstStartAccelerationCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeFirstStartAccelerationCruisingDeltaTime;

			velocity = 0.5 * Key.maxJerk * Math.pow(deltaTimeFromSetpoint, 2);

		}
		// Else if the delta time is after firstStartAccelerationCruisingDeltaTime but
		// before firstEndAccelerationCruisingDeltaTime calculate velocity by finding
		// the velocity covered in between 0 and firstStartAccelerationCruisingDeltaTime
		// and add that to the velocity covered in between the current delta time and
		// firstStartAccelerationCruisingDeltaTime. Calculate the velocity covered in
		// between 0 and firstStartAccelerationCruisingDeltaTime by using the kinematic
		// equation distance = vi*t + 1/2*a*t^2 where distance is velocity covered, vi
		// is the initial acceleration, a is the jerk
		// and t is the firstStartAccelerationCruisingDeltaTime. Calculate the velocity
		// covered in between firstStartAccelerationCruisingDeltaTime and
		// deltaTimeFromSetpoint by using the kinematic equation vf = vi + a*t where vf
		// is the final velocity, a is acceleration and t is time. You can assume vi is
		// 0 because vi is just the initial velocity covered and that is added in when
		// you add together the two velocities covered.
		else if (deltaTimeFromSetpoint < setpoint.firstEndAccelerationCruisingDeltaTime) {

			beforeWhatTime = BeforeWhatTime.beforeFirstEndAccelerationCruisingDeltaTime;

			double jerkVelocityCovered = 0.5 * Key.maxJerk
					* Math.pow(setpoint.firstStartAccelerationCruisingDeltaTime, 2);

			double timeCruisingAtMaxAcceleration = deltaTimeFromSetpoint
					- setpoint.firstStartAccelerationCruisingDeltaTime;
			double velocityCoveredWhileAtMaxAcceleration = timeCruisingAtMaxAcceleration * Key.maxAcceleration;

			velocity = jerkVelocityCovered + velocityCoveredWhileAtMaxAcceleration;

		}
		// Else if the delta time is before the startVelocityCruisingDeltaTime but after
		// firstEndAccelerationCruisingDeltaTime calculate the velocity by getting the
		// velocity covered between 0 and firstEndAccelerationCruisingDeltaTime and
		// adding that to the velocity covered in between
		// firstEndAccelerationCruisingDeltaTime and deltaTime. Calculate the velocity
		// covered between 0 and firstEndAccelerationCruisingDeltaTime in the same way
		// velocity was calculated in the above logic except when multiplying time by
		// acceleration instead of using delta time use the time in between
		// firstStartAccelerationCruisingDeltaTime and
		// firstEndAccelerationCruisingDeltaTime. Calculate the velocity covered between
		// deltaTime and firstEndAccelerationCruisingDeltaTime by using the kinematic
		// equation vf^2 = vi^2 + 2*a*d where vf is the max acceleration, vi is the
		// current acceleration, a is jerk and d is velocity covered. Finally take these
		// velocities and add them together to ge the current velocity.
		else if (deltaTimeFromSetpoint < setpoint.startVelocityCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeStartVelocityCruisingDeltaTime;

			double firstStartJerkVelocityCovered = setpoint.maxAcceleration / 2
					* setpoint.firstStartAccelerationCruisingDeltaTime;
			double velocityCoveredWhileCruisingAtMaxAcceleration = setpoint.maxAcceleration
					* (setpoint.firstEndAccelerationCruisingDeltaTime
							- setpoint.firstStartAccelerationCruisingDeltaTime);
			double deltaTimeFromFirstEndAccelerationCruising = deltaTimeFromSetpoint
					- setpoint.firstEndAccelerationCruisingDeltaTime;
			double currentAcceleration = setpoint.maxAcceleration
					- Key.maxJerk * deltaTimeFromFirstEndAccelerationCruising;

			double firstEndJerkVelocityCovered = (Math.pow(setpoint.maxAcceleration, 2)
					- Math.pow(currentAcceleration, 2)) / (2 * Key.maxJerk);

			velocity = firstStartJerkVelocityCovered + velocityCoveredWhileCruisingAtMaxAcceleration
					+ firstEndJerkVelocityCovered;

		}
		// Else if the deltaTime is before the endVelocityCruisingDeltaTime but after
		// startVelocityCruisingDeltaTime velocity must be max velocity because this is
		// the time when velocity is cruising at max velocity.
		else if (deltaTimeFromSetpoint < setpoint.endVelocityCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeEndVelocityCruisingDeltaTime;

			velocity = setpoint.maxVelocity;

		}
		// Else if the deltaTime is before secondStartAccelerationCruisingDeltaTime
		// but after endVelocityCruisingDeltaTime find velocity by subtracting the
		// velocity covered between endVelocityCruisingDeltaTime and deltaTime from max
		// velocity. To calculate the velocity covered between
		// endVelocityCruisingDeltaTime and deltaTime refer to the comments above to
		// calculate velocity covered in between points
		else if (deltaTimeFromSetpoint < setpoint.secondStartAccelerationCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeSecondStartAccelerationCruisingDeltaTime;

			double secondEndJerkVelocity = setpoint.maxAcceleration / 2
					* (setpoint.endDeltaTime - setpoint.secondEndAccelerationCruisingDeltaTime);
			double velocityCoveredWhileCruisingAtMaxAcceleration = setpoint.maxAcceleration
					* (setpoint.secondEndAccelerationCruisingDeltaTime
							- setpoint.secondStartAccelerationCruisingDeltaTime);
			double currentAcceleration = setpoint.maxAcceleration
					- Key.maxJerk * (setpoint.secondStartAccelerationCruisingDeltaTime - deltaTimeFromSetpoint);
			double secondStartJerkVelocityCovered = (currentAcceleration + setpoint.maxAcceleration) / 2
					* (setpoint.secondStartAccelerationCruisingDeltaTime - deltaTimeFromSetpoint);
			velocity = secondEndJerkVelocity + velocityCoveredWhileCruisingAtMaxAcceleration
					+ secondStartJerkVelocityCovered;

		}
		// Else if deltaTime is before secondEndAccelerationCruisingDeltaTime but after
		// secondStartAccelerationCruisingDeltaTime calculate velocity by subtracting
		// the velocity covered between deltaTime and endVelocityCruisingDeltaTime from
		// the max velocity. To calculate this refer to the comments above to
		// calculate velocity covered in between points
		else if (deltaTimeFromSetpoint < setpoint.secondEndAccelerationCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeSecondEndAccelerationCruisingDeltaTime;

			double secondStartJerkVelocity = Key.maxAcceleration / 2
					* (setpoint.endDeltaTime - setpoint.secondEndAccelerationCruisingDeltaTime);
			double velocityCoveredWhileAtMaxAcceleration = Key.maxAcceleration
					* (setpoint.secondEndAccelerationCruisingDeltaTime - deltaTimeFromSetpoint);
			velocity = secondStartJerkVelocity + velocityCoveredWhileAtMaxAcceleration;

		} 
		// Else if the deltaTime is before 
		else if (deltaTimeFromSetpoint <= setpoint.endDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeEndDeltaTime;

			velocity = 0.5 * Math.pow((setpoint.endDeltaTime - deltaTimeFromSetpoint), 2) * Key.maxJerk;

		} else {
			velocity = 0.0;
			beforeWhatTime = BeforeWhatTime.afterEndDeltaTime;
		}
		if (Double.isNaN(deltaPosition)) {
			deltaPosition = getPosition(setpoint, deltaTimeFromSetpoint, Key.maxJerk, setpoint.maxAcceleration,
					beforeWhatTime, Key);
		}
		double directionConstant = 1.0;

		Point previousSetpoint = new Point(0, 0);
		try {
			previousSetpoint = Key.setpointVector.get(setpointIndex - 1);
		} catch (ArrayIndexOutOfBoundsException a) {

		}
		if (previousSetpoint.m_x > setpoint.m_x) {
			directionConstant = -1;
		} else {
			directionConstant = 1;
		}

		velocity *= directionConstant;
		deltaPosition *= directionConstant;
		double position = previousPositions + deltaPosition;
		TrajectoryPoint point = new TrajectoryPoint(velocity, position, time);

		return point;
	}

	private static double getPosition(Point setpoint, double deltaTime, double maxJerk, double maxAcceleration,
			BeforeWhatTime beforeWhatTime, Path Key) {
		double position = 0.0;
		double time;

		switch (beforeWhatTime) {

		case afterEndDeltaTime:

		case beforeEndDeltaTime:

			if (deltaTime < setpoint.endDeltaTime) {
				time = deltaTime - setpoint.secondEndAccelerationCruisingDeltaTime;
			} else {
				time = setpoint.endDeltaTime - setpoint.secondEndAccelerationCruisingDeltaTime;
			}
			double beforeEndDeltaTimeJerkVelocityCovered = maxAcceleration / 2
					* (setpoint.endDeltaTime - setpoint.secondEndAccelerationCruisingDeltaTime);

			position += beforeEndDeltaTimeJerkVelocityCovered * time
					+ 0.5 * (-1.0 * maxAcceleration) * Math.pow(time, 2)
					+ (1.0 / 6.0) * (1.0 * maxJerk) * Math.pow(time, 3);

		case beforeSecondEndAccelerationCruisingDeltaTime:
			if (deltaTime < setpoint.secondEndAccelerationCruisingDeltaTime) {
				time = deltaTime - setpoint.secondStartAccelerationCruisingDeltaTime;
			} else {
				time = setpoint.secondEndAccelerationCruisingDeltaTime
						- setpoint.secondStartAccelerationCruisingDeltaTime;
			}
			double secondStartAccelerationCruisingJerkVelocityCovered = maxAcceleration / 2
					* (setpoint.secondStartAccelerationCruisingDeltaTime - setpoint.endVelocityCruisingDeltaTime);
			double beforeSecondStartAccelerationCruisingVelocityCovered = setpoint.maxVelocity
					- secondStartAccelerationCruisingJerkVelocityCovered;

			double secondDistanceCoveredWhileAtMaxAcceleration = beforeSecondStartAccelerationCruisingVelocityCovered
					* time - 0.5 * maxAcceleration * Math.pow(time, 2);
			position += secondDistanceCoveredWhileAtMaxAcceleration;

		case beforeSecondStartAccelerationCruisingDeltaTime:
			if (deltaTime < setpoint.secondStartAccelerationCruisingDeltaTime) {
				time = deltaTime - setpoint.endVelocityCruisingDeltaTime;
			} else {
				time = setpoint.secondStartAccelerationCruisingDeltaTime - setpoint.endVelocityCruisingDeltaTime;
			}

			position += setpoint.maxVelocity * time + (1.0 / 6.0) * (-1 * maxJerk) * Math.pow(time, 3);

		case beforeEndVelocityCruisingDeltaTime:
			if (deltaTime < setpoint.endVelocityCruisingDeltaTime) {
				time = deltaTime - setpoint.startVelocityCruisingDeltaTime;
			} else {
				time = setpoint.endVelocityCruisingDeltaTime - setpoint.startVelocityCruisingDeltaTime;
			}

			position += setpoint.maxVelocity * time;

		case beforeStartVelocityCruisingDeltaTime:
			if (deltaTime < setpoint.startVelocityCruisingDeltaTime) {
				time = deltaTime - setpoint.firstEndAccelerationCruisingDeltaTime;
			} else {
				time = setpoint.startVelocityCruisingDeltaTime - setpoint.firstEndAccelerationCruisingDeltaTime;
			}

			double beforeStartVelocityCruisingJerkVelocityCovered = 0.5 * maxJerk
					* Math.pow(setpoint.firstStartAccelerationCruisingDeltaTime, 2);
			double maxAccelerationCruisingTime = setpoint.firstEndAccelerationCruisingDeltaTime
					- setpoint.firstStartAccelerationCruisingDeltaTime;
			double velocityAtFirstEndAccelerationCruisingDeltaTime = beforeStartVelocityCruisingJerkVelocityCovered
					+ maxAcceleration * maxAccelerationCruisingTime;

			position += (velocityAtFirstEndAccelerationCruisingDeltaTime * time
					+ 0.5 * maxAcceleration * Math.pow(time, 2) + (1.0 / 6.0) * (-1 * maxJerk) * Math.pow(time, 3));

		case beforeFirstEndAccelerationCruisingDeltaTime:
			if (deltaTime < setpoint.firstEndAccelerationCruisingDeltaTime) {
				time = deltaTime - setpoint.firstStartAccelerationCruisingDeltaTime;
			} else {
				time = setpoint.firstEndAccelerationCruisingDeltaTime
						- setpoint.firstStartAccelerationCruisingDeltaTime;
			}
			double startAccelerationVi = maxAcceleration / 2 * (setpoint.firstStartAccelerationCruisingDeltaTime);
			double distanceCovered = startAccelerationVi * time + 0.5 * maxAcceleration * Math.pow(time, 2);

			position += distanceCovered;

		case beforeFirstStartAccelerationCruisingDeltaTime:
			if (deltaTime < setpoint.firstStartAccelerationCruisingDeltaTime) {
				time = deltaTime;
			} else {
				time = setpoint.firstStartAccelerationCruisingDeltaTime;

			}
			position += (1.0 / 6.0) * maxJerk * Math.pow(time, 3);

		}
		return position;
	}
}

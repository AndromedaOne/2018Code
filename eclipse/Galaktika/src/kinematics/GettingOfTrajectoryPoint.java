package kinematics;



public class GettingOfTrajectoryPoint {
	/**
	 * This method takes a delta time from the beginning of a trajectory path and
	 * returns the point where that time is
	 */

	public static TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector) {
		return getTrajectoryPoint(Key, deltaTimeFromStartOfSetpointVector, false);
	}

	public static TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector, Boolean debugMode) {
		double velocity = 0.0;
		double deltaPosition = Double.NaN;
		double time = deltaTimeFromStartOfSetpointVector;
		BeforeWhatTime beforeWhatTime = null;
		Point setpoint = new Point(0, 0);
		double previousEndDeltaTimes = 0.0;
		double previousPositions = 0.0;
		int setpointIndex = -1;
		for (int i = 0; i < Key.setpointVector.size(); i++) {
			setpoint = Key.setpointVector.get(i);
			setpointIndex++;
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

		if (deltaTimeFromSetpoint <= 0) {
			velocity = 0.0;
			deltaPosition = 0.0;

		} else if (deltaTimeFromSetpoint < setpoint.firstStartAccelerationCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeFirstStartAccelerationCruisingDeltaTime;

			velocity = 0.5 * Key.maxJerk * Math.pow(deltaTimeFromSetpoint, 2);

		} else if (deltaTimeFromSetpoint < setpoint.firstEndAccelerationCruisingDeltaTime) {

			beforeWhatTime = BeforeWhatTime.beforeFirstEndAccelerationCruisingDeltaTime;

			double jerkVelocityCovered = 0.5 * Key.maxJerk
					* Math.pow(setpoint.firstStartAccelerationCruisingDeltaTime, 2);

			double timeCruisingAtMaxAcceleration = deltaTimeFromSetpoint
					- setpoint.firstStartAccelerationCruisingDeltaTime;
			double velocityCoveredWhileAtMaxAcceleration = timeCruisingAtMaxAcceleration * Key.maxAcceleration;

			velocity = jerkVelocityCovered + velocityCoveredWhileAtMaxAcceleration;

		} else if (deltaTimeFromSetpoint < setpoint.startVelocityCruisingDeltaTime) {
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

		} else if (deltaTimeFromSetpoint < setpoint.endVelocityCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeEndVelocityCruisingDeltaTime;

			velocity = setpoint.maxVelocity;

		} else if (deltaTimeFromSetpoint < setpoint.secondStartAccelerationCruisingDeltaTime) {
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

		} else if (deltaTimeFromSetpoint < setpoint.secondEndAccelerationCruisingDeltaTime) {
			beforeWhatTime = BeforeWhatTime.beforeSecondEndAccelerationCruisingDeltaTime;

			double secondStartJerkVelocity = Key.maxAcceleration / 2
					* (setpoint.endDeltaTime - setpoint.secondEndAccelerationCruisingDeltaTime);
			double velocityCoveredWhileAtMaxAcceleration = Key.maxAcceleration
					* (setpoint.secondEndAccelerationCruisingDeltaTime - deltaTimeFromSetpoint);
			velocity = secondStartJerkVelocity + velocityCoveredWhileAtMaxAcceleration;

		} else if (deltaTimeFromSetpoint <= setpoint.endDeltaTime + previousEndDeltaTimes) {
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

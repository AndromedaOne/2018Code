package kinematics;

import java.util.Vector;

import kinematics.KinematicsException.*;

/**
 * This is the class that holds all of the methods used to check the paths to
 * make sure that they follow the laws of physics.
 * 
 * @author seandoyle
 *
 */
public class Checkers {

	private static double m_velocityTolerance = 0.1;

	/**
	 * This method takes in a path and will run all of the checkers against it to
	 * make sure that the path obeys the laws of physics.
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */
	static void checkTrajectoryPath(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {

		checkVelocity(Key, kinematicsTester);

		checkAcceleration(Key, kinematicsTester);

		checkJerk(Key, kinematicsTester);

		checkFinalPosition(Key, kinematicsTester);

		checkTrajectoryLogic(Key, kinematicsTester);

		checkNaN(Key, kinematicsTester);
	}

	/**
	 * Checks to make sure that there aren't any points that are NAN for whatever
	 * reason. The points it checks are the very last point in time and points every
	 * "trajectory point interval" (A number that is just a good time length in
	 * between points) seconds
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */

	private static void checkNaN(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMessage;
		double endDeltatTimeFromStartOfPath = 0;
		// Gets the total amount of time it takes to reach the end of the path.
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		// If the end time of the path is NAN throw
		if (Double.isNaN(endDeltatTimeFromStartOfPath)) {
			errMessage = "The end time of the setpoint is NaN";

			kinematicsException = new KinematicsException(errMessage);
			throw kinematicsException;
		}
		// Go thru a whole bunch of points and if any of the parameters of the point are
		// NAN throw
		for (double i = 0; i < endDeltatTimeFromStartOfPath; i += Kinematics.getTrajectoryPointInterval()) {
			TrajectoryPoint currentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, i);

			if (Double.isNaN(currentPoint.m_currentVelocity) || Double.isNaN(currentPoint.m_position)
					|| Double.isNaN(currentPoint.m_timestamp)) {
				errMessage = "The point at time: " + currentPoint.m_timestamp + " has NaN as one of the parameters";
				kinematicsException = new KinematicsException(errMessage);
				throw kinematicsException;
			}
		}
	}

	/**
	 * This method just looks at the end time of the path and makes sure that it is
	 * also the setpoint that the path is trying to reach
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */
	private static void checkFinalPosition(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMesage;
		double endDeltatTimeFromStartOfPath = 0;
		// Gets the total amount of time it takes to reach the end of the path.
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}

		// If the position of the final point of the path is not the same as the
		// setpoint that the path is trying to reach throw
		if (GettingOfTrajectoryPoint.getTrajectoryPoint(Key, endDeltatTimeFromStartOfPath).m_position
				- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() > 0.1
				|| GettingOfTrajectoryPoint.getTrajectoryPoint(Key, endDeltatTimeFromStartOfPath).m_position
						- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() < -0.1) {
			errMesage = "The final position of the trajectory path does not match the final position of the setpoint path!";
			kinematicsException = new KinematicsException(errMesage);
			throw kinematicsException;
		}

	}

	/**
	 * This method looks through a path and checks to make sure that velocity never
	 * exceeds the max velocity specified by the user. It also compares the velocity
	 * given by the getTrajectoryOfPoint method and velocity calculated from change
	 * in position over change in time to make sure that they do not differ.
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */

	private static void checkVelocity(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsVector = new Vector<TrajectoryPoint>();
		Vector<Double> velocityCalculatedFromPositionVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		// Gets the total amount of time it takes to reach the end of the path.
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		// Grabs a whole bunch of points to test their velocities
		for (double currentTime = 0.0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);

			originalTrajectoryPointsVector.add(trajectoryPoint);
		}
		// Calculate the velocities of all of the points just grabbed by looking at the
		// change in position in those points divided by the change in time.
		for (int i = 0; i < originalTrajectoryPointsVector.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsVector.get(i);
			double calculatedVelocity = UnitCases.getVelocityOfPoint(Key, originalTrajectoryPoint);
			velocityCalculatedFromPositionVector.add(calculatedVelocity);
		}

		// Checks to make sure that the velocities are reasonable
		for (int i = 0; i < velocityCalculatedFromPositionVector.size(); i++) {
			double calculatedVelocity = velocityCalculatedFromPositionVector.get(i);
			double originalTrajectoryPointPathVelocity = originalTrajectoryPointsVector.get(i).m_currentVelocity;

			// If the velocity calculated by change in position divided by change in time
			// exceeds the max velocity of the Path, throw
			if (Math.abs(calculatedVelocity) > Key.getMaxVelocity() + m_velocityTolerance) {
				errMessage = "The point at time: " + originalTrajectoryPointsVector.get(i).m_timestamp
						+ " has a calculated velocity that exceeds the maxVelocity!";
				kinematicsException = new KinematicsException(errMessage);
				System.out.println("calculatedVelocity: " + calculatedVelocity);
				throw kinematicsException;
			}
			// If the velocity calculated by change in position divided by change in time is
			// not the same as the velocity given back by the getTrajectoryPoint method
			// throw
			// The calculatedVelocity and originalTrajectoryPointPathVelocity should be at
			// the same time because they were added to velocityCalculatedFromPositionVector
			// and originalTrajectoryPointsPath respectively at the same time
			if (Math.abs(calculatedVelocity - originalTrajectoryPointPathVelocity) > m_velocityTolerance) {
				errMessage = "The point at time: " + originalTrajectoryPointsVector.get(i).m_timestamp
						+ " has a difference between calculated velocity and original velocity which exceed 0.1!";
				kinematicsException = new KinematicsException(errMessage);

				throw kinematicsException;
			}
			// If the velocity given back by the getTrajectoryPoint method exceeds the max
			// velocity specified by the user, throw.
			if (Math.abs(originalTrajectoryPointPathVelocity) > Key.getMaxVelocity()) {
				errMessage = "The point at time: " + originalTrajectoryPointsVector.get(i).m_timestamp
						+ " has an original velocity that exceeds the maxVelocity!";
				kinematicsException = new KinematicsException(errMessage);
				throw kinematicsException;
			}
		}
	}

	/**
	 * This method looks through a path and checks to make sure that acceleration
	 * never exceeds the max acceleration specified by the user.
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */
	private static void checkAcceleration(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsPath = new Vector<TrajectoryPoint>();
		Vector<Double> accelerationCalculatedFromPositionVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		// Gets the total amount of time it takes to reach the end of the path.
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		// Grabs a whole bunch of points to test their accelerations
		for (double currentTime = 0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);
			originalTrajectoryPointsPath.add(trajectoryPoint);
		}
		// Calculate the accelerations of all of the points just grabbed by looking at
		// the
		// change in velocity in those points divided by the change in time.
		for (int i = 0; i < originalTrajectoryPointsPath.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsPath.get(i);
			double calculatedAcceleration = UnitCases.getAccelerationOfPoint(Key, originalTrajectoryPoint);

			accelerationCalculatedFromPositionVector.add(calculatedAcceleration);
		}

		// Look through all the calculated accelerations and if one is above the max
		// acceleration, throw
		for (int i = 0; i < accelerationCalculatedFromPositionVector.size(); i++) {
			double calculatedAcceleration = accelerationCalculatedFromPositionVector.get(i);
			if (Math.abs(calculatedAcceleration) > Key.getMaxAcceleration() + 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a calculated acceleration that exceeds the maxAcceleration!";
				kinematicsException = new KinematicsException(errMessage);
				throw kinematicsException;
			}

		}
	}

	/**
	 * This method looks through a path and checks to make sure that jerk never
	 * exceeds the max jerk specified by the user.
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */
	private static void checkJerk(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsPath = new Vector<TrajectoryPoint>();
		Vector<Double> jerkCalculatedFromCalculatedAccelerationVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		// Gets the total amount of time it takes to reach the end of the path.
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		// Grabs a whole bunch of points to test their jerks
		for (double currentTime = 0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);
			originalTrajectoryPointsPath.add(trajectoryPoint);
		}
		// Calculate the jerks of all of the points just grabbed by looking at the
		// change in acceleration in those points divided by the change in time.
		for (int i = 0; i < originalTrajectoryPointsPath.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsPath.get(i);

			double calculatedJerk = UnitCases.getJerkOfPoint(Key, originalTrajectoryPoint);

			jerkCalculatedFromCalculatedAccelerationVector.add(calculatedJerk);
		}
		// Look through all the calculated jerks and if one is above the max jerk, throw
		for (int i = 0; i < jerkCalculatedFromCalculatedAccelerationVector.size(); i++) {
			double calculatedJerk = jerkCalculatedFromCalculatedAccelerationVector.get(i);
			if (Math.abs(calculatedJerk) > Key.getMaxVelocity() + 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a calculated jerk that exceeds the maxJerk!";
				kinematicsException = new KinematicsException(errMessage);
				System.out.println("calculatedJerk: " + calculatedJerk);
				throw kinematicsException;
			}
		}
	}

	/**
	 * This method looks through a path and makes sure that when setpoints change
	 * the velocity of the point when setpoints change is reasonable depending on
	 * whether the direction of the setpoints is changing or not.
	 * 
	 * @param Key
	 * @param kinematicsTester
	 * @throws KinematicsException
	 */
	private static void checkTrajectoryLogic(Path Key, KinematicsTester kinematicsTester) throws KinematicsException {
		KinematicsException kinematicsException;
		String errMessage;
		double currentTrajectoryPointIndex = 0;
		// for every setpoint in the path
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			Point setpoint = Key.getSetpointVector().get(i);
			currentTrajectoryPointIndex += setpoint.getEndDeltaTime();
			Point nextSetpoint;
			Point lastSetpoint = new Point(0, 0);
			boolean traveledInAPositiveDirection;
			boolean willTravelInAPositiveDirection;
			// This point is the last point for each setpoint. It should have a position
			// equal to that of the setpoint because it is supposed to be the time when the
			// setpoint is reached.
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
					currentTrajectoryPointIndex);
			try {
				nextSetpoint = Key.getSetpointVector().get(i + 1);
			} catch (ArrayIndexOutOfBoundsException a) {
				continue;
			}
			try {
				lastSetpoint = Key.getSetpointVector().get(i - 1);
			} catch (ArrayIndexOutOfBoundsException a) {

			}
			// If the previous setpoint is less than the current setpoint that means that
			// between the two setpoints you are traveling in a positive directions,
			// otherwise you are not traveling in a positive direction between the two
			// points.
			if (lastSetpoint.getm_X() < setpoint.getm_X()) {
				traveledInAPositiveDirection = true;
			} else {
				traveledInAPositiveDirection = false;
			}

			// If the next setpoint is greater than the current setpoint that means that
			// between the two setpoints you are traveling in a positive directions,
			// otherwise you are not traveling in a positive direction between the two
			// points
			if (setpoint.getm_X() < nextSetpoint.getm_X()) {
				willTravelInAPositiveDirection = true;
			} else {
				willTravelInAPositiveDirection = false;
			}

			if (trajectoryPoint.m_timestamp == 0.0) {
				continue;
			}

			// If you are traveling in a positive direction and continuing to do so or you
			// are traveling in a negative direction and continuing to do so then your
			// velocity at the point when you reach your current setpoint should not be 0
			// because you don't need to decrease your velocity if you are traveling in the
			// same direction. if the velocity is 0 throw.

			// Else you are are going to change direction in between setpoints. hat means
			// that
			// at your current setpoint your velocity should be 0 because if it is not that
			// means that will overshoot your current setpoint and not change directions as
			// efficiently. In addition the acceleration and jerk should be 0 for the same
			// reasons so
			// if the acceleration and jerk are not 0 or if the velocity is not 0 throw.

			if ((traveledInAPositiveDirection && willTravelInAPositiveDirection)
					|| (!traveledInAPositiveDirection && !willTravelInAPositiveDirection)) {
				if (trajectoryPoint.m_currentVelocity < 0.01 && trajectoryPoint.m_currentVelocity > -0.01) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is not changing however the velocity is 0!";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}

			} else {
				// Checks the velocity of the current point to make sure that it does not exceed
				// 0.
				if (Math.abs(trajectoryPoint.m_currentVelocity) > 0.1) {

					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is changing however the velocity is not 0!";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}
				double calculatedVelocity = UnitCases.getVelocityOfPoint(Key, trajectoryPoint);
				// Calculates the velocity by taking the change in position and diving it by the
				// change in time and then makes sure that that velocity is 0.
				if (Math.abs(trajectoryPoint.m_currentVelocity - calculatedVelocity) > 0.1) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the calculated velocity and the current velocity are not equal";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}
				// Checks the final velocity of the setpoint and makes sure that that velocity
				// is 0.
				if (Math.abs(setpoint.vf - calculatedVelocity) > 0.1) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the vf and the calculated velocity are not equal";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}

				double calculatedAcceleration = UnitCases.getAccelerationOfPoint(Key, trajectoryPoint);
				TrajectoryPoint nextPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
						trajectoryPoint.m_timestamp + UnitCases.m_deltaTimeFromOriginalPoint);
				double nextAcceleration = UnitCases.getAccelerationOfPoint(Key, nextPoint);
				double currentJerk = UnitCases.getJerkOfPoint(Key, trajectoryPoint.m_timestamp);

				// If the acceleration calculated from change in velocity divided by change in
				// time is not he same as the final acceleration of the setpoint, throw.
				if (Math.abs(Math.abs(calculatedAcceleration) - setpoint.getaf()) > 0.0001) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the calculated acceleration and the final acceleration are not equal";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}
				// If the acceleration calculated from change in velocity divided by change in
				// time is very different from the next acceleration (ie the acceleration jumps
				// from 0.5 to 0.0), throw.
				if (Math.abs(calculatedAcceleration - nextAcceleration) > 0.01) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where setpoints are switching and the accelerations are messed up";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}
				// If the jerk is above the max jerk throw.
				if (currentJerk > Key.getMaxJerk()) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the jerk exceeds the maximum jerk";
					kinematicsException = new KinematicsException(errMessage);
					throw kinematicsException;
				}

			}
		}
	}

}

package kinematics;

import java.util.Vector;

import kinematics.CheckerExceptions.*;

public class Checkers {
	static CheckerExceptions m_checkerExceptions = new CheckerExceptions();
	static void checkTrajectoryPath(Path Key, KinematicsTester kinematicsTester)
			throws InvalidVelocityException, InvalidNextVelocityFromLastAcceleration, InvalidAccelerationException,
			InvalidFinalPosition, InvalidTrajectoryLogic, NaNException, InvalidJerkException {

		checkVelocity(Key, kinematicsTester);

		checkAcceleration(Key, kinematicsTester);

		checkJerk(Key, kinematicsTester);

		checkFinalPosition(Key, kinematicsTester);

		checkTrajectoryLogic(Key, kinematicsTester);

		checkNaN(Key, kinematicsTester);
	}

	private static void checkNaN(Path Key, KinematicsTester kinematicsTester) throws NaNException {
		NaNException naNException;
		String errMessage;
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		if (Double.isNaN(endDeltatTimeFromStartOfPath)) {
			errMessage = "The end time of the setpoint is NaN";

			naNException = m_checkerExceptions.new NaNException(errMessage);
			throw naNException;
		}
		for (double i = 0; i < endDeltatTimeFromStartOfPath; i += Kinematics.getTrajectoryPointInterval()) {
			TrajectoryPoint currentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, i);

			if (Double.isNaN(currentPoint.m_currentVelocity) || Double.isNaN(currentPoint.m_position)
					|| Double.isNaN(currentPoint.m_timestamp)) {
				errMessage = "The point at time: " + currentPoint.m_timestamp + " has NaN as one of the parameters";
				naNException = m_checkerExceptions.new NaNException(errMessage);
				throw naNException;
			}
		}
	}

	private static void checkFinalPosition(Path Key, KinematicsTester kinematicsTester) throws InvalidFinalPosition {
		InvalidFinalPosition invalidFinalPosition;
		String errMesage;
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}

		if (GettingOfTrajectoryPoint.getTrajectoryPoint(Key, endDeltatTimeFromStartOfPath).m_position
				- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() > 0.1
				|| GettingOfTrajectoryPoint.getTrajectoryPoint(Key, endDeltatTimeFromStartOfPath).m_position
				- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() < -0.1) {
			errMesage = "The final position of the trajectory path does not match the final position of the setpoint path!";
			invalidFinalPosition = m_checkerExceptions.new InvalidFinalPosition(errMesage);
			throw invalidFinalPosition;
		}

	}

	private static void checkVelocity(Path Key, KinematicsTester kinematicsTester) throws InvalidVelocityException {
		InvalidVelocityException invalidVelocityException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsPath = new Vector<TrajectoryPoint>();
		Vector<Double> velocityCalculatedFromPositionVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		for (double currentTime = 0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);
			originalTrajectoryPointsPath.add(trajectoryPoint);
		}
		for (int i = 0; i < originalTrajectoryPointsPath.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsPath.get(i);
			double calculatedVelocity = UnitCases.getVelocityOfPoint(Key, originalTrajectoryPoint);
			if (calculatedVelocity == 17091.9392) {
				System.out.println(
						"originalTrajectoryPoint.m_currentVelocity: " + originalTrajectoryPoint.m_currentVelocity);
			}
			velocityCalculatedFromPositionVector.add(calculatedVelocity);
		}

		for (int i = 0; i < velocityCalculatedFromPositionVector.size(); i++) {
			double calculatedVelocity = velocityCalculatedFromPositionVector.get(i);
			double originalTrajectoryPointPathVelocity = originalTrajectoryPointsPath.get(i).m_currentVelocity;

			if (Math.abs(calculatedVelocity) > Key.getMaxVelocity() + 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a calculated velocity that exceeds the maxVelocity!";
				invalidVelocityException = m_checkerExceptions.new InvalidVelocityException(errMessage);
				System.out.println("calculatedVelocity: " + calculatedVelocity);
				throw invalidVelocityException;
			}
			if (Math.abs(calculatedVelocity - originalTrajectoryPointPathVelocity) > 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a difference between calculated velocity and original velocity which exceed 0.1!";
				invalidVelocityException = m_checkerExceptions.new InvalidVelocityException(errMessage);

				throw invalidVelocityException;
			}
			if (Math.abs(originalTrajectoryPointPathVelocity) > Key.getMaxVelocity()) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has an original velocity that exceeds the maxVelocity!";
				invalidVelocityException = m_checkerExceptions.new InvalidVelocityException(errMessage);
				throw invalidVelocityException;
			}
		}
	}

	private static void checkAcceleration(Path Key, KinematicsTester kinematicsTester)
			throws InvalidAccelerationException {
		InvalidAccelerationException invalidAccelerationException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsPath = new Vector<TrajectoryPoint>();
		Vector<Double> accelerationCalculatedFromPositionVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		for (double currentTime = 0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);
			originalTrajectoryPointsPath.add(trajectoryPoint);
		}

		for (int i = 0; i < originalTrajectoryPointsPath.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsPath.get(i);
			double calculatedAcceleration = UnitCases.getAccelerationOfPoint(Key, originalTrajectoryPoint);

			accelerationCalculatedFromPositionVector.add(calculatedAcceleration);
		}
		for (int i = 0; i < accelerationCalculatedFromPositionVector.size(); i++) {
			double calculatedAcceleration = accelerationCalculatedFromPositionVector.get(i);
			if (Math.abs(calculatedAcceleration) > Key.getMaxAcceleration() + 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a calculated acceleration that exceeds the maxAcceleration!";
				invalidAccelerationException = m_checkerExceptions.new InvalidAccelerationException(errMessage);
				throw invalidAccelerationException;
			}

		}
	}

	private static void checkJerk(Path Key, KinematicsTester kinematicsTester) throws InvalidJerkException {
		InvalidJerkException invalidJerkException;
		String errMessage;
		Vector<TrajectoryPoint> originalTrajectoryPointsPath = new Vector<TrajectoryPoint>();
		Vector<Double> jerkCalculatedFromCalculatedAccelerationVector = new Vector<Double>();
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		for (double currentTime = 0; currentTime <= endDeltatTimeFromStartOfPath; currentTime += Kinematics
				.getTrajectoryPointInterval()) {
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTime);
			originalTrajectoryPointsPath.add(trajectoryPoint);
		}
		for (int i = 0; i < originalTrajectoryPointsPath.size(); i++) {
			TrajectoryPoint originalTrajectoryPoint = originalTrajectoryPointsPath.get(i);

			double calculatedJerk = UnitCases.getJerkOfPoint(Key, originalTrajectoryPoint);
			
			jerkCalculatedFromCalculatedAccelerationVector.add(calculatedJerk);
		}
		for (int i = 0; i < jerkCalculatedFromCalculatedAccelerationVector.size(); i++) {
			double calculatedJerk = jerkCalculatedFromCalculatedAccelerationVector.get(i);
			if (Math.abs(calculatedJerk) > Key.getMaxVelocity() + 0.1) {
				errMessage = "The point at time: " + originalTrajectoryPointsPath.get(i).m_timestamp
						+ " has a calculated jerk that exceeds the maxJerk!";
				invalidJerkException = m_checkerExceptions.new InvalidJerkException(errMessage);
				System.out.println("calculatedJerk: " + calculatedJerk);
				throw invalidJerkException;
			}
		}
	}

	private static void checkTrajectoryLogic(Path Key, KinematicsTester kinematicsTester)
			throws InvalidTrajectoryLogic {
		InvalidTrajectoryLogic invalidTrajectoryLogic;
		String errMessage;
		double currentTrajectoryPointIndex = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			Point setpoint = Key.getSetpointVector().get(i);
			currentTrajectoryPointIndex += setpoint.getEndDeltaTime();
			Point nextSetpoint;
			Point lastSetpoint = new Point(0, 0);
			boolean traveledInAPositiveDirection;
			boolean willTravelInAPositiveDirection;
			TrajectoryPoint trajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, currentTrajectoryPointIndex);
			try {
				nextSetpoint = Key.getSetpointVector().get(i + 1);
			} catch (ArrayIndexOutOfBoundsException a) {
				continue;
			}
			try {
				lastSetpoint = Key.getSetpointVector().get(i - 1);
			} catch (ArrayIndexOutOfBoundsException a) {

			}

			if (lastSetpoint.getm_X() < setpoint.getm_X()) {
				traveledInAPositiveDirection = true;
			} else {
				traveledInAPositiveDirection = false;
			}

			if (setpoint.getm_X() < nextSetpoint.getm_X()) {
				willTravelInAPositiveDirection = true;
			} else {
				willTravelInAPositiveDirection = false;
			}

			if (trajectoryPoint.m_timestamp == 0.0) {
				continue;
			}

			if ((traveledInAPositiveDirection && willTravelInAPositiveDirection)
					|| (!traveledInAPositiveDirection && !willTravelInAPositiveDirection)) {
				if (trajectoryPoint.m_currentVelocity < 0.01 && trajectoryPoint.m_currentVelocity > -0.01) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is not changing however the velocity is 0!";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
			} else {

				if (Math.abs(trajectoryPoint.m_currentVelocity) > 0.1) {

					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is changing however the velocity is not 0!";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
				double calculatedVelocity = UnitCases.getVelocityOfPoint(Key, trajectoryPoint);
				if (Math.abs(trajectoryPoint.m_currentVelocity - calculatedVelocity) > 0.1) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the calculated velocity and the current velocity are not equal";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
				if (Math.abs(setpoint.vf - calculatedVelocity) > 0.1) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the vf and the calculated velocity are not equal";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}

				double calculatedAcceleration = UnitCases.getAccelerationOfPoint(Key, trajectoryPoint);
				TrajectoryPoint nextPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
						trajectoryPoint.m_timestamp + UnitCases.m_deltaTimeFromOriginalPoint);
				double nextAcceleration = UnitCases.getAccelerationOfPoint(Key, nextPoint);
				double currentJerk = UnitCases.getJerkOfPoint(Key, trajectoryPoint.m_timestamp);

				if (Math.abs(Math.abs(calculatedAcceleration) - setpoint.getaf()) > 0.0001) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the calculated acceleration and the final acceleration are not equal";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
				if (Math.abs(calculatedAcceleration - nextAcceleration) > 0.01) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where setpoints are switching and the accelerations are messed up";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
				if (currentJerk > Key.getMaxJerk()) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point where the jerk exceeds the maximum jerk";
					invalidTrajectoryLogic = m_checkerExceptions.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}

			}
		}
	}

}

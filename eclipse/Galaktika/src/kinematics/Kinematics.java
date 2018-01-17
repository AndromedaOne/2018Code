package kinematics;

import java.util.Vector;

public class Kinematics {

	private enum MaxVelocityAndMaxAccelerationTrajectoryType {
		willCruiseAtMaxVelocityAndMaxAcceleration, willCruiseAtMaxAccelerationNotMaxVelocity, willNotCruiseAtMaxVelocityOrMaxAcceleration
	}

	private Kinematics m_instance;
	private boolean alreadyPrinted = true;

	public Kinematics getInstance() {
		if (m_instance == null) {
			m_instance = new Kinematics();
		}
		return m_instance;
	}

	private static double m_trajectoryPointInterval = 1.0;

	public static double getTrajectoryPointInterval() {
		return m_trajectoryPointInterval;
	}

	public class InvalidDimentionException extends Exception {

		private static final long serialVersionUID = 1L;

		public InvalidDimentionException(String message) {
			super(message);
		}
	}

	public class Path extends TrajectoryPaths {

		Vector<Point> setpointVector = new Vector<Point>();

		public Vector<Point> getSetpointVector() {
			return setpointVector;
		}

		int setPointDimention = 0;

	}

	private class TrajectoryPaths {
		double maxVelocity;
		double maxAcceleration;
		double maxJerk;

		public double getMaxVelocity() {
			return maxVelocity;
		};

		public double getMaxAcceleration() {
			return maxAcceleration;
		};

		public double getMaxJerk() {
			return maxJerk;
		}
	}

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

		private double m_x, m_y = 0.0;
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

	private class TrajectoryDistanceAndVelocityParameters {

		MaxVelocityAndMaxAccelerationTrajectoryType maxVelocityAndMaxAccelerationTrajectoryType;

		double distance;

		double jerkVelocityCovered;
		double jerkTime;
		double justJerkDistanceCovered;

		double initialAccelerationDistanceCovered;
		double initialFirstJerkVelocityCovered;
		double initialFirstJerkTimeCovered;
		double initialFirstJerkDistanceCovered;
		double initialSecondJerkVelocityCovered;

		double finalAccelerationDistanceCovered;
		double finalSecondJerkVelocityCovered;
		double finalSecondJerkTimeCovered;
		double finalSecondJerkDistanceCovered;

	}

	/**
	 * Takes a point and tries to add it to the setpoint vector inside of Key
	 * 
	 * @throws InvalidDimentionException
	 */
	public void addPointToPath(Path Key, Point point) throws InvalidDimentionException {
		privateAddPointToPath(Key, point, 0);
	}

	/**
	 * Takes a point and tries to add it to the setpoint vector inside of Key. Takes
	 * the max velocity that the user wants and adds it to a max velocity vector
	 * that will be used later on
	 * 
	 * @throws InvalidDimentionException
	 */
	public void addPointToPath(Path Key, Point point, double maxVelocity) throws InvalidDimentionException {
		privateAddPointToPath(Key, point, maxVelocity);
	}

	/**
	 * Tries to add a point to the setpoint path inside of Key Throws an exception
	 * if the point is not the same dimension as all the other points inside of the
	 * setpoint vector
	 */
	private void privateAddPointToPath(Path Key, Point point, double maxVelocity) throws InvalidDimentionException {
		// If the dimension of the path vector has not been set yet set it to the
		// dimension of the point
		if (Key.setPointDimention == 0) {
			Key.setPointDimention = point.getDim();
			// If the dimension of the point does not match the dimension of the path vector
			// throw an exception
		} else if (point.getDim() != point.getDim()) {
			String errMessage;
			if (point.getDim() == 1) {
				errMessage = "Point: (" + point.m_x + "," + point.m_y + ") is an invalid dimention.";
			} else {
				errMessage = "Point: (" + point.m_x + ") is an invalid dimention.";
			}
			InvalidDimentionException invalidDimentionException = new InvalidDimentionException(errMessage);
			throw invalidDimentionException;
		}

		if (point.getDim() == 2) {
			splineFitNewPoints(Key);
		}
		point.maxVelocity = maxVelocity;
		Key.setpointVector.add(point);
	}

	/**
	 * This method takes a two dimensional setpoint vector and uses some sort of
	 * spline fitting to create a path from one setpoint to another
	 */
	private void splineFitNewPoints(Path Key) {

	}

	/**
	 * This method takes the setpoint vector and turns it into a trajectory vector
	 * by time parameterizing each setpoint
	 */
	public void createTrajectory(Path Key, double maxVelocity, double maxAcceleration, double maxJerk) {
		createTrajectory(Key, maxVelocity, maxAcceleration, maxJerk, false);
	}

	public void createTrajectory(Path Key, double maxVelocity, double maxAcceleration, double maxJerk,
			boolean debugMode) {
		Key.maxVelocity = maxVelocity;
		Key.maxAcceleration = maxAcceleration;
		Key.maxJerk = maxJerk;

		Vector<Point> setpointVector = Key.setpointVector;

		// Final velocity and Initial Velocity are commonly referred to as vi and vf
		// respectively
		// Likewise Initial acceleration and Final Acceleration are referred to as ai
		// and af respectively
		getVf_Vi_Ai_Af(setpointVector, Key);

		// For every point in the setpoint vector

		for (int i1 = 0; i1 < setpointVector.size(); i1++) {
			Point setpoint = setpointVector.get(i1);
			Point previousSetpoint = new Point(0, 0);
			try {
				previousSetpoint = setpointVector.get(i1 - 1);

			} catch (ArrayIndexOutOfBoundsException a) {

			}
			setpoint.maxAcceleration = Key.maxAcceleration;

			TrajectoryDistanceAndVelocityParameters trajectoryDistanceAndVelocityParameters = setTrajectoryDistanceAndVelocityParameters(
					Key, setpoint, previousSetpoint, debugMode);

			switch (trajectoryDistanceAndVelocityParameters.maxVelocityAndMaxAccelerationTrajectoryType) {
			case willCruiseAtMaxVelocityAndMaxAcceleration:
				setTrajectoryTimesWhenMaxVelocityAndMaxAccelerationAreReached(Key, setpoint,
						trajectoryDistanceAndVelocityParameters);
				break;
			case willCruiseAtMaxAccelerationNotMaxVelocity:
				setTrajectoryTimesWhenMaxAccelerationIsReached(Key, setpoint, trajectoryDistanceAndVelocityParameters);
				break;
			case willNotCruiseAtMaxVelocityOrMaxAcceleration:
				setTrajectoryTimesWhenNietherMaxAccelerationOrMaxVelocityAreReached(Key, setpoint,
						trajectoryDistanceAndVelocityParameters);

			}
			// Needs to do this so that the last time through the code the max velocity is
			// not left at some obscure value

			Key.maxVelocity = maxVelocity;
		}

	}

	private void setTrajectoryTimesWhenMaxVelocityAndMaxAccelerationAreReached(Path Key, Point setpoint,
			TrajectoryDistanceAndVelocityParameters trajectoryDistanceAndVelocityParameters) {
		double initialVelocityCoveredAtMaxAcceleration = setpoint.maxVelocity
				- trajectoryDistanceAndVelocityParameters.jerkVelocityCovered
				- trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered;

		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered;

		setpoint.firstEndAccelerationCruisingDeltaTime = initialVelocityCoveredAtMaxAcceleration / Key.maxAcceleration
				+ setpoint.firstStartAccelerationCruisingDeltaTime;

		setpoint.startVelocityCruisingDeltaTime = setpoint.firstEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;

		double distanceCruisingAtMaxVelocity = trajectoryDistanceAndVelocityParameters.distance
				- trajectoryDistanceAndVelocityParameters.initialAccelerationDistanceCovered
				- trajectoryDistanceAndVelocityParameters.finalAccelerationDistanceCovered;
		double timeCruising = distanceCruisingAtMaxVelocity / Key.maxVelocity;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime + timeCruising;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;
		double finalVelocityCoveredAtMaxAcceleration = setpoint.maxVelocity
				- trajectoryDistanceAndVelocityParameters.jerkVelocityCovered
				- trajectoryDistanceAndVelocityParameters.finalSecondJerkVelocityCovered;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime
				+ finalVelocityCoveredAtMaxAcceleration / Key.maxAcceleration;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered;
	}

	private void setTrajectoryTimesWhenMaxAccelerationIsReached(Path Key, Point setpoint,
			TrajectoryDistanceAndVelocityParameters trajectoryDistanceAndVelocityParameters) {
		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered;
		/*
		 * Trying to find distance covered at each max acceleration cruising
		 * 
		 * The finalAccelerationCruisingDistance can be represented in terms of
		 * initialAccelerationCruisingTime as such
		 * 
		 * vi = vf + fjvc (jerkVelocityCovered)
		 * 
		 * vf = vi + iat * A + jvc This is correct because the final velocity of both
		 * maxAcceleration Cruising times are the same so they can be used
		 * interchangeably
		 * 
		 * Acceleration = A
		 * 
		 * finalAccelerationCruisingDistance ((vi + iat * A + jvc)^2 - (vf +
		 * jvc)^2)/(2*A)
		 * 
		 * Solve for iat Distance = ijdc + (vi + ijvc)*iat + 0.5*A*iat^2 + 2*((vi + ijvc
		 * + iat*A)*jT + 1/2*A*jT^2 + 1/6J*jT^3) + ((vi + iat * A + ijvc)^2 - (vf +
		 * fjvc)^2)/(2*A) + fjdc
		 * 
		 * Distance = ijdc + fjdc + (vi + ijvc)*iat+ 0.5*A*iat^2 + 2*jT(vi+ijvc+iat*A) +
		 * A*jT^2 + 1/3J*jT^3 + (((vi + ijvc) + iat*A)^2 - (vf+fjvc)^2)/(2*A)
		 * 
		 * Distance = ijdc + fjdc + (vi + ijvc)*iat + 0.5*A*iat^2 + 2*jT*(vi + ijvc) +
		 * 2*jT*iat*A + A*jT^2 + 1/3*J*jT^3 + (((vi + ijvc) + iat*A)^2 -
		 * (vf+fjvc)^2)/(2*A)
		 * 
		 * (((vi + ijvc) + iat*A)^2 - (vf+fjvc)^2)/(2*A) = ((vi + ijvc)^2 + 2*((vi +
		 * ijvc)* iat*A) + (iat*A)^2 - (vf + fjvc)^2)/(2*A)
		 * 
		 * ((vi + ijvc)^2 + 2*(vi+ijvc)*iat*A + iat^2*A^2 - (vf + fjvc)^2)/(2*A)
		 * 
		 * ((vi + ijvc)^2)/(2*A) + (2*(vi+ijvc)*iat*A)/(2*A) + (iat^2*A^2)/(2*A) - ((vf
		 * + fjvc)^2)/(2*A) ((vi + ijvc)^2)/(2*A) + (vi+ijvc)*iat + (iat^2*A)/2 - ((vf +
		 * fjvc)^2)/(2*A)
		 * 
		 * Distance = ijdc + fjdc + (vi + ijvc)*iat + 0.5*A*iat^2 + 2*jT*(vi + ijvc) +
		 * 2*jT*iat*A + A*jT^2 + 1/3*J*jT^3 + ((vi + ijvc)^2)/(2*A) + (vi+ijvc)*iat +
		 * (iat^2*A)/2 - ((vf + fjvc)^2)/(2*A)
		 * 
		 * Distance - ijdc - fjdc - 2*jT*(vi + ijvc) - A*jT^2- 1/3*J*jT^3 - ((vi +
		 * ijvc)^2)/(2*A) + ((vf + fjvc)^2)/(2*A) = (vi + ijvc)*iat + 0.5*A*iat^2 +
		 * 2*jT*iat*A + (vi+ijvc)*iat + (iat^2*A)/2
		 * 
		 * newDistance = (vi + ijvc)*iat + 0.5*A*iat^2 + 2*jT*iat*A + (vi+ijvc)*iat +
		 * (iat^2*A)/2
		 * 
		 * newDistance = (0.5*A + A/2)iat^2 + (2*vi + 2*ijvc + 2*jT*A )*iat
		 * 
		 * 0 = (0.5*A + A/2)iat^2 + (2*vi + 2*ijvc + 2*jT*A )*iat- newDistance
		 * 
		 * A term = (0.5*A + A/2) B term = (2*vi + 2*ijvc + 2*jT*A ) C term =
		 * newDistance = Distance - ijdc - fjdc - 2*jT*(vi + ijvc) - A*jT^2- 1/3*J*jT^3
		 * - ((vi + ijvc)^2)/(2*A) + ((vf + fjvc)^2)/(2*A)
		 * 
		 * 
		 */

		double aTerm = setpoint.maxAcceleration;
		double bTerm = 2 * (setpoint.vi + trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered
				+ trajectoryDistanceAndVelocityParameters.jerkTime * setpoint.maxAcceleration);
		double cTerm = -1 * (trajectoryDistanceAndVelocityParameters.distance
				- trajectoryDistanceAndVelocityParameters.initialFirstJerkDistanceCovered
				- trajectoryDistanceAndVelocityParameters.finalSecondJerkDistanceCovered
				- 2 * trajectoryDistanceAndVelocityParameters.jerkTime
						* (setpoint.vi + trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered)
				- setpoint.maxAcceleration * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2)
				+ (1.0 / 3.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3)
				- Math.pow((setpoint.vi + trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered), 2)
						/ (2 * setpoint.maxAcceleration)
				+ Math.pow((setpoint.vf + trajectoryDistanceAndVelocityParameters.finalSecondJerkVelocityCovered), 2)
						/ (2 * setpoint.maxAcceleration));

		double initialAccelerationTime = (-bTerm + Math.sqrt(Math.pow(bTerm, 2) - (4 * aTerm * cTerm))) / (2 * aTerm);
		double accelerationVelocityCovered = Key.maxAcceleration * initialAccelerationTime;
		double finalAccelerationTime = ((setpoint.vi
				+ trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered + accelerationVelocityCovered)
				- (setpoint.vf + trajectoryDistanceAndVelocityParameters.finalSecondJerkVelocityCovered))
				/ setpoint.maxAcceleration;
		setpoint.firstEndAccelerationCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime
				+ initialAccelerationTime;
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime;

		setpoint.maxVelocity = setpoint.vi + trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered
				+ trajectoryDistanceAndVelocityParameters.initialSecondJerkVelocityCovered
				+ accelerationVelocityCovered;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime
				+ finalAccelerationTime;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered;

		double initialMaxAccelerationDistanceCovered = ((setpoint.vi
				+ trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered) * initialAccelerationTime
				+ 0.5 * setpoint.maxAcceleration * Math.pow(initialAccelerationTime, 2));
		double endMaxAccelerationVelocity = (setpoint.vi
				+ trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered
				+ setpoint.maxAcceleration * initialAccelerationTime);
		double initialSecondJerkDistanceCoveredAccountingForAcceleration = endMaxAccelerationVelocity
				* trajectoryDistanceAndVelocityParameters.jerkTime
				+ 0.5 * setpoint.maxAcceleration * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2)
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);
		double finalFirstJerkDistanceCoveredAccountingForAcceleration = setpoint.maxVelocity
				* trajectoryDistanceAndVelocityParameters.jerkTime
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);
	}

	private void setTrajectoryTimesWhenNietherMaxAccelerationOrMaxVelocityAreReached(Path Key, Point setpoint,
			TrajectoryDistanceAndVelocityParameters trajectoryDistanceAndVelocityParameters) {
		double timeTakenToAccelerateUpToAi = setpoint.ai / Key.maxJerk;
		double timeTakenToAccelerateUpToAf = setpoint.af / Key.maxJerk;

		double accelertationUpToAiDistanceCovered = (1.0 / 6.0) * Key.maxJerk
				* Math.pow(timeTakenToAccelerateUpToAi, 3);
		double accelerationUpToAfDistanceCovered = (1.0 / 6.0) * Key.maxJerk * Math.pow(timeTakenToAccelerateUpToAf, 3);

		double maxVelocityDistance = (Math.abs(trajectoryDistanceAndVelocityParameters.distance)
				+ accelertationUpToAiDistanceCovered + accelerationUpToAfDistanceCovered) / 2;
		trajectoryDistanceAndVelocityParameters.jerkTime = Math.cbrt(maxVelocityDistance / Key.maxJerk);
		/*
		 * Not hitting Max Acceleration: Time taken to accelerate up to ai: ai/J
		 * 
		 * Time taken to accelerate up to af: af/J
		 * 
		 * Trying to find jt:
		 * 
		 * Distance = vi*(jt - ai/J)+ 1/2*ai*(jt - ai/J)^2 + 1/6*J*(jt - ai/J)^3 +
		 * 2*((vi + ai*(jt-ai/J) + 1/2*J*(jt-ai/J)^2)*jt + 1/2*(ai + J*(jt-ai/J)) -
		 * 1/6*J*jt^3) + vf*(jt - af/J) + 1/2*af*(jt - af/J) + 1/6*J*(jt - af/J)
		 * 
		 * (jt - ai/J)^3
		 * 
		 * (jt^2 - 2*jt*ai/J + (ai/J)^2)(jt - ai/J)
		 * 
		 * jt^3 - ai/J*jt^2 - 2*jt^2*ai/J + 2*jt*(ai/J)^2 + (ai/J)^2*jt - (ai/J)^3
		 * 
		 * jt^3 - (ai/J)^3 - 3*jt^2*ai/J + 3*jt*(ai/J)^2
		 * 
		 * Distance = vi*jt - vi*ai/J + 1/2*ai*(jt^2 -2*jt*ai/J + (ai/J)^2) +
		 * 1/6*J*(jt^3 - (ai/J)^3 - 3*jt^2*ai/J + 3*jt*(ai/J)^2) + 2*(vi*jt +
		 * jt*ai*(jt-ai/J) + jt*1/2*J*(jt^2 -2*jt*ai/J + (ai/J)^2)) + (ai + J*(jt-ai/J))
		 * -1/3*J*jt^3 + jt*vf - vf*af/J + 1/2*af*jt - 1/2*af*af/J + 1/6*J*jt - 1/6*J
		 * *af/J
		 * 
		 * Distance = vi*jt - vi*ai/J + 1/2*ai*jt^2 - jt*ai/J*ai + 1/2*ai*(ai/J)^2 +
		 * 1/6*J*jt^3 - 1/6*J*(ai/J)^3 - 1/2*J*jt^2*ai/J + 1/2*J*jt*(ai/J)^2 + 2*vi*jt +
		 * 2*jt*ai*(jt-ai/J) + jt*J*(jt^2 -2*jt*ai/J + (ai/J)^2) + ai + J*jt - J*ai/J
		 * -1/3*J*jt^3 + jt*vf - vf*af/J + 1/2*af*jt - 1/2*af*af/J + 1/6*J*jt - 1/6*J
		 * *af
		 * 
		 * Distance = vi*jt - vi*ai/J + 1/2*ai*jt^2 - jt*ai/J*ai + 1/2*ai*(ai/J)^2 +
		 * 1/6*J*jt^3 - 1/6*J*(ai/J)^3 - 1/2*J*jt^2*ai/J + 1/2*J*jt*(ai/J)^2 + 2*vi*jt +
		 * 2*jt*ai*(jt-ai/J) + jt^3*J - 2*jt^2*J*ai/J + jt*J*(ai/J)^2 + jt*J*(ai/J)^2 +
		 * ai + J*jt - J*ai/J -1/3*J*jt^3 + jt*vf - vf*af/J + 1/2*af*jt - 1/2*af*af/J +
		 * 1/6*J*jt - 1/6*J *af
		 * 
		 * Distance + vi*ai/J - 1/2*ai*(ai/J)^2 + 1/6*J*(ai/J)^3 - ai + J*ai/J + vj*af/J
		 * + 1/2*af*af/J + 1/6*J *af = vi*jt + 1/2*ai*jt^2 - jt*ai/J*ai + 1/6*J*jt^3 -
		 * 1/2*J*jt^2*ai/J + 1/2*J*jt*(ai/J)^2 + 2*vi*jt + 2*jt*ai*(jt-ai/J) + jt^3*J -
		 * 2*jt^2*J*ai/J + jt*J*(ai/J)^2 + jt*J*(ai/J)^2 + J*jt -1/3*J*jt^3 + jt*vf +
		 * 1/2*af*jt + 1/6*J*jt
		 * 
		 * newDistance = vi*jt + 1/2*ai*jt^2 - jt*ai/J*ai + 1/6*J*jt^3 - 1/2*J*jt^2*ai/J
		 * + 1/2*J*jt*(ai/J)^2 + 2*vi*jt + 2*jt*ai*(jt-ai/J) + jt^3*J - 2*jt^2*J*ai/J +
		 * jt*J*(ai/J)^2 + jt*J*(ai/J)^2 + J*jt -1/3*J*jt^3 + jt*vf + 1/2*af*jt +
		 * 1/6*J*jt
		 * 
		 * newDistance = vi*jt + 1/2*ai*jt^2 - jt*ai/J*ai + 1/6*J*jt^3 - 1/2*J*jt^2*ai/J
		 * + 1/2*J*jt*(ai/J)^2 + 2*vi*jt + 2*jt^2*ai - 2*jt*ai*ai/J + jt^3*J -
		 * 2*jt^2*J*ai/J + jt*J*(ai/J)^2 + jt*J*(ai/J)^2 + J*jt -1/3*J*jt^3 + jt*vf +
		 * 1/2*af*jt + 1/6*J*jt
		 * 
		 * newDistance = 1/6*J*jt^3 + jt^3*J -1/3*J*jt^3 + 1/2*ai*jt^2 - 1/2*J*jt^2*ai/J
		 * + 2*jt^2*ai - 2*jt^2*J*ai/J + vi*jt - jt*ai/J*ai + 1/2*J*jt*(ai/J)^2 +
		 * 2*vi*jt - 2*jt*ai*ai/J + jt*J*(ai/J)^2 + jt*J*(ai/J)^2 + J*jt + jt*vf +
		 * 1/2*af*jt + 1/6*J*jt
		 * 
		 * newDistance = (1/6*J + J - 1/3*J)*jt^3 + (1/2*ai - 1/2*J*ai/J + 2*ai -
		 * 2*J*ai/J)*jt^2 + (vi - ai*ai/J + 1/2*J*(ai/J)^2 + 2*vi - 2*jt*ai*ai/J
		 * +J*(ai/J)^2 + J*(ai/J)^2 + J + vf + 1/2*af + 1/6*J)*jt
		 * 
		 * A term: (1/6*J + J - 1/3*J) B term: (1/2*ai - 1/2*J*ai/J + 2*ai - 2*J*ai/J) =
		 * (1/2*ai - 1/2*ai + 2*ai - 2*ai) C term: (vi - ai*ai/J + 1/2*J*(ai/J)^2 + 2*vi
		 * - 2*jt*ai*ai/J +J*(ai/J)^2 + J*(ai/J)^2 + J + vf + 1/2*af + 1/6*J) D term:
		 * -1*(Distance + vi*ai/J - 1/2*ai*(ai/J)^2 + 1/6*J*(ai/J)^3 - ai + J*ai/J +
		 * vf*af/J + 1/2*af*af/J + 1/6*J *af)
		 * 
		 * A term: (J*5/6) B term: 0 C term: (3*vi - ai*ai/J + 1/2*J*(ai/J)^2 -
		 * 2*jt*ai*ai/J +J*(ai/J)^2 + J*(ai/J)^2 + J + vf + 1/2*af + 1/6*J) D term:
		 * -1*(Distance + vi*ai/J - 1/2*ai*(ai/J)^2 + 1/6*J*(ai/J)^3 - ai + J*ai/J +
		 * vf*af/J + 1/2*af*af/J + 1/6*J *af)
		 * 
		 * double aTerm = (5.0 / 6.0) * Key.maxJerk; double bTerm = 0.0; double cTerm =
		 * 3 * setpoint.vi - Math.pow(setpoint.ai, 2) / Key.maxJerk + 0.5 * Key.maxJerk
		 * * Math.pow((setpoint.ai / Key.maxJerk), 2) - 2 * Math.pow(setpoint.ai, 2) /
		 * Key.maxJerk + 2 * Key.maxJerk * Math.pow((setpoint.ai / Key.maxJerk), 2) +
		 * Key.maxJerk + setpoint.vf + 0.5 * setpoint.af + (1.0 / 6.0) * Key.maxJerk;
		 * double dTerm = -1 * (distance + setpoint.vi * setpoint.ai / Key.maxJerk - 0.5
		 * * Math.pow(setpoint.ai, 3) / Math.pow(Key.maxJerk, 2) + (1.0 / 6.0) *
		 * Key.maxJerk * Math.pow((setpoint.ai / Key.maxJerk), 3) + setpoint.vf *
		 * setpoint.af / Key.maxJerk + 0.5 * Math.pow(setpoint.af, 2) / Key.maxJerk +
		 * (1.0 / 6.0) * Key.maxJerk * setpoint.af);
		 */

		// What if instead I used the same formula from before and added in the extra
		// distance to make it work...

		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceAndVelocityParameters.jerkTime
				- setpoint.ai / Key.maxJerk;
		setpoint.firstEndAccelerationCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime;
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceAndVelocityParameters.jerkTime - setpoint.af / Key.maxJerk;
		double velocityCoveredByNewJerkTime = 0.5 * Key.maxJerk
				* Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2);
		setpoint.maxVelocity = 2 * velocityCoveredByNewJerkTime;
		setpoint.maxAcceleration = Key.maxJerk * trajectoryDistanceAndVelocityParameters.jerkTime;

		double distanceCoveredDuringFirstJerkTime = (1.0 / 6.0) * Key.maxJerk
				* Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);
		double jerkVi = 0.5 * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2);
		double jerkAcceleration = Key.maxJerk * trajectoryDistanceAndVelocityParameters.jerkTime;
		double distanceCoveredDuringSecondJerkTime = jerkVi * trajectoryDistanceAndVelocityParameters.jerkTime
				+ 0.5 * jerkAcceleration * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2)
				+ (1.0 / 6.0) * (-1 * Key.maxJerk) * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);
	}

	private TrajectoryDistanceAndVelocityParameters setTrajectoryDistanceAndVelocityParameters(Path Key, Point setpoint,
			Point previousSetpoint, boolean debugMode) {
		TrajectoryDistanceAndVelocityParameters trajectoryDistanceAndVelocityParameters = new TrajectoryDistanceAndVelocityParameters();

		trajectoryDistanceAndVelocityParameters.distance = Math.abs(setpoint.m_x - previousSetpoint.m_x);
		trajectoryDistanceAndVelocityParameters.jerkTime = Key.maxAcceleration / Key.maxJerk;
		trajectoryDistanceAndVelocityParameters.jerkVelocityCovered = 0.5 * Key.maxJerk
				* Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 2);

		double deltaInitialAccelerationFromMaxAcceleration = setpoint.maxAcceleration - setpoint.ai;
		trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered = deltaInitialAccelerationFromMaxAcceleration
				/ Key.maxJerk;
		trajectoryDistanceAndVelocityParameters.initialFirstJerkDistanceCovered = Math
				.abs(setpoint.vi * trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered
						+ 0.5 * setpoint.ai
								* Math.pow(trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered, 2)
						+ (1.0 / 6.0) * Key.maxJerk
								* Math.pow(trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered, 3));
		trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered = (Math.pow(setpoint.maxAcceleration, 2)
				- Math.pow(setpoint.ai, 2)) / (2 * Key.maxJerk);

		double initialSecondJerkDistanceCovered = Math
				.abs(Key.maxVelocity * trajectoryDistanceAndVelocityParameters.jerkTime
						- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3));
		trajectoryDistanceAndVelocityParameters.initialSecondJerkVelocityCovered = setpoint.maxAcceleration / 2.0
				* trajectoryDistanceAndVelocityParameters.jerkTime;

		double initialStartMaxAccelerationVelocity = setpoint.vi
				+ trajectoryDistanceAndVelocityParameters.initialFirstJerkVelocityCovered;
		double initialEndMaxAccelerationVelocity = setpoint.maxVelocity
				- trajectoryDistanceAndVelocityParameters.initialSecondJerkVelocityCovered;
		double initialDistanceCoveredWhileAtMaxAcceleration = (Math.pow(initialEndMaxAccelerationVelocity, 2)
				- Math.pow(initialStartMaxAccelerationVelocity, 2)) / (2 * setpoint.maxAcceleration);

		trajectoryDistanceAndVelocityParameters.initialAccelerationDistanceCovered = trajectoryDistanceAndVelocityParameters.initialFirstJerkDistanceCovered
				+ initialSecondJerkDistanceCovered + initialDistanceCoveredWhileAtMaxAcceleration;

		double deltaFinalAccelerationFromMaxAcceleration = setpoint.maxAcceleration - setpoint.af;
		trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered = deltaFinalAccelerationFromMaxAcceleration
				/ Key.maxJerk;
		trajectoryDistanceAndVelocityParameters.finalSecondJerkDistanceCovered = Math
				.abs(setpoint.vf * trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered
						+ 0.5 * setpoint.af
								* Math.pow(trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered, 2)
						+ (1.0 / 6.0) * Key.maxJerk
								* Math.pow(trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered, 3));
		trajectoryDistanceAndVelocityParameters.finalSecondJerkVelocityCovered = (Math.pow(setpoint.maxAcceleration, 2)
				- Math.pow(setpoint.af, 2)) / (2 * Key.maxJerk);

		double finalFirstJerkDistanceCovered = Math
				.abs(Key.maxVelocity * trajectoryDistanceAndVelocityParameters.jerkTime
						- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3));
		double finalFirstJerkVelocityCovered = setpoint.maxAcceleration / 2.0
				* trajectoryDistanceAndVelocityParameters.jerkTime;

		double finalStartMaxAccelerationVelocity = Key.maxVelocity - finalFirstJerkVelocityCovered;
		double finalEndMaxAccelerationVelocity = setpoint.vf
				+ trajectoryDistanceAndVelocityParameters.finalSecondJerkVelocityCovered;
		double finalDistanceCoveredWhileAtMaxAcceleration = (Math.pow(finalEndMaxAccelerationVelocity, 2)
				- Math.pow(finalStartMaxAccelerationVelocity, 2)) / (-2 * setpoint.maxAcceleration);

		trajectoryDistanceAndVelocityParameters.finalAccelerationDistanceCovered = trajectoryDistanceAndVelocityParameters.finalSecondJerkDistanceCovered
				+ finalFirstJerkDistanceCovered + finalDistanceCoveredWhileAtMaxAcceleration;

		if (setpoint.vi == 0.0 && setpoint.ai != 0) {
			double velocityCoveredDuringBothJerkTimes = Math.pow(setpoint.ai, 2) / (2 * Key.maxJerk);
			double timeCoveredBetweenAiand0 = setpoint.ai / Key.maxJerk;
			double finalMaxAcceleration = Math.sqrt(2 * Key.maxJerk * (velocityCoveredDuringBothJerkTimes / 2));
			double finalJerkTime = finalMaxAcceleration / Key.maxJerk;
			double finalFirstJerkInitialVelocity = setpoint.ai / 2 * timeCoveredBetweenAiand0;
			double finalSecondJerkInitialVelocity = finalFirstJerkInitialVelocity
					+ finalMaxAcceleration / 2 * finalJerkTime;
			trajectoryDistanceAndVelocityParameters.justJerkDistanceCovered = 0.5 * setpoint.ai
					* Math.pow(timeCoveredBetweenAiand0, 2)
					- (1.0 / 6.0) * Key.maxJerk * Math.pow(timeCoveredBetweenAiand0, 3)
					+ finalFirstJerkInitialVelocity * finalJerkTime
					- (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3)
					+ finalSecondJerkInitialVelocity * finalJerkTime
					- 0.5 * finalMaxAcceleration * Math.pow(finalJerkTime, 2)
					+ (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3);

		} else {
			double initialmaxAccelerationInitialVelocity = setpoint.vi
					+ setpoint.ai * trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered
					+ 0.5 * Key.maxJerk
							* Math.pow(trajectoryDistanceAndVelocityParameters.initialFirstJerkTimeCovered, 2);
			double initialSecondJerkDistanceCoveredNoMaxAccelerationCruising = initialmaxAccelerationInitialVelocity
					* trajectoryDistanceAndVelocityParameters.jerkTime
					+ 0.5 * setpoint.maxAcceleration * trajectoryDistanceAndVelocityParameters.jerkTime
					- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);

			double finalMaxAccelerationFinalVelocity = setpoint.vf
					+ setpoint.af * trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered
					+ 0.5 * Key.maxJerk
							* Math.pow(trajectoryDistanceAndVelocityParameters.finalSecondJerkTimeCovered, 2);
			double finalFirstJerkDistanceCoveredNoMaxAccelerationCruising = finalMaxAccelerationFinalVelocity
					* trajectoryDistanceAndVelocityParameters.jerkTime
					+ 0.5 * setpoint.maxAcceleration * trajectoryDistanceAndVelocityParameters.jerkTime
					- (1.0 / 6.0) * Key.maxJerk * Math.pow(trajectoryDistanceAndVelocityParameters.jerkTime, 3);

			trajectoryDistanceAndVelocityParameters.justJerkDistanceCovered = trajectoryDistanceAndVelocityParameters.initialFirstJerkDistanceCovered
					+ initialSecondJerkDistanceCoveredNoMaxAccelerationCruising
					+ finalFirstJerkDistanceCoveredNoMaxAccelerationCruising
					+ trajectoryDistanceAndVelocityParameters.finalSecondJerkDistanceCovered;
		}

		if (trajectoryDistanceAndVelocityParameters.initialAccelerationDistanceCovered
				+ trajectoryDistanceAndVelocityParameters.finalAccelerationDistanceCovered <= trajectoryDistanceAndVelocityParameters.distance) {
			trajectoryDistanceAndVelocityParameters.maxVelocityAndMaxAccelerationTrajectoryType = MaxVelocityAndMaxAccelerationTrajectoryType.willCruiseAtMaxVelocityAndMaxAcceleration;
		} else if (trajectoryDistanceAndVelocityParameters.justJerkDistanceCovered <= trajectoryDistanceAndVelocityParameters.distance) {
			trajectoryDistanceAndVelocityParameters.maxVelocityAndMaxAccelerationTrajectoryType = MaxVelocityAndMaxAccelerationTrajectoryType.willCruiseAtMaxAccelerationNotMaxVelocity;
		} else {
			trajectoryDistanceAndVelocityParameters.maxVelocityAndMaxAccelerationTrajectoryType = MaxVelocityAndMaxAccelerationTrajectoryType.willNotCruiseAtMaxVelocityOrMaxAcceleration;
		}

		return trajectoryDistanceAndVelocityParameters;
	}

	private void getVf_Vi_Ai_Af(Vector<Point> setpointVector, Path Key) {

		// For every point inside of the setpoint vector

		for (int i1 = 0; i1 < setpointVector.size(); i1++) {
			boolean traveledInAPositiveDirection;
			boolean willTravelInAPositiveDirection;

			Point setpoint = setpointVector.get(i1);

			if (setpoint.maxVelocity >= Key.maxVelocity || setpoint.maxVelocity <= 0.0) {
				setpoint.maxVelocity = Key.maxVelocity;
			}

			/*
			 * If this is the first setpoint being looked at set the vi to 0.0
			 * 
			 * Else set vi to the vf of the last setpoint looked at
			 */
			if (i1 == 0) {
				setpoint.vi = 0.0;
				setpoint.ai = 0.0;
			} else {
				setpoint.vi = setpointVector.get(i1 - 1).vf;
				setpoint.ai = setpointVector.get(i1 - 1).af;
			}

			/*
			 * If this is the first setpoint being looked at set
			 * traveledInAPositiveDirection according to whether or not setpoint is positive
			 * or negative
			 * 
			 * Else if the setpoint is greater than the last setpoint set
			 * traveledInAPositiveDirection to true
			 * 
			 * Else set traveledInAPositiveDirection to false
			 */
			if (i1 == 0) {
				if (setpoint.m_x > 0) {
					traveledInAPositiveDirection = true;
				} else {
					traveledInAPositiveDirection = false;
				}
			} else if (setpoint.m_x > setpointVector.get(i1 - 1).m_x) {
				traveledInAPositiveDirection = true;
			} else {
				traveledInAPositiveDirection = false;
			}

			/*
			 * If this is the very last setpoint willTravelInAPositiveDirection does not
			 * matter so set it to false
			 * 
			 * Else if this setpoint is greater than the next setpoint set
			 * willTravelInAPositiveDirection to false
			 * 
			 * Else set willTravelInAPositiveDirection to true
			 */
			if (i1 == (setpointVector.size() - 1)) {
				willTravelInAPositiveDirection = false;
			} else if (setpoint.m_x > setpointVector.get(i1 + 1).m_x) {
				willTravelInAPositiveDirection = false;
			} else {
				willTravelInAPositiveDirection = true;
			}

			/*
			 * If this is the final setpoint in the vector you want the final velocity to be
			 * 0 so set vf to 0
			 * 
			 * Else if you have been traveling in a positive direction and you will continue
			 * traveling in a positive direction or you have been traveling in a negative
			 * direction and you will continue traveling in a negative direction calculate
			 * whether or not you have to slow down when going to the current setpoint in
			 * order to prepare to reach the other setpoints
			 * 
			 * Else you will be reversing direction so your vf will be 0.0
			 */
			Point nextSetpoint = new Point(0, 0);
			try {
				nextSetpoint = setpointVector.get(i1 + 1);

			} catch (ArrayIndexOutOfBoundsException a) {

			}
			Point previousSetpoint = new Point(0, 0);
			try {
				previousSetpoint = setpointVector.get(i1 - 1);

			} catch (ArrayIndexOutOfBoundsException a) {

			}
			if (nextSetpoint.maxVelocity >= Key.maxVelocity || nextSetpoint.maxVelocity <= 0.0) {
				nextSetpoint.maxVelocity = Key.maxVelocity;
			}

			double distanceTraveledWhileAcceleratingToMaxVelocityFromVi = (Math.pow(setpoint.maxVelocity, 2)
					- Math.pow(setpoint.vi, 2)) / (2 * Key.maxAcceleration);
			double deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint = Math
					.abs(setpoint.m_x - previousSetpoint.m_x);
			if (distanceTraveledWhileAcceleratingToMaxVelocityFromVi > deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint) {
				setpoint.vf = Math.sqrt(Math.abs(Math.pow(setpoint.vi, 2)
						+ 2 * Key.maxAcceleration * deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint));

			} else {
				setpoint.vf = setpoint.maxVelocity;
			}
			setpoint.vf = Math.abs(setpoint.vf);

			if (i1 == (setpointVector.size() - 1)) {
				setpoint.vf = 0.0;
				setpoint.af = 0.0;
			} else if ((traveledInAPositiveDirection && willTravelInAPositiveDirection)
					|| (!traveledInAPositiveDirection && !willTravelInAPositiveDirection)) {
				Vector<Point> possibleFinalVelocityDeterminingSetpoints = new Vector<Point>();
				Vector<Integer> possibleFinalVelocityDeterminingSetpointIndexes = new Vector<Integer>();

				getPossibleFinalVelocityDeterminingSetpoint(Key, setpoint, setpointVector, i1,
						traveledInAPositiveDirection, possibleFinalVelocityDeterminingSetpoints,
						possibleFinalVelocityDeterminingSetpointIndexes);
				
				getFinalVelocityOfPoint(Key,setpoint,previousSetpoint,
						possibleFinalVelocityDeterminingSetpointIndexes,
						possibleFinalVelocityDeterminingSetpoints, i1,
						deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint);

			} else {
				setpoint.vf = 0.0;
				getFinalAccelerationOfPoint(Key, setpoint, nextSetpoint);
			}

		}

	}

	private void getPossibleFinalVelocityDeterminingSetpoint(Path Key, Point setpoint, Vector<Point> setpointVector,
			int currentSetpointIndex, boolean traveledInAPositiveDirection,
			Vector<Point> possibleFinalVelocityDeterminingSetpoints,
			Vector<Integer> possibleFinalVelocityDeterminingSetpointIndexes) {
		double distanceCoveredWhileAcceleratingToMaxVelocity = getDistanceTraveledWhileAccelerating(0.0,
				setpoint.maxVelocity, Key.maxAcceleration);
		Point previousSetpoint = setpoint;
		Point nextSetpoint;
		for (int i = currentSetpointIndex; i < setpointVector.size(); i++) {

			nextSetpoint = setpointVector.get(i);
			if ((traveledInAPositiveDirection && previousSetpoint.m_x > nextSetpoint.m_x)
					|| (!traveledInAPositiveDirection && previousSetpoint.m_x < nextSetpoint.m_x)) {
				possibleFinalVelocityDeterminingSetpointIndexes.addElement(i - 1);
				break;
			}

			possibleFinalVelocityDeterminingSetpoints.add(nextSetpoint);
			if (i == currentSetpointIndex) {
				continue;
			}

			if (nextSetpoint.maxVelocity >= Key.maxVelocity || nextSetpoint.maxVelocity <= 0.0) {
				nextSetpoint.maxVelocity = Key.maxVelocity;
			} else {
				possibleFinalVelocityDeterminingSetpointIndexes.addElement(i);

			}

			if (Math.abs(nextSetpoint.m_x) - Math.abs(setpoint.m_x) > distanceCoveredWhileAcceleratingToMaxVelocity) {
				possibleFinalVelocityDeterminingSetpointIndexes.addElement(i);
				break;
			}

			if (i + 1 == setpointVector.size()) {
				possibleFinalVelocityDeterminingSetpointIndexes.addElement(i);
				break;
			}
			previousSetpoint = nextSetpoint;

		}

	}

	private void getFinalVelocityOfPoint(Path Key, Point setpoint, Point previousSetpoint,
			Vector<Integer> possibleFinalVelocityDeterminingSetpointIndexes,
			Vector<Point> possibleFinalVelocityDeterminingSetpoints, int currentSetpointIndex,
			double deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint) {
		for (int i11 = 0; i11 < possibleFinalVelocityDeterminingSetpointIndexes.size(); i11++) {
			Point possibleFinalVelocityDeterminingSetpoint = new Point(0, 0);

			double possibleFinalVelocityDeterminingSetpointMaxVelocity = possibleFinalVelocityDeterminingSetpoints
					.elementAt(possibleFinalVelocityDeterminingSetpointIndexes.elementAt(i11)
							- currentSetpointIndex).maxVelocity;
			try {
				possibleFinalVelocityDeterminingSetpoint = possibleFinalVelocityDeterminingSetpoints.elementAt(
						possibleFinalVelocityDeterminingSetpointIndexes.elementAt(i11) - 1 - currentSetpointIndex);
			} catch (ArrayIndexOutOfBoundsException a) {

			}
			double distanceCoveredWhileAcceleratingFrom0ToVi = Math.pow(setpoint.vi, 2) / (2 * Key.maxAcceleration);
			double distaneCoveredWhileAcceleratingFrom0ToVf = Math
					.pow(possibleFinalVelocityDeterminingSetpointMaxVelocity, 2) / (2 * Key.maxAcceleration);
			double deltaDistanceBetweenSetpoints = Math
					.abs(possibleFinalVelocityDeterminingSetpoint.m_x - setpoint.m_x);
			double combinedDistance = deltaDistanceBetweenSetpoints + distanceCoveredWhileAcceleratingFrom0ToVi
					+ Math.abs(deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint)
					+ distaneCoveredWhileAcceleratingFrom0ToVf;

			double maxVelocityDistance = Math.abs(combinedDistance / 2);
			double theoreticalMaxVelocity = Math.sqrt(2 * Key.maxAcceleration * maxVelocityDistance);
			double highestVelocityHitifOnlyAcceleratingToSetpoint = Math.sqrt(
					Math.pow(setpoint.vi, 2) + 2 * Key.maxAcceleration * Math.abs(setpoint.m_x - previousSetpoint.m_x));
			double highestVelocityHitWhileTravelingToSetpoint;
			double tempVf;
			if (highestVelocityHitifOnlyAcceleratingToSetpoint > theoreticalMaxVelocity) {
				highestVelocityHitWhileTravelingToSetpoint = theoreticalMaxVelocity;
				double distanceTraveledWhileAcceleratingFormViToHighestVelocityHitWhileTravelingToSetpoint = (Math
						.pow(theoreticalMaxVelocity, 2) - Math.pow(setpoint.vi, 2)) / (2 * Key.maxAcceleration);
				double distanceBetweenTheoreticalMaxVelocityAndTempVf = deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint
						- distanceTraveledWhileAcceleratingFormViToHighestVelocityHitWhileTravelingToSetpoint;
				tempVf = Math.sqrt(Math.pow(highestVelocityHitWhileTravelingToSetpoint, 2)
						- 2 * Key.maxAcceleration * distanceBetweenTheoreticalMaxVelocityAndTempVf);
			} else {
				highestVelocityHitWhileTravelingToSetpoint = highestVelocityHitifOnlyAcceleratingToSetpoint;
				tempVf = highestVelocityHitWhileTravelingToSetpoint;
			}

			tempVf = Math.abs(tempVf);

			if (setpoint.vf > tempVf) {
				setpoint.vf = tempVf;
			}
			if (i11 + 1 == possibleFinalVelocityDeterminingSetpointIndexes.size()) {
				possibleFinalVelocityDeterminingSetpoint = possibleFinalVelocityDeterminingSetpoints.elementAt(
						possibleFinalVelocityDeterminingSetpointIndexes.elementAt(i11) - currentSetpointIndex);

				deltaDistanceBetweenSetpoints = Math.abs(possibleFinalVelocityDeterminingSetpoint.m_x - setpoint.m_x);
				combinedDistance = deltaDistanceBetweenSetpoints + distanceCoveredWhileAcceleratingFrom0ToVi
						+ Math.abs(deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint);
				maxVelocityDistance = Math.abs(combinedDistance / 2);
				theoreticalMaxVelocity = Math.sqrt(2 * Key.maxAcceleration * maxVelocityDistance);
				highestVelocityHitifOnlyAcceleratingToSetpoint = Math.sqrt(Math.pow(setpoint.vi, 2)
						+ 2 * Key.maxAcceleration * Math.abs(setpoint.m_x - previousSetpoint.m_x));

				if (highestVelocityHitifOnlyAcceleratingToSetpoint > theoreticalMaxVelocity) {
					highestVelocityHitWhileTravelingToSetpoint = theoreticalMaxVelocity;
					double distanceTraveledWhileAcceleratingFormViToHighestVelocityHitWhileTravelingToSetpoint = (Math
							.pow(theoreticalMaxVelocity, 2) - Math.pow(setpoint.vi, 2)) / (2 * Key.maxAcceleration);
					double distanceBetweenTheoreticalMaxVelocityAndTempVf = deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint
							- distanceTraveledWhileAcceleratingFormViToHighestVelocityHitWhileTravelingToSetpoint;
					tempVf = Math.sqrt(Math.pow(highestVelocityHitWhileTravelingToSetpoint, 2)
							- 2 * Key.maxAcceleration * distanceBetweenTheoreticalMaxVelocityAndTempVf);
				} else {
					highestVelocityHitWhileTravelingToSetpoint = highestVelocityHitifOnlyAcceleratingToSetpoint;
					tempVf = highestVelocityHitWhileTravelingToSetpoint;
				}
				tempVf = Math.abs(tempVf);

				if (setpoint.vf > tempVf) {
					setpoint.vf = tempVf;
				}

			}
		}
	}
	
	private void getFinalAccelerationOfPoint(Path Key, Point setpoint, Point nextSetpoint) {
		setpoint.af = Key.maxAcceleration;
		double distance = Math.abs(nextSetpoint.m_x - setpoint.m_x);
		double velocityCoveredWhileDecceleratingFromMaxAccelertion = Math.pow(Key.maxAcceleration, 2)
				/ (2 * Key.maxJerk);
		double timeCoveredWhileMaxAccelerationIsDecceleratingTo0 = Key.maxAcceleration / Key.maxJerk;
		double finalEachJerkVelocityCovered = velocityCoveredWhileDecceleratingFromMaxAccelertion / 2;
		double finalJerkMaxAcceleration = Math.sqrt(2 * Key.maxJerk * finalEachJerkVelocityCovered);
		double finalJerkTime = finalJerkMaxAcceleration / Key.maxJerk;

		double initialJerkDistanceCovered = 0.5 * Key.maxAcceleration
				* Math.pow(timeCoveredWhileMaxAccelerationIsDecceleratingTo0, 2)
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(timeCoveredWhileMaxAccelerationIsDecceleratingTo0, 3);
		double finalFirstJerkDistanceCovered = velocityCoveredWhileDecceleratingFromMaxAccelertion
				* finalJerkTime - (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3);
		double finalSecondJerkDistanceCovered = (velocityCoveredWhileDecceleratingFromMaxAccelertion
				- finalEachJerkVelocityCovered) * finalJerkTime
				- 0.5 * finalJerkMaxAcceleration * Math.pow(finalJerkTime, 2)
				+ (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3);

		double distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 = initialJerkDistanceCovered
				+ finalFirstJerkDistanceCovered + finalSecondJerkDistanceCovered;
		if (distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 <= distance) {
			setpoint.af = Key.maxAcceleration;
		} else {
			double aTerm = (1.0 / 6.0 * Key.maxJerk);
			double bTerm = 0.0;
			double cTerm = Key.maxAcceleration / (2 * Key.maxJerk);
			double dTerm = -1 * Math.abs(distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 - distance);

			double timeBeforeEndDeltaTimeForAccelerationToStartChanging = solveCubicEquation(aTerm, bTerm,
					cTerm, dTerm);

			double tempAf = Key.maxAcceleration
					- Key.maxJerk / timeBeforeEndDeltaTimeForAccelerationToStartChanging;
			/*
			 * Distance = 1/2*ai*(iT + fT)^2 - 1/6*J*(iT + fT)^3 + (ai*(iT + fT) - 1/2*J*(iT
			 * + fT)^2)*fT + 1/2*(ai - J*(iT + fT))*fT^2 + 1/6*J*fT^3
			 * 
			 * iT = ai/J VelocityCoveredWhileAccelerationIsDecceleratingTo0 = ai/2*ai/J =
			 * ai^2/(2*J) FinalTimeVelocityCovered = ai^2/(4*J) fT = sqrt(ai^2/(4*J)*2 *J) =
			 * sqrt(ai^2/2) = ai/sqrt(2) iT + fT = ai/J + ai/sqrt(2) = ai*(1/J + 1/sqrt(2))
			 * 
			 * Distance = 1/2*ai*(ai*(1/J + 1/sqrt(2)))^2 - 1/6*J*(ai*(1/J + 1/sqrt(2)))^3 +
			 * (ai*(ai*(1/J + 1/sqrt(2))) - 1/2*J*(ai*(1/J + 1/sqrt(2)))^2)*ai/sqrt(2) +
			 * 1/2*(ai - J*(ai*(1/J + 1/sqrt(2))))*(ai/ sqrt(2))^2 + 1/6*J*(ai 1/sqrt(2))^3
			 * 
			 * Distance = 1/2*ai*ai^2*(1/J + 1/sqrt(2))^2 - 1/6*J*ai^3*(1/J + 1/sqrt(2))^3 +
			 * (ai^2*(1/J + 1/sqrt(2)) - 1/2*J*ai^2*(1/J + 1/sqrt(2))^2)*ai/sqrt(2) +
			 * 1/2*(ai - J*(ai*(1/J + 1/sqrt(2))))*(ai/sqrt(2))^2 + 1/6*J*ai^3 * 1/sqrt(2)^3
			 * 
			 * 
			 * 1/2*(ai - J*ai*(1/J + 1/sqrt(2)))*ai^2 /sqrt(2)^2 (ai - J*ai*(1/J +
			 * 1/sqrt(2)))*ai^2 ai^3 - J*ai^3*(1/J + 1/sqrt(2))
			 * 
			 * Distance = 1/2*ai*ai^2*(1/J + 1/sqrt(2))^2 - 1/6*J*ai^3*(1/J + 1/sqrt(2))^3 +
			 * (ai^2*(1/J + 1/sqrt(2)) - 1/2*J*ai^2*(1/J + 1/sqrt(2))^2)*ai/sqrt(2) +ai^3 -
			 * J*ai^3*(1/J + 1/sqrt(2)) + 1/6*J*ai^3 * 1/sqrt(2)^3
			 * 
			 * Distance = 1/2*ai^3*(1/J +1/ sqrt(2))^2 - 1/6*J*ai^3*(1/J + 1/sqrt(2))^3 +
			 * ai^3/sqrt(2)*(1/J + 1/sqrt(2)) - 1/2*J*ai^3/sqrt(2)*(1/J + 1/sqrt(2))^2 +
			 * ai^3 - J*ai^3*(1/J + 1/sqrt(2)) + 1/6*J*ai^3 * 1/sqrt(2)^3
			 * 
			 * Distance = 1/2*(1/J + 1/sqrt(2))^2*ai^3 - 1/6*J*(1/J + 1/sqrt(2))^3*ai^3 +
			 * 1/sqrt(2)*(1/J + 1/sqrt(2))*ai^3 - 1/2*J*1/sqrt(2)*(1/J + 1/sqrt(2))^2*ai^3 +
			 * ai^3 - J*(1/J +1/ sqrt(2))*ai^3 + 1/6*J*1/sqrt(2)^3*ai^3
			 * 
			 * Distance = (1/2*(1/J + 1/sqrt(2))^2 -1/6*J*(1/J + 1/sqrt(2))^3 +
			 * 1/sqrt(2)*(1/J + 1/sqrt(2)) - 1/2*J*1/sqrt(2)*(1/J + 1/sqrt(2))^2 + 1 -
			 * J*(1/J + 1/sqrt(2)) + 1/6*J*1/sqrt(2)^3)*ai^3
			 * 
			 * Distance/(1/2*(1/J + 1/sqrt(2))^2 -1/6*J*(1/J + 1/sqrt(2))^3 + 1/sqrt(2)*(1/J
			 * + 1/sqrt(2)) - 1/2*J*1/sqrt(2)*(1/J + 1/sqrt(2))^2 + 1 - J*(1/J + 1/sqrt(2))
			 * + 1/6*J*1/sqrt(2)^3) = ai^3
			 * 
			 * cbrt(Distance/(1/2*(1/J + 1/sqrt(2))^2 -1/6*J*(1/J + 1/sqrt(2))^3 +
			 * 1/sqrt(2)*(1/J + 1/sqrt(2)) - 1/2*J*1/sqrt(2)*(1/J + 1/sqrt(2))^2 + 1 -
			 * J*(1/J + 1/sqrt(2)) + 1/6*J*1/sqrt(2)^3)) = ai
			 */

			tempAf = Math.cbrt(distance / (0.5 * Math.pow((1.0 / Key.maxJerk) + (1.0 / Math.sqrt(2)), 2)
					- (1.0 / 6) * Key.maxJerk * Math.pow(((1.0 / Key.maxJerk) + (1.0 / Math.sqrt(2))), 3)
					+ (1.0 / Math.sqrt(2)) * ((1.0 / Key.maxJerk) + (1.0 / Math.sqrt(2)))
					- (1.0 / 2) * Key.maxJerk * (1.0 / Math.sqrt(2))
							* Math.pow(((1.0 / Key.maxJerk) + (1.0 / Math.sqrt(2))), 2)
					+ 1.0 - Key.maxJerk * ((1.0 / Key.maxJerk) + (1.0 / Math.sqrt(2)))
					+ (1.0 / 6) * Key.maxJerk * Math.pow((1.0 / Math.sqrt(2)), 3)));

			if (tempAf < setpoint.af) {
				setpoint.af = tempAf;
			}

		}

	}
	

	/**
	 * This method uses the kinematic equation involving vi, vf, acceleration and
	 * displacement to get distance traveled while accelerating
	 */
	public double getDistanceTraveledWhileAccelerating(double vi, double vf, double maxAcceleration) {
		double distance = Math.abs((Math.pow(vf, 2) - Math.pow(vi, 2)) / (2 * maxAcceleration));
		return distance;
	}

	/**
	 * This method takes a delta time from the beginning of a trajectory path and
	 * returns the point where that time is
	 */

	public TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector) {
		return getTrajectoryPoint(Key, deltaTimeFromStartOfSetpointVector, false);
	}

	public TrajectoryPoint getTrajectoryPoint(Path Key, double deltaTimeFromStartOfSetpointVector, Boolean debugMode) {
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

	private enum BeforeWhatTime {
		beforeFirstStartAccelerationCruisingDeltaTime, beforeFirstEndAccelerationCruisingDeltaTime, beforeStartVelocityCruisingDeltaTime, beforeEndVelocityCruisingDeltaTime, beforeSecondStartAccelerationCruisingDeltaTime, beforeSecondEndAccelerationCruisingDeltaTime, beforeEndDeltaTime, afterEndDeltaTime
	}

	private double getPosition(Point setpoint, double deltaTime, double maxJerk, double maxAcceleration,
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

	// Equation used can be found at
	// https://math.vanderbilt.edu/schectex/courses/cubic/
	private double solveCubicEquation(double aTerm, double bTerm, double cTerm, double dTerm) {
		double x = Math
				.cbrt(((-Math.pow(bTerm, 3)) / (27 * Math.pow(aTerm, 3)) + (bTerm * cTerm) / (6 * Math.pow(aTerm, 2))
						- dTerm / (2 * aTerm))
						+ Math.sqrt(Math
								.pow((-Math.pow(bTerm, 3) / (27 * Math.pow(aTerm, 3))
										+ (bTerm * cTerm) / (6 * Math.pow(aTerm, 2)) - dTerm / (2 * aTerm)), 2)
								+ Math.pow((cTerm / (3 * aTerm) - Math.pow(bTerm, 2) / (9 * Math.pow(aTerm, 2))), 3)))
				+ Math.cbrt(((-Math.pow(bTerm, 3)) / (27 * Math.pow(aTerm, 3))
						+ (bTerm * cTerm) / (6 * Math.pow(aTerm, 2)) - dTerm / (2 * aTerm))
						- Math.sqrt(Math
								.pow((-Math.pow(bTerm, 3) / (27 * Math.pow(aTerm, 3))
										+ (bTerm * cTerm) / (6 * Math.pow(aTerm, 2)) - dTerm / (2 * aTerm)), 2)
								+ Math.pow((cTerm / (3 * aTerm) - Math.pow(bTerm, 2) / (9 * Math.pow(aTerm, 2))), 3)))
				- bTerm / (3 * aTerm);
		return x;
	}

}

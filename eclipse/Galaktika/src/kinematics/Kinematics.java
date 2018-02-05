package kinematics;

import java.util.Vector;

public class Kinematics {

	private boolean alreadyPrinted = true;
	private static double m_trajectoryPointInterval = 1.0;
	
	public static void setTrajectoryPointInterval(double trajectoryPointInterval) {
		m_trajectoryPointInterval = trajectoryPointInterval;
	}

	public static double getTrajectoryPointInterval() {
		return m_trajectoryPointInterval;
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

		point.maxVelocity = maxVelocity;
		Key.setpointVector.add(point);
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
		if (debugMode && Key.getSetpointVector().get(0).m_x == 100000) {
			System.out.println("Key.maxJerk: " + Key.maxJerk);
			System.out.println("maxJerk: " + Double.toString(maxJerk));
		}

		Vector<Point> setpointVector = Key.setpointVector;

		// Final velocity and Initial Velocity are commonly referred to as vi and vf
		// respectively
		// Likewise Initial acceleration and Final Acceleration are referred to as ai
		// and af respectively
		getVf_Vi_Ai_Af(setpointVector, Key, debugMode);

		// For every point in the setpoint vector

		for (int i1 = 0; i1 < setpointVector.size(); i1++) {

			Point setpoint = setpointVector.get(i1);
			Point previousSetpoint = new Point(0, 0);
			try {
				previousSetpoint = setpointVector.get(i1 - 1);

			} catch (ArrayIndexOutOfBoundsException a) {

			}
			setpoint.maxAcceleration = Key.maxAcceleration;
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters = new TrajectoryDistanceTimeVelocityParameters();

			MaxVelocityAndMaxAccelerationReachability maxVelocityAndMaxAccelerationTrajectoryType = getWillReachMaxVelocityAndWillReachAtMaxAcceleration(
					Key, setpoint, previousSetpoint, trajectoryDistanceTimeVelocityParameters, debugMode);

			switch (maxVelocityAndMaxAccelerationTrajectoryType) {

			case willReachMaxVelocityAndMaxAcceleration:
				setTrajectoryTimesWhenMaxVelocityAndMaxAccelerationAreReached(Key, setpoint,
						trajectoryDistanceTimeVelocityParameters, debugMode);
				break;

			case willReachMaxAccelerationNotMaxVelocity:
				setTrajectoryTimesWhenMaxAccelerationIsReachedNotMaxVelocity(Key, setpoint,
						trajectoryDistanceTimeVelocityParameters);
				break;

			case willReachMaxVelocityNotMaxAcceleration:
				setTrajectoryTimesWhenMaxVelocityIsReachedNotMaxAcceleration(Key, setpoint,
						trajectoryDistanceTimeVelocityParameters, debugMode);
				break;

			case willNotReachMaxVelocityOrMaxAcceleration:
				setTrajectoryTimesWhenNietherMaxAccelerationOrMaxVelocityAreReached(Key, setpoint,
						trajectoryDistanceTimeVelocityParameters);

			}
			if (debugMode) {
				System.out.println("");
				System.out.println(
						"maxVelocityAndMaxAccelerationTrajectoryType: " + maxVelocityAndMaxAccelerationTrajectoryType);
				System.out.println(
						"firstStartAccelerationCruisingDeltaTime: " + setpoint.firstStartAccelerationCruisingDeltaTime);
				System.out.println(
						"firstEndAccelerationCruisingDeltaTime: " + setpoint.firstEndAccelerationCruisingDeltaTime);
				System.out.println("startVelocityCruisingDeltaTime: " + setpoint.startVelocityCruisingDeltaTime);
				System.out.println("endVelocityCruisingDeltaTime: " + setpoint.endVelocityCruisingDeltaTime);
				System.out.println("secondStartAccelerationCruisingDeltaTime: "
						+ setpoint.secondStartAccelerationCruisingDeltaTime);
				System.out.println(
						"secondEndAccelerationCruisingDeltaTime: " + setpoint.secondEndAccelerationCruisingDeltaTime);
				System.out.println("Key.maxJerk: " + Key.maxJerk);
				System.out.println("");
			}
			// Needs to do this so that the last time through the code the max velocity is
			// not left at some obscure value

			Key.maxVelocity = maxVelocity;
		}

	}

	private void setTrajectoryTimesWhenMaxVelocityAndMaxAccelerationAreReached(Path Key, Point setpoint,
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters, boolean debugMode) {
		double initialVelocityCoveredAtMaxAcceleration = setpoint.maxVelocity - 2
				* trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration;

		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;

		setpoint.firstEndAccelerationCruisingDeltaTime = initialVelocityCoveredAtMaxAcceleration / Key.maxAcceleration
				+ setpoint.firstStartAccelerationCruisingDeltaTime;
		if (debugMode) {
			System.out.println("initialVelocityCoveredAtMaxAcceleration: " + initialVelocityCoveredAtMaxAcceleration);
			System.out.println("Key.maxAcceleration: " + Key.maxAcceleration);
			System.out.println("setpoint.maxVelocity: " + setpoint.maxVelocity);
			System.out.println("trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating: "
					+ trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating);
			System.out.println("trajectoryDistanceTimeVelocityParameters.distance: "
					+ trajectoryDistanceTimeVelocityParameters.distance);
		}
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;

		double distanceCruisingAtMaxVelocity = trajectoryDistanceTimeVelocityParameters.distance
				- 2 * trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating;
		double timeCruising = distanceCruisingAtMaxVelocity / Key.maxVelocity;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime + timeCruising;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		double finalVelocityCoveredAtMaxAcceleration = setpoint.maxVelocity - 2
				* trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime
				+ finalVelocityCoveredAtMaxAcceleration / Key.maxAcceleration;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
	}

	private void setTrajectoryTimesWhenMaxAccelerationIsReachedNotMaxVelocity(Path Key, Point setpoint,
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters) {
		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
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
		 * jvc)^2)/(2*A) f]\ Solve for iat Distance = ijdc + (vi + ijvc)*iat +
		 * 0.5*A*iat^2 + 2*((vi + ijvc + iat*A)*jT + 1/2*A*jT^2 + 1/6J*jT^3) + ((vi +
		 * iat * A + ijvc)^2 - (vf + fjvc)^2)/(2*A) + fjdc
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
		double bTerm = 2 * (setpoint.vi
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
						* setpoint.maxAcceleration);
		double cTerm = -1 * (trajectoryDistanceTimeVelocityParameters.distance
				- trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing
				- trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing
				- 2 * trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
						* (setpoint.vi
								+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration)
				- setpoint.maxAcceleration * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						2)
				+ (1.0 / 3.0) * Key.maxJerk * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						3)
				- Math.pow((setpoint.vi
						+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration),
						2) / (2 * setpoint.maxAcceleration)
				+ Math.pow((setpoint.vf
						+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration),
						2) / (2 * setpoint.maxAcceleration));

		double initialAccelerationTime = (-bTerm + Math.sqrt(Math.pow(bTerm, 2) - (4 * aTerm * cTerm))) / (2 * aTerm);
		double accelerationVelocityCovered = Key.maxAcceleration * initialAccelerationTime;
		double finalAccelerationTime = ((setpoint.vi
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
				+ accelerationVelocityCovered)
				- (setpoint.vf
						+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration))
				/ setpoint.maxAcceleration;
		setpoint.firstEndAccelerationCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime
				+ initialAccelerationTime;
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime;

		setpoint.maxVelocity = setpoint.vi
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
				+ accelerationVelocityCovered;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime
				+ finalAccelerationTime;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;

		double initialMaxAccelerationDistanceCovered = ((setpoint.vi
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration)
				* initialAccelerationTime + 0.5 * setpoint.maxAcceleration * Math.pow(initialAccelerationTime, 2));
		double endMaxAccelerationVelocity = (setpoint.vi
				+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
				+ setpoint.maxAcceleration * initialAccelerationTime);
		double initialSecondJerkDistanceCoveredAccountingForAcceleration = endMaxAccelerationVelocity
				* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
				+ 0.5 * setpoint.maxAcceleration * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						2)
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						3);
		double finalFirstJerkDistanceCoveredAccountingForAcceleration = setpoint.maxVelocity
				* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						3);
	}

	private void setTrajectoryTimesWhenMaxVelocityIsReachedNotMaxAcceleration(Path Key, Point setpoint,
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters, boolean debugMode) {
		
		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging;
		setpoint.firstEndAccelerationCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime;
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstEndAccelerationCruisingDeltaTime + trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging;
		
		double distanceCruisingAtMaxVelocity = trajectoryDistanceTimeVelocityParameters.distance - 2*trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating;
		double timeCruisingAtMaxVelocity = distanceCruisingAtMaxVelocity/Key.maxVelocity;
		
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime + timeCruisingAtMaxVelocity;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime + trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime + trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging;
		if (debugMode) {

		}
	}

	private void setTrajectoryTimesWhenNietherMaxAccelerationOrMaxVelocityAreReached(Path Key, Point setpoint,
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters) {
		double timeTakenToAccelerateUpToAi = setpoint.ai / Key.maxJerk;
		double timeTakenToAccelerateUpToAf = setpoint.af / Key.maxJerk;

		double accelertationUpToAiDistanceCovered = (1.0 / 6.0) * Key.maxJerk
				* Math.pow(timeTakenToAccelerateUpToAi, 3);
		double accelerationUpToAfDistanceCovered = (1.0 / 6.0) * Key.maxJerk * Math.pow(timeTakenToAccelerateUpToAf, 3);

		double maxVelocityDistance = (Math.abs(trajectoryDistanceTimeVelocityParameters.distance)
				+ accelertationUpToAiDistanceCovered + accelerationUpToAfDistanceCovered) / 2;
		trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration = Math
				.cbrt(maxVelocityDistance / Key.maxJerk);
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

		setpoint.firstStartAccelerationCruisingDeltaTime = trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
				- setpoint.ai / Key.maxJerk;
		setpoint.firstEndAccelerationCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime;
		setpoint.startVelocityCruisingDeltaTime = setpoint.firstStartAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		setpoint.endVelocityCruisingDeltaTime = setpoint.startVelocityCruisingDeltaTime;
		setpoint.secondStartAccelerationCruisingDeltaTime = setpoint.endVelocityCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		setpoint.secondEndAccelerationCruisingDeltaTime = setpoint.secondStartAccelerationCruisingDeltaTime;
		setpoint.endDeltaTime = setpoint.secondEndAccelerationCruisingDeltaTime
				+ trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
				- setpoint.af / Key.maxJerk;
		double velocityCoveredByNewJerkTime = 0.5 * Key.maxJerk * Math
				.pow(trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration, 2);
		setpoint.maxVelocity = 2 * velocityCoveredByNewJerkTime;
		setpoint.maxAcceleration = Key.maxJerk
				* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;

		double distanceCoveredDuringFirstJerkTime = (1.0 / 6.0) * Key.maxJerk * Math
				.pow(trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration, 3);
		double jerkVi = 0.5 * Key.maxJerk * Math
				.pow(trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration, 2);
		double jerkAcceleration = Key.maxJerk
				* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
		double distanceCoveredDuringSecondJerkTime = jerkVi
				* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
				+ 0.5 * jerkAcceleration * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						2)
				+ (1.0 / 6.0) * (-1 * Key.maxJerk) * Math.pow(
						trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
						3);
	}

	private MaxVelocityAndMaxAccelerationReachability getWillReachMaxVelocityAndWillReachAtMaxAcceleration(Path Key,
			Point setpoint, Point previousSetpoint,
			TrajectoryDistanceTimeVelocityParameters trajectoryDistanceTimeVelocityParameters, boolean debugMode) {

		trajectoryDistanceTimeVelocityParameters.distance = Math.abs(setpoint.m_x - previousSetpoint.m_x);

		boolean willReachMaxAcceleration = true;
		boolean willReachMaxVelocity = true;

		trajectoryDistanceTimeVelocityParameters.maxVelocityDistance = trajectoryDistanceTimeVelocityParameters.distance
				/ 2;
		trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration = Math
				.pow(Key.maxAcceleration, 2) / (2 * Key.maxJerk);
		
		
		
		if (2 * trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration > setpoint.maxVelocity) {
			trajectoryDistanceTimeVelocityParameters.maxAccelerationVelocity = Key.maxVelocity / 2;
			trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging = Math
					.sqrt(trajectoryDistanceTimeVelocityParameters.maxAccelerationVelocity * 2 / Key.maxJerk);
			trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing = (1.0 / 6.0)
					* Key.maxJerk * Math.pow(trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging, 3);
			setpoint.maxAcceleration = Key.maxJerk
					* trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging;
			trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsDecreasing = trajectoryDistanceTimeVelocityParameters.maxAccelerationVelocity
					* trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging
					+ 0.5 * setpoint.maxAcceleration
							* Math.pow(trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging, 2)
					- (1.0 / 6.0) * Key.maxJerk
							* Math.pow(trajectoryDistanceTimeVelocityParameters.timeAccelerationIsChanging, 3);

			trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating = trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing
					+ trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsDecreasing;
			if (debugMode) {
				System.out.println("");
				System.out.println("trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration: " + trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration);
				System.out.println("trajectoryDistanceTimeVelocityParameters.maxVelocityDistance: " + trajectoryDistanceTimeVelocityParameters.maxVelocityDistance);
				System.out.println("trajectoryDistanceTimeVelocityParameters.distance: " + trajectoryDistanceTimeVelocityParameters.distance);
				System.out.println("Key.maxAcceleration: " + Key.maxAcceleration);
				System.out.println("trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating: " + trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating);
				System.out.println("");
			}
			if (2 * trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating <= trajectoryDistanceTimeVelocityParameters.distance) {
				willReachMaxAcceleration = false;
				willReachMaxVelocity = true;
			} else {
				willReachMaxAcceleration = false;
				willReachMaxVelocity = false;
			}

		} else {
			trajectoryDistanceTimeVelocityParameters.velocityAfterCruisingAtMaxAcceleration = Key.maxVelocity
					- trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration;
			trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration = Key.maxAcceleration
					/ Key.maxJerk;
			trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing = (1.0 / 6.0)
					* Key.maxJerk
					* Math.pow(
							trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
							3);

			trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileCruisingAtMaxAcceleration = (Math
					.pow(trajectoryDistanceTimeVelocityParameters.velocityAfterCruisingAtMaxAcceleration, 2)
					- Math.pow(
							trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration,
							2))
					/ (2.0 * Key.maxAcceleration);

			trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsDecreasing = trajectoryDistanceTimeVelocityParameters.velocityAfterCruisingAtMaxAcceleration
					* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
					+ 0.5 * Key.maxAcceleration * Math.pow(
							trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
							2)
					- (1.0 / 6.0) * Key.maxJerk * Math.pow(
							trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
							3);

			trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating = trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing
					+ trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileCruisingAtMaxAcceleration
					+ trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsDecreasing;
			if (debugMode) {
				System.out.println("");
				System.out.println(
						"trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration: "
								+ trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration);
				System.out.println(
						"trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating: "
								+ trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating);
				System.out.println("trajectoryDistanceTimeVelocityParameters.distance: "
						+ trajectoryDistanceTimeVelocityParameters.distance);
				System.out.println("");
				System.out.println("");
			}
			if (2 * trajectoryDistanceTimeVelocityParameters.maxPossibleDistanceCoveredWhileAccelerating <= trajectoryDistanceTimeVelocityParameters.distance) {
				willReachMaxAcceleration = true;
				willReachMaxVelocity = true;
			} else {
				double distanceCoveredWhileAccelerationIsDecreasingWithNoMaxAccelerationCruising = trajectoryDistanceTimeVelocityParameters.velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration
						* trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration
						+ 0.5 * Key.maxAcceleration * Math.pow(
								trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
								2)
						- (1.0 / 6.0) * Key.maxJerk * Math.pow(
								trajectoryDistanceTimeVelocityParameters.timeWhenAccelerationIsChangingFrom0ToMaxAcceleration,
								3);
				double distanceCoveredWhileAcceleratingNoMaxAccelerationCruising = trajectoryDistanceTimeVelocityParameters.distanceCoveredWhileAccelerationIsIncreasing
						+ distanceCoveredWhileAccelerationIsDecreasingWithNoMaxAccelerationCruising;

				if (2 * distanceCoveredWhileAcceleratingNoMaxAccelerationCruising <= setpoint.maxVelocity) {
					willReachMaxAcceleration = true;
					willReachMaxVelocity = false;
				} else {
					willReachMaxAcceleration = false;
					willReachMaxVelocity = false;
				}
			}

			if (debugMode) {
				System.out.println("");
				System.out.println("");
			}
		}

		if (willReachMaxAcceleration && willReachMaxVelocity) {
			return MaxVelocityAndMaxAccelerationReachability.willReachMaxVelocityAndMaxAcceleration;
		} else if (willReachMaxAcceleration && !willReachMaxVelocity) {
			return MaxVelocityAndMaxAccelerationReachability.willReachMaxAccelerationNotMaxVelocity;
		} else if (!willReachMaxAcceleration && willReachMaxVelocity) {
			return MaxVelocityAndMaxAccelerationReachability.willReachMaxVelocityNotMaxAcceleration;
		} else {
			return MaxVelocityAndMaxAccelerationReachability.willNotReachMaxVelocityOrMaxAcceleration;
		}

	}

	private void getVf_Vi_Ai_Af(Vector<Point> setpointVector, Path Key, boolean debugMode) {

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

				getFinalVelocityOfPoint(Key, setpoint, previousSetpoint,
						possibleFinalVelocityDeterminingSetpointIndexes, possibleFinalVelocityDeterminingSetpoints, i1,
						deltaDistanceBetweenCurrentSetpointAndPreviousSetpoint);

			} else {
				setpoint.vf = 0.0;
				getFinalAccelerationOfPoint(Key, setpoint, nextSetpoint, debugMode);
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

	private void getFinalAccelerationOfPoint(Path Key, Point setpoint, Point nextSetpoint, boolean debugMode) {
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
		double finalFirstJerkDistanceCovered = velocityCoveredWhileDecceleratingFromMaxAccelertion * finalJerkTime
				- (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3);
		double finalSecondJerkDistanceCovered = (velocityCoveredWhileDecceleratingFromMaxAccelertion
				- finalEachJerkVelocityCovered) * finalJerkTime
				- 0.5 * finalJerkMaxAcceleration * Math.pow(finalJerkTime, 2)
				+ (1.0 / 6.0) * Key.maxJerk * Math.pow(finalJerkTime, 3);

		double distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 = Math.pow(Key.maxAcceleration, 3)
				* (0.5 * Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 2)
						- (1.0 / 6.0) * Key.maxJerk * Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 3)
						+ (Key.maxJerk / Math.sqrt(2)) * ((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)))
						- (0.5) * Math.pow(Key.maxJerk, 2) * (1.0 / Math.sqrt(2))
								* Math.pow(((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2))), 2)
						+ Math.pow(Key.maxJerk, 2) / 4.0
						- ((Math.pow(Key.maxJerk, 2) / 4) + (Math.pow(Key.maxJerk, 4) / (4 * Math.sqrt(2))))
						+ (1.0 / 6.0) * Math.pow(Key.maxJerk, 4) * (1.0 / Math.pow(Math.sqrt(2), 3)));
		if (distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 <= distance) {
			setpoint.af = Key.maxAcceleration;
		} else {
			double aTerm = (1.0 / 6.0 * Key.maxJerk);
			double bTerm = 0.0;
			double cTerm = Key.maxAcceleration / (2 * Key.maxJerk);
			double dTerm = -1 * Math.abs(distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0 - distance);

			double timeBeforeEndDeltaTimeForAccelerationToStartChanging = solveCubicEquation(aTerm, bTerm, cTerm,
					dTerm);

			double tempAf = Key.maxAcceleration - Key.maxJerk / timeBeforeEndDeltaTimeForAccelerationToStartChanging;
			/*
			 * Distance = 1/2*ai*(iT + fT)^2 - 1/6*J*(iT + fT)^3 + (ai*(iT + fT) - 1/2*J*(iT
			 * + fT)^2)*fT + 1/2*(ai - J*(iT + fT))*fT^2 + 1/6*J*fT^3
			 * 
			 * iT = ai/J VelocityCoveredWhileAccelerationIsDecceleratingTo0 = ai/2*ai/J =
			 * ai^2/(2*J) FinalTimeVelocityCovered = ai^2/(4*J) fT = sqrt(ai^2/(4*J)*2 *1/J)
			 * = sqrt(ai^2/(2*J^2)) = (ai*J)/sqrt(2) iT + fT = ai/J + ai*J/sqrt(2) = ai*(1/J
			 * + J/sqrt(2))
			 * 
			 * Distance = 1/2*ai*(ai*(1/J + J/sqrt(2)))^2 - 1/6*J*(ai*(1/J + J/sqrt(2)))^3 +
			 * (ai*(ai*(1/J + J/sqrt(2))) - 1/2*J*(ai*(1/J + J/sqrt(2)))^2)*(ai*J)/sqrt(2) +
			 * 1/2*(ai - J*(ai*(1/J + J/sqrt(2))))*((ai*J)/ sqrt(2))^2 +
			 * 1/6*J*((ai*J)/sqrt(2))^3
			 * 
			 * Distance = 1/2*ai*ai^2*(1/J + J/sqrt(2))^2 - 1/6*J*ai^3*(1/J + J/sqrt(2))^3 +
			 * (ai^2*(1/J + J/sqrt(2)) - 1/2*J*ai^2*(1/J + J/sqrt(2))^2)*(ai*J)/sqrt(2) +
			 * 1/2*(ai - J*(ai*(1/J + J/sqrt(2))))*((ai*J)/sqrt(2))^2 + 1/6*J*ai^3 *
			 * J^3/sqrt(2)^3
			 * 
			 * 1/2*(ai - J*(ai*(1/J + J/sqrt(2))))*((ai*J)/sqrt(2))^2 1/2*(ai - J*ai*(1/J +
			 * J/sqrt(2)))*ai^2 * J^2/sqrt(2)^2 (ai - J*ai*(1/J + J/sqrt(2)))*ai^2 * J^2/4
			 * (ai^3 - J*ai^3*(1/J + J/sqrt(2))) * J^2/4 J^2*ai^3/4 - J^3*ai^3*(1/J +
			 * J/sqrt(2))/4 J^2/4*ai^3 - ai^3*(J^2/4 + J^4/(4*sqrt(2)))
			 * 
			 * Distance = 1/2*ai*ai^2*(1/J + J/sqrt(2))^2 - 1/6*J*ai^3*(1/J + J/sqrt(2))^3 +
			 * (ai^2*(1/J + J/sqrt(2)) - 1/2*J*ai^2*(1/J + J/sqrt(2))^2)*(ai*J)/sqrt(2) +
			 * J^2/4*ai^3 - ai^3*(J^2/4 + J^4/(4*sqrt(2))) + 1/6*J*ai^3 * J^3/sqrt(2)^3
			 * 
			 * Distance = 1/2*ai^3*(1/J +J/ sqrt(2))^2 - 1/6*J*ai^3*(1/J + J/sqrt(2))^3 +
			 * ai^3*J/sqrt(2)*(1/J + J/sqrt(2)) - 1/2*J^2*ai^3/sqrt(2)*(1/J + J/sqrt(2))^2 +
			 * J^2/4*ai^3 - ai^3*(J^2/4 + J^4/(4*sqrt(2))) + 1/6*J*ai^3 * J^3/sqrt(2)^3
			 * 
			 * Distance = 1/2*(1/J + J/sqrt(2))^2*ai^3 - 1/6*J*(1/J + J/sqrt(2))^3*ai^3 +
			 * J/sqrt(2)*(1/J + J/sqrt(2))*ai^3 - 1/2*J^2/sqrt(2)*(1/J + J/sqrt(2))^2*ai^3 +
			 * J^2/4*ai^3 - (J^2/4 + J^4/ (4*sqrt(2)))*ai^3 + 1/6*J^4/sqrt(2)^3*ai^3
			 * 
			 * Distance = (1/2*(1/J + J/sqrt(2))^2 -1/6*J*(1/J + J/sqrt(2))^3 +
			 * J/sqrt(2)*(1/J + J/sqrt(2)) - 1/2*J^2/sqrt(2)*(1/J + J/sqrt(2))^2 + J^2/4 -
			 * (J^2/4 + J^4/(4*sqrt(2))) + 1/6*J^4/sqrt(2)^3)*ai^3
			 * 
			 * Distance/(1/2*(1/J + J/sqrt(2))^2 -1/6*J*(1/J + J/sqrt(2))^3 + J/sqrt(2)*(1/J
			 * + J/sqrt(2)) - 1/2*J^2/sqrt(2)*(1/J + J/sqrt(2))^2 + J^2/4 - (J^2/4 +
			 * J^4/(4*sqrt(2))) + 1/6*J^4/sqrt(2)^3)= ai^3
			 * 
			 * cbrt(Distance/(1/2*(1/J + J/sqrt(2))^2 -1/6*J*(1/J + J/sqrt(2))^3 +
			 * J/sqrt(2)*(1/J + J/sqrt(2)) - 1/2*J^2/sqrt(2)*(1/J + J/sqrt(2))^2 + J^2/4 -
			 * (J^2/4 + J^4/(4*sqrt(2))) + 1/6*J^4/sqrt(2)^3)) = ai
			 */

			tempAf = Math.cbrt(distance / (0.5 * Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 2)
					- (1.0 / 6.0) * Key.maxJerk * Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 3)
					+ (Key.maxJerk / Math.sqrt(2)) * ((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)))
					- (0.5) * Math.pow(Key.maxJerk, 2) * (1.0 / Math.sqrt(2))
							* Math.pow(((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2))), 2)
					+ Math.pow(Key.maxJerk, 2) / 4.0
					- ((Math.pow(Key.maxJerk, 2) / 4) + (Math.pow(Key.maxJerk, 4) / (4 * Math.sqrt(2))))
					+ (1.0 / 6.0) * Math.pow(Key.maxJerk, 4) * (1.0 / Math.pow(Math.sqrt(2), 3))));
			if (debugMode) {
				double distanceCoveredWhileDeceleratingFromAIOfMaxAI = Math.pow(Key.maxAcceleration, 3)
						* (0.5 * Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 2)
								- (1.0 / 6.0) * Key.maxJerk
										* Math.pow((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)), 3)
								+ (Key.maxJerk / Math.sqrt(2)) * ((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2)))
								- (0.5) * Math.pow(Key.maxJerk, 2) * (1.0 / Math.sqrt(2))
										* Math.pow(((1.0 / Key.maxJerk) + (Key.maxJerk / Math.sqrt(2))), 2)
								+ Math.pow(Key.maxJerk, 2) / 4.0
								- ((Math.pow(Key.maxJerk, 2) / 4) + (Math.pow(Key.maxJerk, 4) / (4 * Math.sqrt(2))))
								+ (1.0 / 6.0) * Math.pow(Key.maxJerk, 4) * (1.0 / Math.pow(Math.sqrt(2), 3)));
				/*
				 * Distance = 1/2*ai*(ai*(1/J + J/sqrt(2)))^2 - 1/6*J*(ai*(1/J + J/sqrt(2)))^3 +
				 * (ai*(ai*(1/J + J/sqrt(2))) - 1/2*J*(ai*(1/J + J/sqrt(2)))^2)*(ai*J)/sqrt(2) +
				 * 1/2*(ai - J*(ai*(1/J + J/sqrt(2))))*((ai*J)/ sqrt(2))^2 +
				 * 1/6*J*((ai*J)/sqrt(2))^3
				 */
				double beforeSimplifiedDistanceCoveredWhileDeceleratingFromAIOfMaxAI = 0.5 * Key.maxAcceleration
						* Math.pow((Key.maxAcceleration * (1.0 / Key.maxJerk + Key.maxJerk / Math.sqrt(2))), 2)
						- (1.0 / 6.0) * Key.maxJerk
								* Math.pow((Key.maxAcceleration * (1.0 / Key.maxJerk + Key.maxJerk / Math.sqrt(2))), 3)
						+ (Key.maxAcceleration
								* (Key.maxAcceleration * (1.0 / Key.maxJerk + Key.maxJerk / Math.sqrt(2)))
								- 0.5 * Key.maxJerk * Math
										.pow((Key.maxAcceleration * (1 / Key.maxJerk + Key.maxJerk / Math.sqrt(2))), 2))
								* (Key.maxAcceleration * Key.maxJerk) / Math.sqrt(2)
						+ 0.5 * (Key.maxAcceleration - Key.maxJerk
								* (Key.maxAcceleration * (1.0 / Key.maxJerk + Key.maxJerk / Math.sqrt(2))))
								* Math.pow(((Key.maxAcceleration * Key.maxJerk) / Math.sqrt(2)), 2)
						+ 1.0 / 6.0 * Key.maxJerk * Math.pow(((Key.maxAcceleration * Key.maxJerk) / Math.sqrt(2)), 3);
				System.out.println("");
				System.out.println("distance: " + distance);
				System.out.println("tempAf: " + tempAf);
				System.out.println("distanceCoveredWhileDeceleratingFromAIOfMaxAI: "
						+ distanceCoveredWhileDeceleratingFromAIOfMaxAI);
				System.out.println("beforeSimplifiedDistanceCoveredWhileDeceleratingFromAIOfMaxAI: "
						+ beforeSimplifiedDistanceCoveredWhileDeceleratingFromAIOfMaxAI);
				System.out.println("distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0: "
						+ distanceCoveredWhileAccelerationIsChangingKeepingViAndVf0);
				System.out.println("");
			}

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

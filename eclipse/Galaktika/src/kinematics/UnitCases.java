package kinematics;

import java.util.Random;

import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import kinematics.KinematicsException.*;

public class UnitCases {
	static double m_deltaTimeFromOriginalPoint = 0.01;
	
	static Kinematics m_kinematics = new Kinematics();

	private static void createUnitCase(Double... setpoints) throws InvalidDimentionException, KinematicsException {
		createUnitCase(false, false, setpoints);
	}

	private static void createUnitCase(boolean printMode, Double... setpoints) throws InvalidDimentionException,
		KinematicsException {
		createUnitCase(printMode, false, setpoints);
	}

	private static void createUnitCase(boolean printMode, boolean debugMode, Double... setpoints)
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(2.0, 0.5, 0.25, printMode, debugMode, setpoints);
	}

	private static void createUnitCase(double maxV, double maxA, double maxJ, boolean printMode, boolean debugMode, 
			Double... setpoints) throws InvalidDimentionException, KinematicsException {

		int numberOfSetpoints = 0;
		int setpointIndex = 0;
		for (double setpoint : setpoints) {
			numberOfSetpoints++;
		}
		CustomMaxVelocitySetpoint[] customMaxVelocitySetpoint = new CustomMaxVelocitySetpoint[numberOfSetpoints];
		for (double setpoint : setpoints) {
			customMaxVelocitySetpoint[setpointIndex] = new CustomMaxVelocitySetpoint(setpoint, 0.0);
			setpointIndex++;
		}
		createUnitCase(maxV, maxA, maxJ, printMode, debugMode,  customMaxVelocitySetpoint);
	}

	private static void createUnitCase(CustomMaxVelocitySetpoint... setpoints) throws InvalidDimentionException,
		KinematicsException {
		createUnitCase(false, false, setpoints);
	}

	private static void createUnitCase(boolean printMode, CustomMaxVelocitySetpoint... setpoints)
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(printMode, false, setpoints);
	}
	
	private static void createUnitCase(boolean printMode, boolean debugMode, CustomMaxVelocitySetpoint... setpoints)
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(2.0, 0.5, 0.25, printMode, debugMode,  setpoints);
	}

	private static void createUnitCase(double maxV, double maxA, double maxJ, boolean printMode, boolean debugMode, 
			CustomMaxVelocitySetpoint... setpoints) throws InvalidDimentionException, KinematicsException {
		Path myPath = new Path();
		KinematicsTester kinematicsTester = new KinematicsTester();
		for (CustomMaxVelocitySetpoint setpoint : setpoints) {
			Kinematics.addPointToPath(myPath, new Point(setpoint.getSetpoint(), setpoint.getCustomMaxVelocity()));
		}
		m_kinematics.createTrajectory(myPath, maxV, maxA, maxJ, debugMode); // 2.0, 0.5, 0.25
		if (printMode) {
			printTrajectory(myPath);
		}
		Checkers.checkTrajectoryPath(myPath, kinematicsTester);

	}

	public static void realTests() throws InvalidDimentionException, 
	KinematicsException {
		/*Kinematics.setTrajectoryPointInterval(0.000001);
		createUnitCase(DriveTrain.getMaxVelocity(), DriveTrain.getMaxAcceleration(), DriveTrain.getMaxJerk(), false, false, 100000.0);
		
		Kinematics.setTrajectoryPointInterval(1.0);*/
		Kinematics.setTrajectoryPointInterval(0.001);
		createUnitCase(64000, 61500, 56250, true, false, 25000.0);
		Kinematics.setTrajectoryPointInterval(1.0);
	}

	static void createSingleSetpointCases() throws InvalidDimentionException, 
	KinematicsException {
		createUnitCase(true, 14.0);
		System.out.println("1");
		createUnitCase(8.0);
		System.out.println("2");
		createUnitCase(-14.0);
		System.out.println("3");
		createUnitCase(-8.0);
		System.out.println("4");
		createUnitCase(2.0);
		System.out.println("5");
		createUnitCase(-2.0);
		System.out.println("6");
		createUnitCase(2.0, 8.0, 0.25, false, false, 14.0);
		System.out.println("7");
		createUnitCase(2.0, 8.0, 0.25, false, false, -14.0);
		System.out.println("8");
		realTests();
		System.out.println("9");
	}

	static void createChangingDirectionCases() throws InvalidDimentionException, KinematicsException {
		createChangingDirectionCasesFirstPointReachesMaxVAndMaxA();
		createChangingDirectionCasesFirstPointReachesMaxANotMaxV();
		createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxV();
	}

	static void createSameDirectionCases() throws InvalidDimentionException, KinematicsException {
		createSameDirectionCasesFirstPointReachesMaxVAndMaxA();
		createSameDirectionCasesFirstPointReachesMaxANotMaxV();
		createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxV();
	}

	static void createSingleSetpointCasesWithCustomMaxV() throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.5, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.5, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0));
	}

	static void createSameDirectionCasesFirstPointWithCustomMaxV() throws InvalidDimentionException,
	KinematicsException {
		createSameDirectionCasesFirstPointReachesMaxVAndMaxAFirstPointWithCustomMaxV();
		createSameDirectionCasesFirstPointReachesMaxANotMaxVFirstPointWithCustomMaxV();
		createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxVFirstPointWithCustomMaxV();
	}

	static void createSameDirectionCasesSecondPointWithCustomMaxV() throws InvalidDimentionException,
	KinematicsException {
		createSameDirectionCasesFirstPointReachesMaxVAndMaxASecondPointWithCustomMaxV();
		createSameDirectionCasesFirstPointReachesMaxANotMaxVSecondPointWithCustomMaxV();
		createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxVSecondPointWithCustomMaxV();
	}

	static void createChangingDirectionCasesFirstPointWithCustomMaxV() throws InvalidDimentionException,
	KinematicsException {
		createChangingDirectionCasesFirstPointReachesMaxAAndMaxVFirstPointWithCustomMaxV();
		createChangingDirectionCasesFirstPointReachesMaxANotMaxVFirstPointWithCustomMaxV();
		createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxVFirstPointWithCustomMaxV();
	}

	static void createChangingDirectionCasesSecondPointWithCustomMaxV() throws InvalidDimentionException,
	KinematicsException {
		createChangingDirectionCasesFirstPointReachesMaxAAndMaxVSecondPointWithCustomMaxV();
		createChangingDirectionCasesFirstPointReachesMaxANotMaxVSecondPointWithCustomMaxV();
		createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxVSecondPointWithCustomMaxV();
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxVAndMaxA() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(14.0, -6.0);
		createUnitCase(-14.0, 6.0);
		createUnitCase(14.0, 6.0);
		createUnitCase(-14.0, -6.0);
		createUnitCase(true, true, 14.0, 13.0);
		createUnitCase(-14.0, -13.0);
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxANotMaxV() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(6.0, -12.0);
		createUnitCase(-6.0, 12.0);
		createUnitCase(6.0, 0.0);
		createUnitCase(-6.0, 0.0);
		createUnitCase(6.0, 5.0);
		createUnitCase(-6.0, -5.0);
	}

	private static void createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxV() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(1.0, -14.0);
		createUnitCase(-1.0, 14.0);
		createUnitCase(1.0, -4.0);
		createUnitCase(-1.0, 4.0);
		createUnitCase(1.0, 0.0);
		createUnitCase(-1.0, 0.0);
	}

	private static void createSameDirectionCasesFirstPointReachesMaxVAndMaxA() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(14.0, 28.0);
		createUnitCase(-14.0, -28.0);
		createUnitCase(14.0, 20.0);
		createUnitCase(-14.0, -20.0);
		createUnitCase(14.0, 15.0);
		createUnitCase(-14.0, -15.0);
	}

	private static void createSameDirectionCasesFirstPointReachesMaxANotMaxV() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(6.0, 20.0);
		createUnitCase(-6.0, -20.0);
		createUnitCase(6.0, 10.0);
		createUnitCase(-6.0, -10.0);
		createUnitCase(6.0, 7.0);
		createUnitCase(6.0, -7.0);
	}

	private static void createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxV() throws InvalidDimentionException,
	KinematicsException {
		createUnitCase(1.0, 16.0);
		createUnitCase(-1.0, -16.0);
		createUnitCase(1.0, 6.0);
		createUnitCase(-1.0, -6.0);
		createUnitCase(1.0, 2.0);
		createUnitCase(-1.0, -2.0);
	}

	private static void createSameDirectionCasesFirstPointReachesMaxVAndMaxAFirstPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(20.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(-20.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(15.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(-15.0, 1.0));
	}

	private static void createSameDirectionCasesFirstPointReachesMaxANotMaxVFirstPointWithCustomMaxV()
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(12.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0, 1.0), new CustomMaxVelocitySetpoint(-12.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(7.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(7.0));
	}

	private static void createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxVFirstPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(6.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(-6.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(2.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(-2.0));
	}

	private static void createSameDirectionCasesFirstPointReachesMaxVAndMaxASecondPointWithCustomMaxV()
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(20.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(-20.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(15.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(-15.0, 1.0));
	}

	private static void createSameDirectionCasesFirstPointReachesMaxANotMaxVSecondPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(12.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(-12.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(7.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(-7.0, 1.0));
	}

	private static void createSameDirectionCasesFirstPointDoesNotReachMaxAOrMaxVSecondPointWithCustomMaxV()
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(7.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(-7.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(2.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(-2.0, 1.0));
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxAAndMaxVFirstPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(8.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(-8.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0, 1.0), new CustomMaxVelocitySetpoint(13.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0, 1.0), new CustomMaxVelocitySetpoint(-13.0));
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxANotMaxVFirstPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException{
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(0.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0, 1.0), new CustomMaxVelocitySetpoint(0.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0, 1.0), new CustomMaxVelocitySetpoint(5.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0, 1.0), new CustomMaxVelocitySetpoint(-5.0));
	}

	private static void createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxVFirstPointWithCustomMaxV()
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(-28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(28.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(-5.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(5.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0, 1.0), new CustomMaxVelocitySetpoint(0.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0, 1.0), new CustomMaxVelocitySetpoint(0.0));
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxAAndMaxVSecondPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException{
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(8.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(-8.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(14.0), new CustomMaxVelocitySetpoint(13.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-14.0), new CustomMaxVelocitySetpoint(-13.0, 1.0));
	}

	private static void createChangingDirectionCasesFirstPointReachesMaxANotMaxVSecondPointWithCustomMaxV()
			throws InvalidDimentionException,KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(0.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(0.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(6.0), new CustomMaxVelocitySetpoint(5.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-6.0), new CustomMaxVelocitySetpoint(-5.0, 1.0));
	}

	private static void createChangingDirectionCasesFirstPointDoesNotReachMaxAOrMaxVSecondPointWithCustomMaxV()
			throws InvalidDimentionException, KinematicsException {
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(-28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(28.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(-5.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(5.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(1.0), new CustomMaxVelocitySetpoint(0.0, 1.0));
		createUnitCase(new CustomMaxVelocitySetpoint(-1.0), new CustomMaxVelocitySetpoint(0.0, 1.0));
	}

	static void createRandomTestCases() throws InvalidDimentionException,KinematicsException {
		for (int i = 0; i < 10000; i++) {
			Random random = new Random();
			Path myPath = new Path();
			KinematicsTester kinematicsTester = new KinematicsTester();
			int numberOfSetpoints = 1;//random.nextInt(10);
			int maxVelocityInt = random.nextInt(6);

			double maxVelocity = Math.abs(random.nextDouble() + maxVelocityInt);
			double maxAcceleration = random.nextDouble() + random.nextInt(3);
			if (maxVelocity < 0.1) {
				maxVelocity += 1;
			}
			if (maxAcceleration < 0.01) {
				maxAcceleration = 0.02;
			}
			Point previousPoint = new Point(0);
			for (int i1 = 0; i1 < numberOfSetpoints; i1++) {
				double setpointInt = random.nextInt(50);
				double setpointDouble = random.nextDouble();
				double setpoint = setpointInt + setpointDouble;
				double customMaxVelocity;
				double directionConstant;
				if (random.nextBoolean()) {

					try {
						customMaxVelocity = random.nextDouble() + random.nextInt(maxVelocityInt);
					} catch (IllegalArgumentException a) {
						customMaxVelocity = random.nextDouble();
					}
				} else {
					customMaxVelocity = 0.0;
				}
				if (customMaxVelocity != 0.0 && customMaxVelocity < 0.1) {
					customMaxVelocity += 1;
				}
				if (random.nextBoolean()) {
					directionConstant = 1.0;
				} else {
					directionConstant = -1.0;
				}
				if (Math.abs(setpoint - previousPoint.getm_X()) < maxAcceleration
						* Kinematics.getTrajectoryPointInterval()) {
					if (setpoint > 0.0) {
						setpoint += maxAcceleration * Kinematics.getTrajectoryPointInterval();
					} else {
						setpoint -= maxAcceleration * Kinematics.getTrajectoryPointInterval();
					}
				}
				Kinematics.addPointToPath(myPath, new Point(setpoint * directionConstant), customMaxVelocity);

				previousPoint = new Point(setpoint * directionConstant);

			}

			m_kinematics.createTrajectory(myPath, maxVelocity, maxAcceleration, 0.25, false);
			System.out.println("Number: " + (i + 1));
			Checkers.checkTrajectoryPath(myPath, kinematicsTester);
		}
	}

	static double getVelocityOfPoint(Path Key, TrajectoryPoint4905 originalTrajectoryPoint) {
		TrajectoryPoint4905 deltaBeforeOriginalTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
				originalTrajectoryPoint.m_timestamp - m_deltaTimeFromOriginalPoint);
		TrajectoryPoint4905 deltaAfterOriginalTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
				originalTrajectoryPoint.m_timestamp + m_deltaTimeFromOriginalPoint);
		double changeInPosition = deltaAfterOriginalTrajectoryPoint.m_position
				- deltaBeforeOriginalTrajectoryPoint.m_position;
		double changeInTime = m_deltaTimeFromOriginalPoint * 2;
		double calculatedVelocity = changeInPosition / changeInTime;
		if (calculatedVelocity == 17091.9392) {
			System.out.println(
					"deltaAfterOriginalTrajectoryPoint.m_position: " + deltaAfterOriginalTrajectoryPoint.m_position);
		}
		return calculatedVelocity;
	}

	private static double getAccelerationOfPoint(Path Key, double originalTrajectoryPointTime) {
		TrajectoryPoint4905 trajectoryPoint = new TrajectoryPoint4905(0.0, 0.0, originalTrajectoryPointTime);
		return getAccelerationOfPoint(Key, trajectoryPoint);
	}

	static double getAccelerationOfPoint(Path Key, TrajectoryPoint4905 originalTrajectoryPoint) {
		TrajectoryPoint4905 deltaBeforeOriginalTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
				originalTrajectoryPoint.m_timestamp - m_deltaTimeFromOriginalPoint);
		TrajectoryPoint4905 deltaAfterOriginalTrajectoryPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
				originalTrajectoryPoint.m_timestamp + m_deltaTimeFromOriginalPoint);
		double changeInVelocity = deltaAfterOriginalTrajectoryPoint.m_currentVelocity
				- deltaBeforeOriginalTrajectoryPoint.m_currentVelocity;
		double changeInTime = m_deltaTimeFromOriginalPoint * 2;
		double calculatedAcceleration = changeInVelocity / changeInTime;

		return calculatedAcceleration;
	}

	private static double getJerkOfPoint(Path Key, double originalTrajectoryPointTime, boolean testMode) {
		TrajectoryPoint4905 trajectoryPoint = new TrajectoryPoint4905(0.0, 0.0, originalTrajectoryPointTime);
		return getJerkOfPoint(Key, trajectoryPoint, testMode);
	}

	static double getJerkOfPoint(Path Key, double originalTrajectoryPointTime) {
		TrajectoryPoint4905 trajectoryPoint = new TrajectoryPoint4905(0.0, 0.0, originalTrajectoryPointTime);
		return getJerkOfPoint(Key, trajectoryPoint);
	}

	static double getJerkOfPoint(Path Key, TrajectoryPoint4905 originalTrajectoryPoint) {
		return getJerkOfPoint(Key, originalTrajectoryPoint, false);
	}

	private static double getJerkOfPoint(Path Key, TrajectoryPoint4905 originalTrajectoryPoint, boolean testMode) {
		double deltaBeforeAcceleration = getAccelerationOfPoint(Key,
				originalTrajectoryPoint.m_timestamp - m_deltaTimeFromOriginalPoint);
		double deltaAfterAcceleration = getAccelerationOfPoint(Key,
				originalTrajectoryPoint.m_timestamp + m_deltaTimeFromOriginalPoint);

		double changeInAcceleration = deltaAfterAcceleration - deltaBeforeAcceleration;
		double changeInTime = m_deltaTimeFromOriginalPoint * 2;
		double calculatedJerk = changeInAcceleration / changeInTime;
		return calculatedJerk;
	}

	public static void printTrajectory(Path Key) {
		System.out.println("Trajectory Point: [vel, acel, jerk, pos, time]");
		double endDeltatTimeFromStartOfPath = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			endDeltatTimeFromStartOfPath += Key.getSetpointVector().get(i).getEndDeltaTime();
		}
		double originalTrajectoryPointInterval = Kinematics.getTrajectoryPointInterval();
		if(originalTrajectoryPointInterval < 0.1) {
			Kinematics.setTrajectoryPointInterval(0.01);
		}

		for (double i = 0.0; i < endDeltatTimeFromStartOfPath; i += Kinematics.getTrajectoryPointInterval()) {
			TrajectoryPoint4905 currentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key, i, true);

			double currentAcceleration = getAccelerationOfPoint(Key, currentPoint);

			TrajectoryPoint4905 twiceDeltaBeforeCurrentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
					i - 2 * m_deltaTimeFromOriginalPoint);
			TrajectoryPoint4905 twiceDeltaAfterCurrentPoint = GettingOfTrajectoryPoint.getTrajectoryPoint(Key,
					i + 2 * m_deltaTimeFromOriginalPoint);

			double deltaBeforeAcceleration = (currentPoint.m_currentVelocity
					- twiceDeltaBeforeCurrentPoint.m_currentVelocity) / (2 * m_deltaTimeFromOriginalPoint);
			double deltaAfterAcceleration = (twiceDeltaAfterCurrentPoint.m_currentVelocity
					- currentPoint.m_currentVelocity) / (2 * m_deltaTimeFromOriginalPoint);

			double currentJerk = getJerkOfPoint(Key, currentPoint);

			System.out.println("Trajectory Point: [" + currentPoint.m_currentVelocity + ", " + currentAcceleration
					+ ", " + currentJerk + ", " + currentPoint.m_position + ", " + currentPoint.m_timestamp + "]");

		}

		System.out.print("The Setpoints are: ");
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			if (i == 0) {
				System.out.print(Key.getSetpointVector().get(i).getm_X());
			} else {
				System.out.print("		   " + Key.getSetpointVector().get(i).getm_X());
			}
			System.out.println(" MaxV " + Key.getSetpointVector().get(i).getMaxVelocity());

		}
		System.out.println("");
		if(originalTrajectoryPointInterval != Kinematics.getTrajectoryPointInterval()) {
			Kinematics.setTrajectoryPointInterval(originalTrajectoryPointInterval);
		}

	}
}
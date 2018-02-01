package kinematics;

import java.util.Random;

import kinematics.Kinematics;
import kinematics.Kinematics.InvalidDimentionException;
import kinematics.Kinematics.Path;
import kinematics.Kinematics.Point;
import kinematics.Kinematics.TrajectoryPoint;

public class KinematicsTester {

	static Kinematics m_kinematicsSimpler = new Kinematics();

	public static void main(String[] args) {

		try {
			createPositiveTrajectoryGreaterThanTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryGreaterThanDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToNegativeTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectoryLessThanTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryToNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryWithACustomMaxVelocityGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createNegativeTrajectoryWithACustomMaxVelocityGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase();

			createPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingWithACustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase();

			createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase();

			TestCases();

			TestCases2();

			TestCases3();

			TestCases4();

			TestCases5();

			TestCases6();

			TestCases7();

			TestCases8();

			TestCases9();
			
			TestCases10();
			
			createRandomTestCases();
		} catch (InvalidDimentionException | InvalidVelocityException | InvalidNextVelocityFromLastAcceleration
				| InvalidAccelerationException | InvalidFinalPosition | InvalidTrajectoryLogic e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void createPositiveTrajectoryGreaterThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester);

	}

	private static void createPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);
	}

	private static void createNegativeTrajectoryGreaterThanDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester);

	}

	private static void createNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-6));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToNegativeTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-2));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		
		checkTrajectoryPath(myPath, kinematicsTester);

	}

	private static void createPositiveTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(8));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester);

	}

	private static void createNegativeTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-8));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-6));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-2));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-16));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-12));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-6));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-5));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-7));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectoryLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-3));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-6));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(16));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(12));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);
		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(5));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(7));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(3));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryWithACustomMaxVelocityGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10), 1.0);

		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryWithACustomMaxVelocityGreaterThanTwiceTheDistanceCoveredWhileAcceleratingCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10), 1.0);

		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingWithACustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1.5), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(11.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1.5), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(0.25), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1.25), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(0.25), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(0.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(0.5), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryGreaterThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(16), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(12), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectory1stPointLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTwiceTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(5));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(7), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(3), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createPositiveTrajectoryToPositiveTrajectoryLessThanAndGreaterThanTheDistanceCoveredWhileAcceleratingSecondPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6), 1.0);
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-11.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory2ndPointLessThanTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createNegativeTrajectoryToNegativeTrajectory1stPointLessThanTwiceTheDistanceCoveredWhileAcceleratingFirstPointWithCustomMaxVelocityCase()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1.5), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-0.25), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-0.75));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1), 1.0);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1.5));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases2()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(3));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(5));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases3()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-1));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-2));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(3));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-3));
		m_kinematicsSimpler.createTrajectory(myPath, 2.0, 0.5);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases4()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(0.6676984378508238));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-14.74165235513177));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-17.15461976175742),
				2.401903900952746);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-10.775473113278561));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(23.287314160976056),
				1.7480089052606078);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-23.00441246857718));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(19.36341833011983),
				3.4463078435781904);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(9.850778313398827), 3.167239498524375);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(17.120057437440117),
				0.3450556942188484);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(3.030929225820089),
				0.8670591115037023);
		m_kinematicsSimpler.createTrajectory(myPath, 5.0, 0.36515929984665874 / 0.5);

		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases5()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(40.53565169658988),
				5.3586511134357115);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(46.491519281646305),
				4.179458104695883);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-19.190750014133542),
				3.6954516978928784);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-17.120308093026875),
				1.7424646963652979);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-11.035602015458972),
				2.9871172081420534);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-46.63110005952963),
				4.6986956941676805);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(30.361184256683988),
				5.3586511134357115);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(37.55188638372119),
				4.5923302134967615);

		m_kinematicsSimpler.createTrajectory(myPath, 5.3586511134357115, 1.4664421093524547);
		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases6()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(33.9370070533335), 4.701044877204572);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10.002953802776918),
				0.5079967207342222);

		m_kinematicsSimpler.createTrajectory(myPath, 4.701044877204572, 1.9744750446466444);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases7()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-29.72793639392313));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(15.682323303735526));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-8.110274239816203));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20.07575101738465));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(20.13057905076917));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-35.321114559505055));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-22.748726815119696));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(36.90881735518415));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(32.079603084124635));

		m_kinematicsSimpler.createTrajectory(myPath, 0.7382733504805985, 1.5809005848843993);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void TestCases8()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(8.737135159714155),
				0.9525062390771225);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20.10901176025183),
				0.34878247201444434);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(13.824709243288261),
				0.26824622496025685);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-22.824387461043777),
				0.9525062390771225);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-18.169928311634923),
				0.9525062390771225);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(47.023991904601466),
				0.5350931841916245);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-0.9861959107101634),
				0.9283171601067858);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(48.76809424952464),
				0.5345721572727593);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-18.311456520830976),
				0.9525062390771225);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(2.064709272940976),
				0.9525062390771225);

		m_kinematicsSimpler.createTrajectory(myPath, 0.9525062390771225, 2.6067592595296496);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}
	
	private static void TestCases9()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-20.32764301488901),
				0.15250686766661847);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(21.99733665249151),
				4.080024724815174);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(10.55355031489063),
				4.080024724815174);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(49.858778818138155),
				1.8226935752054945);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-47.62435994674573),
				3.6765475776912986);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(19.43488968199528),
				2.5066914438499);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(19.940657016345178),
				2.825519975231435);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(26.77541910177322),
				3.9343101113625583);
		

		m_kinematicsSimpler.createTrajectory(myPath, 4.080024724815174, 1.680675105230724);

		
		checkTrajectoryPath(myPath, kinematicsTester1);

	}
	private static void TestCases10()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		Path myPath = m_kinematicsSimpler.new Path();
		KinematicsTester kinematicsTester1 = new KinematicsTester();

		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-43.77381014821977));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-45.977336529920905));
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-0.5933286514858669),
				0.3755657126204104);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-0.6156752310157565),
				0.206984809559303);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(5.839746189755365),
				3.6765475776912986);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(-49.84878486860723),
				2.5066914438499);
		m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(6.004674385512021),
				2.825519975231435);
	
		m_kinematicsSimpler.createTrajectory(myPath, 0.3929169767434745, 1.9171932478971456);

		printTrajectory(myPath);
		checkTrajectoryPath(myPath, kinematicsTester1);

	}

	private static void createRandomTestCases()
			throws InvalidDimentionException, InvalidVelocityException, InvalidNextVelocityFromLastAcceleration,
			InvalidAccelerationException, InvalidFinalPosition, InvalidTrajectoryLogic {
		for (int i = 0; i < 10000; i++) {
			Random random = new Random();
			Path myPath = m_kinematicsSimpler.new Path();
			KinematicsTester kinematicsTester1 = new KinematicsTester();
			int numberOfSetpoints = random.nextInt(10) + 1;
			int maxVelocityInt = random.nextInt(6);

			double maxVelocity = Math.abs(random.nextDouble() + maxVelocityInt);
			double maxAcceleration = random.nextDouble() + random.nextInt(3);
			if (maxVelocity < 0.1) {
				maxVelocity += 1;
			}
			if (maxAcceleration < 0.01) {
				maxAcceleration = 0.02;
			}
			 Point previousPoint = m_kinematicsSimpler.new Point(0);
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
				if(Math.abs(setpoint - previousPoint.getm_X()) < maxAcceleration*m_kinematicsSimpler.getTrajectoryPointInterval()) {
					if(setpoint > 0.0) {
						setpoint += maxAcceleration*Kinematics.getTrajectoryPointInterval();
					}else {
						setpoint -= maxAcceleration*Kinematics.getTrajectoryPointInterval();
					}
				}
				m_kinematicsSimpler.addPointToPath(myPath, m_kinematicsSimpler.new Point(setpoint * directionConstant),
						customMaxVelocity);
				
				
				previousPoint = m_kinematicsSimpler.new Point(setpoint*directionConstant);
				
			}
			
			m_kinematicsSimpler.createTrajectory(myPath, maxVelocity, maxAcceleration);

			printTrajectory(myPath);
			System.out.println("Number: " + (i + 1));
			checkTrajectoryPath(myPath, kinematicsTester1);
		}
	}

	private static void checkTrajectoryPath(Path Key, KinematicsTester kinematicsTester)
			throws InvalidVelocityException, InvalidNextVelocityFromLastAcceleration, InvalidAccelerationException,
			InvalidFinalPosition, InvalidTrajectoryLogic {

		checkAcceleration(Key, kinematicsTester);

		checkVelocity(Key, kinematicsTester);

		checkFinalPosition(Key, kinematicsTester);

		checkTrajectoryLogic(Key, kinematicsTester);
	}

	private static void checkVelocity(Path Key, KinematicsTester kinematicsTester)
			throws InvalidVelocityException, InvalidNextVelocityFromLastAcceleration {
		InvalidVelocityException invalidVelocityException;
		String errMessage;
		for (int i = 0; i < Key.getTrajectoryVector().size(); i++) {
			TrajectoryPoint currentPoint = Key.getTrajectoryVector().get(i);
			if (currentPoint.m_currentVelocity > Key.getMaxVelocity()
					|| currentPoint.m_currentVelocity < -Key.getMaxVelocity()) {
				errMessage = "The Current Velocity at time: " + currentPoint.m_timestamp
						+ " is above the maximum velocity!";
				invalidVelocityException = kinematicsTester.new InvalidVelocityException(errMessage);
				throw invalidVelocityException;
			}
		}
		checkNextVelocityFromLastAcceleration(Key, kinematicsTester);
	}

	private static void checkAcceleration(Path Key, KinematicsTester kinematicsTester)
			throws InvalidAccelerationException {
		InvalidAccelerationException invalidAccelerationException;
		String errMessage;
		for (int i = 0; i < Key.getTrajectoryVector().size(); i++) {
			TrajectoryPoint currentPoint = Key.getTrajectoryVector().get(i);
			if (currentPoint.m_acceleration > (Key.getMaxAcceleration() * Kinematics.getTrajectoryPointInterval()
					+ 0.1)
					|| currentPoint.m_acceleration < (-Key.getMaxAcceleration()
							* Kinematics.getTrajectoryPointInterval() - 0.1)) {
				errMessage = "The Current Acceleration at time: " + currentPoint.m_timestamp
						+ " is above the maximum acceleration!";
				invalidAccelerationException = kinematicsTester.new InvalidAccelerationException(errMessage);
				throw invalidAccelerationException;
			}
		}
	}

	private static void checkFinalPosition(Path Key, KinematicsTester kinematicsTester) throws InvalidFinalPosition {
		InvalidFinalPosition invalidFinalPosition;
		String errMesage;
		try {
			if (Key.getTrajectoryVector().get(Key.getTrajectoryVector().size() - 1).m_position
					- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() > 0.1
					|| Key.getTrajectoryVector().get(Key.getTrajectoryVector().size() - 1).m_position
							- Key.getSetpointVector().get(Key.getSetpointVector().size() - 1).getm_X() < -0.1) {
				errMesage = "The final position of the trajectory path does not match the final position of the setpoint path!";
				invalidFinalPosition = kinematicsTester.new InvalidFinalPosition(errMesage);
				throw invalidFinalPosition;
			}
		} catch (ArrayIndexOutOfBoundsException q) {
			errMesage = "There are no points in this path!";
			invalidFinalPosition = kinematicsTester.new InvalidFinalPosition(errMesage);
			throw invalidFinalPosition;
		}
	}

	private static void checkNextVelocityFromLastAcceleration(Path Key, KinematicsTester kinematicsTester)
			throws InvalidNextVelocityFromLastAcceleration {
		InvalidNextVelocityFromLastAcceleration invalidNextVelocityFromLastAcceleration;
		String errMessage;
		for (int i = 0; i < Key.getTrajectoryVector().size(); i++) {
			TrajectoryPoint currentPoint = Key.getTrajectoryVector().get(i);
			TrajectoryPoint lastPoint;
			try {
				lastPoint = Key.getTrajectoryVector().get(i - 1);
			} catch (ArrayIndexOutOfBoundsException a) {
				continue;
			}
			if (lastPoint.m_currentVelocity + lastPoint.m_acceleration - currentPoint.m_currentVelocity > 0.5
					|| lastPoint.m_currentVelocity + lastPoint.m_acceleration - currentPoint.m_currentVelocity < -0.5) {
				errMessage = "The Current Acceleration added to the Current Velocity at time: " + lastPoint.m_timestamp
						+ " Do not equal the next Velocity at time: " + currentPoint.m_timestamp;
				invalidNextVelocityFromLastAcceleration = kinematicsTester.new InvalidNextVelocityFromLastAcceleration(
						errMessage);
				throw invalidNextVelocityFromLastAcceleration;
			}
		}
	}

	private static void checkTrajectoryLogic(Path Key, KinematicsTester kinematicsTester)
			throws InvalidTrajectoryLogic {
		InvalidTrajectoryLogic invalidTrajectoryLogic;
		String errMessage;
		int currentTrajectoryPointIndex = 0;
		for (int i = 0; i < Key.getSetpointVector().size(); i++) {
			Point setpoint = Key.getSetpointVector().get(i);
			currentTrajectoryPointIndex++;
			Point nextSetpoint;
			Point lastSetpoint = m_kinematicsSimpler.new Point(0, 0);
			boolean traveledInAPositiveDirection;
			boolean willTravelInAPositiveDirection;
			TrajectoryPoint trajectoryPoint = Key.getTrajectoryVector().get(currentTrajectoryPointIndex);
			TrajectoryPoint previousTrajectoryPoint = Key.getTrajectoryVector().get(currentTrajectoryPointIndex);
			TrajectoryPoint nextTrajectoryPoint = m_kinematicsSimpler.new TrajectoryPoint(0, 0, 0, 0);

			int initialCurrentTrajectoryPointIndex = currentTrajectoryPointIndex;

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

			double timestampOfSetpoint = 0.0;
			for (int b = 0; b < Key.getSetpointVector().size(); b++) {
				timestampOfSetpoint += Key.getSetpointVector().get(b).getEndDeltaTime();
				if (b < i) {
					continue;
				} else if (setpoint.getm_X() == Key.getSetpointVector().get(b).getm_X()) {
					break;
				}
			}
			
			for (int a = 0; a < Key.getTrajectoryVector().size(); a++) {
				if (Math.abs(Key.getTrajectoryVector().get(a).m_timestamp
						- timestampOfSetpoint) < (Kinematics.getTrajectoryPointInterval() / 2.0)) {
					trajectoryPoint = Key.getTrajectoryVector().get(a);
					try {
						nextTrajectoryPoint = Key.getTrajectoryVector().get(a + 1);
					} catch (ArrayIndexOutOfBoundsException c) {
						nextTrajectoryPoint = m_kinematicsSimpler.new TrajectoryPoint(0, 0, 0, 0);
					}
					break;
				}
				previousTrajectoryPoint = Key.getTrajectoryVector().get(a);
			}
			if (currentTrajectoryPointIndex == (Key.getTrajectoryVector().size())) {
				currentTrajectoryPointIndex = initialCurrentTrajectoryPointIndex;
				continue;
			}
			if (trajectoryPoint.m_timestamp == 0.0) {
				continue;
			}
			
			if ((traveledInAPositiveDirection && willTravelInAPositiveDirection)
					|| (!traveledInAPositiveDirection && !willTravelInAPositiveDirection)) {
				if (trajectoryPoint.m_currentVelocity < 0.01 && trajectoryPoint.m_currentVelocity > -0.01) {
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is not changing however the velocity is 0!";
					invalidTrajectoryLogic = kinematicsTester.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
			} else {
				if ((trajectoryPoint.m_currentVelocity > 0.0 && previousTrajectoryPoint.m_currentVelocity > 0.0
						&& nextTrajectoryPoint.m_currentVelocity > 0.0)
						|| (trajectoryPoint.m_currentVelocity < 0.0 && previousTrajectoryPoint.m_currentVelocity < 0.0
								&& nextTrajectoryPoint.m_currentVelocity < 0.0)) {
					
					errMessage = "The point at time: " + trajectoryPoint.m_timestamp
							+ " is a point in which the direction is changing however the velocity is not 0!";
					invalidTrajectoryLogic = kinematicsTester.new InvalidTrajectoryLogic(errMessage);
					throw invalidTrajectoryLogic;
				}
			}
		}
	}

	public class InvalidVelocityException extends Exception {
		public InvalidVelocityException(String errMessage) {
			super(errMessage);
		}

		private static final long serialVersionUID = 1L;

	}

	public class InvalidAccelerationException extends Exception {
		public InvalidAccelerationException(String errMessage) {
			super(errMessage);
		}

		private static final long serialVersionUID = 1L;

	}

	public class InvalidNextVelocityFromLastAcceleration extends Exception {
		public InvalidNextVelocityFromLastAcceleration(String errMessage) {
			super(errMessage);
		}

		private static final long serialVersionUID = 1L;
	}

	public class InvalidFinalPosition extends Exception {
		public InvalidFinalPosition(String errMessage) {
			super(errMessage);
		}

		private static final long serialVersionUID = 1L;

	}

	public class InvalidTrajectoryLogic extends Exception {
		public InvalidTrajectoryLogic(String errMessage) {
			super(errMessage);
		}

		private static final long serialVersionUID = 1L;

	}

	private static void printTrajectory(Path Key) {
		System.out.println("Trajectory Point: [vel, acc, pos, time]");
		for (int i = 0; i < Key.getTrajectoryVector().size(); i++) {
			if(Key.getTrajectoryVector().get(i).m_timestamp < 150 || Key.getTrajectoryVector().get(i).m_timestamp > 275) {
				continue;
			}
			System.out.println("Trajectory Point: [" + Key.getTrajectoryVector().get(i).m_currentVelocity + ", "
					+ Key.getTrajectoryVector().get(i).m_acceleration + ", "
					+ Key.getTrajectoryVector().get(i).m_position + ", " + Key.getTrajectoryVector().get(i).m_timestamp
					+ "]");

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

		System.out.println("The max limits are: MaxV " + Key.getMaxVelocity() + " MaxA " + Key.getMaxAcceleration());
		System.out.println("");
	}

}
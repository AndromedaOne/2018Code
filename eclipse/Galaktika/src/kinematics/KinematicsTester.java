package kinematics;

import java.util.Random;
import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Trace;
import Utilities.TracePair;
import kinematics.Kinematics;
import kinematics.CheckerExceptions.*;
import kinematics.CheckerExceptions.InvalidAccelerationException;

public class KinematicsTester {
	CheckerExceptions m_checkerExceptions = new CheckerExceptions();
	
	public static void main(String[] args) {
		try {
			UnitCases.createSingleSetpointCases();
			
			UnitCases.createChangingDirectionCases();
			
			UnitCases.createSameDirectionCases();
			
			UnitCases.createSingleSetpointCasesWithCustomMaxV();
			
			UnitCases.createChangingDirectionCasesFirstPointWithCustomMaxV();
			
			UnitCases.createChangingDirectionCasesSecondPointWithCustomMaxV();
			
			UnitCases.createSameDirectionCasesFirstPointWithCustomMaxV();
			
			UnitCases.createSameDirectionCasesSecondPointWithCustomMaxV();

			UnitCases.realTest();

			UnitCases.createRandomTestCases();
			
		} catch (InvalidAccelerationException | InvalidVelocityException | InvalidNextVelocityFromLastAcceleration
				| InvalidFinalPosition | InvalidTrajectoryLogic | NaNException
				| InvalidJerkException | InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}



package kinematics;

import java.util.Random;
import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;


import kinematics.Kinematics;
import kinematics.KinematicsException;

public class KinematicsTester {
	
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

			UnitCases.realTests();

			UnitCases.createRandomTestCases();
			
		} catch (KinematicsException | InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

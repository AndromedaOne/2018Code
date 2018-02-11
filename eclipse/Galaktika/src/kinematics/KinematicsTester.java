

package kinematics;

import java.util.Random;

import java.util.Vector;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import kinematics.Kinematics;
import kinematics.KinematicsException;

public class KinematicsTester {
	
	public static void main(String[] args) {
		try {
			System.out.println("DriveTrain.getMaxVelocity(): " + DriveTrain.getMaxVelocity());
			System.out.println("DriveTrain.getMaxAcceleration(): " + DriveTrain.getMaxAcceleration());
			System.out.println("DriveTrain.getMaxJerk(): " + DriveTrain.getMaxJerk());
			UnitCases.createSingleSetpointCases();
			
			UnitCases.createRandomTestCases();
			
			UnitCases.createChangingDirectionCases();
			
			UnitCases.createSameDirectionCases();
			
			UnitCases.createSingleSetpointCasesWithCustomMaxV();
			
			UnitCases.createChangingDirectionCasesFirstPointWithCustomMaxV();
			
			UnitCases.createChangingDirectionCasesSecondPointWithCustomMaxV();
			
			UnitCases.createSameDirectionCasesFirstPointWithCustomMaxV();
			
			UnitCases.createSameDirectionCasesSecondPointWithCustomMaxV();

			UnitCases.realTests();

			
			
		} catch (KinematicsException | InvalidDimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

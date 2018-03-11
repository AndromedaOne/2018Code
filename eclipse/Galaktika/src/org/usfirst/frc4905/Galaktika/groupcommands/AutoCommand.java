package org.usfirst.frc4905.Galaktika.groupcommands;

import java.sql.PreparedStatement;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.AutoTimedArmsClose;
import org.usfirst.frc4905.Galaktika.commands.AutonomousCommand;
import org.usfirst.frc4905.Galaktika.commands.Delay;
import org.usfirst.frc4905.Galaktika.commands.ExtendIntakeInAuto;
import org.usfirst.frc4905.Galaktika.commands.GyroPIDTurnDeltaAngle;
import org.usfirst.frc4905.Galaktika.commands.JawsOpenClose;
import org.usfirst.frc4905.Galaktika.commands.MoveElevator;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingEncoderPID;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingFrontUltrasonic;
import org.usfirst.frc4905.Galaktika.commands.RetractExtendArms;
import org.usfirst.frc4905.Galaktika.commands.SetIntakeShouldBeUpCommand;
import org.usfirst.frc4905.Galaktika.commands.SetShouldJawsBeOpenStateCommand;
import org.usfirst.frc4905.Galaktika.commands.ShootCubeInAuto;
import org.usfirst.frc4905.Galaktika.commands.TurnToCompassHeading;
import org.usfirst.frc4905.Galaktika.commands.ResetElevatorEncoder;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MoveToWall;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public abstract class AutoCommand extends CommandGroup {
	private boolean m_preparedToStart = false;
	@Deprecated // ("Use the new Ultrasonic command")
	public class MoveToWall extends Command {

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
	}

	public enum MatchType {
		QUALIFIERS,
		PLAYOFFS
	}

	public AutoCommand() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}

	protected static final double FORWARD_DISTANCE_TO_SWITCH = 120;
	protected static final double LATERAL_DISTANCE_TO_SWITCH = 28.72;
	//TODO: Get the following number from CAD
	protected static final double FORWARD_DISTANCE_TO_SWITCH_PLATES = 100;
	protected static final double FORWARD_DISTANCE_TO_SCALE = 304.25;
	protected static final double LATERAL_DISTANCE_TO_SCALE = 15.08;
	protected static final double FORWARD_DISTANCE_TO_MIDDLE = 212;
	protected static final double LATERAL_DISTANCE_TO_SCALE_PLATES = 199.99;
	protected static final double FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE = 228.16;
	protected static final double LATERAL_DISTANCE_BETWEEN_PATHS = 236.6;
	protected static final double FORWARD_DISTANCE_TO_AUTO_LINE = 122;
	protected static final double LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE = 41.15;
	protected static final double LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE = 36.85;
	protected static final double LATERAL_DISTANCE_TO_FIRST_CUBE = 50.75;
	protected static final double LATERAL_DISTANCE_TO_EXCHANGE_L = 90;
	protected static final double LATERAL_DISTANCE_TO_EXCHANGE_R = 154;
	protected static final double LATERAL_DISTANCE_TO_EXCHANGE_M = 31.13;
	private static final double BUMPER_WIDTH = 1.5;
	protected static final double CLEARANCE_TO_TURN = 25;

	protected void driveForward(double forwardDistanceInches) {
		double distanceScaleFactor = Robot.getAutonomousDistanceScaleFactor();
		debug("top of driveForward, Forward Distance = " +
				forwardDistanceInches +
				"Scale Factor = " + distanceScaleFactor +
				"Actual Distance = " + distanceScaleFactor * forwardDistanceInches);
		addSequential(new MoveUsingEncoderPID(forwardDistanceInches * distanceScaleFactor));
	}

	protected void delay(double delaySeconds) {
		addSequential(new Delay(delaySeconds));

	}

	protected void debug(String information) {
		char location = Robot.safelyGetInitialRobotLocation();
		//System.out.println("In AutoCommand.java (" + getClass().getSimpleName() + ")! ");
		//System.out.flush();
		// System.out.println("In AutoCommand.java Field Setup: Robot = " +
		//		location + "! " +
		//		information);
		// System.out.flush();
	}

	public void start() {
		debug("top of start");
		if(!m_preparedToStart) {
			prepareToStart();
			m_preparedToStart = true;
		}
		super.start();
		debug("bottom of start");
	}

	protected void prepareToStart() {
		debug("top of AutoCommand prepareToStart");
		debug("bottom of AutoCommand prepareToStart");
	}

	protected void turnRight() {
		addSequential(new GyroPIDTurnDeltaAngle(90));

	}

	protected void turnLeft() {
		addSequential(new GyroPIDTurnDeltaAngle(-90));

	}

	protected void turnAround() {
		addSequential(new GyroPIDTurnDeltaAngle(180));
	}

	protected void driveForwardToWall(double estimatedDistance) {
		double distanceScaleFactor = Robot.getAutonomousDistanceScaleFactor();
		if (DriveTrain.ULTRASONIC_RANGE_IN_INCHES < estimatedDistance) {
			addSequential(new MoveUsingEncoderPID(estimatedDistance*distanceScaleFactor - DriveTrain.ULTRASONIC_RANGE_IN_INCHES));
		}
		if (DriveTrain.ULTRASONIC_RANGE_IN_INCHES > 0) {
			addSequential(new MoveUsingFrontUltrasonic(BUMPER_WIDTH));
		}
	}

	protected void moveElevatorToSwitchHeight() {
		addParallel(new MoveElevator(MoveElevator.SWITCH_HEIGHT));
	}

	protected void moveElevatorToSwitchHeightSequential() {
		addSequential(new MoveElevator(MoveElevator.SWITCH_HEIGHT));
	}

	protected void moveElevatorToScaleHeight() {
		addParallel(new MoveElevator(MoveElevator.HIGH_SCALE_HEIGHT + 300));
	}

	protected void moveElevatorToLowScaleHeight() {
		addParallel(new MoveElevator(MoveElevator.LOW_SCALE_HEIGHT));
	}

	protected void moveElevatorToExchangeHeight() {
		addParallel(new MoveElevator(MoveElevator.EXCHANGE_HEIGHT));
	}

	protected void moveElevatorToGroundHeight(){
		addParallel(new MoveElevator(MoveElevator.GROUND_LEVEL));
	}

	protected void resetElevatorInAuto() {
		addSequential(new ResetElevatorEncoder());
	}

	protected void parallelJawsOpenClose(){
		addParallel(new JawsOpenClose());
	}

	protected void parallelRetractExtendArms(){
		addParallel(new RetractExtendArms());
	}

	protected void driveBackward(double backwardDistanceInches) {
		driveForward(- backwardDistanceInches);
	}


	protected void closeArmsInAuto(double timeout) {
		addParallel(new AutoTimedArmsClose(timeout));
	}

	protected void extendIntakeAuto() {
		addParallel(new ExtendIntakeInAuto());
	}

	protected void resetEncoderInElevator() {
		addSequential(new ResetElevatorEncoder());
	}

	protected void turnToCompassHeading(double compassHeading) {
		addSequential(new TurnToCompassHeading(compassHeading));
	}

	protected void openJaws(){
		addSequential(new SetShouldJawsBeOpenStateCommand(true));
		// ENABLE IF BACK-UP IS TOO SOON AFTER OPEN
		// delay(0.5);
	}

	protected void closeJaws(boolean waitForClose){
		addSequential(new SetShouldJawsBeOpenStateCommand(false));
		if (waitForClose) {
			delay(0.5);
		}
	}

	protected void raiseIntake(){
		addSequential(new SetIntakeShouldBeUpCommand(true));
	}

	protected void lowerIntake(){
		addSequential(new SetIntakeShouldBeUpCommand(false));
	}

	protected void turnDeltaAngle(double angle){
		addSequential(new GyroPIDTurnDeltaAngle(angle));

	}

	public void crossAutoLine(char robotPos) {
		debug("top of crossAutoLine");
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		debug("bottom of crossAutoLine");
	}
	
	public void shootCube(double timeout){
		addSequential(new ShootCubeInAuto(timeout));
	}

}

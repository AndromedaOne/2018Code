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
import org.usfirst.frc4905.Galaktika.subsystems.Elevator;

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

	public enum AutoType {
		QUALIFIERS,
		PLAYOFFS,
		DOUBLE_CUBE_CROSS,
		DOUBLE_CUBE_FIELD,
		TRIPLE_CUBE,
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

	private static final double ROBOT_LENGTH = 39;
	protected static final double FORWARD_DISTANCE_TO_SWITCH = 144;
	protected static final double LATERAL_DISTANCE_TO_SWITCH = 12;
	//TODO: Get the following number from CAD
	protected static final double FORWARD_DISTANCE_TO_SWITCH_PLATES = 100;
	public static final double FORWARD_DISTANCE_TO_SCALE = 304.25;
	public static final double FORWARD_DISTANCE_TO_SCALE_FORTY_FIVE_DEGREE = 299.34 - ROBOT_LENGTH;
	protected static final double LATERAL_DISTANCE_FROM_SCALE = 20.00;
	protected static final double LATERAL_DISTANCE_TO_SCALE_PLATES = 188;
	protected static final double FORWARD_DISTANCE_BETWEEN_SWITCH_AND_SCALE = 218;
	protected static final double FORWARD_DISTANCE_TO_SCALE_PLATE_FROM_CUBE = 128.35;
	protected static final double FORWARD_DISTANCE_TO_CUBES = 9;
	protected static final double FORWARD_DISTANCE_TO_SIDE_OF_SCALE = 49.99;
	protected static final double LATERAL_DISTANCE_BETWEEN_PATHS = 236.6;
	protected static final double FORWARD_DISTANCE_TO_AUTO_LINE = 122;
	protected static final double LATERAL_DISTANCE_TO_LEFT_SWITCH_PLATE = 41.15;
	protected static final double LATERAL_DISTANCE_TO_RIGHT_SWITCH_PLATE = 36.85;
	protected static final double LATERAL_DISTANCE_TO_FIRST_CUBE = 50.75;
	protected static final double LATERAL_DISTANCE_TO_SECOND_CUBE = 28;
	protected static final double DIAGONAL_DISTANCE_TO_FIRST_CUBE = LATERAL_DISTANCE_TO_FIRST_CUBE * Math.sqrt(2);
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
		System.out.println("In AutoCommand.java (" + getClass().getSimpleName() + ")! ");
		System.out.flush();
		 System.out.println("In AutoCommand.java Field Setup: Robot = " +
				location + "! " +
				information);
		 System.out.flush();
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
		debug("Attempting to turn right.");
		addSequential(new GyroPIDTurnDeltaAngle(90));

	}

	protected void turnLeft() {
		debug("Attempting to turn left.");
		addSequential(new GyroPIDTurnDeltaAngle(-90));

	}

	protected void turnAround() {
		debug("Attempting to turn around.");
		addSequential(new GyroPIDTurnDeltaAngle(180));
	}

	protected void driveForwardToWall(double estimatedDistance) {
		debug("Attempting to drive forward to wall, estimatedDistance = " + estimatedDistance);
		double distanceScaleFactor = Robot.getAutonomousDistanceScaleFactor();
		if (DriveTrain.ULTRASONIC_RANGE_IN_INCHES < estimatedDistance) {
			addSequential(new MoveUsingEncoderPID(estimatedDistance*distanceScaleFactor - DriveTrain.ULTRASONIC_RANGE_IN_INCHES));
		}
		if (DriveTrain.ULTRASONIC_RANGE_IN_INCHES > 0) {
			addSequential(new MoveUsingFrontUltrasonic(BUMPER_WIDTH));
		}
	}

	protected void moveElevatorToSwitchHeight() {
		debug("Attempting to raise elevator to switch height");
		addParallel(new MoveElevator(Elevator.SWITCH_HEIGHT));
	}

	protected void moveElevatorToSwitchHeightSequential() {
		debug("Attempting to raise elevator to switch height sequentially");
		addSequential(new MoveElevator(Elevator.SWITCH_HEIGHT));
	}

	protected void moveElevatorToScaleHeight() {
		debug("Attempting to raise elevator to high scale height");
		addParallel(new MoveElevator(Elevator.HIGH_SCALE_HEIGHT));

	}

	protected void moveElevatorToLowScaleHeight() {
		debug("Attempting to raise elevator to low scale height");
		addParallel(new MoveElevator(Elevator.LOW_SCALE_HEIGHT));
	}

	protected void moveElevatorToExchangeHeight() {
		debug("Attempting to raise elevator to exchange height");
		addParallel(new MoveElevator(Elevator.EXCHANGE_HEIGHT));
	}

	protected void moveElevatorToGroundHeight(){
		debug("Attempting to lower elevator to ground height");
		addParallel(new MoveElevator(Elevator.GROUND_LEVEL));
	}

	protected void resetElevatorInAuto() {
		debug("Resetting elevator encoder in Auto.");
		addSequential(new ResetElevatorEncoder());
	}

	protected void parallelJawsOpenClose(){
		debug("Attempting to set jaws to correct state.");
		addParallel(new JawsOpenClose());
	}

	protected void parallelRetractExtendArms(){
		debug("Attempting to set arms to correct state.");
		addParallel(new RetractExtendArms());
	}

	protected void driveBackward(double backwardDistanceInches) {
		debug("Attempting to drive backward, distance = " + backwardDistanceInches);
		driveForward(- backwardDistanceInches);
	}


	protected void closeArmsInAuto(double timeout) {
		debug("Attempting to close arms.");
		addParallel(new AutoTimedArmsClose(timeout));
	}

	protected void extendIntakeAuto() {
		debug("Attempting to extend intake.");
		addParallel(new ExtendIntakeInAuto());
	}

	protected void resetEncoderInElevator() {
		debug("Attempting to reset elevator encoder.");
		addSequential(new ResetElevatorEncoder());
	}

	protected void turnToCompassHeading(double compassHeading) {
		debug("Attempting to turn to compass heading, compassHeading = " + compassHeading);
		addSequential(new TurnToCompassHeading(compassHeading));
	}

	protected void openJaws(){
		debug("Attempting to open jaws.");
		addSequential(new SetShouldJawsBeOpenStateCommand(true));
		// ENABLE IF BACK-UP IS TOO SOON AFTER OPEN
		// delay(0.5);
	}

	protected void closeJaws(boolean waitForClose){
		debug("Attempting to close jaws.");
		addSequential(new SetShouldJawsBeOpenStateCommand(false));
		if (waitForClose) {
			delay(0.5);
		}
	}

	protected void raiseIntake(){
		debug("Attempting to raise intake.");
		addSequential(new SetIntakeShouldBeUpCommand(true));
	}

	protected void lowerIntake(){
		debug("Attempting to lower intake.");
		addSequential(new SetIntakeShouldBeUpCommand(false));
	}

	protected void turnDeltaAngle(double angle){
		debug("Attempting to turn delta angle, angle = " + angle);
		addSequential(new GyroPIDTurnDeltaAngle(angle));

	}

	public void crossAutoLine(char robotPos) {
		debug("top of crossAutoLine");
		driveForward(FORWARD_DISTANCE_TO_AUTO_LINE);
		debug("bottom of crossAutoLine");
	}

	public void shootCube(double timeout){
		debug("Attempting to shoot cube");
		addSequential(new ShootCubeInAuto(timeout));
	}

	public void shootCubeParallel(double timeout){
		debug("Attempting to shoot cube");
		addParallel(new ShootCubeInAuto(timeout));
	}


}

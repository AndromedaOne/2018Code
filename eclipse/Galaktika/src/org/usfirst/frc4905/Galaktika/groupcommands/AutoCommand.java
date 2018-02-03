package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.Delay;
import org.usfirst.frc4905.Galaktika.commands.GyroPIDTurnDeltaAngle;
import org.usfirst.frc4905.Galaktika.commands.MoveUsingEncoderPID;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCommand.MoveToWall;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public abstract class AutoCommand extends CommandGroup {
    @Deprecated // ("Use the new Ultrasonic command")
    public class MoveToWall extends Command {

           @Override
           protected boolean isFinished() {
               // TODO Auto-generated method stub
               return false;
           }
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

    protected static final double FORWARD_DISTANCE_TO_SWITCH = 148.04;
    protected static final double LATERAL_DISTANCE_TO_SWITCH = 28.72;
    protected static final double FORWARD_DISTANCE_TO_SCALE = 304.25;
    protected static final double LATERAL_DISTANCE_TO_SCALE = 15.08;
    protected static final double FORWARD_DISTANCE_TO_MIDDLE = 212;
    protected static final double LATERAL_DISTANCE_BETWEEN_PATHS = 236.6;
    protected static final double FORWARD_DISTANCE_TO_AUTO_LINE = 122;
    protected static final double LATERAL_DISTANCE_TO_RIGHT = 116;
    protected static final double LATERAL_DISTANCE_TO_LEFT = 120.3;

    protected void turnRight() {
            debug("top of turnRight");
        addSequential(new GyroPIDTurnDeltaAngle(90));
    }

    protected void turnLeft() {
        debug("top of turnLeft");
        addSequential(new GyroPIDTurnDeltaAngle(-90));
    }

    protected void turnAround() {
        debug("top of turnAround");
        addSequential(new GyroPIDTurnDeltaAngle(180));
    }

    protected void driveForward(double forwardDistanceInches) {
        double distanceScaleFactor = Robot.getAutonomousDistanceScaleFactor();
        debug("top of driveForward, Forward Distance = " +
	        forwardDistanceInches +
	        "Scale Factor = " + distanceScaleFactor +
	        "Actual Distance = " + distanceScaleFactor * forwardDistanceInches);
		addSequential(new MoveUsingEncoderPID(forwardDistanceInches * distanceScaleFactor));
    }

	protected void driveForwardToWall(double forwardDistanceInches) {
        driveForward(forwardDistanceInches);
        addSequential(new MoveToWall());
    }

    protected void delay(double delaySeconds) {
        addSequential(new Delay(delaySeconds));

    }

    protected void loadPowerCubeOntoSwitch() {
        // TODO Auto-generated method stub

    }

    protected void loadPowerCubeOntoScale() {
        // TODO Auto-generated method stub

    }

    public void loadPowerCubeIntoExchange() {
        // TODO Auto-generated method stub

    }

    protected void driveBackward(double backwardDistanceInches) {
        driveForward(- backwardDistanceInches);
    }

    protected void debug(String information) {
    		System.out.println("In AutoCommand.java Field Setup: Robot = " +
    				Robot.getInitialRobotLocation() +
    				" Scale = " + Robot.getScalePlatePosition() +
    				" Switch = " + Robot.getSwitchPlatePosition() +
    				information);

	}
}

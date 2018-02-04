package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.commands.ConditionalGyroPIDTurnDeltaAngle;
import org.usfirst.frc4905.Galaktika.commands.ConditionalMoveUsingEncoderPID;
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

	protected boolean m_needsInitialization = true;

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

    protected void turnRight(char scaleSide, char switchSide) {
            debug("top of turnRight");
            addSequential(new ConditionalGyroPIDTurnDeltaAngle(90, scaleSide, switchSide));
    }

    protected void turnLeft(char scaleSide, char switchSide) {
        debug("top of turnLeft");
        addSequential(new ConditionalGyroPIDTurnDeltaAngle(-90, scaleSide, switchSide));
    }

    protected void turnAround(char scaleSide, char switchSide) {
        debug("top of turnAround");
        addSequential(new ConditionalGyroPIDTurnDeltaAngle(180, scaleSide, switchSide));
    }

    protected void driveForward(double forwardDistanceInches, char scaleSide, char switchSide) {
        double distanceScaleFactor = Robot.getAutonomousDistanceScaleFactor();
        debug("top of driveForward, Forward Distance = " +
	        forwardDistanceInches +
	        "Scale Factor = " + distanceScaleFactor +
	        "Actual Distance = " + distanceScaleFactor * forwardDistanceInches);
		addSequential(new ConditionalMoveUsingEncoderPID(forwardDistanceInches * distanceScaleFactor, scaleSide, switchSide));
    }

	protected void driveForwardToWall(double forwardDistanceInches, char scaleSide, char switchSide) {
        driveForward(forwardDistanceInches, scaleSide, switchSide);
        addSequential(new MoveToWall());
    }

    protected void delay(double delaySeconds) {
        addSequential(new Delay(delaySeconds));

    }

    protected void loadPowerCubeOntoSwitch(char scaleSide, char switchSide) {
        // TODO Auto-generated method stub

    }

    protected void loadPowerCubeOntoScale(char scaleSide, char switchSide) {
        // TODO Auto-generated method stub

    }

    public void loadPowerCubeIntoExchange(char scaleSide, char switchSide) {
        // TODO Auto-generated method stub

    }

    protected void driveBackward(double backwardDistanceInches, char scaleSide, char switchSide) {
        driveForward(- backwardDistanceInches, scaleSide, switchSide);
    }

    protected void debug(String information) {
    		 char location = Robot.safelyGetInitialRobotLocation();
    		 System.out.println("In AutoCommand.java ! ");
    		 System.out.flush();
    		 System.out.println("In AutoCommand.java Field Setup: Robot = " +
     				location + "! " +
     				information);
    		 System.out.flush();
	}
}

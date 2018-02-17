package org.usfirst.frc4905.Galaktika.commands;

import org.usfirst.frc4905.Galaktika.Robot;
import org.usfirst.frc4905.Galaktika.RobotMap;

import Utilities.Signal;
import Utilities.SpikeDetector;
import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveForwardUntilScaleBump extends Command {
	private static final double BUMPTHRESHHOLD = 1.0;
	private static final long MSTORUNATEND = 0;
	protected long afterSecondTime = 0;
	protected SpikeDetector spikeDetector;
	protected enum State {
		START,
		FIRSTWHEEL,
		BETWEEN,
		SECONDWHEEL,
		AFTERSECOND,
		FINISHED
	}
	protected State state;
	

    public MoveForwardUntilScaleBump() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	spikeDetector = new SpikeDetector(5, 4.0, 1);
    	state = State.START;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	

    	double pitch = (double) -RobotMap.navX.getAHRS().getRoll();
		
		spikeDetector.update(pitch);
		
		Trace.getInstance().addTrace(true, "ErisTrace",
				
				new TracePair("Pitch", (double) RobotMap.navX.getAHRS().getPitch()),
				new TracePair("Roll", (double) RobotMap.navX.getAHRS().getRoll()),
				new TracePair("Yaw", (double) RobotMap.navX.getAHRS().getYaw()));
		
		if (state.equals(State.START) && spikeDetector.min > BUMPTHRESHHOLD) {
			state = State.FIRSTWHEEL;
		}else if (state.equals(State.FIRSTWHEEL) && spikeDetector.max < BUMPTHRESHHOLD) {
			state = State.BETWEEN;
		}else if (state.equals(State.BETWEEN) && spikeDetector.min > BUMPTHRESHHOLD) {
			state = State.SECONDWHEEL;
		}else if (state.equals(State.SECONDWHEEL) && spikeDetector.max < BUMPTHRESHHOLD) {
			state = State.FINISHED;
			afterSecondTime = System.currentTimeMillis();
		}else if (state.equals(State.AFTERSECOND) && (System.currentTimeMillis() - afterSecondTime) > MSTORUNATEND) {
			state = State.FINISHED;
		}
		
		SmartDashboard.putString("MFUSBstate", state.toString());
		SmartDashboard.putNumber("SpikeDetectorMin", spikeDetector.min);
		SmartDashboard.putNumber("SpikeDetectorMax", spikeDetector.max);
		
		Robot.driveTrain.gyroCorrectMove(-0.7, 0, 0.6);
		

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state.equals(State.FINISHED);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

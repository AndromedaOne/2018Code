// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4905.Galaktika;

import org.usfirst.frc4905.Galaktika.commands.AutonomousCommand;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCombinedLeftRight;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoCrossTheLine;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoMiddleLoadSwitch;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoMiddleRightLoadSwitch;
import org.usfirst.frc4905.Galaktika.subsystems.DriveTrain;
import org.usfirst.frc4905.Galaktika.subsystems.Elevator;
import org.usfirst.frc4905.Galaktika.subsystems.Intake;
import org.usfirst.frc4905.Galaktika.subsystems.Jaws;
import org.usfirst.frc4905.Galaktika.subsystems.Pneumatics;
import org.usfirst.frc4905.Galaktika.subsystems.Ramps;
import org.usfirst.frc4905.Galaktika.subsystems.Retractor;

import Utilities.PDPLogging;
import Utilities.Tracing.Trace;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import kinematics.Kinematics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in
 * the project.
 */
public class Robot extends TimedRobot {
    private class Pair<T1, T2> {
    		private final T1 m_first;
		private final T2 m_second;

		public T1 getFirst() {
			return m_first;
		}

		public T2 getSecond() {
			return m_second;
		}

		Pair(T1 first, T2 second) {
    			m_first = first;
    			m_second = second;
    		}
	}

	public static final int SWITCH = 0;
    public static final int SCALE = 1;
    private static double delaySeconds = 5.0;
	private static double distanceScaleFactor = 1.0;


  	Command autonomousCommand;
    private static char initialRobotLocation = '?';
    private static char scalePlatePosition = '?';
    private static char switchPlatePosition = '?';
    private SendableChooser<Pair<Command, Character>> chooser = new SendableChooser<>();
	private SendableChooser<Character> scalePlateChooser = new SendableChooser<>();
	private SendableChooser<Character> switchPlateChooser = new SendableChooser<>();
	private SendableChooser<Character> rampSafeties = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Intake intake;
    public static Ramps ramps;
    public static Elevator elevator;
    public static Pneumatics pneumatics;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static PowerDistributionPanel pdp;
    public static Retractor retractor;
    public static Jaws jaws;

    public static Kinematics kinematics;
    public static boolean AutonomousMode = false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    		debug("top of robotInit" );
    		RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        intake = new Intake();
        ramps = new Ramps();
        elevator = new Elevator();
        pneumatics = new Pneumatics();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        if (DriverStation.getInstance().isFMSAttached()) {
        		Trace.getInstance().matchStarted();
        }

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 300);
        camera.setFPS(20);

        retractor = new Retractor();
        jaws = new Jaws();
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        kinematics = new Kinematics();
        oi = new OI();
        pdp = new PowerDistributionPanel();


        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        chooser.addDefault("Do Nothing", new Pair<>(new AutonomousCommand(), '*'));

        chooser.addObject(
        		"1.Drive Fwd Auto",
        		new Pair<>(
        				new AutoCrossTheLine(
        						false),
        				'L')
        		);

        chooser.addObject(
        		"1.D Drive Fwd Auto",
        		new Pair<>(
        				new AutoCrossTheLine(
        						true),
        				'L')
        		);

        chooser.addObject(
        		"2.Switch Auto - In-Field Dual Option",
        		new Pair<>(
        				new AutoMiddleRightLoadSwitch(
        						false),
        				'M')
        		);

        chooser.addObject(
        		"2.D Switch Auto - In-Field Dual Option",
        		new Pair<>(
        				new AutoMiddleRightLoadSwitch(
        						true),
        				'M')
        		);
        chooser.addObject(
        		"3.Scale Auto - Right(3) Dual Option",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        						false,
        						AutoCombinedLeftRight.AutoType.QUALIFIERS,
        						AutoCombinedLeftRight.PathOption.IGNORE_SWITCH),
        				'R'
        				)
        		);
        chooser.addObject(
        		"3.D Scale Auto - Right(3) Dual Option",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        						true,
        						AutoCombinedLeftRight.AutoType.QUALIFIERS,
        						AutoCombinedLeftRight.PathOption.IGNORE_SWITCH),
        				'R'
        				)
        		);

        chooser.addObject(
        		"4.Scale Auto - Left(1) Dual Option",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        						false,
        						AutoCombinedLeftRight.AutoType.QUALIFIERS,
        						AutoCombinedLeftRight.PathOption.IGNORE_SWITCH),
        				'L'
        				)
        		);

        chooser.addObject(
        		"4.D Scale Auto - Left(1) Dual Option",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        						true,
        						AutoCombinedLeftRight.AutoType.QUALIFIERS,
        						AutoCombinedLeftRight.PathOption.IGNORE_SWITCH),
        				'L'
        				)
        		);

        chooser.addObject(
        		"5.SCALE/Switch/Drive Fwd - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.PLAYOFFS),
        				'R')
        		);

        chooser.addObject(
        		"5.D SCALE/Switch/Drive Fwd - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.PLAYOFFS),
        				'R')
        		);

        chooser.addObject(
        		"6.SCALE/Switch/Drive Fwd - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.PLAYOFFS),
        				'L')
        		);

        chooser.addObject(
        		"6.D SCALE/Switch/Drive Fwd - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        						true,
        						AutoCombinedLeftRight.AutoType.PLAYOFFS),
        				'L')
        		);

        chooser.addObject(
        		"7.SWITCH/Scale/Drive Fwd - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS,
        				AutoCombinedLeftRight.PathOption.IGNORE_FAR_SCALE),
        				'R'
        				)
        		);

        chooser.addObject(
        		"7.D SWITCH/Scale/Drive Fwd - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS,
        				AutoCombinedLeftRight.PathOption.IGNORE_FAR_SCALE),
        				'R'
        				)
        		);

        chooser.addObject(
        		"8.SWITCH/Scale/Drive Fwd - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS,
        				AutoCombinedLeftRight.PathOption.IGNORE_FAR_SCALE),
        				'L'
        				)
        		);

        chooser.addObject(
        		"8.D SWITCH/Scale/Drive Fwd - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS,
        				AutoCombinedLeftRight.PathOption.IGNORE_FAR_SCALE),
        				'L'
        				)
        		);

        chooser.addObject(
        		"9.SWITCH/Scale/Far Scale - Right(3)",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS),
        				'R'
        				)
        		);

        chooser.addObject(
        		"9.D SWITCH/Scale/Far Scale - Right(3)",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS),
        				'R')
        		);

        chooser.addObject(
        		"10.SWITCH/Scale/Far Scale - Left(1)",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS),
        				'L')
        		);

        chooser.addObject(
        		"10.D SWITCH/Scale/Far Scale - Left(1)",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.QUALIFIERS),
        				'L')
        		);

        chooser.addObject(
        		"11. Scale/Switch 2 Cube - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.DOUBLE_CUBE_FIELD),
        				'R')
        		);

        chooser.addObject(
        		"11. D Scale/Switch 2 Cube - Right(3) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.DOUBLE_CUBE_FIELD),
        				'R')
        		);

        chooser.addObject(
        		"12. Scale/Switch 2 Cube - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				false,
        				AutoCombinedLeftRight.AutoType.DOUBLE_CUBE_FIELD),
        				'L')
        		);

        chooser.addObject(
        		"12. D Scale/Switch 2 Cube - Left(1) Field",
        		new Pair<>(
        				new AutoCombinedLeftRight(
        				true,
        				AutoCombinedLeftRight.AutoType.DOUBLE_CUBE_FIELD),
        				'L')
        		);


        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Autonomous Delay", delaySeconds);
        SmartDashboard.putNumber("Autonomous Scale Factor", distanceScaleFactor);
        scalePlateChooser.setName("Title for Scale Plate Chooser");
        scalePlateChooser.addDefault("Scale Left", 'L');
        scalePlateChooser.addObject("Scale Right", 'R');
        switchPlateChooser.addDefault("Switch Left", 'L');
        switchPlateChooser.addObject("Switch Right", 'R');

        SmartDashboard.putData("Scale Plate Position (Testing Only)", scalePlateChooser);
        SmartDashboard.putData("Switch Plate Position (Testing Only)", switchPlateChooser);
        debug("bottom of robotInit" );


        rampSafeties.addDefault("RAMP SAFETIES ON", 'y');
        rampSafeties.addObject("RAMP SAFETEIS OFF", 'n');


        SmartDashboard.putData("RAMP SAFETIES", rampSafeties);


        Robot.ramps.lockRampsIn();
    }



    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
    	AutonomousMode = false;
    	Trace.getInstance().flushTraceFiles();
    	Robot.ramps.lockRampsIn();
    	Robot.elevator.disableEncoderPID();
    	Robot.elevator.stopElevator();
    	Robot.driveTrain.reset();
    	Robot.intake.stopIntake();

    }

    @Override
    public void disabledPeriodic() {

        Scheduler.getInstance().run();
      
    }

    @Override
    public void robotPeriodic() {
    	PDPLogging.pdpLog();
    }
    
    @Override
    public void autonomousInit() {
    	AutonomousMode = true;
    	debug("top of autonomousInit");
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	System.out.println("GAMEDATA!!!!!!!!" + gameData.charAt(SWITCH));
    	//if (gameData.length() > SCALE) {
    	scalePlatePosition =	gameData.charAt(SCALE);
    	switchPlatePosition = gameData.charAt(SWITCH);
    	/*} else {
	    		//here for testing purposes. Could break things.
	    		scalePlatePosition = scalePlateChooser.getSelected();
	    		switchPlatePosition = switchPlateChooser.getSelected();
	    	}*/
    	delaySeconds = SmartDashboard.getNumber("Autonomous Delay", 5.0);
    	distanceScaleFactor = SmartDashboard.getNumber("Autonomous Scale Factor", 1.0);
    	Pair<Command, Character> pair = chooser.getSelected();
    	autonomousCommand = pair.getFirst();
    	initialRobotLocation = pair.getSecond();
    	debug("middle of autonomousInit - robot location = " +
    			initialRobotLocation +
    			" Scale = " + Robot.getScalePlatePosition() +
    			" Switch = " + Robot.getSwitchPlatePosition() +
    			" delaySeconds = " + delaySeconds +
    			" distanceScaleFactor = " + distanceScaleFactor +
    			" autonomousCommand " + autonomousCommand.getClass().getSimpleName());
    	// schedule the autonomous command (example)
    	if (autonomousCommand != null) {
    		autonomousCommand.start();
    	}
    	debug("bottom of autonomousInit");
    }

	private static void debug(String information) {
		System.out.println("in Robot.java, " + information);
		System.out.flush();
	}

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();

    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();

        if(rampSafeties.getSelected() == 'y'){
        	Robot.ramps.setSafetyBooleanStatus(true);
        }
        else{
        	Robot.ramps.setSafetyBooleanStatus(false);
        }


        Robot.ramps.lockRampsIn();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }


    @Override
    public void testInit() {
    	driveTrain.resetRobotState();
    }

    public static char getScalePlatePosition() {
        if (scalePlatePosition == '?') {
    			throw new RuntimeException("Scale plate position acquired too early.");
    		}
	    	return scalePlatePosition;
    }

    public static char getSwitchPlatePosition() {
        if (switchPlatePosition == '?') {
    			throw new RuntimeException("Switch plate position acquired too early.");
    		}
	    	return switchPlatePosition;
    }

    public static char getInitialRobotLocation() {
        if (initialRobotLocation == '?') {
    			throw new RuntimeException("Initial robot location acquired too early.");
    		}
	    	return initialRobotLocation;
    }

    public static char safelyGetInitialRobotLocation() {
	    	return initialRobotLocation;
    }

	public static double getAutonomousDelay() {
		return delaySeconds;
	}

	public static double getAutonomousDistanceScaleFactor() {
		return distanceScaleFactor;
	}
}

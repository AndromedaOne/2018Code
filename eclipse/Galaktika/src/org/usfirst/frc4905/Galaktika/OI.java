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

import org.usfirst.frc4905.Galaktika.commands.*;
import org.usfirst.frc4905.Galaktika.commands.AutonomousCommand;
import org.usfirst.frc4905.Galaktika.commands.TeleOpDrive;
import org.usfirst.frc4905.Galaktika.groupcommands.AutoRampPlacement;

import Utilities.ControllerButtons.ButtonsEnumerated;
import Utilities.LEDColor;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = ButtonsEnumerated.<button>.getJoystickButton(stick);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick driveController = new Joystick(0);
    public JoystickButton runIntakeButton;
    public Joystick subsystemController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton encoderPIDButton;
    JoystickButton TurnToEast;
    JoystickButton TurnToWest;
    JoystickButton TurnToNorth;
    JoystickButton TurnToSouth;
    public OI() {
    	//Button Presets for compass headings
    	 TurnToWest = new JoystickButton(driveController, ButtonsEnumerated.XBUTTON.getValue());
    	 TurnToEast = new JoystickButton(driveController, ButtonsEnumerated.BBUTTON.getValue());
    	 TurnToSouth = new JoystickButton(driveController, ButtonsEnumerated.ABUTTON.getValue());
    	 TurnToNorth = new JoystickButton(driveController, ButtonsEnumerated.YBUTTON.getValue());
    	 TurnToWest.whenPressed(new TurnToCompassHeading(270));
    	 TurnToSouth.whenPressed(new TurnToCompassHeading(180));
    	 TurnToNorth.whenPressed(new TurnToCompassHeading(0));
    	 TurnToEast.whenPressed(new TurnToCompassHeading(90));
    	 
    	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        subsystemController = new Joystick(1);
        
        runIntakeButton = new JoystickButton(subsystemController, 1);
        runIntakeButton.whileHeld(new RunIntakeIn());        
        runIntakeButton.whileHeld(new RunIntakeIn());
        driveController = new Joystick(0);
        


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("TeleOpDrive", new TeleOpDrive());
        SmartDashboard.putData("RunIntakeIn", new RunIntakeIn());
        SmartDashboard.putData("Move Using Ultrasonic", new MoveUsingFrontUltrasonic(10));

     

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        subsystemController = new Joystick(1);
        driveController = new Joystick(0);

        SmartDashboard.putData("JawsOpenClose", new JawsOpenClose());
        SmartDashboard.putData("RetratExtendArms", new RetractExtendArms());
        SmartDashboard.putData("LEDRed", new LEDCommand(LEDColor.RED));
        SmartDashboard.putData("LEDBlue", new LEDCommand(LEDColor.BLUE));
        SmartDashboard.putData("LEDGreenishYellow", new LEDCommand(LEDColor.GREEN));
        SmartDashboard.putData("GyroPIDTurnDeltaAngle", new GyroPIDTurnDeltaAngle());  
        SmartDashboard.putData("TurnToCompassHeading", new TurnToCompassHeading(90));
        SmartDashboard.putData("AutoRampPlacement", new AutoRampPlacement());
       
        SmartDashboard.putData("MoveUsingEncoderPID", new MoveUsingEncoderPID(100000));
        
        
    }
 
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriveController() {
        return driveController;
    }

    public Joystick getSubsystemController() {
        return subsystemController;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}


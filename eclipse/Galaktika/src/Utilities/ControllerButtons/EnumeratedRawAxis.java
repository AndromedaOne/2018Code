package Utilities.ControllerButtons;

import edu.wpi.first.wpilibj.Joystick;

public enum EnumeratedRawAxis {
	LEFTSTICKHORIZONTAL(0),
	LEFTSTICKVERTICAL(1),
	LEFTTRIGGER(2),
	RIGHTTRIGGER(3),
	RIGHTSTICKHORIZONTAL(4),
	RIGHTSTICKVERTICAL(5);

	private int m_rawAxisValue;

	private EnumeratedRawAxis(int value) {
		m_rawAxisValue = value;
	}

	public double getRawAxis(Joystick gamepad) {
		return gamepad.getRawAxis(m_rawAxisValue);
	}

	public static double getLeftStickHorizontal (Joystick gamepad) {
		return 0.0;//LEFTSTICKHORIZONTAL.getRawAxis(gamepad);
	}

	public static double getLeftStickVertical (Joystick gamepad) {
		return 0.0;//LEFTSTICKVERTICAL.getRawAxis(gamepad);
	}

	public static boolean getLeftTriggerButton(Joystick gamepad) {
		return false;//LEFTTRIGGER.getRawAxis(gamepad)>0.5;
	}

	public static double getLeftTriggerValue(Joystick gamepad) {
		return 0.0;//LEFTTRIGGER.getRawAxis(gamepad);
	}

	public static boolean getRightTriggerButton(Joystick gamepad) {
		return false;//RIGHTTRIGGER.getRawAxis(gamepad)>0.5;
	}

	public static double getRightTriggerValue(Joystick gamepad) {
		return 0.0;//RIGHTTRIGGER.getRawAxis(gamepad);
	}

	public static double getRightStickHorizontal (Joystick gamepad) {
		return 0.0;//RIGHTSTICKHORIZONTAL.getRawAxis(gamepad);
	}

	public static double getRightStickVertical (Joystick gamepad) {
		return 0.0;//RIGHTSTICKVERTICAL.getRawAxis(gamepad);
	}
}

package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedMiddleLoadSwitch extends FiveSecondDelay {

	AutoDelayedMiddleLoadSwitch() {
		super(new AutoMiddleLoadSwitch());
	}

}

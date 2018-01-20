package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedLoadSwitch extends FiveSecondDelay {

	AutoDelayedLoadSwitch() {
		super(new AutoLoadSwitch());
	}

}

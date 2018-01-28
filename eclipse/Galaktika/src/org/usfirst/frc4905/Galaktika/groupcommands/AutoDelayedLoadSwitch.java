package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedLoadSwitch extends FiveSecondDelay {

	public AutoDelayedLoadSwitch() {
		super(new AutoLoadSwitch());
	}

}

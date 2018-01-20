package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedLoadScale extends FiveSecondDelay {

	AutoDelayedLoadScale() {
		super(new AutoLoadScale());
	}

}

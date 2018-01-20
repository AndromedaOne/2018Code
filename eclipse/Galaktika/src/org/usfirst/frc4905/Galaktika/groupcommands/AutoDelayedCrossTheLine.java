package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedCrossTheLine extends FiveSecondDelay {

	AutoDelayedCrossTheLine() {
		super(new AutoCrossTheLine());
	}

}

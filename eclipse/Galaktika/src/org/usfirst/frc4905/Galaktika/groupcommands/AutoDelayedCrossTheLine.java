package org.usfirst.frc4905.Galaktika.groupcommands;

public class AutoDelayedCrossTheLine extends FiveSecondDelay {

	public AutoDelayedCrossTheLine() {
		super(new AutoCrossTheLine());
	}

}

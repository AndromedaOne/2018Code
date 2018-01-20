package org.usfirst.frc4905.Galaktika.groupcommands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FiveSecondDelay extends CommandGroup {
	private CommandGroup m_nextStep;

	FiveSecondDelay(CommandGroup nextStep){
		m_nextStep = nextStep;
	}
	
	public void start() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}
		m_nextStep.start();
	}
}

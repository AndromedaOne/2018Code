package Utilities;

import org.usfirst.frc4905.Galaktika.Robot;

public class PDPLogging implements Runnable {

	private Thread thread;
	private String threadName = "PDP Logging";
	@Override
	public void run() {
		Trace pdpTrace = Trace.getInstance();
		pdpTrace.addTrace("PDPTrace", 
				new TracePair("voltage", Robot.pdp.getVoltage()),
				new TracePair("TotalCurrent", Robot.pdp.getTotalCurrent()),
				new TracePair("TotalPower", Robot.pdp.getTotalPower()),
				new TracePair("TotalEnergy", Robot.pdp.getTotalEnergy()));
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			System.out.println("PDP logging thread interrupted!");
			e.printStackTrace();
			return;
		}
		
	}
}

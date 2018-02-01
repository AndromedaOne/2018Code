package Utilities;

import org.usfirst.frc4905.Galaktika.Robot;

public class PDPLogging {
	public static void pdpLog() {
		Trace pdpTrace = Trace.getInstance();
		pdpTrace.addTrace("PDPTrace", 
				new TracePair("voltage", Robot.pdp.getVoltage()),
				new TracePair("TotalCurrent", Robot.pdp.getTotalCurrent()),
				new TracePair("TotalPower", Robot.pdp.getTotalPower()),
				new TracePair("TotalEnergy", Robot.pdp.getTotalEnergy()));
		
	}

}

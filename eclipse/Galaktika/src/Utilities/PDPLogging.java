package Utilities;

import org.usfirst.frc4905.Galaktika.Robot;

import Utilities.Tracing.Trace;
import Utilities.Tracing.TracePair;

public class PDPLogging {
	public static void pdpLog() {
		Trace pdpTrace = Trace.getInstance();
		pdpTrace.addTrace(true, "PDPTrace", 
				new TracePair("Voltage", Robot.pdp.getVoltage()),
				new TracePair("TotalCurrent", Robot.pdp.getTotalCurrent()),
				new TracePair("TotalPower", Robot.pdp.getTotalPower()),
				new TracePair("TotalEnergy", Robot.pdp.getTotalEnergy()),
				new TracePair("Channel 0", Robot.pdp.getCurrent(0)),
				new TracePair("Channel 1", Robot.pdp.getCurrent(1)),
				new TracePair("Channel 2", Robot.pdp.getCurrent(2)),
				new TracePair("Channel 3", Robot.pdp.getCurrent(3)),
				new TracePair("Channel 4", Robot.pdp.getCurrent(4)),
				new TracePair("Channel 5", Robot.pdp.getCurrent(5)),
				new TracePair("Channel 6", Robot.pdp.getCurrent(6)),
				new TracePair("Channel 7", Robot.pdp.getCurrent(7)),
				new TracePair("Channel 8", Robot.pdp.getCurrent(8)),
				new TracePair("Channel 9", Robot.pdp.getCurrent(9)),
				new TracePair("Channel 10", Robot.pdp.getCurrent(10)),
				new TracePair("Channel 11", Robot.pdp.getCurrent(11)),
				new TracePair("Channel 12", Robot.pdp.getCurrent(12)),
				new TracePair("Channel 13", Robot.pdp.getCurrent(13)),
				new TracePair("Channel 14", Robot.pdp.getCurrent(14)),
				new TracePair("Channel 15", Robot.pdp.getCurrent(15)));
	}

}
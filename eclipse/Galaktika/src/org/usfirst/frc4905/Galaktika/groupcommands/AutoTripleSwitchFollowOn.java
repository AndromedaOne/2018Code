package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoTripleSwitchFollowOn extends AutoDoubleSwitchFollowOn {

	@Override
	public void addCommands(AutoCombinedLeftRight autoCommand) {
		super.addCommands(autoCommand);
		char robotPos = Robot.getInitialRobotLocation();
        char switchPlatePos = Robot.getSwitchPlatePosition();
        //Only for when robotPos is 'L' or 'R'

        if (robotPos == 'L' && switchPlatePos == 'L') {
        		autoCommand.driveBackward(AutoCommand.CLEARANCE_TO_TURN);
	    		autoCommand.turnLeft();
	    		autoCommand.driveForward(15.4);
	    		autoCommand.turnRight();
	    		autoCommand.driveForward(13);
	    		autoCommand.closeJaws(true);
	    		dropCubeOntoSwitch(autoCommand);
            System.out.println("Done :D");
        } else if (robotPos == 'R' && switchPlatePos == 'R') {
        		autoCommand.driveBackward(AutoCommand.CLEARANCE_TO_TURN);
	    		autoCommand.turnRight();
	    		autoCommand.driveForward(15.4);
	    		autoCommand.turnLeft();
	    		autoCommand.driveForward(13);
	    		autoCommand.closeJaws(true);
	    		dropCubeOntoSwitch(autoCommand);
    			System.out.println("Done :D");
        }
	}
}

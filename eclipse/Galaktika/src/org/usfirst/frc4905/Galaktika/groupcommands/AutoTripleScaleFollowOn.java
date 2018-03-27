package org.usfirst.frc4905.Galaktika.groupcommands;

import org.usfirst.frc4905.Galaktika.Robot;

public class AutoTripleScaleFollowOn extends AutoDoubleScaleFollowOn {

	@Override
	public void addCommands(AutoCombinedLeftRight autoCommand) {
		super.addCommands(autoCommand);
		char robotPos = Robot.getInitialRobotLocation();
        char scalePlatePos = Robot.getScalePlatePosition();
        //Only for when robotPos is 'L' or 'R'
        if (robotPos == 'L' && scalePlatePos == 'L') {
        		autoCommand.driveBackward(53);
        		autoCommand.moveElevatorToGroundHeight();
        		autoCommand.turnDeltaAngle(175);
        		autoCommand.driveForward(53);
        		autoCommand.closeJaws(true);
        		autoCommand.moveElevatorToSwitchHeightSequential();
        		autoCommand.openJaws();
            System.out.println("Done :D");
        } else if (robotPos == 'R' && scalePlatePos == 'R') {
        		autoCommand.driveBackward(53);
        		autoCommand.moveElevatorToGroundHeight();
        		autoCommand.turnDeltaAngle(-175);
        		autoCommand.driveForward(53);
        		autoCommand.closeJaws(true);
        		autoCommand.moveElevatorToSwitchHeightSequential();
        		autoCommand.openJaws();
            System.out.println("Done :D");
        }
	}
}

package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.Forward;
import frc.robot.commands.GripClose;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PassAutoLine extends CommandGroup {

	public PassAutoLine() {
		addParallel(new GripClose());
		
		addSequential(new Forward(3.6), 4);
	}
}

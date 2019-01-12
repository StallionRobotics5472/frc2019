package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.RaiseLiftLow;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestCommand extends CommandGroup {

	public TestCommand() {
		addSequential(new RaiseLiftLow());
	}
}

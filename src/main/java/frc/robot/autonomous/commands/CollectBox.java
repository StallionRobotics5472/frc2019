package frc.robot.autonomous.commands;

import frc.robot.commands.BoxPipeline;
import frc.robot.commands.EnableVision;
import frc.robot.commands.GripClose;
import frc.robot.commands.GripOpen;
import frc.robot.commands.IntakePull;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectBox extends CommandGroup
{

	public CollectBox() {
		addSequential(new EnableVision());
		addSequential(new BoxPipeline());
		addParallel(new GripOpen());
		LookForBox lfb = new LookForBox();
		addSequential(lfb);
		addSequential(new ApproachBox());
		addSequential(new IntakePull());
		addSequential(new GripClose());
		addSequential(new Delay(0.5));
		addSequential(new IntakeStop());
		addSequential(new RaiseLiftLow());
	}
}

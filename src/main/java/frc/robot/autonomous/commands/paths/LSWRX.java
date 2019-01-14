package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.ApproachBox;
import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftLow;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.BoxPipeline;
import frc.robot.commands.EnableVision;
import frc.robot.commands.GripClose;
import frc.robot.commands.IntakePull;
import frc.robot.commands.IntakePushAuto;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LSWRX extends CommandGroup {

	public LSWRX() {
		addParallel(new GripClose());
		
		addSequential(new Forward(5.29), 6);
		addSequential(new Turn(-90), 2);
		addParallel(new RaiseLiftLow(), 2);
		addSequential(new Forward(4.2), 6);
		addSequential(new Turn(-170), 2);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		addSequential(new Forward(-0.7), 1);

		addSequential(new LiftZero(), 3);
		
		addParallel(new IntakePull());
		addSequential(new EnableVision());
		addSequential(new BoxPipeline());
		addSequential(new ApproachBox(), 3);
		addSequential(new IntakeStop());
		addSequential(new GripClose());
		
		addSequential(new Forward(-1.00), 2);
		addSequential(new RaiseLiftLow(), 2);
		addSequential(new Forward(0.6));
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
	}
}

package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.ApproachBox;
import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftHalf;
import frc.robot.autonomous.commands.RaiseLiftHigh;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.BoxPipeline;
import frc.robot.commands.EnableVision;
import frc.robot.commands.GripClose;
import frc.robot.commands.HighGear;
import frc.robot.commands.IntakePull;
import frc.robot.commands.IntakePushAuto;
import frc.robot.commands.IntakeStop;
import frc.robot.commands.LowGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RSCXL extends CommandGroup {

	public RSCXL() {
		addParallel(new GripClose());

		addParallel(new RaiseLiftHalf(), 3);
		addSequential(new Forward(5.50), 4);
		addSequential(new Turn(90), 2);
		
		addSequential(new Forward(4.9), 4); // From 4.2
		addSequential(new Turn(0), 2);
		
		addParallel(new LowGear());
		addSequential(new RaiseLiftHigh(), 4);
		addSequential(new Forward(1.05), 1); // From 0.8
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		
		addSequential(new Forward(-1.0), 1);
		addParallel(new LiftZero(), 2);
		addSequential(new Turn(200), 3);
		addSequential(new HighGear());
		
		addParallel(new IntakePull());
		addSequential(new EnableVision());
		addSequential(new BoxPipeline());
		addSequential(new ApproachBox(), 3);
		addSequential(new IntakeStop());
		addSequential(new GripClose());
		addSequential(new Forward(-0.600), 1);
	}
}

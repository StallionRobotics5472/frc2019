package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.EnableBrake;
import frc.robot.autonomous.commands.EnableCoast;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftHigh;
import frc.robot.autonomous.commands.RaiseLiftMinimum;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.GripClose;
import frc.robot.commands.GripOpen;
import frc.robot.commands.HighGear;
import frc.robot.commands.IntakePull;
import frc.robot.commands.IntakePullSlow;
import frc.robot.commands.IntakePushAuto;
import frc.robot.commands.IntakeStop;
import frc.robot.commands.LowGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RSCXR extends CommandGroup {

	public RSCXR() {
		addParallel(new GripClose());
		addParallel(new HighGear());
		
		addParallel(new EnableBrake());
		addParallel(new RaiseLiftMinimum(), 4);
		addSequential(new Forward(5.30), 3);
		addSequential(new EnableCoast());
		addSequential(new Turn(28), 2);
		
		addSequential(new RaiseLiftHigh(), 4);
		addSequential(new LowGear());
		addSequential(new Forward(2.0, 28), 1);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		
		addSequential(new Forward(-0.400), 0.4);
		addSequential(new LiftZero(), 2);
		
		addSequential(new Turn(160), 1.5);
		addSequential(new HighGear());
		
		addParallel(new IntakePull());
//		addSequential(new EnableVision());
//		addSequential(new BoxPipeline());
		addParallel(new GripOpen());
		addSequential(new Forward(1.30), 1.5);
//		addSequential(new ApproachBox(), 1.5);
//		addSequential(new IntakeStop());
		addSequential(new GripClose());
		addSequential(new IntakePullSlow());
		addSequential(new Forward(-0.600), 0.5);
		addSequential(new Turn(0));
		addParallel(new LowGear());
		addSequential(new RaiseLiftHigh(), 4.00);
		addSequential(new Forward(0.8), 1);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new Forward(-0.5), 1);
		addSequential(new LiftZero());
		addSequential(new IntakeStop());
	}
}

package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftHigh;
import frc.robot.autonomous.commands.RaiseLiftMinimum;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.GripClose;
import frc.robot.commands.HighGear;
import frc.robot.commands.IntakePushSlow;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtremeLeft extends CommandGroup{
	
	public ExtremeLeft() {
		addParallel(new HighGear());
		addParallel(new GripClose());
		
		addParallel(new RaiseLiftMinimum());
		addSequential(new Forward(7.00));
		addSequential(new Turn(-90), 1.0);
		addSequential(new Forward(-0.8), 1);
		addSequential(new RaiseLiftHigh());
//		addSequential()
		addSequential(new Forward(0.8), 1.0);
		addSequential(new IntakePushSlow());
		addSequential(new Delay(1.0));
		addSequential(new IntakeStop());
		addSequential(new Forward(-0.8), 1);
		addSequential(new LiftZero(), 2);
		addSequential(new Forward(0.2));
		addSequential(new Turn(0));
	}
	
}

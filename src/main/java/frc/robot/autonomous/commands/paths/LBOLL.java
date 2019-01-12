package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftLow;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.IntakePushAuto;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LBOLL extends CommandGroup{
	
	public LBOLL() {
		addSequential(new LSCXL());
		
		addParallel(new RaiseLiftLow(), 3);
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		addSequential(new Turn(-135), 1);
		addSequential(new Forward(0.9), 2);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		addSequential(new Forward(-0.6), 1);
		addSequential(new LiftZero());
	}
	
}

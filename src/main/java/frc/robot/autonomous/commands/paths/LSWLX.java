package frc.robot.autonomous.commands.paths;

import frc.robot.Constants;
import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.RaiseLiftLow;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.GripClose;
import frc.robot.commands.IntakePushSlow;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LSWLX extends CommandGroup {

	public LSWLX() {
		addParallel(new GripClose());
		
		addSequential(new Forward(3.6), 4);
		addParallel(new RaiseLiftLow(), 2);
		addSequential(new Turn(-85), 2);
		addSequential(new Forward(Constants.V_CONSTANT), 0.5);
		addSequential(new IntakePushSlow());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		addSequential(new Forward(-1.00), 0.5);
	}
}

package frc.robot.autonomous.commands.paths;

import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.Forward;
import frc.robot.autonomous.commands.LiftZero;
import frc.robot.autonomous.commands.RaiseLiftLow;
import frc.robot.autonomous.commands.Turn;
import frc.robot.commands.GripClose;
import frc.robot.commands.GripOpen;
import frc.robot.commands.IntakePull;
import frc.robot.commands.IntakePullAuto;
import frc.robot.commands.IntakePushAuto;
import frc.robot.commands.IntakeStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CSWRX extends CommandGroup{
	
	public CSWRX() {
		addParallel(new GripClose());
		addSequential(new Forward(0.66), 0.5);
		addSequential(new Turn(-45), 0.8);
		addParallel(new RaiseLiftLow());
		addSequential(new Forward(1.45), 1.0);
		addSequential(new Turn(0), 1);
		addSequential(new Forward(0.8), 1);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(0.5));
		addSequential(new IntakeStop());
		
		addSequential(new Forward(-0.5), 0.6);
		addSequential(new Turn(-45), 0.8);
		addParallel(new LiftZero(), 1);
		addSequential(new Forward(-1.75), 2);
		addSequential(new Turn(0), 1);
		
		
		addParallel(new GripOpen());
		addParallel(new IntakePull());
		addSequential(new Forward(1.2), 1.0);
		addSequential(new GripClose());
		addSequential(new IntakePullAuto());
		addSequential(new Forward(-1.2), 1.0);
		addSequential(new IntakeStop());
		
		addParallel(new RaiseLiftLow(), 2);
		addSequential(new Turn(-45), 1);
		addSequential(new Forward(1.65), 1.0);
		addSequential(new Turn(0), 1);
		addSequential(new Forward(0.8), 0.8);
		addSequential(new IntakePushAuto());
		addSequential(new Delay(0.5));
		addSequential(new IntakeStop());
		addSequential(new Forward(-1.2), 0.6);
		
		
		
		
		
		
//		addParallel(new GripClose());
//		addSequential(new Forward(0.66), 1);
//		addSequential(new Turn(-45), 1);
//		addParallel(new RaiseLiftLow());
//		addSequential(new Forward(1.45), 1.5);
//		addSequential(new Turn(0), 1);
//		addSequential(new Forward(1.0), 1);
//		addSequential(new IntakePushAuto());
//		addSequential(new Delay(1.0));
//		addSequential(new IntakeStop());
//		
//		addSequential(new Forward(-1.0), 1.0);
//		addSequential(new Turn(-50), 1);
//		addParallel(new LiftZero(), 1);
//		addSequential(new Forward(-1.45), 2);
//		addSequential(new Turn(0), 1);
//		
//		
//		addParallel(new GripOpen());
//		addParallel(new IntakePull());
//		addSequential(new Forward(1.2), 1.5);
//		addSequential(new GripClose());
//		addSequential(new IntakePullAuto());
//		addSequential(new Forward(-1.2), 1.5);
//		addSequential(new IntakeStop());
//		
//		addParallel(new RaiseLiftLow(), 2);
//		addSequential(new Turn(-45), 2);
//		addSequential(new Forward(1.45), 1.5);
//		addSequential(new Turn(0), 2);
//		addSequential(new Forward(0.8), 1);
//		addSequential(new IntakePushAuto());
//		addSequential(new Delay(1.0));
//		addSequential(new IntakeStop());
//		addSequential(new Forward(-0.8));
		
		
		
		
		
		
		
		
//		addParallel(new GripClose());
//		addSequential(new Forward(1.50 - Constants.ROBOT_LENGTH), 1);
//		addSequential(new Turn(-45), 1);
//		addParallel(new RaiseLiftLow());
//		addSequential(new Forward(1.45), 1.5); // From 1.65
//		addSequential(new Turn(-10), 1);
//		addSequential(new Forward(0.7), 1); // From 0.5
//		addSequential(new IntakePushAuto());
//		addSequential(new Delay(1.0));
//		addSequential(new IntakeStop());
//		addSequential(new Forward(-1.3), 1.5);
//		
//		addSequential(new Turn(35), 2);
//		addSequential(new LiftZero(), 2);
//		addParallel(new GripOpen());
//		addParallel(new IntakePull());
//		addSequential(new EnableVision());
//		addSequential(new BoxPipeline());
//		addSequential(new ApproachBox(), 1.5);
//		addSequential(new GripClose());
//		addSequential(new IntakePullAuto());
//		addSequential(new Forward(-0.8), 1);
//		addSequential(new IntakeStop());
//
//		addParallel(new RaiseLiftLow(), 3);
//		addSequential(new Turn(0), 1);
//		addSequential(new Forward(0.8), 1);
//		addSequential(new IntakePushAuto());
//		addSequential(new Delay(1));
//		addSequential(new IntakeStop());
//		addSequential(new Forward(-0.8), 1);
	}
	
}

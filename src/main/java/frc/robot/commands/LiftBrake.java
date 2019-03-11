package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftBrake extends Command{
	
	private boolean finished;
	private LiftSubsystem lift;
	
	public LiftBrake() {
		lift = Robot.lift;
	}
	
	@Override
	public void execute() {
		lift.enableBrake();
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}

}

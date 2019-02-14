package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftBrake extends Command{
	
	private boolean finished;
	private LiftPIDSubsystem lift;
	
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

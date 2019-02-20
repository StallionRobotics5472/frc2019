package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftZero extends Command{
	
	private LiftPIDSubsystem lift;
	
	public LiftZero() {
		requires(Robot.lift);
		lift = Robot.lift;
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute(){
	}
	
	@Override
	protected boolean isFinished() {
		return lift.onTarget();
	}
}

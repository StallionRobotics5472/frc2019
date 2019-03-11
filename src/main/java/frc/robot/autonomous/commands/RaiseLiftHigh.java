package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLiftHigh extends Command{
	
	private LiftSubsystem lift;
	
	
	@Override
	public void initialize() {
		requires(Robot.lift);
		lift = Robot.lift;
		lift.enable();
		lift.setSetpoint(31000);
	}
	
	@Override
	public void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return lift.onTarget();
	}
}

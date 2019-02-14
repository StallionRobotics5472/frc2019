package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLiftMinimum extends Command{
	
	private LiftPIDSubsystem lift;

	@Override
	public void initialize() {
		lift = Robot.lift;
		lift.setSetpoint(4000);
	}
	
	@Override
	public void execute() {
	}
	
	
	@Override
	protected boolean isFinished() {
		return lift.getPosition() > 3000;
	}
	
}

package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLiftHigh extends Command{
	
	private LiftPIDSubsystem lift;
	
	
	@Override
	public void initialize() {
		lift = Robot.lift;
		lift.enableClosedLoop();
		lift.setSetpoint(31000);
	}
	
	@Override
	public void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return lift.getPosition() > 31000;
	}
}

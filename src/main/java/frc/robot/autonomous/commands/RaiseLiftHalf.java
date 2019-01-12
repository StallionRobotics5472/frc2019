package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLiftHalf extends Command{
	
	private LiftSubsystem lift;
	
	
	@Override
	public void initialize() {
		lift = Robot.lift;
		lift.enableClosedLoop();
		lift.setSetpoint(16000);
	}
	
	@Override
	public void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return lift.getPosition() > 20000;
	}
}

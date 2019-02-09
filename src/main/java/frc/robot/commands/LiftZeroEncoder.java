package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftZeroEncoder extends Command{
	
	private LiftPIDSubsystem lift;
	private boolean finished;
	
	@Override
	public void initialize() {
		lift = Robot.lift;
	}
	
	@Override
	public void execute() {
//		lift.zeroEncoder();
		finished = true;
	}
		
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
}

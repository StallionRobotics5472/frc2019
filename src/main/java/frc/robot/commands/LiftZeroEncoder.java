package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftZeroEncoder extends Command{
	
	private LiftSubsystem lift;
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

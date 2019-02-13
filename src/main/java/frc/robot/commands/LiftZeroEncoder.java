package frc.robot.commands;

import frc.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class LiftZeroEncoder extends Command{
	
	
	private boolean finished;
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void execute() {
		Robot.lift.zeroEncoder();
		finished = true;
	}
		
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
}

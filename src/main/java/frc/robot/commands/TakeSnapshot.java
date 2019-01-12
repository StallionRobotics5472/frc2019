package frc.robot.commands;

import frc.robot.Limelight;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TakeSnapshot extends Command{
	
	private boolean finished;
	private Limelight instance;
	
	public TakeSnapshot() {
	}
	
	@Override
	public void initialize() {
		instance = Robot.limelight;
	}
	
	@Override
	public void execute() {
		instance.takeSnapshot();
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

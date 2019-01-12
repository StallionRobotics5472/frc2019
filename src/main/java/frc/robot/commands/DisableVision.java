package frc.robot.commands;

import frc.robot.Limelight;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DisableVision extends Command{

	private boolean finished = false;
	private Limelight instance;
	
	public DisableVision() {
	}
	
	@Override
	public void initialize() {
		instance = Robot.limelight;
	}
	
	@Override
	public void execute() {
		instance.setLed(false);
		instance.setVisionProcessing(false);
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

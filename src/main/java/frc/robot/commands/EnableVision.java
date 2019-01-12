package frc.robot.commands;

import frc.robot.Limelight;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnableVision extends Command{

	private boolean finished = false;
	private Limelight instance;
	
	public EnableVision() {
	}
	
	@Override
	public void initialize() {
		instance = Robot.limelight;
	}
	
	@Override
	public void execute() {
		if(instance.getActivePipeline() == 0)
			instance.setLed(true);
		instance.setVisionProcessing(true);
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

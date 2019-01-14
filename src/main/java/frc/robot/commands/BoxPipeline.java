package frc.robot.commands;

import frc.robot.Limelight;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BoxPipeline extends Command{
	
	private boolean finished;
	private Limelight instance;
	
	public BoxPipeline() {
	}
	
	@Override
	public void initialize() {
		instance = Robot.limelight;
	}
	
	@Override
	public void execute() {
		if(instance.getVisionProcessingEnabled())
			instance.setLed(false);
		instance.useBoxDetectionPipeline();
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

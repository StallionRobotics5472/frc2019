package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakePushSlow extends Command{
	
	private IntakeSubsystem intake;
	private boolean finished;
	
	@Override
	public void initialize() {
		intake = Robot.intake;
		finished = false;
	}
	
	@Override
	public void execute() {
		intake.reverse(true);
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

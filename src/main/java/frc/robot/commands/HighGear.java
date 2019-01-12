package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class HighGear extends Command {
	
	private boolean finished = false;
	private DriveSubsystem drive;
	
	@Override
	public void initialize() {
		drive = Robot.drive;
	}
	
	@Override
	public void execute() {
		drive.highGear();
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

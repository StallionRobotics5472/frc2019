package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

public class EnableCoast extends Command{
	
	private boolean finished;
	private DriveSubsystem drive;
	
	public EnableCoast() {
		drive = Robot.drive;
	}
	
	@Override
	public void execute() {
		drive.setCoast();
		finished = true;
	}
	
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
}

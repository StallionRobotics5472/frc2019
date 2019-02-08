package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

public class EnableBrake extends Command{
	
	private boolean finished;
	private DriveSubsystem drive;
	
	public EnableBrake() {
		drive = Robot.drive;
	}
	
	@Override
	public void execute() {
		drive.setBrake();
		finished = true;
	}
	
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
}

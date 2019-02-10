package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;
 
import edu.wpi.first.wpilibj.command.Command;

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

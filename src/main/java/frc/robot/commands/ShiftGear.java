package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGear extends Command {

	private boolean finished = false;
	private DriveSubsystem drive;

	@Override
	public void initialize() {
		this.drive = Robot.drive;
	}

	@Override
	public void execute() {
		drive.shiftGear();
		finished = true;
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

}

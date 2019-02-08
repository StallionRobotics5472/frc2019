package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Controls;
import frc.robot.Robot;

public class JoystickDriveCommand extends Command{
	
	private boolean completed = false;
	private Controls controls = Robot.controls;
	
	public JoystickDriveCommand() {
		requires(Robot.drive);
	}
	
	@Override
	public void initialize() {}
	
	@Override
	public void execute() {
		if(Robot.getState() == Robot.State.USERCONTROL) {
			double y = -controls.getDriveVerticalAxis();
			double x = controls.getDriveHorizontalAxis() / 2;
			
			y = Math.abs(y) < 0.15 ? 0 : y;
			x = Math.abs(x) < 0.05 ? 0 : x;
			
			Robot.drive.drive(y + x, y - x);
		}
	}
	
	public boolean isFinished() {
		return this.completed;
	}

}

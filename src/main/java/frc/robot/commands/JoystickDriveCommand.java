package frc.robot.commands;

import frc.robot.Controls;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDriveCommand extends Command{
	
	private boolean completed = false;
	private Controls controls = Robot.controls;
	
	public JoystickDriveCommand() {
		requires(Robot.drive);
	}
	
	@Override
	public void initialize() {
		Robot.drive.setCoast();

	}
	
	@Override
	public void execute() {
	
			double y = -controls.getDriveVerticalAxis();
			double x = controls.getDriveHorizontalAxis() / 2;
			
			y = Math.abs(y) < 0.05 ? 0 : y;
			x = Math.abs(x) < 0.05 ? 0 : x;
			// if(Robot.controls.getSpeed()){
			// 	Robot.drive.drive((y*0.5 + x*0.75), (y*0.5 - x*0.75));
			// }
			// else
				Robot.drive.drive((y*0.6 + x*0.5), (y*0.6 - x*0.5));
			Robot.drive.seeVoltage();
		}	
	
	
	public boolean isFinished() {
		return this.completed;
	}

}

package frc.robot.commands;

import frc.robot.Controls;
import frc.robot.Robot;

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
			double y = -controls.getDriveVerticalAxis() * 0.75;
			double x = controls.getDriveHorizontalAxis() * 0.75; 
			double j = -controls.getSpeedAxis() * 0.43;
			y = Math.abs(y) < 0.10 ? 0 : y;
			x = Math.abs(x) < 0.10 ? 0 : x;
			// if(Robot.controls.getSpeed()){
			// 	Robot .drive.drive((y*0.5 + x*0.75), (y*0.5 - x*0.75));
			// }
			// else
				Robot.drive.drive((Math.abs(y)*y + Math.abs(x)*x+j), (Math.abs(y)*y - Math.abs(x)*x)+j);
			Robot.drive.seeVoltage();
		}	
	
	
	public boolean isFinished() {
		return this.completed;
	}

}

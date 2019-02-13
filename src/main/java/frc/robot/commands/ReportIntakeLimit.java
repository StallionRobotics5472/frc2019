package frc.robot.commands;


import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ReportIntakeLimit extends Command{
	
	
	private boolean finished;
	
	public ReportIntakeLimit() {
		finished = false;
	}
	
	@Override
	protected void execute() {
		SmartDashboard.putBoolean("Intake Limit Switch", Robot.controls.intakeLimit.get());
		finished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
}

package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmHalf extends Command{
	
	private ArmPIDSubsystem arm;
	
	
	@Override
	public void initialize() {
		arm = Robot.arm;
	}
	
	@Override
	public void execute() {
	}
	
	@Override
	protected boolean isFinished() {
	return false;//plz fix.  Dont know end encoder value
	}
}

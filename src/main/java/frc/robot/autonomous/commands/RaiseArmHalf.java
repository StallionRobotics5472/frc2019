package frc.robot.autonomous.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.ArmPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmHalf extends Command{
	
	private ArmPIDSubsystem arm;
	boolean isFinished = false;
	private final double startingPosition = Robot.arm.getPosition();
	private final double finalPosition = startingPosition + Constants.ARM_HALF_HEIGHT;
	
	@Override
	public void initialize() {
		arm = Robot.arm;
		arm.moveArm(0.2);
	}
	
	@Override
	public void execute() {
		if (Robot.arm.getPosition() < finalPosition)
			isFinished = true;
	}
	
	@Override
	protected boolean isFinished() {	
		return isFinished;
	}

	@Override
	public void end()
	{
		arm.moveArm(0);
	}
}

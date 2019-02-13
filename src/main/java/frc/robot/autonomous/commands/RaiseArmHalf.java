package frc.robot.autonomous.commands;

import frc.robot.Constants;
import frc.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmHalf extends Command{
	
	
	boolean isFinished = false;
	private final double startingPosition = Robot.arm.getPosition();
	private final double finalPosition = startingPosition + Constants.ARM_HALF_HEIGHT;
	
	@Override
	public void initialize() {
		Robot.arm.moveArm(0.2);
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
		Robot.arm.moveArm(0);
	}
}

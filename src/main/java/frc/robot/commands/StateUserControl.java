package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StateUserControl extends Command{

	private boolean isFinished = false;

	@Override
	public void initialize() {
		Robot.setState(Robot.State.USERCONTROL);
		isFinished = true;
	}
	
	@Override
	protected boolean isFinished() {
		return isFinished;
	}
}

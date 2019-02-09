package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftZero extends Command{
	
//	private LiftPIDSubsystem lift;
//	private boolean finished;
//	
//	public LiftZero() {
//		lift = Robot.lift;
//	}
//	
//	@Override
//	public void execute() {
//		lift.setSetpoint(100);
//		finished = lift.onTarget();
//	}
//	
//	@Override
//	protected boolean isFinished() {
//		return finished;
//	}
//	
	
	private LiftPIDSubsystem lift;
	
	public LiftZero() {
		lift = Robot.lift;
	}
	
	@Override
	public void initialize() {
		lift.disableClosedLoop();
	}
	
	@Override
	public void execute() {
		if(lift.getPosition() > 6500) {
			lift.enableCoast();
			lift.setPercent(0.00);
		} else {
			if(!lift.closedLoopEnabled()) {
				lift.enableBrake();
				lift.enableClosedLoop();
				lift.setSetpoint(100);
			}
		}
	}
	
	@Override
	protected boolean isFinished() {
		return lift.closedLoopEnabled() && lift.onTarget();
	}
}

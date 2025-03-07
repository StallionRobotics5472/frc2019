package frc.robot.autonomous.commands;

import frc.robot.Robot;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class LiftGetHatch extends Command{
	
	private LiftSubsystem lift;

	@Override
	public void initialize() {
    lift = Robot.lift;
		// lift.setSetpoint(330000);
		lift.setSetpoint(200000);
		lift.enable();
	}
	
	@Override
	public void execute() {
	}
	
	
	@Override
	protected boolean isFinished() {
		return lift.onTarget();
  }
  
  @Override
  protected void end(){
    lift.disable();
  }
	
}

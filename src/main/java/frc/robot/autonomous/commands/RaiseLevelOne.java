/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class RaiseLevelOne extends Command {

  public RaiseLevelOne() {
      requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.lift.enable();
    Robot.lift.setSetpoint(Constants.LEVEL_ONE_HEIGHT);
  }

  @Override
  protected void execute() {
        // Called repeatedly when this Command is scheduled to run
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.lift.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

public class ArmCommand extends Command {

  private boolean isFinished = false;

  public ArmCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.arm);
    Robot.arm.enableBrake();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = -Robot.controls.getPlayerTwo().getRawAxis(Constants.Axis_1);
    Robot.arm.moveArm(speed / 2);
    SmartDashboard.putNumber("Current Arm", Robot.arm.getCurrent());
    SmartDashboard.putNumber("Current Position", Robot.arm.getPosition());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

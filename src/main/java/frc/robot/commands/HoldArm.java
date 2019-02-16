/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HoldArm extends Command {

  private int encoderStatusArm, encoderStatusWrist;
  private boolean isFinished;

  public HoldArm() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.arm);
    requires(Robot.wrist);
    isFinished = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.arm.enablePID();
    Robot.wrist.enablePID();
    encoderStatusArm = Robot.arm.getEncoder();
    encoderStatusWrist = Robot.arm.getEncoder();
    Robot.arm.setSetpoint(encoderStatusArm);
    Robot.wrist.setSetpoint(encoderStatusWrist);
    Robot.arm.usePID(0.25);
    Robot.wrist.usePID(0.25);
    isFinished = true;
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

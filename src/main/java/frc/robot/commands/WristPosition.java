// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;

// public class WristPosition extends Command {

//   private double position;

//   public WristPosition(double pos) {
//       requires(Robot.wrist);
//       position = pos;
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//     Robot.wrist.enable();
//     Robot.wrist.setSetpoint(position);
//   }

//   @Override
//   protected void execute() {
//         // Called repeatedly when this Command is scheduled to run
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     return Robot.wrist.onTarget();
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     Robot.wrist.disable();
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//     // end();
//   }
// }

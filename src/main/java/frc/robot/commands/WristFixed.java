/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class WristFixed extends Command {

    private boolean isFinished = false;
    private double targetAngle;

    public WristFixed(double angle) {
        requires(Robot.wrist);
        Robot.wrist.setBrake();
        targetAngle = angle;
    }

    @Override
    protected void initialize(){
        Robot.wrist.enable();
    }

    @Override
    protected void execute() {
        Robot.wrist.setSetpoint(targetAngle - Robot.arm.getPosition());
        Robot.wrist.showVoltage();
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void end() {
        Robot.wrist.disable();
    }

    @Override
    protected void interrupted() {
        end();
    }
}

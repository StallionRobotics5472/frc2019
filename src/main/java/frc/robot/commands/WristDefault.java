/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class WristDefault extends Command {

    private boolean isFinished = false;

    public WristDefault() {
        requires(Robot.wrist);
        Robot.wrist.setBrake();

    }

    @Override
    protected void initialize() {
        Robot.wrist.disable();
    }

    @Override
    protected void execute() {
        boolean autonomous = DriverStation.getInstance().isAutonomous();

        if (!autonomous) {
            double downspeed = -Robot.controls.getPlayerTwo().getRawAxis(Constants.Axis_5);
            double output = downspeed;
            output = Math.abs(output) < 0.05 ? 0 : output;
            Robot.wrist.spin(output);
        }

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

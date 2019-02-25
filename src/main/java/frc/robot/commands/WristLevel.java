package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristLevel extends Command {

    public WristLevel() {
        requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
        Robot.wrist.setSetpoint(-90);
        Robot.wrist.enable();
    }

    @Override
    protected void end() {
        Robot.wrist.disable();
    }

    @Override
    protected boolean isFinished() {
        return Robot.wrist.onTarget();
    }
}
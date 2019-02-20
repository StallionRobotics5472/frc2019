package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.WristSubsystem;

public class WristLevel extends Command {

    private WristSubsystem instance;

    public WristLevel() {
        requires(Robot.wrist);
        instance = Robot.wrist;
    }

    @Override
    protected void initialize() {
        instance.setSetpoint(-90);
        instance.enable();
    }

    @Override
    protected void end() {
        instance.disable();
    }

    @Override
    protected boolean isFinished() {
        return instance.onTarget();
    }
}
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

public class InitializeLift extends Command {

    private LiftPIDSubsystem instance;

    public InitializeLift() {
        requires(Robot.lift);
        instance = Robot.lift;
    }

    @Override
    protected void initialize() {
        instance.setSetpoint(4000);
        instance.enable();
    }

    @Override
    protected void end() {
    }

    @Override
    protected boolean isFinished() {
        return instance.onTarget();
    }
}
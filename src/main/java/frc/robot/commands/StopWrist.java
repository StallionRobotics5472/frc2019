package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopWrist extends Command
{

    private boolean isFinished;

    public StopWrist()
    {
        requires(Robot.wrist);
        isFinished = false;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    protected void execute()
    {
        Robot.wrist.spin(0);
        isFinished = true;
    }

}
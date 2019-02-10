package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristUpCommand extends Command
{

    //the speed the wrist should go up at
    private double speed;
    //whether or not the command is finished or not
    private boolean isFinished;

    public WristUpCommand(double s)
    {
        requires(Robot.wrist);
        speed = s;
        isFinished = false;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    protected void execute()
    {
        Robot.wrist.spin(speed);
        isFinished = true;
    }

}
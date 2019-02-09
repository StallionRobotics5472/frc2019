package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristDownCommand extends Command
{

    //the speed the wrist should go up at
    private double speed;
    //whether or not the command is finished or not
    private boolean isFinished;

    public WristDownCommand(double s)
    {
        requires(Robot.wrist);
        speed = -s;
        isFinished = false;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void execute()
    {
        Robot.wrist.spin(speed);
        isFinished = true;
    }

}
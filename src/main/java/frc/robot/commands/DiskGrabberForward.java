package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DiskGrabberForward extends Command {
    
    private boolean finished;

    public DiskGrabberForward(){
        requires(Robot.diskPush);
        finished = false;
    }

    @Override
    protected void initialize(){
        Robot.diskPush.shift();
        finished = true;
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}

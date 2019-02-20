package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DiskGrabberReverse extends Command {
    
    private boolean finished;

    public DiskGrabberReverse(){
        requires(Robot.diskPush);
        finished = false;
    }

    @Override
    protected void initialize(){
        Robot.diskPush.reverse();
        finished = true;
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}

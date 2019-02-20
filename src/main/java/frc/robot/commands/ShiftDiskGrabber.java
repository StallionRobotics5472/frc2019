package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ShiftDiskGrabber extends Command {
    
    private boolean finished;

    public ShiftDiskGrabber(){
        requires(Robot.diskPush);
        finished = false;
    }

    @Override
    protected void initialize(){
        Robot.diskPush.shift();
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}

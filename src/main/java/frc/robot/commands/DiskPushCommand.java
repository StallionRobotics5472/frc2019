package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DiskPushCommand extends Command {
    private boolean finished = false;
    
    @Override
    protected boolean isFinished() {
        return finished;
    }

    public void execute()
    {
        Robot.diskPush.shift();
        double time = Timer.getFPGATimestamp();
        while(Timer.getFPGATimestamp() - time <= 0.1){
            
        }
        time = Timer.getFPGATimestamp();
        while(Timer.getFPGATimestamp()-time <= 0.33){
            Robot.drive.drive(-0.35, -0.35);
        }
        Robot.drive.drive(0,0);
        Robot.diskPush.shift();
        finished = true;    
    }
}

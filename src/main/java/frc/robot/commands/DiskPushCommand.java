
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class DiskPushCommand extends Command {

   private boolean isFinished = false;

    public DiskPushCommand() {
        
    }

    public void execute(){
       Robot.diskPush.shift();
       isFinished = true;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }
}

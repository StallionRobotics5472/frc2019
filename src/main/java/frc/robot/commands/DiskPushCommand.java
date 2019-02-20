
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DiskPushCommand extends CommandGroup {

    public DiskPushCommand() {
        addSequential(new DiskGrabberReverse());
        addSequential(new Delay(0.1));
        addSequential(new StraightDrive(-0.35));
        addSequential(new Delay(0.33));
        addSequential(new StraightDrive(0.0));
        addSequential(new DiskGrabberForward());
    }
}


package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DiskGetCommand extends CommandGroup {

    public DiskGetCommand() {
        addSequential(new DiskGrabberForward());
        addSequential(new Delay(0.1));
        addSequential(new StraightDrive(-0.35));
        addSequential(new Delay(0.33));
        addSequential(new StraightDrive(0.0));
        addSequential(new DiskGrabberReverse());
    }
}

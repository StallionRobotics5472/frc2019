package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Delayed extends CommandGroup {

    /**
     * Starts a command after a given amount of time. Useful for delayed parallel commands.
     * @param command The command to run.
     * @param seconds The amount of time, in seconds, to wait.
     */
    public Delayed(Command command, double seconds){
        addSequential(new Delay(seconds));
        addSequential(command);
    }
}

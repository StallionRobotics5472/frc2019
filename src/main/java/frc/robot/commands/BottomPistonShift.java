package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class BottomPistonShift extends Command
{

    //the boolean for whether or not this command is finished running
    private boolean isFinished;

    public BottomPistonShift()
    {
        requires(Robot.bottomPistons);
        isFinished = false;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }

    protected void execute()
    {
        //toggle the bottom piston to the opposite of what it is now
        Robot.bottomPistons.shift();

        //become the done
        isFinished = true;

    }

}
package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.autonomous.commands.RaiseLevelThree;
import frc.robot.commands.ArmPosition;
import frc.robot.commands.WristPosition;

public class RocketHighLift extends CommandGroup {

    public RocketHighLift() {
        addParallel(new ArmPosition(0.0));
        addSequential(new RaiseLevelThree(), 3);
        addSequential(new WristPosition(90));
    }

}
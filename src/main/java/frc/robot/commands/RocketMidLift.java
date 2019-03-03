package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RocketMidLift extends CommandGroup {

    public RocketMidLift() {
        addParallel(new ArmPosition(0.0));
        addSequential(new RaiseLevelTwo(), 3);
        addSequential(new WristPosition(90));
    }

}
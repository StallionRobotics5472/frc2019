package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RocketHighLift extends CommandGroup {

    public RocketHighLift() {
        addParallel(new ArmPosition(0.0));
        addSequential(new RaiseLevelThree(), 3);
        addSequential(new WristPosition(90));
    }

}
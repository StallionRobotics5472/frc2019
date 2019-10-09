package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.autonomous.commands.RaiseLevelTwo;
import frc.robot.commands.ArmPosition;
import frc.robot.commands.Delayed;
//import frc.robot.commands.WristPosition;

public class RocketMidLift extends CommandGroup {

    public RocketMidLift() {
        addParallel(new ArmPosition(0.0));
        //addParallel(new Delayed(new WristPosition(90), 2));
        addSequential(new RaiseLevelTwo(), 3);
    }

}
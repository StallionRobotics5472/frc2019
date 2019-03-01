package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitializeRobotState extends CommandGroup {

    public InitializeRobotState() {
        addParallel(new ArmPosition(0.0));
        addSequential(new RaiseLevelOne(), 3);
        addSequential(new WristPosition(90));
    }

}
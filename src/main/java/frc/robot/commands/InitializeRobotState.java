package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.autonomous.commands.LiftGetHatch;

public class InitializeRobotState extends CommandGroup {

    public InitializeRobotState() {
        addParallel(new ArmPosition(0.0));
       addSequential(new LiftGetHatch(), 3);
        addSequential(new WristPosition(90));
    }

}
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.commands.FollowTrajectory;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.nio.file.Paths;

public class Autonomous {

    public static enum StartingPosition {
        CENTER("Center"), LEFT("Left"), RIGHT("Right");

        private String name;

        private StartingPosition(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static enum Plan {
        DRIVE_STRAIGHT_MOTION_PROFILE("Motion Profile Straight");

        private String name;

        private Plan(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private SendableChooser<StartingPosition> starting = new SendableChooser<>();
    private SendableChooser<Plan> plan = new SendableChooser<>();

    private Command command = null;

    public Autonomous() {
        for(StartingPosition sp : StartingPosition.values())
            starting.addOption(sp.toString(), sp);
        starting.setDefaultOption(StartingPosition.CENTER.toString(), StartingPosition.CENTER);

        for(Plan p : Plan.values())
            plan.addOption(p.toString(), p);
        plan.setDefaultOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);


        SmartDashboard.putData("Autonomous Starting Position", starting);
        SmartDashboard.putData("Autonomous Task", plan);
    }

    public void start() {
        StartingPosition startPos = starting.getSelected();
        Plan thePlan = plan.getSelected();

        File trajectory = Paths.get("/home/lvuser/Paths/straight_left.csv").toFile();
        Trajectory traj = Pathfinder.readFromCSV(trajectory);

        command = new FollowTrajectory(traj, FollowTrajectory.Algorithm.FEEDFORWARD);
        command.start();
    }

    public void startingCenter(Plan task) {
        switch (task) {
            default:
                break;
        }
    }

    public void startingLeft(Plan task) {
        switch (task) {
            default:
                break;
        }
    }

    public void startingRight(Plan task) {
        switch (task) {
            default:
                break;
        }
    }

    public void end() {
        if (command != null)
            command.cancel();
    }
}

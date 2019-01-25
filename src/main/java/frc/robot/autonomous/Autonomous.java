package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.commands.paths.StraightPath;

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
        starting.setDefaultOption(StartingPosition.CENTER.toString(), StartingPosition.CENTER);
        starting.addOption(StartingPosition.LEFT.toString(), StartingPosition.LEFT);
        starting.addOption(StartingPosition.RIGHT.toString(), StartingPosition.RIGHT);

        plan.setDefaultOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);
        SmartDashboard.putData("Autonomous Starting Position", starting);
        SmartDashboard.putData("Autonomous Task", plan);
    }

    public void start() {
        StartingPosition startPos = starting.getSelected();
        Plan thePlan = plan.getSelected();

        switch (startPos) {
            case CENTER:
                startingCenter(thePlan);
                break;
            case LEFT:
                startingLeft(thePlan);
                break;
            case RIGHT:
                startingRight(thePlan);
                break;
        }

        if (command != null)
            command.start();
    }

    public void startingCenter(Plan task) {
        switch (task) {
            case DRIVE_STRAIGHT_MOTION_PROFILE:
                command = new StraightPath();
                break;
        }
    }

    public void startingLeft(Plan task) {
        switch (task) {
            case DRIVE_STRAIGHT_MOTION_PROFILE:
                command = new StraightPath();
        }
    }

    public void startingRight(Plan task) {
        switch (task) {
            case DRIVE_STRAIGHT_MOTION_PROFILE:
                command = new StraightPath();
        }
    }

    public void end() {
        if (command != null)
            command.cancel();
    }
}

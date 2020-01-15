package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ApproachTargetTeleop extends Command {

    /*
     * Approaches a target given the target is within the camera's fov
     */

    private boolean finished = false;
    private Limelight limelight;
    private DriveSubsystem drive;
    private TurretSubsystem turret;

    public ApproachTargetTeleop() {
        requires(Robot.drive);
        turret = Robot.turret;
        drive = Robot.drive;
        limelight = Robot.limelight;
    }
    // // Z: -22.95"
    // // X: +0.5"
    // double cameraZ = -29.86; // Fill this in
    // double cameraX = 4; // fill this in as well
    // double zError = -22.95 - cameraZ;
    // double xError = 0.5 - cameraX;

    // zError *= 2.54 / 100.0; // Convert to meters
    // xError *= 2.54 / 100.0; // Convert to meters

    // Waypoint[] waypoints = { new Waypoint(0, 0, drive.getHeading()),
    // new Waypoint(zError, xError, drive.getHeading()) };

    // Trajectory.Config configuration = new
    // Trajectory.Config(FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
    // 0.01, 0.75, 10, 20);
    // Trajectory traj = Pathfinder.generate(waypoints, configuration);
    // TankModifier mod = new TankModifier(traj).modify(0.6255);
    // Trajectory left = mod.getLeftTrajectory(), right = mod.getRightTrajectory();

    // addSequential(new MotionProfile(left, right));
    // }

    // @Override
    // public void end() {
    // limelight.disableWallTargetPipeline();
    // new JoystickDriveCommand().start();
    // }
    // }

    @Override
    public void initialize() {
        limelight.setWallTargetPipeline();
    }

    @Override
    public void execute() {
       // int liftEncoder = Robot.lift.getEncoder();
        double area = 0, turn = 0;

        // if (liftEncoder < Constants.LIMELIGHT_HEIGHT_THRESHOLD) {
        //     area = Constants.LIMELIGHT_APPROACH_A * Math.pow(limelight.getTargetArea(), Constants.LIMELIGHT_APPROACH_B)
        //             + Constants.LIMELIGHT_APPROACH_C * limelight.getTargetArea() + Constants.LIMELIGHT_APPROACH_D;
            double horizontalError = limelight.getHorizontalAngle();
            turn = horizontalError * Constants.LIMELIGHT_APPROACH_TARGET_TURNP;
        // } else {
        //     area = Constants.LIMELIGHT_HIGH_APPROACH_A
        //             * Math.pow(limelight.getTargetArea(), Constants.LIMELIGHT_HIGH_APPROACH_B)
        //             + Constants.LIMELIGHT_HIGH_APPROACH_C * limelight.getTargetArea()
        //             + Constants.LIMELIGHT_HIGH_APPROACH_D;
        //     double horizontalError = limelight.getHorizontalAngle();
        //     turn = horizontalError * Constants.LIMELIGHT_APPROACH_TARGET_TURNP;
        // }

        if (limelight.getTargetArea() <= 1e-4) {
            // || limelight.isFrozen()) {
            area = 0;
            turn = 0;
        }
        
        turret.rotate(turn);

        SmartDashboard.putNumber("Turret Rotation", turn);

    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    public void end() {
        limelight.disableWallTargetPipeline();
        turret.rotate(0);
    }
}

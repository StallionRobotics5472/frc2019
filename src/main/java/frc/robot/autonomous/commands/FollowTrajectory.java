package frc.robot.autonomous.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.KinematicsPaper;
import frc.robot.util.Vec;
import jaci.pathfinder.Trajectory;

import static jaci.pathfinder.Trajectory.Segment;

import java.util.Timer;
import java.util.TimerTask;

public class FollowTrajectory extends Command {

    public static enum Algorithm {JACI, FEEDFORWARD, LINEAR, NONLINEAR, DYNAMIC}


    private static final double TRAJECTORY_DT = 0.01;


    private static final double JACI_P = 0.0;
    private static final double JACI_D = 0.0;
    private static final double JACI_V = 0.0;
    private static final double JACI_A = 0.0;
    private static final double JACI_G = 0.0;


    private static final double LINEAR_ZETA = 0.0;  // Damping Coefficient: (0, 1)
    private static final double LINEAR_B = 0.0;  // b > 0


    private static final double NONLINEAR_ZETA = 0.0;  // Damping Coefficient: (0, 1)
    private static final double NONLINEAR_B = 0.0;  // b > 0


    private static final Vec DYNAMIC_P = new Vec(1.0, 1.0, 0.0);
    private static final Vec DYNAMIC_D = new Vec(1.0, 1.0, 0.0);


    private boolean isFinished;

    private Algorithm algorithm;
    private Vec initialPos;
    private Trajectory trajectory;

    private Timer timer;
    private TimerTask updateTask;

    private DriveSubsystem instance;


    public FollowTrajectory(Trajectory traj, Algorithm algo) {
        algorithm = algo;
        initialPos = new Vec();
        trajectory = traj;

        instance = Robot.drive;

        timer = new Timer();
        switch (algorithm) {
            case JACI:
                break;
            case FEEDFORWARD:
                updateTask = new TimerTask() {
                    private int index = 0;
                    @Override
                    public void run() {
                        if(index >= traj.segments.length){
                            this.cancel();
                            return;
                        }
                        Segment current = traj.segments[index];
                        updateFeedforward(current);
                    }
                };
                break;
            case LINEAR:
                updateTask = new TimerTask() {
                    private int index = 0;
                    @Override
                    public void run() {
                        if(index >= traj.segments.length){
                            this.cancel();
                            return;
                        }
                        Segment current = traj.segments[index];
                        updateLinear(current);
                    }
                };
                break;
            case NONLINEAR:
                updateTask = new TimerTask() {
                    private int index = 0;
                    @Override
                    public void run() {
                        if(index >= traj.segments.length){
                            this.cancel();
                            return;
                        }
                        Segment current = traj.segments[index];
                        updateNonlinear(current);
                    }
                };
                break;
            case DYNAMIC:
                updateTask = new TimerTask() {
                    private int index = 0;
                    private Vec compensator = new Vec();
                    @Override
                    public void run() {
                        if(index >= traj.segments.length){
                            this.cancel();
                            isFinished = true;
                            return;
                        }
                        if(index == 0){
                            compensator.setY(traj.segments[0].velocity);
                        }
                        Segment current = traj.segments[index];
                        updateDynamic(current, compensator);
                    }
                };
                break;
        }
    }

    @Override
    protected void initialize(){
        instance.startStateEstimation();
        timer.scheduleAtFixedRate(updateTask, 0, (long) (1000 * TRAJECTORY_DT));
    }

    @Override
    protected void interrupted(){
        updateTask.cancel();
    }

    @Override
    protected boolean isFinished(){
        return isFinished;
    }

    @Override
    protected void end(){
        instance.drive(0, 0);
    }

    private Vec getDestPosition(Segment seg) {
        return new Vec(
                seg.x,
                seg.y,
                Math.toRadians(seg.heading)
        );
    }

    private Vec getDestVelocity(Segment seg) {
        double mag = seg.velocity;
        double theta = Math.toRadians(seg.heading);
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        return new Vec(
                mag * cos,
                mag * sin,
                theta
        );
    }


    private Vec getDestAcceleration(Segment seg) {
        double mag = seg.acceleration;
        double theta = Math.toRadians(seg.heading);
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        return new Vec(
                mag * cos,
                mag * sin,
                theta
        );
    }


    private void updateFeedforward(Segment currentSegment) {
        Vec velocity = getDestVelocity(currentSegment);
        Vec accel = getDestAcceleration(currentSegment);

        Vec destVW = KinematicsPaper.eq5_3_vwd(velocity, accel);
        Vec angularV = KinematicsPaper.eq4_1_wheel_velocities(destVW, Constants.WHEEL_DIAMETER / 2,
                Constants.ROBOT_WHEELBASE_WIDTH);
        instance.driveAngular(angularV);
    }


    private void updateLinear(Segment currentSegment) {
        Vec state = instance.getRobotState();
        Vec position = getDestPosition(currentSegment);
        Vec velocity = getDestVelocity(currentSegment);
        Vec accel = getDestAcceleration(currentSegment);

        Vec destVW = KinematicsPaper.eq5_3_vwd(velocity, accel);

        Vec gains = KinematicsPaper.eq5_9_kgains(destVW, LINEAR_ZETA, LINEAR_B);
        Vec targetVW = KinematicsPaper.eq5_10_nonlincontroller(state, position, destVW, gains);
        Vec angularV = KinematicsPaper.eq4_1_wheel_velocities(targetVW, Constants.WHEEL_DIAMETER / 2,
                Constants.ROBOT_WHEELBASE_WIDTH);
        instance.driveAngular(angularV);
    }

    private void updateNonlinear(Segment currentSegment) {
        Vec state = instance.getRobotState();
        Vec position = getDestPosition(currentSegment);
        Vec velocity = getDestVelocity(currentSegment);
        Vec accel = getDestAcceleration(currentSegment);

        Vec destVW = KinematicsPaper.eq5_3_vwd(velocity, accel);

        Vec gains = KinematicsPaper.eq5_9_kgains(destVW, NONLINEAR_ZETA, NONLINEAR_B);
        Vec targetVW = KinematicsPaper.eq5_12_unified_ctrllaw(state, position, destVW, gains);
        Vec angularV = KinematicsPaper.eq4_1_wheel_velocities(targetVW, Constants.WHEEL_DIAMETER / 2,
                Constants.ROBOT_WHEELBASE_WIDTH);
        instance.driveAngular(angularV);
    }

    private void updateDynamic(Segment currentSegment, Vec comp) {
        Vec state = instance.getRobotState();
        Vec stateDot = instance.getVelocity();
        Vec position = getDestPosition(currentSegment);
        Vec velocity = getDestVelocity(currentSegment);
        Vec accel = getDestAcceleration(currentSegment);

        Vec uInputs = KinematicsPaper.eq5_18_feedback_controller(state, stateDot, position, velocity, accel,
                DYNAMIC_P, DYNAMIC_D);
        Vec newComp = KinematicsPaper.eq5_15_dynamic_comp(uInputs, state, comp, TRAJECTORY_DT);
        comp.set(newComp);
        Vec targetVW = new Vec(comp.getY(), comp.getZ(), 0.0);
        Vec angularV = KinematicsPaper.eq4_1_wheel_velocities(targetVW, Constants.WHEEL_DIAMETER / 2,
                Constants.ROBOT_WHEELBASE_WIDTH);
        instance.driveAngular(angularV);
    }

}

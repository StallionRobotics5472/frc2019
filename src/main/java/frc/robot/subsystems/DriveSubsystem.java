package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.util.DataProvider;
import frc.robot.util.Vec;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DriveSubsystem extends Subsystem implements DataProvider {

    private AHRS navx = new AHRS(SPI.Port.kMXP);
    private TalonSRX left, right, leftFollower, rightFollower;
    private ControlMode controlMode;
    private Solenoid shiftSolenoid;


    private Timer stateTimer = new Timer();
    private TimerTask updateStateEstimate;
    private boolean updatingStateEstimate;
    private Vec estState = new Vec();
    private Vec lastReadings = new Vec();
    private static final double stateEstimateDt = 0.01;

    public DriveSubsystem() {

        left = new TalonSRX(Constants.DRIVE_LEFT_TALON_CAN);
        right = new TalonSRX(Constants.DRIVE_RIGHT_TALON_CAN);
        leftFollower = new TalonSRX(Constants.DRIVE_LEFT_FOLLOWER_CAN);
        rightFollower = new TalonSRX(Constants.DRIVE_RIGHT_FOLLOWER_CAN);

        shiftSolenoid = new Solenoid(Constants.DRIVE_SHIFT_SOLENOID);

        left.setInverted(false);
        leftFollower.setInverted(false);
        right.setInverted(true);
        rightFollower.setInverted(true);

        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
        left.setSensorPhase(true);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
        right.setSensorPhase(true);

        left.set(ControlMode.PercentOutput, 0);
        leftFollower.follow(left);
        right.set(ControlMode.PercentOutput, 0);
        rightFollower.follow(right);

        highGear();

        reassignEstimationTask();
    }

    public void setControlMode(ControlMode newMode) {
        controlMode = newMode;
    }

    /**
     * Converts from rad/sec to native units / 100ms
     *
     * @param angularVel angular velocities of left and right wheels, respectively
     * @return CTRE velocities for left and right wheels, respectively
     */
    public Vec angularToCTRE(Vec angularVel) {
        // rad * 1 sec     * 1 rev    * 4096 native units
        // sec * 10 (100ms)  2pi rads   1 rev
        double multiplier = (Constants.TICKS_PER_REV / Math.PI / 2.0);
        return angularVel.clone().multiply(multiplier);
    }

    public void drive(double left, double right) {
        this.left.set(ControlMode.PercentOutput, left);
        this.right.set(ControlMode.PercentOutput, right);
    }

    public void driveAngular(Vec speeds) {
        Vec converted = angularToCTRE(speeds);
        SmartDashboard.putNumber("Command Left", converted.getX());
        SmartDashboard.putNumber("Command Right", converted.getY());
        SmartDashboard.putNumber("Timestamp", edu.wpi.first.wpilibj.Timer.getFPGATimestamp());
        left.set(ControlMode.Velocity, converted.getX());
        right.set(ControlMode.Velocity, converted.getY());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickDriveCommand());
    }

    private void reassignEstimationTask() {
        updateStateEstimate = new TimerTask() {
            @Override
            public void run() {
                double left = getLeftPosition(), right = getRightPosition();
                Vec dEnc = new Vec(left, right, 0).subtract(lastReadings);
                lastReadings.setX(left);
                lastReadings.setY(right);
                double ds = dEnc.dot(Vec.ONES) / 2.0;
                double theta = Math.toRadians(getHeading());
                double dx = ds * Math.cos(theta);
                double dy = ds * Math.sin(theta);
                estState.add(new Vec(dx, dy, 0));
                estState.setZ(theta);
            }
        };
    }

    public void startStateEstimation() {
        if (updatingStateEstimate)
            return;
        updatingStateEstimate = true;

        stateTimer = new Timer();

        reassignEstimationTask();

        estState.set(0, 0, 0);
        lastReadings.set(getLeftPosition(), getRightPosition(), 0);

        stateTimer.scheduleAtFixedRate(updateStateEstimate, 0, (long) (stateEstimateDt * 1000));
        updatingStateEstimate = true;
    }

    public void stopStateEstimation() {
        if (!updatingStateEstimate)
            return;
        updateStateEstimate.cancel();

        estState.set(0, 0, 0);
        lastReadings.set(getLeftPosition(), getRightPosition(), 0);

        updatingStateEstimate = false;
    }

    public Vec getRobotState() {
        return estState;
    }

    public void resetRobotState() {
        estState.set(0, 0, 0);
    }

    public void shiftGear() {
        shiftSolenoid.set(!shiftSolenoid.get());
    }

    public void highGear() {
        shiftSolenoid.set(true);
    }

    public void lowGear() {
        shiftSolenoid.set(false);
    }

    public boolean isHighGear() {
        return shiftSolenoid.get();
    }

    public void resetEncoders() {
        left.setSelectedSensorPosition(0, 0, 0);
        right.setSelectedSensorPosition(0, 0, 0);
    }

    public int getLeftRaw() {
        return left.getSelectedSensorPosition(0);
    }

    public double getLeftPosition() {
        return left.getSelectedSensorPosition(0) / Constants.TICKS_PER_METER;
    }

    public double getLeftVelocity() {
        return left.getSelectedSensorVelocity(0) / Constants.TICKS_PER_METER;
    }

    public double getLeftPercent() {
        return left.getMotorOutputPercent();
    }

    public int getRightRaw() {
        return right.getSelectedSensorPosition(0);
    }

    public double getRightPosition() {
        return right.getSelectedSensorPosition(0) / Constants.TICKS_PER_METER;
    }

    public double getRightVelocity() {
        return right.getSelectedSensorVelocity(0) / Constants.TICKS_PER_METER;
    }

    public Vec getAngularVelocities() {
        return new Vec(
                2.0 * getLeftVelocity() / Constants.WHEEL_DIAMETER,
                2.0 * getRightVelocity() / Constants.WHEEL_DIAMETER,
                0
        );
    }

    public Vec getVelocity() {
        double left = getLeftVelocity();
        double right = getRightVelocity();
        double vAvg = (left + right / 2);
        double theta = Math.toRadians(getHeading());
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Vec(
                vAvg * cos,
                vAvg * sin,
                0.0
        );
    }

    public double getRightPercent() {
        return right.getMotorOutputPercent();
    }

    public double getHeading() {
        return -navx.getAngle();
    }

    public void resetHeading() {
        navx.zeroYaw();
    }

    public void setBrake() {
        left.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoast() {
        left.setNeutralMode(NeutralMode.Coast);
        leftFollower.setNeutralMode(NeutralMode.Coast);
        right.setNeutralMode(NeutralMode.Coast);
        rightFollower.setNeutralMode(NeutralMode.Coast);
    }

    // Autonomous Stuff
    public static double truncate(double value, double limit) {
        limit = Math.abs(limit);
        return value > limit ? limit : value < -limit ? -limit : value;
    }


    public HashMap<String, double[]> getData() {
        HashMap<String, double[]> toReturn = new HashMap<>();
        toReturn.put("Drive Motor Output Percent", new double[]{
                leftFollower.getMotorOutputPercent(), rightFollower.getMotorOutputPercent(),
                left.getMotorOutputPercent(), right.getMotorOutputPercent()
        });
        toReturn.put("Drive Motor Output Current", new double[]{
                leftFollower.getOutputCurrent(), rightFollower.getOutputCurrent(),
                left.getOutputCurrent(), right.getOutputCurrent()
        });
        toReturn.put("Drive Motor Encoder Position", new double[]{
                getLeftPosition(), getRightPosition()
        });
        toReturn.put("Drive Motor Encoder Velocity", new double[]{
                getLeftVelocity(), getRightVelocity()
        });
        toReturn.put("Robot Heading", new double[]{
                getHeading()
        });
        return toReturn;
    }
}

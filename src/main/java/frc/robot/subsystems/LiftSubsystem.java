package frc.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.util.DataProvider;
import frc.robot.Robot;
import frc.robot.commands.LiftDefault;

public class LiftSubsystem extends PIDSubsystem implements DataProvider {

    private TalonSRX leftLiftMotor;
    private TalonSRX rightLiftMotor;

    public LiftSubsystem() {
        super("Lift Subsystem", Constants.LIFT_PIDF_P, Constants.LIFT_PIDF_I, Constants.LIFT_PIDF_D,
                Constants.LIFT_PIDF_F);
        leftLiftMotor = new TalonSRX(Constants.LIFT_TALON_CAN_LEFT);
        leftLiftMotor.setNeutralMode(NeutralMode.Brake);
        leftLiftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        leftLiftMotor.setInverted(false);
        leftLiftMotor.setSensorPhase(true);
        leftLiftMotor.configPeakOutputForward(1.0, 10);
        leftLiftMotor.configPeakOutputReverse(-1.0, 10);
        // leftLiftMotor.configForwardSoftLimitThreshold(35000, 10);
        // leftLiftMotor.configForwardSoftLimitEnable(true, 10);
        // leftLiftMotor.configReverseSoftLimitThreshold(0, 10);
        // leftLiftMotor.configReverseSoftLimitEnable(false, 10);

        rightLiftMotor = new TalonSRX(Constants.LIFT_TALON_CAN_RIGHT);
        rightLiftMotor.setNeutralMode(NeutralMode.Brake);
        rightLiftMotor.setInverted(false);
        rightLiftMotor.follow(leftLiftMotor);
        // rightLiftMotor.configPeakOutputForward(1.0, 10);
        // leftLiftMotor.configPeakOutputReverse(-1.0, 10);
        // rightLiftMotor.configPeakOutputReverse(Constants.LIFT_REVERSE_OUTPUT_LIMIT,
        // 10);

        this.setAbsoluteTolerance(50000);

    }

    public void autoPeakOutput() {
        leftLiftMotor.configPeakOutputForward(1.0, 10);
        rightLiftMotor.configPeakOutputForward(1.0, 10);
    }

    public void teleopPeakOutput() {
        leftLiftMotor.configPeakOutputForward(1.0, 10);
        rightLiftMotor.configPeakOutputForward(1.0, 10);
    }

    public void setPercent(double percent) {
        leftLiftMotor.set(ControlMode.PercentOutput, percent);
    }

    public double getPercentOutput() {
        return leftLiftMotor.getMotorOutputPercent();
    }

    public void hold() {
        setPercent(0.05);
        //change to 0.2 for I'm Silver
        
    }
    //179049.0
    //2677280.0

    public void resetEncoder() {
        leftLiftMotor.getSensorCollection().setQuadraturePosition(0, 0);
    }

    public int getEncoder() {
        return -leftLiftMotor.getSensorCollection().getQuadraturePosition();
    }

    public double getPosition() {
        return getEncoder();
    }

    private double map(double value, double in1, double in2, double out1, double out2) {
        return ((out2 - out1) / (in2 - in1)) * (value - in1) + out1;
    }

    public double estimateEndEffectorHeight() {
        final double NEUTRAL_TO_GROUND = 0.381;
        final double ARM_JOINT_LENGTH = 0.3048;
        final int LIFT_NEUTRAL_ENC = 179049;
        final int LIFT_HIGH_NEUTRAL_ENC = 2677280;
        final double LIFT_HIGH_METERS = 1.73355;

        double liftHeight = map(getPosition(), LIFT_NEUTRAL_ENC, LIFT_HIGH_NEUTRAL_ENC, NEUTRAL_TO_GROUND, LIFT_HIGH_METERS);
        double jointAngle = Math.toRadians(Robot.arm.getPosition());
        double dJointHeight = Math.sin(jointAngle) * ARM_JOINT_LENGTH;

        return liftHeight + dJointHeight;
    }

    public void enableBrake() {
        leftLiftMotor.setNeutralMode(NeutralMode.Brake);
        rightLiftMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void enableCoast() {
        leftLiftMotor.setNeutralMode(NeutralMode.Coast);
        rightLiftMotor.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    protected void usePIDOutput(double output) {
        setPercent(output);
    }

    @Override
    protected double returnPIDInput() {
        return getPosition();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftDefault());

    }

    public HashMap<String, double[]> getData() {
        HashMap<String, double[]> toReturn = new HashMap<>();
        toReturn.put("Lift Position", new double[]{getPosition()});
        toReturn.put("Lift Current",
                new double[]{leftLiftMotor.getOutputCurrent(), rightLiftMotor.getOutputCurrent()});
        toReturn.put("Lift Output Percent", new double[]{leftLiftMotor.getMotorOutputPercent()});
        return toReturn;
    }

}

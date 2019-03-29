
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.WristDefault;

public class WristSubsystem extends PIDSubsystem {

    private TalonSRX wristMotor;

    public WristSubsystem() {
        super("WristSubsystem", Constants.WRIST_PIDF_P, Constants.WRIST_PIDF_I, Constants.WRIST_PIDF_D);
        wristMotor = new TalonSRX(Constants.WRIST_TALON);
        wristMotor.setInverted(true);
        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        wristMotor.setSensorPhase(true);

        this.setAbsoluteTolerance(5);
        this.setOutputRange(-0.7, 0.7);
        this.setInputRange(-45, 90);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new WristDefault());
    }

    public void spin(double s) {
        wristMotor.set(ControlMode.PercentOutput, s);
    }

    public void setBrake() {
        wristMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void showVoltage() {
        SmartDashboard.putNumber("Wrist Current", wristMotor.getOutputCurrent());
        SmartDashboard.putNumber("Wrist Voltage", wristMotor.getMotorOutputVoltage());
    }

    @Override
    protected double returnPIDInput() {
        return getDisplacement();
    }

    @Override
    protected void usePIDOutput(double output) {
        wristMotor.set(ControlMode.PercentOutput, output);
    }

    public void enableBrake() {
        wristMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void enableCoast() {
        wristMotor.setNeutralMode(NeutralMode.Coast);
    }

    public double getCurrent() {
        return wristMotor.getOutputCurrent();
    }

    public double getPercentOutput() {
        return wristMotor.getMotorOutputPercent();
    }

    public int getEncoderOutput() {
        return -wristMotor.getSensorCollection().getQuadraturePosition();
    }

    public void resetEncoder() {
        wristMotor.getSensorCollection().setQuadraturePosition(0, 0);
    }

    public double getDisplacement() {
        return map(getEncoderOutput(),0,Constants.NINETY_WRIST,0,90);
    }

    // private double practiceBot(double displacement){
    //    return map(displacement, -87.5, 84.75, -90, 90);
    // }

    private double map(double value, double in1, double in2, double out1, double out2){
        return ((out2 - out1) / (in2 - in1)) * (value - in1) + out1;
    }

    @Override
    public double getPosition() {
        return getDisplacement();
    }

}
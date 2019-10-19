/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;

public class ArmSubsystem extends PIDSubsystem {
  private TalonSRX arm;
  private TalonSRX arm2;

  public ArmSubsystem() {
    super("ArmSubsystem", Constants.ARM_PIDF_P, Constants.ARM_PIDF_I, Constants.ARM_PIDF_D);

    arm = new TalonSRX(Constants.ARM_TALON);
    arm2 = new TalonSRX(Constants.ARM_TALON_FOLLOWER);
    arm.setInverted(true);
    arm2.setInverted(true);

    arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    arm.setSensorPhase(true);

    getPIDController().setContinuous(false);

    this.setInputRange(-45, 80);
    this.setOutputRange(-0.6, 0.6);
    this.setAbsoluteTolerance(4);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmCommand());
  }

  @Override
  protected double returnPIDInput() {
    return getPosition();
  }

  @Override
  protected void usePIDOutput(double output) {
    arm.set(ControlMode.PercentOutput, output);
  }

  public void moveArm(double speed) {
    arm.set(ControlMode.PercentOutput, speed);
    arm2.set(ControlMode.PercentOutput, speed);
  }

  public void enableBrake() {
    arm.setNeutralMode(NeutralMode.Brake);
  }

  public void enableCoast() {
    arm.setNeutralMode(NeutralMode.Coast);
  }

  public double getCurrent() {
    return (arm.getOutputCurrent() + arm2.getOutputCurrent()) / 2.0;
  }

  public void autoPeakOutput() {
    arm.configPeakOutputForward(0.5, 10);
  }

  public void resetEncoder() {
    arm.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public int getEncoder() {
    return arm.getSensorCollection().getQuadraturePosition();
  }

  private double practiceBot(double encoder) {
    return map(encoder, 92.4, 1.87, 90, 0);
  }

  @Override
  public double getPosition() {
    double disp = map(getEncoder(), 0,Constants.NINETY_ARM, -90, 0);
    return disp; //yooooo
  }

  private double map(double value, double in1, double in2, double out1, double out2) {
    return ((out2 - out1) / (in2 - in1)) * (value - in1) + out1;
  }

  public double getPercentOutput() {
    return arm.getMotorOutputPercent();
  }

}
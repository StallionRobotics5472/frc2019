/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.ArmCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Add your docs here.
 * what up youtube 
 * oof man here
 * today we are going to be making a pid 
 */
public class ArmPIDSubsystem extends PIDSubsystem {
 
  private TalonSRX arm = new TalonSRX(Constants.ARM_TALON);
  private TalonSRX arm2 = new TalonSRX(Constants.ARM_TALON_2);

  public ArmPIDSubsystem() {
    // Intert a subsystem name and PID values here
    super("ArmPIDSubsystem", 1, 2, 3);
    arm2.setInverted(true);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ArmCommand());
  }

  public void moveArm(double speed)
  {
    arm.set(ControlMode.PercentOutput, speed);
    arm2.set(ControlMode.PercentOutput, speed);
  }

  public void setBrake()
  {
    arm.setNeutralMode(NeutralMode.Brake);
    arm2.setNeutralMode(NeutralMode.Brake);
  }

  public double getCurrent()
  {
    return (arm.getOutputCurrent() + arm2.getOutputCurrent())/2.0;
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return 0.0;
    
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
  }
}

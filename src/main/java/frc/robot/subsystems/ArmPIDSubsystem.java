/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.commands.ArmCommand;

/**
 * Add your docs here.
 */
public class ArmPIDSubsystem extends PIDSubsystem {
  /**
   * Add your docs here.
   * 
   * 
   */

   private TalonSRX arm;
   private TalonSRX arm2;
   
  public ArmPIDSubsystem() {
    // Intert a subsystem name and PID values here
    super("ArmPIDSubsystem", Constants.ARM_PIDF_P, Constants.ARM_PIDF_I, Constants.ARM_PIDF_D);
    arm = new TalonSRX(Constants.ARM_TALON);;
    arm2 = new TalonSRX(Constants.ARM_TALON_2);
    arm2.setInverted(true);
    setAbsoluteTolerance(50);
    getPIDController().setContinuous(false);
    getPIDController().setSetpoint(0);

    // setSetpoint(0);
    
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

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return arm.getSelectedSensorPosition(0);
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    arm.set(ControlMode.PercentOutput, output);
    arm2.set(ControlMode.PercentOutput, output);
  }
  public void moveArm(double speed)
  {
    arm.set(ControlMode.PercentOutput, speed);
    arm2.set(ControlMode.PercentOutput, speed);
  }

  public void enableBrake()
  {
    arm.setNeutralMode(NeutralMode.Brake);
    arm2.setNeutralMode(NeutralMode.Brake);
  }

  public void enableCoast()
  {
      arm.setNeutralMode(NeutralMode.Coast);
      arm2.setNeutralMode(NeutralMode.Coast);
      
  }

  public double getCurrent()
  {
    return (arm.getOutputCurrent() + arm2.getOutputCurrent())/2.0;
  }

  public void setSetpoint(double pos){
   getPIDController().setSetpoint(pos);
  }

  public void enablePID(){
    getPIDController().enable();
  }

  public void disablePID(){
   getPIDController().disable();
  }

  public void autoPeakOutput(){
    arm.configPeakOutputForward( 0.5 , 10);
    
  }


  public void resetEncoders(){
    arm.setSelectedSensorPosition(0, 0, 0);
  }                          

  public int getEncoder(){
    return arm.getSelectedSensorPosition(0);
  }

  public double getSetPoint(){
    return getPIDController().getSetpoint();
  }

  public void usePID(double o){
    usePIDOutput(0);
  }
}

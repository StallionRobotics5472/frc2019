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

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.ArmCommand;

/**
 * Add your docs here.
 */
public class ArmSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private TalonSRX arm = new TalonSRX(Constants.ARM_TALON);
  private TalonSRX arm2 = new TalonSRX(Constants.ARM_TALON_2);
  
  public ArmSubsystem(){
    arm2.setInverted(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArmCommand());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand())
    
  }

  public void moveArm(double speed){
    arm.set(ControlMode.PercentOutput, speed);
    arm2.set(ControlMode.PercentOutput, speed);
  }

  public void setBrake(){
    arm.setNeutralMode(NeutralMode.Brake);
    arm2.setNeutralMode(NeutralMode.Brake);
  }
  
  public double getCurrent(){
    return (arm.getOutputCurrent()+ arm2.getOutputCurrent())/2.0;
  }
}

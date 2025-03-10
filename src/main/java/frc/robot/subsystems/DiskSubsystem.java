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

public class DiskSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX motor = new TalonSRX(Constants.DISK_TALON);
  

  public DiskSubsystem() {
    motor.setInverted(false);

  }

  @Override
  public void initDefaultCommand() {

  }

  public void spin(double speed) {
   

    motor.set(ControlMode.PercentOutput, speed*0.988888);
    }
    public void setBrake()
    {
        motor.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoast()
    {
        motor.setNeutralMode(NeutralMode.Coast);;
    }



}

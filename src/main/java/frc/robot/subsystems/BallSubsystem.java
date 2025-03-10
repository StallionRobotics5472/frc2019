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
import frc.robot.util.LimitSwitch;
import frc.robot.commands.ReportIntakeLimit;

public class BallSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private TalonSRX motor = new TalonSRX(Constants.INTAKE_CAN);
  private LimitSwitch ballLimit = new LimitSwitch(Constants.LIMIT_SWITCH_INTAKE, false);

  //private Solenoid actuallyUselessSolenoid = new Solenoid(Constants.USELESS_SOLENOID);

  public BallSubsystem() {
    motor.setInverted(true);
    ballLimit.whenPressed(new ReportIntakeLimit());
    ballLimit.whenReleased(new ReportIntakeLimit());
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

  public boolean getLimit() {
    return ballLimit.get();
  }

}

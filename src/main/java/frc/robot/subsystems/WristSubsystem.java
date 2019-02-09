
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

public class WristSubsystem extends Subsystem
{

    //the motor for the wrist
    private TalonSRX motor;
    //the speed the motor should spin at

    public WristSubsystem()
    {
        motor = new TalonSRX(Constants.WRIST_TALON);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void spin(double s)
    {
        motor.set(ControlMode.PercentOutput, s);
    }

}
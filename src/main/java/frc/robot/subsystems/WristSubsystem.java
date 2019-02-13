
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.WristCommand;

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
        setDefaultCommand(new WristCommand());
    }

    public void spin(double s)
    {
        motor.set(ControlMode.PercentOutput, s);
    }

    public void setBrake()
    {
        motor.setNeutralMode(NeutralMode.Brake);
    }

    public void showVoltage(){
        SmartDashboard.putNumber("Wrist Current", motor.getOutputCurrent());
        SmartDashboard.putNumber("Wrist Voltage", motor.getMotorOutputVoltage());
    }

}
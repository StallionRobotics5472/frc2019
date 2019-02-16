
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.WristCommand;

public class WristSubsystem extends PIDSubsystem
{

    //the motor for the wrist
    private TalonSRX motor;
    //the speed the motor should spin at

    public WristSubsystem()
    {
        super("WristSubsystem", Constants.WRIST_PIDF_P, Constants.WRIST_PIDF_I, Constants.WRIST_PIDF_D);
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

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {

    }

    public void enablePID(){
        getPIDController().enable();
    }

    public double getSetPoint(){
        return getPIDController().getSetpoint();
    }
    
    public void usePID(double o){
        usePIDOutput(o);
    }

    public void setSetpoint(double pos){
        getPIDController().setSetpoint(pos);
    }

    public void disablePID(){
        getPIDController().disable();
    }

    public void enableBrake()
    {
      motor.setNeutralMode(NeutralMode.Brake);
    }
  
    public void enableCoast()
    {
        motor.setNeutralMode(NeutralMode.Coast);
        
    }
  
    public double getCurrent()
    {
      return motor.getOutputCurrent();
    }

}
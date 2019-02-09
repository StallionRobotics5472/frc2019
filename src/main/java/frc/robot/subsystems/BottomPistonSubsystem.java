package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

public class BottomPistonSubsystem extends Subsystem
{

    private DoubleSolenoid sole;

    public BottomPistonSubsystem()
    {
        sole = new DoubleSolenoid(Constants.RAMP_SOLENOID_RIGHT, Constants.RAMP_SOLENOID_LEFT);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public Value getSolenoidValue()
    {
        return sole.get();
    }

    public boolean isOpen()
    {
        return getSolenoidValue().equals(Value.kForward);
    }

    public void shift()
    {
        if (getSolenoidValue().equals(Value.kForward))
            sole.set(Value.kReverse);
        else
            sole.set(Value.kForward);
    }
}
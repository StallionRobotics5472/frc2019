package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

public class DiskPushSubsystem extends Subsystem{
    private DoubleSolenoid pusher;

    public DiskPushSubsystem(){
        pusher = new DoubleSolenoid(Constants.PUSH_DOUBLESOLENOID1, Constants.PUSH_DOUBLESOLENOID2);
    }

    
    @Override
    protected void initDefaultCommand() {
        
    }

    public Value getSolenoidValue(){
       return  pusher.get();
    }

    public boolean isOpen(){
        return getSolenoidValue().equals(Value.kForward);
    }

    public void shift(){
        if(isOpen()){
            pusher.set(Value.kReverse);
        
        }else{
            pusher.set(Value.kForward);
        }
    }


}
package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

public class DiskPushSubsystem extends Subsystem{
    private DoubleSolenoid pusher;

    public DiskPushSubsystem(){
        pusher = new DoubleSolenoid(Constants.PUSH_SOLENOID_FORWARD, Constants.PUSH_SOLENOID_REVERSE);
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

    public void forward(){
        pusher.set(Value.kForward);
    }

    public void reverse(){
        pusher.set(Value.kReverse);
    }


}
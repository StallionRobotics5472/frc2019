
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Delay extends Command {
    
    private double startTime;
    private double length;

    public Delay(double length){
        startTime = Timer.getFPGATimestamp();
        this.length = length;
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() >= startTime + length;
    }
}

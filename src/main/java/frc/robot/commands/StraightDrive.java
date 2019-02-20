package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

public class StraightDrive extends Command {
    
    private double power;
    private DriveSubsystem instance;
    private boolean finished;

    public StraightDrive(double power){
        requires(Robot.drive);
        instance = Robot.drive;
        this.power = power;
        finished = false;
    }

    @Override
    protected void initialize(){
        instance.drive(power, power);
        finished = true;
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}

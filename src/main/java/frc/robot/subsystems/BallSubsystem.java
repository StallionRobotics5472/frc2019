package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BallSubsystem extends Subsystem {

    private TalonSRX ballMotor;
    private static final ControlMode MODE = ControlMode.PercentOutput;

    public BallSubsystem()
    {
        ballMotor = new TalonSRX(Constants.BALL_INTAKE);
    }

    @Override
    protected void initDefaultCommand() {

    }

    // public void set(double speed)
    // {
    //     ballMotor.set(MODE, speed);
    // }

    public void start(boolean slow) {
		double speed = slow ? Constants.INTAKE_INPUT_SLOW_SPEED : Constants.INTAKE_INPUT_SPEED;
		ballMotor.set(MODE, speed);
	}




}
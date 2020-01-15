package frc.robot.subsystems;

import java.util.HashMap;

import frc.robot.Constants;
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.util.DataProvider;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretSubsystem extends Subsystem{

	public CANSparkMax turret;
	public TurretSubsystem() {

		//public Talon motor = new Talon(1);
		
		turret = new CANSparkMax(1, MotorType.kBrushless);
		turret.setIdleMode(IdleMode.kBrake);
		turret.setSmartCurrentLimit(20);
		
	}

	public void rotate(double speed){
		turret.set(speed);
	}

	public void spin(double speed){
		
	}

	@Override
	public void initDefaultCommand()
	{}

	// public void resetHeading() {

	// }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.ArmCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Add your docs here.
 * what up youtube 
 * oof man here
 * today we are going to be making a pid 
 */
public class ArmPIDSubsystem extends PIDSubsystem {
 
  private TalonSRX arm = new TalonSRX(Constants.ARM_TALON);
  private TalonSRX arm2 = new TalonSRX(Constants.ARM_TALON_2);
  private PIDController positionController;
	private PIDSource positionSource;
  private PIDOutput positionOutput;
  
  

  
  public ArmPIDSubsystem() {
    // Intert a subsystem name and PID values here
    super("ArmPIDSubsystem", 1, 2, 3);


    arm = new TalonSRX(Constants.ARM_TALON);
    arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute); //absolute was recommended for <360 degree movement
    arm.setInverted(false);
    arm.setSensorPhase(true);

    


    arm2 = new TalonSRX(Constants.ARM_TALON_2);
    arm2.setInverted(true);



    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.

    positionOutput = (double output) -> {
			moveArm(output);
		};
		
		positionSource = new PIDSource() {
			public double pidGet() {
				return getPosition();
			}
			public PIDSourceType getPIDSourceType() {return PIDSourceType.kDisplacement;}
			public void setPIDSourceType(PIDSourceType t) {}
		};
		
		positionController = new PIDController(Constants.LIFT_PIDF_P, Constants.LIFT_PIDF_I, Constants.LIFT_PIDF_D, Constants.LIFT_PIDF_F, positionSource, positionOutput);
		positionController.setSetpoint(0.0);
		positionController.setInputRange(0, 0);
		positionController.setOutputRange(-1.0 , 1.0);
		positionController.setAbsoluteTolerance(50);
    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ArmCommand());
  }

  public void moveArm(double speed)
  {
    arm.set(ControlMode.PercentOutput, speed);
    arm2.set(ControlMode.PercentOutput, speed);
  }

  public void enableBrake()
  {
    arm.setNeutralMode(NeutralMode.Brake);
    arm2.setNeutralMode(NeutralMode.Brake);
  }

  public void enableCoast()
  {
      arm.setNeutralMode(NeutralMode.Coast);
      arm2.setNeutralMode(NeutralMode.Coast);
  }

  public double getCurrent()
  {
    return (arm.getOutputCurrent() + arm2.getOutputCurrent())/2.0;
  }

  public void resetEncoder()
  {
      arm.setSelectedSensorPosition(0,0,0);
  }
  
  public double getPosition()
  {
    return arm.getSelectedSensorPosition(0);
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return 0.0;
    
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
  }

public void setBrake() {
}
}

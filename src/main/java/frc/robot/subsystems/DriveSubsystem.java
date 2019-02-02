package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import frc.robot.Constants;
import frc.robot.util.DataProvider;
import frc.robot.commands.JoystickDriveCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.Vec;

public class DriveSubsystem extends Subsystem implements DataProvider{

	private AHRS navx = new AHRS(SPI.Port.kMXP);
	private TalonSRX left, right, leftFollower, rightFollower;
	private ControlMode controlMode;
	private Solenoid shiftSolenoid;
	

	private Timer stateTimer = new Timer();
	private TimerTask updateStateEstimate;
	private boolean updatingStateEstimate;
	private Vec estState = new Vec();
	private Vec lastReadings = new Vec();
	private static final double stateEstimateDt = 0.01;
	
	public DriveSubsystem() {

		left = new TalonSRX(Constants.DRIVE_LEFT_TALON_CAN);
		right = new TalonSRX(Constants.DRIVE_RIGHT_TALON_CAN);
		leftFollower = new TalonSRX(Constants.DRIVE_LEFT_FOLLOWER_CAN);
		rightFollower = new TalonSRX(Constants.DRIVE_RIGHT_FOLLOWER_CAN);
		
		shiftSolenoid = new Solenoid(Constants.DRIVE_SHIFT_SOLENOID);

		left.setInverted(false);
		leftFollower.setInverted(false);
		right.setInverted(true);
		rightFollower.setInverted(true);
		
		//This got flipped around for some reason. 
		
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		left.setSensorPhase(true);
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		right.setSensorPhase(true);

		controlMode = ControlMode.PercentOutput;
		
		setCoast();
		

		left.set(controlMode, 0);
		leftFollower.set(controlMode, 0);
		right.set(controlMode, 0);
		rightFollower.set(controlMode, 0);
		
		highGear();

		updateStateEstimate = new TimerTask() {
			@Override
			public void run() {
				Vec dEnc = new Vec(getLeftPosition(), getRightPosition(), 0).subtract(lastReadings);
				double ds = dEnc.dot(Vec.ONES) / 2.0;
				double theta = Math.toRadians(getHeading());
				double dx = ds * Math.cos(theta);
				double dy = ds * Math.sin(theta);
				estState.add(new Vec(dx, dy, 0));
				estState.setZ(theta);
			}
		};
	}

	public void setControlMode(ControlMode newMode) {
		controlMode = newMode;
	}

	public void drive(double left, double right) {
		this.left.set(controlMode, left);
		this.right.set(controlMode, right);
		this.leftFollower.set(controlMode, left);
		this.rightFollower.set(controlMode, right);
	}
	
	public void turn(double throttle, double twist) {
		left.set(controlMode, throttle + twist);
		leftFollower.set(controlMode, throttle - twist);
		right.set(controlMode, throttle - twist);
		rightFollower.set(controlMode, throttle + twist);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new JoystickDriveCommand());
	}

	public void startStateEstimation(){
		if(updatingStateEstimate)
			return;
		updatingStateEstimate = true;

		estState.set(0, 0, 0);
		lastReadings.set(getLeftPosition(), getRightPosition(), 0);

		stateTimer.scheduleAtFixedRate(updateStateEstimate, 0, (long)(stateEstimateDt * 1000));
		updatingStateEstimate = true;
	}

	public void stopStateEstimation(){
		if(!updatingStateEstimate)
			return;
		updateStateEstimate.cancel();

		estState.set(0, 0, 0);
		lastReadings.set(getLeftPosition(), getRightPosition(), 0);

		updatingStateEstimate = false;
	}

	public void shiftGear() {
		shiftSolenoid.set(!shiftSolenoid.get());
	}

	public void highGear() {
		shiftSolenoid.set(true);
	}

	public void lowGear() {
		shiftSolenoid.set(false);
	}
	
	public boolean isHighGear() {
		return shiftSolenoid.get();
	}

	public void resetEncoders() {
		left.setSelectedSensorPosition(0, 0, 0);
		right.setSelectedSensorPosition(0, 0, 0);
	}

	public int getLeftRaw() {
		return left.getSelectedSensorPosition(0);
	}

	public double getLeftPosition() {
		return left.getSelectedSensorPosition(0) / Constants.LEFT_ENCODER_TICKS_PER_METER;
	}

	public double getLeftVelocity() {
		return left.getSelectedSensorVelocity(0) / Constants.LEFT_ENCODER_TICKS_PER_METER;
	}
	
	public double getLeftPercent() {
		return left.getMotorOutputPercent();
	}

	public int getRightRaw() {
		return right.getSelectedSensorPosition(0);
	}

	public double getRightPosition() {
		return right.getSelectedSensorPosition(0) / Constants.RIGHT_ENCODER_TICKS_PER_METER;
	}

	public double getRightVelocity() {
		return right.getSelectedSensorVelocity(0) / Constants.RIGHT_ENCODER_TICKS_PER_METER;
	}

	public double getRightPercent() {
		return right.getMotorOutputPercent();
	}
	
	public double getHeading() {
		return -navx.getAngle();
	}

	public void resetHeading() {
		navx.zeroYaw();
	}
	
	public void setBrake() {
		left.setNeutralMode(NeutralMode.Brake);
		leftFollower.setNeutralMode(NeutralMode.Brake);
		right.setNeutralMode(NeutralMode.Brake);
		rightFollower.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoast() {
		left.setNeutralMode(NeutralMode.Coast);
		leftFollower.setNeutralMode(NeutralMode.Coast);
		right.setNeutralMode(NeutralMode.Coast);
		rightFollower.setNeutralMode(NeutralMode.Coast);
	}
	
	// Autonomous Stuff
	public static double truncate(double value, double limit) {
		limit = Math.abs(limit);
		return value > limit ? limit : value < -limit ? -limit : value;
	}
	
	
	public HashMap<String, double[]> getData(){
		HashMap<String, double[]> toReturn = new HashMap<>();
		toReturn.put("Drive Motor Output Percent", new double[] {
				leftFollower.getMotorOutputPercent(), rightFollower.getMotorOutputPercent(),
				left.getMotorOutputPercent(), right.getMotorOutputPercent()
		});
		toReturn.put("Drive Motor Output Current", new double[] {
				leftFollower.getOutputCurrent(), rightFollower.getOutputCurrent(),
				left.getOutputCurrent(), right.getOutputCurrent()
		});
		toReturn.put("Drive Motor Encoder Position", new double[] {
				getLeftPosition(), getRightPosition()
		});
		toReturn.put("Drive Motor Encoder Velocity", new double[] {
				getLeftVelocity(), getRightVelocity()
		});
		toReturn.put("Robot Heading", new double[] {
				getHeading()
		});
		return toReturn;
	}
}

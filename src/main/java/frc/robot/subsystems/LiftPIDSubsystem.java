package frc.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.DataProvider;
import frc.robot.commands.LiftDefault;

public class LiftPIDSubsystem extends PIDSubsystem implements DataProvider {

	private TalonSRX leftLiftMotor;
	private TalonSRX rightLiftMotor;

	public LiftPIDSubsystem() {
		super("Lift Subsystem", Constants.LIFT_PIDF_P, Constants.LIFT_PIDF_I, Constants.LIFT_PIDF_D, Constants.LIFT_PIDF_F);
		leftLiftMotor = new TalonSRX(Constants.LIFT_TALON_CAN_LEFT);
		leftLiftMotor.setNeutralMode(NeutralMode.Brake);
		leftLiftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftLiftMotor.setInverted(false);
		leftLiftMotor.setSensorPhase(true);
		leftLiftMotor.configPeakOutputForward(1.0, 10);
		leftLiftMotor.configPeakOutputReverse(-1.0, 10);
		// leftLiftMotor.configForwardSoftLimitThreshold(35000, 10);
		// leftLiftMotor.configForwardSoftLimitEnable(true, 10);
		// leftLiftMotor.configReverseSoftLimitThreshold(0, 10);
		// leftLiftMotor.configReverseSoftLimitEnable(false, 10);

		rightLiftMotor = new TalonSRX(Constants.LIFT_TALON_CAN_RIGHT);
		rightLiftMotor.setNeutralMode(NeutralMode.Brake);
		rightLiftMotor.setInverted(false);
		rightLiftMotor.follow(leftLiftMotor);
		// rightLiftMotor.configPeakOutputForward(1.0, 10);
		// leftLiftMotor.configPeakOutputReverse(-1.0, 10);
		// rightLiftMotor.configPeakOutputReverse(Constants.LIFT_REVERSE_OUTPUT_LIMIT, 10);
		
		this.setAbsoluteTolerance(80000);
	
	}

	public void autoPeakOutput() {
		leftLiftMotor.configPeakOutputForward(1.0, 10);
		rightLiftMotor.configPeakOutputForward(1.0, 10);
	}

	public void teleopPeakOutput() {
		leftLiftMotor.configPeakOutputForward(1.0, 10);
		rightLiftMotor.configPeakOutputForward(1.0, 10);
	}

	public void setPercent(double percent) {
		leftLiftMotor.set(ControlMode.PercentOutput, percent);
	}

	public double getPercentOutput() {
		return leftLiftMotor.getMotorOutputPercent();
	}

	public void hold() {
		setPercent(0.05);
	}

	public void resetEncoder() {
		leftLiftMotor.getSensorCollection().setQuadraturePosition(0, 0);
	}

	public double getPosition() {
		return -leftLiftMotor.getSensorCollection().getQuadraturePosition();
	}

	public void enableBrake() {
		leftLiftMotor.setNeutralMode(NeutralMode.Brake);
		rightLiftMotor.setNeutralMode(NeutralMode.Brake);
	}

	public void enableCoast() {
		leftLiftMotor.setNeutralMode(NeutralMode.Coast);
		rightLiftMotor.setNeutralMode(NeutralMode.Coast);
	}


	@Override
	protected void usePIDOutput(double output) {
		setPercent(output);
	}

	@Override
	protected double returnPIDInput() {
		return getPosition();
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new LiftDefault());

	}

	public HashMap<String, double[]> getData() {
		HashMap<String, double[]> toReturn = new HashMap<>();
		toReturn.put("Lift Position", new double[] { getPosition() });
		toReturn.put("Lift Current",
				new double[] { leftLiftMotor.getOutputCurrent(), rightLiftMotor.getOutputCurrent() });
		toReturn.put("Lift Output Percent", new double[] { leftLiftMotor.getMotorOutputPercent() });
		return toReturn;
	}

}

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.LimitSwitch;
import frc.robot.Robot;
import frc.robot.subsystems.LiftPIDSubsystem;

public class LiftDefault extends Command {

	private LiftPIDSubsystem lift;
	private Controls controls = Robot.controls;
	private LimitSwitch liftBottom;

	public LiftDefault() {
		requires(Robot.lift);
	}

	@Override
	public void initialize() {
		lift = Robot.lift;
		lift.enableBrake();
		liftBottom = Robot.controls.lowLimit;
	}

	@Override
	public void execute() {
		double up = controls.getLiftUpAxis();
		double down = controls.getLiftDownAxis() * Constants.LIFT_REVERSE_OUTPUT_LIMIT;
		double absdown = Math.abs(down);
		double desiredOutput = 0;
		boolean low, mid, high;
		SmartDashboard.putNumber("Lift Encoder Value", Robot.lift.getPosition());
		if(Robot.lift.getPosition() > Constants.LIFT_LOW_POSITION)
			low = true;
		else
			low = false;
		if(Robot.lift.getPosition() > Constants.LIFT_MID_POSITION)
			mid = true;
		else
			mid = false;
		if(Robot.lift.getPosition() > Constants.LIFT_HIGH_POSITION)
			high = true;
		else
			high = false;
		SmartDashboard.putBoolean("Lift Low", low);
		SmartDashboard.putBoolean("Lift Mid", mid);
		SmartDashboard.putBoolean("Ligt High", high);

		// if (lift.getPosition() < 3000 && absdown > 0.05) {
		// //The lift is near the bottom and the operator wishes to lower it
		// lift.enableBrake();
		//// lift.setPercent(0);
		// }
		if (absdown > 0.1)
			// The lift is not near the bottom and the operator wishes to lower it
			lift.enableCoast();
		else
			lift.enableBrake();
		//
		if (liftBottom.get() && (up + down) < 0.00)
			// The lift is at the bottom and the operator does not wish to raise it
			desiredOutput = 0;
		else
			// Normal operation procedure
			desiredOutput = up + down;

		if (Robot.controls.highLimit.get() && (up + down) > 0)
			desiredOutput = 0.2;

		lift.setPercent(-desiredOutput);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
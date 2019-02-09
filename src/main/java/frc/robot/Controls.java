package frc.robot;
import frc.robot.commands.HighGear;
import frc.robot.commands.LiftStop;
import frc.robot.commands.LiftZeroEncoder;
import frc.robot.commands.ReportIntakeLimit;
import frc.robot.commands.ShiftGear;
import frc.robot.commands.StopBall;
import frc.robot.commands.StopWrist;
import frc.robot.commands.TakeSnapshot;
import frc.robot.commands.WristDownCommand;
import frc.robot.commands.WristUpCommand;
import frc.robot.commands.BallCommand;
import frc.robot.commands.DiskPushCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controls {

	private Joystick playerOne = new Joystick(0);
	private Joystick playerTwo = new Joystick(1);

	private JoystickButton shiftGear = new JoystickButton(playerOne, 3); // X Button
	
	private JoystickButton highButton = new JoystickButton(playerOne, 4); // Y Button
	
	//temporary
	private JoystickButton takeSnapshot = new JoystickButton(playerTwo, 4); // Y Button
	//temporary

	private JoystickButton wristDown = new JoystickButton(playerTwo, 1);
	private JoystickButton wristUp = new JoystickButton(playerTwo, 2);

	private JoystickButton diskPush = new JoystickButton(playerOne, 1);

	
	public LimitSwitch highLimit = new LimitSwitch(Constants.LIMIT_SWITCH_HIGH, true);
	public LimitSwitch lowLimit = new LimitSwitch(Constants.LIMIT_SWITCH_LOW, false);
	public LimitSwitch intakeLimit = new LimitSwitch(Constants.LIMIT_SWITCH_INTAKE, true);

	private JoystickButton fastBallIn = new JoystickButton(playerOne, 5);
	private JoystickButton fastBallReverse = new JoystickButton(playerOne, 7);
	private JoystickButton fastBallOut = new JoystickButton(playerOne, 6);
	private JoystickButton slowBallReverse = new JoystickButton(playerOne, 8);
	
	public Controls() {
		shiftGear.whenPressed(new ShiftGear());
		shiftGear.whenReleased(new ShiftGear());
		highButton.whenPressed(new HighGear());
		
		diskPush.whenPressed(new DiskPushCommand());
		
		takeSnapshot.whenPressed(new TakeSnapshot());

		//the speed should be made into a constant
		//the speed is just temporary, update when you figure out a good speed
		wristUp.whileHeld(new WristUpCommand(0.2));
		wristDown.whileHeld(new WristDownCommand(0.2));
		wristUp.whenReleased(new StopWrist());
		
		highLimit.whileActive(new LiftStop());
		lowLimit.whileActive(new LiftZeroEncoder());
		intakeLimit.whenPressed(new ReportIntakeLimit());
		intakeLimit.whenReleased(new ReportIntakeLimit());
		
		fastBallIn.whileHeld(new BallCommand(-Constants.HIGH_BALL, true));
		fastBallOut.whileHeld(new BallCommand(-Constants.HIGH_BALL, false));
		fastBallReverse.whileHeld(new BallCommand(Constants.LOW_BALL, false));
		slowBallReverse.whileHeld(new BallCommand(Constants.LOW_BALL, false));
		
		fastBallIn.whenReleased(new StopBall());
		slowBallReverse.whenReleased(new StopBall());
		fastBallOut.whenReleased(new StopBall());
		fastBallReverse.whenReleased(new StopBall());
	}

	public Joystick getPlayerOne() {
		return playerOne;
	}
	
	public Joystick getPlayerTwo() {
		return playerTwo;
	}
	
	public double getLiftUpAxis() {
		return playerTwo.getRawAxis(3);
	}
	
	public double getLiftDownAxis() {
		return playerTwo.getRawAxis(2);
	}
	
	public double getDriveVerticalAxis() {
		return playerOne.getRawAxis(1);
	}
	
	public double getDriveHorizontalAxis() {
		return playerOne.getRawAxis(0);
	}
}

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
import frc.robot.commands.BottomPistonShift;
import frc.robot.commands.DiskPushCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controls {

	private Joystick playerOne = new Joystick(0);
	private Joystick playerTwo = new Joystick(1);

	private JoystickButton shiftGear = new JoystickButton(playerOne, Constants.BUTTON_X); // X Button
	
	private JoystickButton highButton = new JoystickButton(playerOne, Constants.BUTTON_Y); // Y Button
	
	//temporary
	private JoystickButton takeSnapshot = new JoystickButton(playerTwo, Constants.BUTTON_Y); // Y Button
	//temporary

	private JoystickButton toggleBottomPistons = new JoystickButton(playerOne, Constants.BUTTON_Y);

	private JoystickButton diskPush = new JoystickButton(playerOne, Constants.BUTTON_A);

	
	public LimitSwitch highLimit = new LimitSwitch(Constants.LIMIT_SWITCH_HIGH, true);
	public LimitSwitch lowLimit = new LimitSwitch(Constants.LIMIT_SWITCH_LOW, false);
	public LimitSwitch intakeLimit = new LimitSwitch(Constants.LIMIT_SWITCH_INTAKE, true);

	private JoystickButton fastBallIn = new JoystickButton(playerOne, Constants.SHOULDER_BUTTON_LEFT);
	private JoystickButton fastBallReverse = new JoystickButton(playerOne, Constants.SHOULDER_BUTTON_RIGHT);
	// I dont think fastBallOut and slowBallReverse are on the correct buttons
	private TriggerButton fastBallOut = new TriggerButton(playerOne, Constants.BUTTON_B); 
	private TriggerButton slowBallReverse = new TriggerButton(playerOne, Constants.BUTTON_X);
	
	public Controls() {
		shiftGear.whenPressed(new ShiftGear());
		shiftGear.whenReleased(new ShiftGear());
		highButton.whenPressed(new HighGear());
		
		diskPush.whenPressed(new DiskPushCommand());
		
		takeSnapshot.whenPressed(new TakeSnapshot());

		//the speed should be made into a constant
		//the speed is just temporary, update when you figure out a good speed
		
		
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

		toggleBottomPistons.whenPressed(new BottomPistonShift());
	}

	public Joystick getPlayerOne() {
		return playerOne;
	}
	
	public Joystick getPlayerTwo() {
		return playerTwo;
	}
	
	public double getLiftUpAxis() {
		return playerTwo.getRawAxis(Constants.Axis_2);
	}
	
	public double getLiftDownAxis() {
		return playerTwo.getRawAxis(Constants.Axis_3);
	}
	
	public double getDriveVerticalAxis() {
		return playerOne.getRawAxis(Constants.Axis_1);
	}
	
	public double getDriveHorizontalAxis() {
		return playerOne.getRawAxis(Constants.Axis_0);
	}

}

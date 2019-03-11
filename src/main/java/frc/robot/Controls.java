package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import frc.robot.util.POVButton;
import frc.robot.util.POVButton.POVAngle;
import frc.robot.autonomous.commands.LiftGetHatch;
import frc.robot.util.LimitSwitch;
import frc.robot.util.TriggerButton;

public class Controls {

	private Joystick playerOne = new Joystick(0);
	private Joystick playerTwo = new Joystick(1);
	private Joystick buttonPanel = new Joystick(2);

	//BUTTON PANEL BUTTONS
    //buttons to rasie lift to three possible levels
	// private JoystickButton raiseLevelOne = new JoystickButton(buttonPanel, 1);
	// private JoystickButton raiseLevelTwo = new JoystickButton(buttonPanel, 2);
	// private JoystickButton raiseLevelThree = new JoystickButton(buttonPanel, 3);
	//other button panel buttons
	private JoystickButton buttonPanelDiskPush = new JoystickButton(buttonPanel, 4); //button to push the disk out from the button panel
	private JoystickButton buttonPanelLevelArm = new JoystickButton(buttonPanel, 5); //button to level the arm
	
	//BUTTONS FOR SHIFTING GEAR
	private JoystickButton shiftGear = new JoystickButton(playerOne, Constants.BUTTON_X); // X Button
	// HEY
	// THIS BUTTON IS ALSO ASSIGNED TO THE TOGGLEBOTTOMPISTONS BUTTON
	// KNOW IF I KNOW A LITTLE DIDDY OR TWO ABOUT ROBOTS, ITS THAT THAT SHOULD NEVER HAPPEN!!!!
	// FIX IT
	private JoystickButton highButton = new JoystickButton(playerOne, Constants.BUTTON_Y); // Y Button

	// temporary
	private JoystickButton takeSnapshot = new JoystickButton(playerTwo, Constants.BUTTON_Y); // Y Button
	// temporary
//BUTTON TO TOGGLE THE BOTTOM PISTON
	private JoystickButton toggleBottomPistons = new JoystickButton(playerOne, Constants.BUTTON_Y);

	//BUTTON TO PUSH THE DISK
	private JoystickButton diskPush = new JoystickButton(playerOne, Constants.BUTTON_A);

	//BUTTONS TO MANAGE THE BALL COMING INTO THE INTAKE
	public LimitSwitch highLimit = new LimitSwitch(Constants.LIMIT_SWITCH_HIGH, true);
	public LimitSwitch lowLimit = new LimitSwitch(Constants.LIMIT_SWITCH_LOW, false);

	//BUTTONS TO TAKE THE BALL IN AND OUT AT DIFFERENT 
	private JoystickButton fastBallIn = new JoystickButton(playerOne, Constants.SHOULDER_BUTTON_LEFT);
	private TriggerButton fastBallReverse = new TriggerButton(playerOne, 3);
	// I dont think fastBallOut and slowBallReverse are on the correct buttons
	private TriggerButton fastBallOut = new TriggerButton(playerOne, Constants.BUTTON_B);
	private JoystickButton slowBallReverse = new JoystickButton(playerOne, 6);
	private JoystickButton holdArm = new JoystickButton(playerTwo, Constants.BUTTON_A);
	private JoystickButton seekTarget = new JoystickButton(playerOne, Constants.BUTTON_B);
	// private JoystickButton encoderReseter = new JoystickButton(playerTwo,
	// Constants.BUTTON_Y);

	private POVButton rocketLow = new POVButton(playerTwo, POVAngle.BOTTOM);
	// private POVButton rocketMid = new POVButton(playerTwo, POVAngle.LEFT);
	// private POVButton rocketHigh = new POVButton(playerTwo, POVAngle.TOP);

	private POVButton testRightAngle = new POVButton(playerTwo, POVAngle.LEFT);
	private POVButton testFlatAngle = new POVButton(playerTwo, POVAngle.RIGHT);

	private TriggerButton testWristOverride = new TriggerButton(playerTwo, 5, 0.05);

	public Controls() {

		//NOT TESTED
		buttonPanelLevelArm.whenPressed(new ArmLevel());

		holdArm.whenReleased(new DisableArmPID());

		shiftGear.whenPressed(new ShiftGear());
		shiftGear.whenReleased(new ShiftGear());
		highButton.whenPressed(new HighGear());

		buttonPanelDiskPush.whenPressed(new DiskPushCommand());
		diskPush.whenPressed(new DiskPushCommand());

		takeSnapshot.whenPressed(new TakeSnapshot());

		// the speed should be made into a constant
		// the speed is just temporary, update when you figure out a good speed

		highLimit.whileActive(new LiftStop());
		lowLimit.whileActive(new LiftZeroEncoder());
		seekTarget.whileHeld(new ApproachTargetTeleop());

		fastBallIn.whileHeld(new BallCommand(Constants.HIGH_BALL, true));
		fastBallOut.whileHeld(new BallCommand(Constants.HIGH_BALL, false));
		fastBallReverse.whileHeld(new BallCommand(-Constants.LOW_BALL, false));
		slowBallReverse.whileHeld(new BallCommand(-Constants.LOW_BALL, true));

		fastBallIn.whenReleased(new StopBall());
		slowBallReverse.whenReleased(new StopBall());
		fastBallOut.whenReleased(new StopBall());
		fastBallReverse.whenReleased(new StopBall());

		toggleBottomPistons.whenPressed(new BottomPistonShift());

		//these don't raise to the right levels because we don't have the proper encoder values
		//update the commands when we get it
		// raiseLevelOne.whenPressed(new RaiseLevelOne());
		// raiseLevelTwo.whenPressed(new RaiseLevelTwo());
		// raiseLevelThree.whenPressed(new RaiseLevelThree());

		rocketLow.whenPressed(new LiftGetHatch());
		// rocketMid.whenPressed(new RocketMidLift());
		// rocketHigh.whenPressed(new RocketHighLift());
		testRightAngle.whenPressed(new WristFixed(90));
		testFlatAngle.whenPressed(new WristFixed(0));
		testWristOverride.whenPressed(new WristDefault());


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

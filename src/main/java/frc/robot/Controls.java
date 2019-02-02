package frc.robot;


import frc.robot.commands.DiskPushCommand;
import frc.robot.commands.HighGear;
import frc.robot.commands.LiftStop;
import frc.robot.commands.LiftZeroEncoder;
import frc.robot.commands.ReportIntakeLimit;
import frc.robot.commands.ShiftGear;
import frc.robot.commands.TakeSnapshot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controls {

	private Joystick playerOne = new Joystick(0);
	private Joystick playerTwo = new Joystick(1);

	private JoystickButton shiftGear = new JoystickButton(playerOne, 3); // X Button

	

	
	
	private JoystickButton toggleGrip = new JoystickButton(playerOne, 1); // A Button
	
	private JoystickButton highButton = new JoystickButton(playerOne, 4); // Y Button
	
	//temporary
	private JoystickButton takeSnapshot = new JoystickButton(playerTwo, 4); // Y Button
	//temporary
	
	
	public LimitSwitch highLimit = new LimitSwitch(Constants.LIMIT_SWITCH_HIGH, true);
	public LimitSwitch lowLimit = new LimitSwitch(Constants.LIMIT_SWITCH_LOW, false);
	public LimitSwitch intakeLimit = new LimitSwitch(Constants.LIMIT_SWITCH_INTAKE, true);
	
	public Controls() {
		shiftGear.whenPressed(new ShiftGear());
		shiftGear.whenReleased(new ShiftGear());
		highButton.whenPressed(new HighGear());
		
		

		toggleGrip.whenPressed(new DiskPushCommand());
		
		takeSnapshot.whenPressed(new TakeSnapshot());
		
		highLimit.whileActive(new LiftStop());
		lowLimit.whileActive(new LiftZeroEncoder());
		intakeLimit.whenPressed(new ReportIntakeLimit());
		intakeLimit.whenReleased(new ReportIntakeLimit());

	

		
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

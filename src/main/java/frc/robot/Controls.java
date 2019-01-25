package frc.robot;

import frc.robot.commands.HighGear;
import frc.robot.commands.ShiftGear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controls {

	private Joystick playerOne = new Joystick(0);

	private JoystickButton shiftGear = new JoystickButton(playerOne, 3); // X Button
	private JoystickButton highButton = new JoystickButton(playerOne, 4); // Y Button
	
	
	public Controls() {
		shiftGear.whenPressed(new ShiftGear());
		shiftGear.whenReleased(new ShiftGear());
		highButton.whenPressed(new HighGear());
	}

	public Joystick getPlayerOne() {
		return playerOne;
	}
	
	public double getDriveVerticalAxis() {
		return playerOne.getRawAxis(1);
	}
	
	public double getDriveHorizontalAxis() {
		return playerOne.getRawAxis(0);
	}
}

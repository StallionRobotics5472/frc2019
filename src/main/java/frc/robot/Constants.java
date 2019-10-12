/*
Class to store constants that don't change over the course of a competition
Use these instead of hard-coding so it can be easily changed across the whole robot
*/
package frc.robot;

public class Constants {

	public static final double LEVEL_ONE_HEIGHT = 420000;
	public static final double LEVEL_TWO_HEIGHT = 0;
	public static final double LEVEL_THREE_HEIGHT = 0;

	public static final double LIFT_LOW_POSITION = 382000;
	public static final double LIFT_MID_POSITION = 0;
	public static final double LIFT_HIGH_POSITION = 0;


	public static final double WHEEL_DIAMETER = 0.17;
	private static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
	public static final int TICKS_PER_REV = 4096;
	public static final double TICKS_PER_METER = TICKS_PER_REV / WHEEL_CIRCUMFERENCE;


	public static final double HIGH_BALL = 0.4;
	public static final double LOW_BALL = 0.8;

	public static final double DISK_SPEED = 0.2;


	// Motor Controllers
	public static final int DRIVE_LEFT_TALON_CAN = 1;
	public static final int DRIVE_LEFT_FOLLOWER_CAN = 2;
	public static final int DRIVE_RIGHT_TALON_CAN = 14;
	public static final int DRIVE_RIGHT_FOLLOWER_CAN = 13;
	public static final int DRIVE_LEFT_FOLLOWER_2_CAN = 4;
	public static final int DRIVE_RIGHT_FOLLOWER_2_CAN = 11;

	public static final int LIFT_TALON_CAN_LEFT = 3;
	public static final int LIFT_TALON_CAN_RIGHT = 12;

	public static final int INTAKE_CAN = 8;

	public static final int ARM_TALON = 6;
	// public static final int WRIST_TALON = 9;
	public static final int DISK_TALON = 9;
	public static final int ARM_TALON_FOLLOWER = 7;


	// Solenoids
	public static final int DRIVE_SHIFT_SOLENOID = 0;

	public static final int PUSH_SOLENOID_FORWARD = 1;
	public static final int PUSH_SOLENOID_REVERSE = 4;

	public static final int BOTTOM_PISTON_SOLENOID_RIGHT = 2;
	public static final int BOTTOM_PISTON_SOLENOID_LEFT = 3;


	// Switches
	public static final int LIMIT_SWITCH_HIGH = 0;
	public static final int LIMIT_SWITCH_LOW = 4;
	public static final int LIMIT_SWITCH_INTAKE = 2;


	// Closed Loop Coefficients
	public static final double DRIVE_AUTO_OUTPUT_LIMIT = 1.0;
	public static final double DRIVE_AUTO_TURN_P = 2.0 / 45.0;
	public static final double DRIVE_AUTO_TURN_I = 0.0000;
	public static final double DRIVE_AUTO_TURN_D = 6.0 / 45.0;

//	public static final double LIMELIGHT_APPROACH_TARGET_MAX_AREA = 5.00;
	public static final double LIMELIGHT_APPROACH_A = 1.3827;
	public static final double LIMELIGHT_APPROACH_B = -1.25017;
	public static final double LIMELIGHT_APPROACH_C = -0.000971;
	public static final double LIMELIGHT_APPROACH_D = -0.03187; // -0.0246

	public static final double LIMELIGHT_HIGH_APPROACH_A = 1.3827;
	public static final double LIMELIGHT_HIGH_APPROACH_B = -1.25017;
	public static final double LIMELIGHT_HIGH_APPROACH_C = -0.000971;
	public static final double LIMELIGHT_HIGH_APPROACH_D = -0.03187; // -0.0246

	public static final double LIMELIGHT_APPROACH_TARGET_TURNP = 0.11 / (5.00); // When the error is 5deg, the turn output will be 0.05
	public static final int LIMELIGHT_HEIGHT_THRESHOLD = 2000000;

	public static final double ARM_PIDF_P = 0.8 / 15.0;  // Output should be 0.4 when the error is 30deg
	public static final double ARM_PIDF_I = 0;
	public static final double ARM_PIDF_D = 0;  //  The high gearing prevents overshooting, no D required

	public static final double WRIST_PIDF_P = 0.8 / 15.0;  // Output should be 0.5 when the error is 30deg and decrease from there
	public static final double WRIST_PIDF_I = 0;
	public static final double WRIST_PIDF_D = 0;  // ^

	public static final double LIFT_PIDF_P = 1.0 / 400000.0;
	public static final double LIFT_PIDF_I = 0.00000;
	public static final double LIFT_PIDF_D = 0.00000;
	public static final double LIFT_PIDF_F = 0.00000;

	public static final double LIFT_REVERSE_OUTPUT_LIMIT = -1;

	public static final double DRIVE_FOLLOWER_P = 1.2;
	public static final double DRIVE_FOLLOWER_I = 0.0;
	public static final double DRIVE_FOLLOWER_D = 6.0;
	public static final double DRIVE_FOLLOWER_F = 0.5;


	// Joystick buttons and Axis
	public static final int Axis_0 = 0;
	public static final int Axis_1 = 1;
	public static final int Axis_2 = 2;
	public static final int Axis_3 = 3;
	public static final int Axis_4 = 4;
	public static final int Axis_5 = 5;
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;
	public static final int SHOULDER_BUTTON_LEFT = 5;
	public static final int SHOULDER_BUTTON_RIGHT = 6;
	public static final int BUTTON_BACK = 7;
	public static final int BUTTON_START = 8;
	public static final int RIGHT_JOYSTICK_PRESS = 9;

	//initalize position encoder values
	public static final int NINETY_WRIST = 1500000;//1766040;
	public static final int NINETY_ARM = -1512277;

	
}
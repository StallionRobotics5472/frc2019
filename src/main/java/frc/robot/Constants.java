/*
Class to store constants that don't change over the course of a competition
Use these instead of hard-coding so it can be easily changed across the whole robot
*/
package frc.robot;

public class Constants {
    //uncertain, CHECK WHEN YOU HAVE ENCODER VALUES
    public static final double LEVEL_ONE_HEIGHT = 500000;
    public static final double LEVEL_TWO_HEIGHT = 100000;
    public static final double LEVEL_THREE_HEIGHT = 1500000;

    public static final double WHEEL_DIAMETER = 0.1016;

    public static final double LEFT_ENCODER_TICKS_PER_METER = 12833;
    public static final double RIGHT_ENCODER_TICKS_PER_METER = 12833;
    public static final int TICKS_PER_REV = 4096;

    public static final double DRIVE_FOLLOWER_P = 1.2;
    public static final double DRIVE_FOLLOWER_I = 0.0;
    public static final double DRIVE_FOLLOWER_D = 6.0;
    public static final double DRIVE_FOLLOWER_F = 0.5;

    // Put all Talons here:

    public static final int DRIVE_LEFT_TALON_CAN = 1;
    public static final int DRIVE_LEFT_FOLLOWER_CAN = 2;
    public static final int DRIVE_RIGHT_TALON_CAN = 14;
    public static final int DRIVE_RIGHT_FOLLOWER_CAN = 13;
    public static final int DRIVE_SHIFT_SOLENOID = 0;

    public static final int INTAKE_CAN = 8;

    public static final double DRIVE_AUTO_OUTPUT_LIMIT = 1.0;
    public static final double DRIVE_AUTO_TURN_P = 2.0 / 45.0;
    public static final double DRIVE_AUTO_TURN_I = 0.0000;
    public static final double DRIVE_AUTO_TURN_D = 6.0 / 45.0;
    public static final double LIMELIGHT_APPROACH_BOX_KP = 0.015;

    public static final int USELESS_SOLENOID = 1;
    public static final int INTAKE_SOLENOID_ID_BACK = 2;
    public static final double INTAKE_INPUT_SPEED = 1.00;
    public static final double INTAKE_INPUT_SLOW_SPEED = 0.40;
    public static final double INTAKE_OUTPUT_SPEED = 1.00;
    public static final double INTAKE_OUTPUT_SLOW_SPEED = 0.40;

    public static final int LIFT_TALON_CAN_LEFT = 3;
    public static final int LIFT_TALON_CAN_RIGHT = 12;
    public static final double LIFT_REVERSE_OUTPUT_LIMIT = -0.5;

    public static final double HIGH_BALL = 0.8;
    public static final double LOW_BALL = 0.5;

    public static final int LIMIT_SWITCH_HIGH = 0;
    public static final int LIMIT_SWITCH_LOW = 4;
    public static final int LIMIT_SWITCH_INTAKE = 2;

    public static final int PUSH_DOUBLESOLENOID1 = 4;
    public static final int PUSH_DOUBLESOLENOID2 = 5;

    public static final int ARM_TALON = 6;
    public static final int WRIST_TALON = 9;
    public static final int ARM_TALON_2 = 7;

    // Correct Solenoid values
    public static final int BOTTOM_PISTON_SOLENOID_RIGHT = 2;
    public static final int BOTTOM_PISTON_SOLENOID_LEFT = 3;

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
    public static final int LEFT_JOYSTICK_PRESS = 10;


    public static final double ARM_PIDF_P = 0.4 / 30.0;  // Output should be 0.4 when the error is 30deg
    public static final double ARM_PIDF_I = 0;
    public static final double ARM_PIDF_D = 0;  //  The high gearing prevents overshooting, no D required
    public static final double ARM_PIDF_F = 0;  //  The arm holds itself up through high gearing, no F required

    public static final double WRIST_PIDF_P = 0.5 / 30.0;  // Output should be 0.5 when the error is 30deg and decrease from there
    public static final double WRIST_PIDF_I = 0;
    public static final double WRIST_PIDF_D = 0;  // ^
    public static final double WRIST_PIDF_F = 0;  // ^


    public static final double LIFT_PIDF_P = 0.6 / 3000.0;  // Output should be 0.7 when the error is 3000
    public static final double LIFT_PIDF_I = 0.05 / 1000;  // For every second with an error of 1000, add 0.1 to the output
    public static final double LIFT_PIDF_D = 0.4 / 3000.0;
    public static final double LIFT_PIDF_F = 0.2;
}

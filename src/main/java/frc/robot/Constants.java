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


	public static final double HIGH_BALL = 0.8;
	public static final double LOW_BALL = 0.8;


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
	public static final int WRIST_TALON = 9;
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

	public	 static final String SICKO_MODE = "Astro, yeah Sun is down, freezin' cold That's how we already know winter's\n  here My dawg would prolly do it for a Louis belt That's just all he know, he\n  don't know nothin' else I tried to show 'em, yeah I tried to show 'em, yeah,\n  yeah Yeah, yeah, yeah Gone on you with the pick and roll Young LaFlame, he in\n  sicko mode Woo, made this here with all the ice on in the booth At the gate\n  outside, when they pull up, they get me loose Yeah, Jump Out boys, that's\n  Nike boys, hoppin' out coupes This shit way too big, when we pull up give me\n  the loot (Gimme the loot!) Was off the Remy, had a Papoose Had to hit my old\n  town to duck the news Two-four hour lockdown, we made no moves Now it's 4AM\n  and I'm back up poppin' with the crew I just landed in, Chase B mixes pop\n  like Jamba Juice Different colored chains, think my jeweler really sellin'\n  fruits And they chokin', man, know the crackers wish it was a noose\n  Some-some-some, someone said To win the retreat, we all in too deep\n  P-p-playin' for keeps, don't play us for weak (someone said) To win the\n  retreat, we all in too deep P-p-playin' for keeps, don't play us for weak\n  (yeah) This shit way too formal, y'all know I don't follow suit Stacey Dash,\n  most of these girls ain't got a clue All of these hoes I made off records I\n  produced I might take all my exes and put 'em all in a group Hit my es\u00E9s, I\n  need the bootch 'Bout to turn this function to Bonnaroo Told her,\n  \"Hop in, you comin' too\" In the 305, bitches treat me like I'm Uncle Luke\n  (Don't stop, pop that pussy!) Had to slop the top off, it's just a roof (uh)\n  She said, \"Where we goin'?\" I said, \"The moon\" We ain't even make it to the\n  room She thought it was the ocean, it's just the pool Now I got her open,\n  it's just the Goose Who put this shit together? I'm the glue (someone said)\n  Shorty FaceTimed me out the blue Someone said (Playin' for keeps) Someone\n  said, motherfuck what someone said (Don't play us for weak) Yeah Astro Yeah,\n  yeah Tay Keith, fuck these niggas up (Ay, ay) She's in love with who I am\n  Back in high school, I used to bus it to the dance (yeah) Now I hit the FBO\n  with duffles in my hands I did half a Xan, thirteen hours 'til I land Had me\n  out like a light, ayy, yeah Like a light, ayy, yeah Like a light, ayy Slept\n  through the flight, ayy Knocked for the night, ayy, 767, man This shit got\n  double bedroom, man I still got scores to settle, man I crept down the block\n  (down the block), made a right (yeah, right) Cut the lights (yeah, what?),\n  paid the price (yeah) Niggas think it's sweet (nah, nah), it's on sight\n  (yeah, what?) Nothin' nice (yeah), baguettes in my ice (aww, man) Jesus\n  Christ (yeah), checks over stripes (yeah) That's what I like (yeah), that's\n  what we like (yeah) Lost my respect, you not a threat When I shoot my shot,\n  that shit wetty like I'm Sheck (bitch!) See the shots that I took (ayy), wet\n  like I'm Book (ayy) Wet like I'm Lizzie, I be spinnin' Valley Circle blocks\n  'til I'm dizzy (yeah, what?) Like where is he? (Yeah, what?) No one seen him\n  (yeah, yeah) I'm tryna clean 'em (yeah) She's in love with who I am Back in\n  high school, I used to bus it to the dance Now I hit the FBO with duffles in\n  my hand (woo!) I did half a Xan, thirteen hours 'til I land Had me out like a\n  light, like a light Like a light, like a light Like a light (yeah), like a\n  light Like a light Yeah, passed the dawgs a celly Sendin' texts, ain't\n  sendin' kites, yeah He said, \"Keep that on lock\" I said,\n  \"You know this shit, it\u2019s life\", yeah It's absolute (yeah), I'm back reboot\n  (it's lit!) LaFerrari to Jamba Juice, yeah (skrrt, skrrt) We back on the\n  road, they jumpin' off, no parachute, yeah Shawty in the back She said she\n  workin' on her glutes, yeah (oh my God) Ain't by the book, yeah This how it\n  look, yeah 'Bout a check, yeah Just check the foots, yeah Pass this to my\n  daughter, I'ma show her what it took (yeah) Baby mama cover Forbes, got these\n  other bitches shook, yeah Ye-ah";
}
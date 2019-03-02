package frc.robot.autonomous;

import frc.robot.autonomous.commands.paths.StraightPath;
import frc.robot.commands.InitializeRobotState;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends CommandGroup{

	public static enum StartingPosition {
		CENTER("Center"), LEFT("Left"), RIGHT("Right"), MOTION_PROFILE("Motion Profile");

		private String name;

		private StartingPosition(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static enum Plan {
		DRIVE_STRAIGHT_MOTION_PROFILE("Motion Profile Straight");

		private String name;

		private Plan(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public static enum Paths {
		STRAIGHT("Straight"), LEFT_ROCKET("Left Rocket"), RIGHT_ROCKET("Right Rocket"), NO_PATHS("No Paths");

		private String name;

		private Paths(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private SendableChooser<StartingPosition> starting = new SendableChooser<>();
	private SendableChooser<Plan> plan = new SendableChooser<>();

	private Command command = null;

	private static SendableChooser<Paths> paths = new SendableChooser<>();

	private ArrayList<String> fileNames = new ArrayList<String>();

	public static SendableChooser<Paths> getPaths() {
		return paths;
	}

	public Autonomous() {
		starting.setDefaultOption(StartingPosition.CENTER.toString(), StartingPosition.CENTER);
		starting.addOption(StartingPosition.LEFT.toString(), StartingPosition.LEFT);
		starting.addOption(StartingPosition.RIGHT.toString(), StartingPosition.RIGHT);
		starting.addOption(StartingPosition.MOTION_PROFILE.toString(), StartingPosition.MOTION_PROFILE);
		plan.setDefaultOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);
		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);

		// paths.addDefault("No path", Paths.NO_PATHS);
		paths.setDefaultOption(Paths.STRAIGHT.toString(), Paths.STRAIGHT);
		paths.addOption(Paths.LEFT_ROCKET.toString(), Paths.LEFT_ROCKET);
		paths.addOption(Paths.RIGHT_ROCKET.toString(), Paths.RIGHT_ROCKET);
		SmartDashboard.putData("Path", paths);

		fileNames.add("/home/lvuser/deploy/Paths/straight");
		fileNames.add("/home/lvuser/deploy/Paths/leftrocket");
		fileNames.add("/home/lvuser/deploy/Paths/rightrocket");
	}

	public void init() {
		StartingPosition startPos = starting.getSelected();
		Plan thePlan = plan.getSelected();

		Paths thePath = paths.getSelected();

		switch (startPos) {
		case CENTER:
			startingCenter(thePlan);
			break;
		case LEFT:
			startingLeft(thePlan);
			break;
		case RIGHT:
			startingRight(thePlan);
			break;
		case MOTION_PROFILE:
			runMotionProfile(thePath);
			break;
		}


		addParallel(new InitializeRobotState());
		// if(command != null)
		// 	addSequential(command);
		
	}

	public void runMotionProfile(Paths path) {
		switch (path) {
		case STRAIGHT:
			command = new StraightPath(fileNames.get(0));
			break;

		case LEFT_ROCKET:
			command = new StraightPath(fileNames.get(1));
			break;

		case RIGHT_ROCKET:
			command = new StraightPath(fileNames.get(2));
			break;
		default:
			System.out.println("You tried to run a motion profile path that doesn't exist!");
		}
	}

	public void startingCenter(Plan task) {

		switch (task) {
		case DRIVE_STRAIGHT_MOTION_PROFILE:
			command = new StraightPath(fileNames.get(0));
			break;
		}
	}

	public void startingLeft(Plan task) {
		switch (task) {

		case DRIVE_STRAIGHT_MOTION_PROFILE:
			command = new StraightPath(fileNames.get(0));
			break;
		}
	}

	public void startingRight(Plan task) {

		switch (task) {

		case DRIVE_STRAIGHT_MOTION_PROFILE:
			command = new StraightPath(fileNames.get(0));
			break;
		}
	}
}
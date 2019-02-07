package frc.robot.autonomous;

import frc.robot.autonomous.commands.paths.StraightPath;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	public static enum StartingPosition{
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
	
	public static enum Plan{
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
	
	public static enum Paths{
		STRAIGHT("Straight"), LEFT_ROCKET("Left Rocket"), LEFT_TURN("Left Turn"), NO_PATHS("No Paths");

		private String name;

		private Paths(String name){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	private SendableChooser<StartingPosition> starting = new SendableChooser<>();
	private SendableChooser<Plan> plan = new SendableChooser<>();
	
	private Command command = null;
	private String gameSpecificData = "";

	private static SendableChooser<Paths> paths = new SendableChooser<>();

	private String[] fileNames = {"/home/lvuser/Paths/straight", "/home/lvuser/Paths/leftrocket", "/home/lvuser/Paths/left_turn"};

	public static SendableChooser<Paths> getPaths(){
		return paths;
	}

	public String getAbsolutePath()
	{
		String name;
		switch(paths.getSelected())
		{
				case STRAIGHT:
					name = fileNames[0];
					break;
	
				case LEFT_ROCKET:
					name = fileNames[1];
					break;
	
				case LEFT_TURN:
					name = fileNames[2];
					break;
				default:
					name = "You tried to run a motion profile path that doesn't exist!";
		}
		return name;
	}

	public Autonomous() {
		starting.setDefaultOption(StartingPosition.CENTER.toString(), StartingPosition.CENTER);
		starting.addOption(StartingPosition.LEFT.toString(), StartingPosition.LEFT);
		starting.addOption(StartingPosition.RIGHT.toString(), StartingPosition.RIGHT);
	
		plan.addOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);
		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);
		
		paths.addDefault("No path", Paths.NO_PATHS);
		paths.addOption(Paths.STRAIGHT.toString(), Paths.STRAIGHT);
		paths.addOption(Paths.LEFT_ROCKET.toString(), Paths.LEFT_ROCKET);
		paths.addOption(Paths.LEFT_TURN.toString(), Paths.LEFT_TURN);
		SmartDashboard.putData("Path", paths);
		SmartDashboard.putString("Location of Path:", getAbsolutePath());
	}

	public void start() {
		StartingPosition startPos = starting.getSelected();
		Plan thePlan = plan.getSelected();
		
		Paths thePath = paths.getSelected();

		SmartDashboard.putString("Game Data", gameSpecificData);
		
		switch(startPos) {
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
		
		if (command != null)
			command.start();
	}
	
	public void runMotionProfile(Paths path)
	{
		switch(path){
			case STRAIGHT:
				command = new StraightPath(fileNames[0]);
				break;

			case LEFT_ROCKET:
				command = new StraightPath(fileNames[1]);
				break;

			case LEFT_TURN:
				command = new StraightPath(fileNames[2]);
				break;
			default:
				System.out.println("You tried to run a motion profile path that doesn't exist!");
		}
	}

	public void startingCenter(Plan task) {
	
		
		switch(task) {
			
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
				break;
		}
	}
	
	public void startingLeft(Plan task) {
		switch(task) {
			
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
				break;
		}
		}
		
		
	public void startingRight(Plan task) {
		
		
		switch(task) {
		
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
				break;
		}
	}

	public void end() {
		if (command != null)
			command.cancel();
	}

	public void checkGameSpecificData() {
		this.gameSpecificData = DriverStation.getInstance().getGameSpecificMessage().toUpperCase();
	}
}
package frc.robot.autonomous;

import frc.robot.autonomous.commands.ApproachTarget;
import frc.robot.autonomous.commands.Delay;
import frc.robot.autonomous.commands.paths.MotionProfile;
import frc.robot.commands.InitializeRobotState;
import frc.robot.commands.JoystickDriveCommand;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends CommandGroup{

	public static enum StartingPosition {
		MOTION_PROFILE("Motion Profile");

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
		FRONT_LEFT_CARGO_MOTION_PROFILE("Front Left Cargo Motion Profile"),
		DRIVE_TEST_MOTION_PROFILE("Test Path");

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
		FRONTLEFTCARGO("Front Left Cargo"), TEST("Test Path");

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

	public Autonomous()	{
	// 	for(Plan p : Plan.values()){
	// 		plan.addOption(p.toString(), p);
	// 	}
		starting.setDefaultOption(StartingPosition.MOTION_PROFILE.toString(), StartingPosition.MOTION_PROFILE);
		plan.setDefaultOption(Plan.FRONT_LEFT_CARGO_MOTION_PROFILE.toString(), Plan.FRONT_LEFT_CARGO_MOTION_PROFILE);
		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);

		// paths.addDefault("No path", Paths.NO_PATHS);
		paths.setDefaultOption(Paths.FRONTLEFTCARGO.toString(), Paths.FRONTLEFTCARGO);
		SmartDashboard.putData("Path", paths);

		fileNames.add("/home/lvuser/deploy/Paths/frontcargoleft");
		fileNames.add("/home/lvusser/deploy/Paths/testpath");
	}

	public void init() {
		StartingPosition startPos = starting.getSelected();
		Plan thePlan = plan.getSelected();

		Paths thePath = paths.getSelected();

		switch (startPos) {
		

		case MOTION_PROFILE:
			runMotionProfile(thePath);
			
			break;
		}


		addParallel(new InitializeRobotState());
		 if(command != null){
			//addSequential(new Delay(6.5));
			// addSequential(command);
		 }
		// addSequential(new ApproachTarget());
		addSequential(new JoystickDriveCommand());
			
		
	}


	public void runMotionProfile(Paths path) {
		switch (path) {
		case FRONTLEFTCARGO:
			command = new MotionProfile(fileNames.get(0));
			break;
		case TEST:
			command = new MotionProfile(fileNames.get(1));

		default:
			System.out.println("You tried to run a motion profile path that doesn't exist!");
		}
	}

	
	
}
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
		SL_FRONT_LEFT_CARGO_MOTION_PROFILE("Starting Left Front Left Cargo Motion Profile"),
		SC_FRONT_LEFT_CARGO_MOTION_PROFILE("Starting Center Front Left Cargo Motion Profile"),
		SR_FRONT_LEFT_CARGO_MOTION_PROFILE("Starting Right Front Left Cargo Motion Profile"),

		SL_FRONT_RIGHT_CARGO_MOTION_PROFILE("Starting Left Front Right Cargo Motion Profile"),
		SC_FRONT_RIGHT_CARGO_MOTION_PROFILE("Starting Center Front Right Cargo Motion Profile"),
		SR_FRONT_RIGHT_CARGO_MOTION_PROFILE("Starting Right Front Right Cargo Motion Profile");

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
		SL_FRONTLEFTCARGO("Starting Left Front Left Cargo"), SC_FRONTLEFTCARGO("Starting Center Front Left Cargo"), SR_FRONTLEFTCARGO("Starting Right Front Left Cargo"),
		SL_FRONTRIGHTCARGO("Starting Left Front Right Cargo"), SC_FRONTRIGHTCARGO("Starting Center Front Right Cargo"), SR_FRONTRIGHTCARGO("Starting Right Front Right Cargo");

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
		plan.setDefaultOption(Plan.SL_FRONT_LEFT_CARGO_MOTION_PROFILE.toString(), Plan.SL_FRONT_LEFT_CARGO_MOTION_PROFILE);
		plan.addOption(Plan.SC_FRONT_LEFT_CARGO_MOTION_PROFILE.toString(), Plan.SC_FRONT_LEFT_CARGO_MOTION_PROFILE);
		plan.addOption(Plan.SR_FRONT_LEFT_CARGO_MOTION_PROFILE.toString(), Plan.SR_FRONT_LEFT_CARGO_MOTION_PROFILE);

		plan.addOption(Plan.SL_FRONT_RIGHT_CARGO_MOTION_PROFILE.toString(), Plan.SL_FRONT_RIGHT_CARGO_MOTION_PROFILE);
		plan.addOption(Plan.SC_FRONT_RIGHT_CARGO_MOTION_PROFILE.toString(), Plan.SC_FRONT_RIGHT_CARGO_MOTION_PROFILE);
		plan.addOption(Plan.SR_FRONT_RIGHT_CARGO_MOTION_PROFILE.toString(), Plan.SR_FRONT_RIGHT_CARGO_MOTION_PROFILE);

		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);

		// paths.addDefault("No path", Paths.NO_PATHS);
		paths.setDefaultOption(Paths.SL_FRONTLEFTCARGO.toString(), Paths.SL_FRONTLEFTCARGO);
		paths.addOption(Paths.SC_FRONTLEFTCARGO.toString(), Paths.SC_FRONTLEFTCARGO);
		paths.addOption(Paths.SR_FRONTLEFTCARGO.toString(), Paths.SR_FRONTLEFTCARGO);

		paths.addOption(Paths.SL_FRONTRIGHTCARGO.toString(), Paths.SL_FRONTRIGHTCARGO);
		paths.addOption(Paths.SC_FRONTRIGHTCARGO.toString(), Paths.SC_FRONTRIGHTCARGO);
		paths.addOption(Paths.SR_FRONTRIGHTCARGO.toString(), Paths.SR_FRONTRIGHTCARGO);

		SmartDashboard.putData("Path", paths);

		fileNames.add("/home/lvuser/deploy/Paths/frontcargoleft/SL_frontcargoleft/SL_frontcargoleft");
		fileNames.add("/home/lvuser/deploy/Paths/frontcargoleft/SC_frontcargoleft/SC_frontcargoleft");
		fileNames.add("/home/lvuser/deploy/Paths/frontcargoleft/SR_frontcargoleft/SR_frontcargoleft");

		fileNames.add("/home/lvuser/deploy/Paths/frontcargoright/SL_frontcargoright/SL_frontcargoright");
		fileNames.add("/home/lvuser/deploy/Paths/frontcargoright/SC_frontcargoright/SC_frontcargoright");
		fileNames.add("/home/lvuser/deploy/Paths/frontcargoright/SR_frontcargoright/SR_frontcargoright");
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
			addSequential(command);
		 }
		// addSequential(new ApproachTarget());
		addSequential(new JoystickDriveCommand()); 
			
		
	}


	public void runMotionProfile(Paths path) {
		switch (path) {
		case SL_FRONTLEFTCARGO:
			command = new MotionProfile(fileNames.get(0));
			break;
		case SC_FRONTLEFTCARGO:
			command = new MotionProfile(fileNames.get(1));
			break;
		case SR_FRONTLEFTCARGO:
			command = new MotionProfile(fileNames.get(2));
			break;

			case SL_FRONTRIGHTCARGO:
			command = new MotionProfile(fileNames.get(3));
			break;
		case SC_FRONTRIGHTCARGO:
			command = new MotionProfile(fileNames.get(4));
			break;
		case SR_FRONTRIGHTCARGO:
			command = new MotionProfile(fileNames.get(5));
			break;

		default:
			System.out.println("You tried to run a motion profile path that doesn't exist!");
		}
	}

	
	
}
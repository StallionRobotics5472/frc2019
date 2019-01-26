package frc.robot.autonomous;

import frc.robot.autonomous.commands.paths.CSWLX;
import frc.robot.autonomous.commands.paths.CSWRX;
import frc.robot.autonomous.commands.paths.ExtremeLeft;
import frc.robot.autonomous.commands.paths.ExtremeRight;
import frc.robot.autonomous.commands.paths.LBOLL;                                                                                                                                                                                                                                                                                                                                                                               

import frc.robot.autonomous.commands.paths.LSCXL;
import frc.robot.autonomous.commands.paths.LSCXR;
import frc.robot.autonomous.commands.paths.LSWLX;
import frc.robot.autonomous.commands.paths.LSWRX;
import frc.robot.autonomous.commands.paths.PassAutoLine;
import frc.robot.autonomous.commands.paths.RBORR;
import frc.robot.autonomous.commands.paths.RSCXL;
import frc.robot.autonomous.commands.paths.RSCXR;
import frc.robot.autonomous.commands.paths.RSWLX;
import frc.robot.autonomous.commands.paths.RSWRX;
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
		SWITCH("Switch only"), SCALE("Scale only"), BOTH("Both Switch and Scale"), STRAIGHT("Drive Straight"), SCALE_OUTER("Outer Scale"), DRIVE_STRAIGHT_MOTION_PROFILE("Motion Profile Straight");
		
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
		STRAIGHT("Straight"), LEFT_ROCKET("Left Rocket"), LEFT_TURN("Left Turn");

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

	public static String filename = "/home/lvuser/Paths/straight";

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
		starting.addOption(StartingPosition.MOTION_PROFILE.toString(), StartingPosition.MOTION_PROFILE);
		plan.setDefaultOption(Plan.BOTH.toString(), Plan.BOTH);
		plan.addOption(Plan.SWITCH.toString(), Plan.SWITCH);
		plan.addOption(Plan.SCALE.toString(), Plan.SCALE);
		plan.addOption(Plan.STRAIGHT.toString(), Plan.STRAIGHT);
		plan.addOption(Plan.SCALE_OUTER.toString(), Plan.SCALE_OUTER);
		plan.addOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);
		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);

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
		boolean rightSwitchOwnership = gameSpecificData.charAt(0) == 'R';
		boolean rightScaleOwnership = gameSpecificData.charAt(1) == 'R';
		
		switch(task) {
			case SWITCH:
				if(rightSwitchOwnership)
					command = new CSWRX();
				else
					command = new CSWLX();
				break;
			case SCALE:
				if(rightScaleOwnership)
					command = new CSWRX();
				else
					command = new CSWLX();
				break;
			case BOTH:
				if(rightScaleOwnership)
					command = new CSWRX();
				else
					command = new CSWLX();
				break;
			case STRAIGHT:
				command = null;
				break;
			case SCALE_OUTER:
				command = null;
				break;
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
				break;
		}
	}
	
	public void startingLeft(Plan task) {
		boolean rightSwitchOwnership = gameSpecificData.charAt(0) == 'R';
		boolean rightScaleOwnership = gameSpecificData.charAt(1) == 'R';
		
		switch(task) {
			case SWITCH:
				if(rightSwitchOwnership)
					command = new LSWRX();
				else
					command = new LSWLX();
				break;
			case SCALE:
				if(rightScaleOwnership)
					command = new LSCXR();
				else
					command = new LSCXL();
				break;
			case BOTH:
				if(rightSwitchOwnership && rightScaleOwnership)
//					command = new LBORR();
					command = new LSCXR();
				else if(rightSwitchOwnership && !rightScaleOwnership)
//					command = new LBORL();
					command = new LSCXL();
				else if (!rightSwitchOwnership && rightScaleOwnership)
//					command = new LBOLR();
					command = new LSCXR();
				else
					command = new LBOLL();
			case STRAIGHT:
				if(rightSwitchOwnership)
					command = new PassAutoLine();
				else
					command = new LSWLX();
				break;
			case SCALE_OUTER:
				if(!rightScaleOwnership)
					command = new ExtremeLeft();
				else if(!rightSwitchOwnership)
					command = new LSWLX();
				else
					command = new PassAutoLine();
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
		}
	}
	
	public void startingRight(Plan task) {
		boolean rightSwitchOwnership = gameSpecificData.charAt(0) == 'R';
		boolean rightScaleOwnership = gameSpecificData.charAt(1) == 'R';
		
		switch(task) {
			case SWITCH:
				if(rightSwitchOwnership)
					command = new RSWRX();
				else
					command = new RSWLX();
				break;
			case SCALE:
				if(rightScaleOwnership)
					command = new RSCXR();
				else
					command = new RSCXL();	
				break;
			case BOTH:
				if(rightSwitchOwnership && rightScaleOwnership)
					command = new RBORR();
				else if(rightSwitchOwnership && !rightScaleOwnership)
//					command = new RBORL();
					command = new RSCXL();
				else if (!rightSwitchOwnership && rightScaleOwnership)
//					command = new RBOLR();
					command = new RSCXR();
				else
//					command = new RBOLL();
					command = new RSCXL();
			case STRAIGHT:
				if(rightSwitchOwnership)
					command = new RSWRX();
				else
					command = new PassAutoLine();
				break;
			case SCALE_OUTER:
				if(rightScaleOwnership)
					command = new ExtremeRight();
				else if(rightSwitchOwnership)
					command = new RSWRX();
				else
					command = new PassAutoLine();
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath(fileNames[0]);
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

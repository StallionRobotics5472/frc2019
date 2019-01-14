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
		CENTER("Center"), LEFT("Left"), RIGHT("Right");
		
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
	
	private SendableChooser<StartingPosition> starting = new SendableChooser<>();
	private SendableChooser<Plan> plan = new SendableChooser<>();
	
	private Command command = null;
	private String gameSpecificData = "";

	public Autonomous() {
		starting.setDefaultOption(StartingPosition.CENTER.toString(), StartingPosition.CENTER);
		starting.addOption(StartingPosition.LEFT.toString(), StartingPosition.LEFT);
		starting.addOption(StartingPosition.RIGHT.toString(), StartingPosition.RIGHT);
		
		plan.setDefaultOption(Plan.BOTH.toString(), Plan.BOTH);
		plan.addOption(Plan.SWITCH.toString(), Plan.SWITCH);
		plan.addOption(Plan.SCALE.toString(), Plan.SCALE);
		plan.addOption(Plan.STRAIGHT.toString(), Plan.STRAIGHT);
		plan.addOption(Plan.SCALE_OUTER.toString(), Plan.SCALE_OUTER);
		plan.addOption(Plan.DRIVE_STRAIGHT_MOTION_PROFILE.toString(), Plan.DRIVE_STRAIGHT_MOTION_PROFILE);
		
		SmartDashboard.putData("Autonomous Starting Position", starting);
		SmartDashboard.putData("Autonomous Task", plan);
		
	}

	public void start() {
		StartingPosition startPos = starting.getSelected();
		Plan thePlan = plan.getSelected();
		
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
		}
		
		if (command != null)
			command.start();
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
				command = new StraightPath();
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

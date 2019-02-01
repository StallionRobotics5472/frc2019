package frc.robot.autonomous;


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
	
	private SendableChooser<StartingPosition> starting = new SendableChooser<>();
	private SendableChooser<Plan> plan = new SendableChooser<>();
	
	private Command command = null;
	private String gameSpecificData = "";

	public Autonomous() {
		
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
			
				
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath();
				break;
		}
	}
	
	public void startingLeft(Plan task) {
		boolean rightSwitchOwnership = gameSpecificData.charAt(0) == 'R';
		boolean rightScaleOwnership = gameSpecificData.charAt(1) == 'R';
		
		switch(task) {
			
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath();
		}
	}
	
	public void startingRight(Plan task) {
		boolean rightSwitchOwnership = gameSpecificData.charAt(0) == 'R';
		boolean rightScaleOwnership = gameSpecificData.charAt(1) == 'R';
		
		switch(task) {
			
			case DRIVE_STRAIGHT_MOTION_PROFILE:
				command = new StraightPath();
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

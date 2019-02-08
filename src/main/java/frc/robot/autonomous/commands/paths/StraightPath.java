package frc.robot.autonomous.commands.paths;

import java.io.File;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;


public class StraightPath extends Command {

	
	private static final double P_CONST = 0.8;
	private static final double D_CONST = 0.3;
	private static final double V_CONST = 0;
	private static final double A_CONST = 0;
	private static final double GYRO_CONST = 0;
	
	EncoderFollower rightEncoder;
	EncoderFollower leftEncoder;
	TimerTask encoderTask;
	
	public StraightPath()
	{
		File leftFilePath = Paths.get("/home/lvuser/HalfPath/halfturn_left_Jaci.csv").toFile();
		Trajectory leftTrajectory = Pathfinder.readFromCSV(leftFilePath);
		File rightFilePath = Paths.get("/home/lvuser/HalfPath/halfturn_right_Jaci.csv").toFile();
		Trajectory rightTrajectory = Pathfinder.readFromCSV(rightFilePath);
		
//		Robot.drive.resetEncoders();
		rightEncoder = new EncoderFollower();
		rightEncoder.configurePIDVA(P_CONST, 0.0, D_CONST, V_CONST, A_CONST);
		rightEncoder.configureEncoder(0, Constants.TICKS_PER_REV, Constants.WHEEL_DIAMETER);
		rightEncoder.setTrajectory(rightTrajectory);
		
		leftEncoder = new EncoderFollower();
		leftEncoder.configurePIDVA(P_CONST, 0.0, D_CONST, V_CONST, A_CONST);
		leftEncoder.configureEncoder(0, Constants.TICKS_PER_REV, Constants.WHEEL_DIAMETER);
		leftEncoder.setTrajectory(leftTrajectory);
		
		encoderTask = new TimerTask() {
			public void run() {
				double leftOutput = leftEncoder.calculate(Robot.drive.getLeftRaw());
				double rightOutput = rightEncoder.calculate(Robot.drive.getRightRaw());
				
				double gyroHeading = Robot.drive.getHeading();
				double desiredHeading = Pathfinder.r2d(leftEncoder.getHeading());  // The heading is equivalent for both left and right
				double headingDiff = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
				
				
				// When we need to turn left, turnValue and headingDiff should be positive.
				double turnValue = headingDiff * GYRO_CONST * (1.0 / 90.0);
				
				rightOutput += turnValue;
				leftOutput -= turnValue;
				
				SmartDashboard.putNumber("Left Output", leftOutput);
				SmartDashboard.putNumber("Right Output", rightOutput);
				SmartDashboard.putNumber("Angle Diff", headingDiff);
				SmartDashboard.putNumber("Actual Heading", Robot.drive.getHeading());
				SmartDashboard.putNumber("Desired Heading", desiredHeading);
				Robot.drive.drive(leftOutput, rightOutput);
				
				
				if(isFinished()) {
					Robot.drive.drive(0, 0);
					this.cancel();
				}
			}
		};
	}
	
	public void end()
	{
		encoderTask.cancel();
		Robot.drive.drive(0, 0);
	}
	
	public void initialize()
	{
		Robot.drive.setBrake();
		Robot.drive.resetEncoders();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(encoderTask, 0, 50L);
		Robot.drive.resetHeading();
	}
	
	protected boolean isFinished() {
		//return leftEncoder.isFinished() && rightEncoder.isFinished();
		return false;
	}
}

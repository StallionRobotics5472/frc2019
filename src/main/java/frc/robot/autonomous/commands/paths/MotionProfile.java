package frc.robot.autonomous.commands.paths;

import java.io.File;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;


public class MotionProfile extends Command {

	
	private double p;
	private double d;
	private double v;
	private double a;
	private double g;

	// private double startingAngle = 90;
	
	private Trajectory leftTrajectory, rightTrajectory;

	private EncoderFollower rightEncoder;
	private EncoderFollower leftEncoder;
	private TimerTask encoderTask;
	
	public MotionProfile(String fileName)
	{
		requires(Robot.drive);
		File leftFilePath = Paths.get(fileName + "_left.csv").toFile();
		leftTrajectory = Pathfinder.readFromCSV(leftFilePath);
		File rightFilePath = Paths.get(fileName + "_right.csv").toFile();
		rightTrajectory = Pathfinder.readFromCSV(rightFilePath);

		initializePath();
	}

	public MotionProfile(Trajectory left, Trajectory right){
		leftTrajectory = left;
		rightTrajectory = right;
		initializePath();
	}
	
	private void initializePath(){
		p = 1;
		d = 0;
		v = 0;
		a = 0;
		g = 0.3;

		rightEncoder = new EncoderFollower();
		rightEncoder.configurePIDVA(p, 0.0, d, v, a);
		rightEncoder.configureEncoder(Robot.drive.getRightRaw(), Constants.TICKS_PER_REV, Constants.WHEEL_DIAMETER);
		rightEncoder.setTrajectory(rightTrajectory);
	
		leftEncoder = new EncoderFollower();
		leftEncoder.configurePIDVA(p, 0.0, d, v, a);
		leftEncoder.configureEncoder(Robot.drive.getLeftRaw(), Constants.TICKS_PER_REV, Constants.WHEEL_DIAMETER);
		leftEncoder.setTrajectory(leftTrajectory);
		
		encoderTask = new TimerTask() {
			public void run() {
				double leftOutput = leftEncoder.calculate(Robot.drive.getLeftRaw());
				double rightOutput = rightEncoder.calculate(Robot.drive.getRightRaw());
				
				double gyroHeading = Robot.drive.getHeading();
				double desiredHeading = Pathfinder.r2d(leftEncoder.getHeading());  // The heading is equivalent for both left and right
				double headingDiff = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
				
				
				// When we need to turn left, turnValue and headingDiff should be positive.
				double turnValue = headingDiff * g * (1.0 / 90.0);
				
				rightOutput += turnValue;
				leftOutput -= turnValue;
				
				SmartDashboard.putNumber("Left Output", leftOutput);
				SmartDashboard.putNumber("Right Output", rightOutput);
				SmartDashboard.putNumber("Angle Diff", headingDiff);
				SmartDashboard.putNumber("Actual Heading", Robot.drive.getHeading());
				SmartDashboard.putNumber("Desired Heading", desiredHeading);
				Robot.drive.drive(leftOutput, rightOutput);
				SmartDashboard.putNumber("AutoRawLeft", Robot.drive.getLeftRaw());
				SmartDashboard.putNumber("AutoRawRight", Robot.drive.getRightRaw());
				SmartDashboard.putNumber("AutoLeftPositon", Robot.drive.getLeftPosition());
				SmartDashboard.putNumber("AutoLeftVelocity", Robot.drive.getLeftVelocity());
				SmartDashboard.putNumber("AutoRightPosition", Robot.drive.getRightPosition());
				SmartDashboard.putNumber("AutoRightVelocity", Robot.drive.getRightVelocity());
				
				
				
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
		return leftEncoder.isFinished() && rightEncoder.isFinished();
		// return false;
	}

	//This method makes it so that the PID values don't reset
	private double createSmashboardNumber(String key, double val)
	{
		double value = SmartDashboard.getNumber(key, val);
		SmartDashboard.putNumber(key, value);
		return value;
	}
}

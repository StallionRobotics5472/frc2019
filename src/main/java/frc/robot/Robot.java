/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;

import frc.robot.autonomous.Autonomous;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LedSubsystem;
import frc.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot implements DataProvider{

	private Autonomous auto;

	public static Controls controls;
	public static DriveSubsystem drive;
	public static IntakeSubsystem intake;
	public static LiftSubsystem lift;
	public static LedSubsystem led;
	public static Limelight limelight;
	public static Cameras cameras;
	private static DataLogger logger;
	
	private AnalogInput pressureSensor;
	ArrayList<Double> positions = new ArrayList<Double>();
    ArrayList<Double> times = new ArrayList<Double>();

	
	@Override
	public void robotInit() {
		drive = new DriveSubsystem();
		intake = new IntakeSubsystem();
		lift = new LiftSubsystem();
		led = new LedSubsystem();
		limelight = new Limelight();
		cameras = new Cameras();
		auto = new Autonomous();
		controls = new Controls();
		logger = new DataLogger();
		
		pressureSensor = new AnalogInput(0);
		
	}

	@Override
	public void disabledInit() {
		auto.end();
		drive.resetEncoders();
		drive.resetHeading();
		drive.drive(0.0, 0.0);
		lift.resetEncoder();
		lift.disableClosedLoop();
		intake.stop();
		logger.end();
		
		limelight.setLed(false);
		if (positions.size() > 0) {
            double[] velocities = new double[positions.size() - 1];
            double[] velTimes = new double[positions.size() - 1];
            double maxVelocity = 0.0;
            for (int i = 0; i < velocities.length; i++) {
                velocities[i] = (positions.get(i + 1) - positions.get(i))
                        / (times.get(i + 1) - times.get(i));
                velTimes[i] = (times.get(i) + times.get(i + 1)) / 2.0;
                if (velocities[i] > maxVelocity)
                    maxVelocity = velocities[i];
            }

            double[] accelerations = new double[velocities.length - 1];
            double[] accelTimes = new double[velocities.length - 1];
            double maxAccel = 0.0;

            for (int i = 0; i < accelerations.length; i++) {
                accelerations[i] = (velocities[i + 1] - velocities[i])
                        / (velTimes[i + 1] - velTimes[i]);
                accelTimes[i] = (velTimes[i] + velTimes[i + 1]) / 2.0;
                if (accelerations[i] > maxAccel)
                    maxAccel = accelerations[i];
            }

            double[] jerks = new double[accelerations.length - 1];
            double maxJerk = 0.0;

            for (int i = 0; i < jerks.length; i++) {
                jerks[i] = (accelerations[i + 1] - accelerations[i])
                        / (accelTimes[i + 1] - accelTimes[i]);
                if (jerks[i] > maxJerk)
                    maxJerk = jerks[i];
            }


            SmartDashboard.putNumber("Max Velocity", maxVelocity);
            SmartDashboard.putNumber("Max Acceleration", maxAccel);
            SmartDashboard.putNumber("Max Jerk", maxJerk);
        }
	}

	@Override
	public void disabledPeriodic() {
		if (auto != null)
			auto.checkGameSpecificData();
		
		SmartDashboard.putNumber("Pressure", getPressure());
		SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
		SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());
		
		limelight.setLed(false);
	}

	@Override
	public void autonomousInit() {
		drive.resetEncoders();
		drive.resetHeading();
		drive.drive(0.0, 0.0);
		lift.resetEncoder();
		lift.autoPeakOutput();
		lift.enableClosedLoop();
		logger.start();
		auto.start();
	}
	double lastVelocity = 0.0;
    double lastAccel = 0.0;
    double lastTime = 0.0;
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		logger.appendData(drive);
		logger.appendData(lift);
		logger.appendData(intake);
		logger.appendData(limelight);
		logger.appendData(led);
		logger.appendData(this);
		logger.writeFrame();
		
		SmartDashboard.putNumber("Lift Closed-Loop Error", lift.getError());
		
		SmartDashboard.putNumber("Pressure", getPressure());
		SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
		SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());
		
		SmartDashboard.putNumber("Left Encoder", drive.getLeftPosition());
		SmartDashboard.putNumber("Right Encoder", drive.getRightPosition());
	

        double time = Timer.getFPGATimestamp();

        double velocity = (drive.getLeftVelocity() + drive.getRightVelocity()) / 2.0;
        double accel = (velocity - lastVelocity) / (time - lastTime);
        double jerk = (accel - lastAccel) / (time - lastTime);
        SmartDashboard.putNumber("Instantaneous Velocity", velocity);
        SmartDashboard.putNumber("\"Instantaneous\" Acceleration", accel);
        SmartDashboard.putNumber("\"Instantaneous\" Jerk", jerk);
        lastAccel = accel;
        lastVelocity = velocity;
		lastTime = time;
	}

	@Override
	public void teleopInit() {
		auto.end();
		limelight.setLed(false);
		drive.resetEncoders();
		drive.resetHeading();
		drive.drive(0.0, 0.0);
		lift.disableClosedLoop();
		lift.teleopPeakOutput();
		drive.highGear();
		logger.start();
		
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		logger.appendData(drive);
		logger.appendData(lift);
		logger.appendData(intake);
		logger.appendData(limelight);
		logger.appendData(led);
		logger.appendData(this);
		logger.writeFrame();
		
		// SmartDashboard.putNumber("Pressure", getPressure());
		// SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
		// SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());
		// SmartDashboard.putNumber("Heading", Robot.drive.getHeading());
		// double time = Timer.getFPGATimestamp();

        // double velocity = (drive.getLeftVelocity() + drive.getRightVelocity()) / 2.0;
        // double accel = (velocity - lastVelocity) / (time - lastTime);
        // double jerk = (accel - lastAccel) / (time - lastTime);
        // SmartDashboard.putNumber("Instantaneous Velocity", velocity);
        // SmartDashboard.putNumber("\"Instantaneous\" Acceleration", accel);
        // SmartDashboard.putNumber("\"Instantaneous\" Jerk", jerk);
        // lastAccel = accel;
        // lastVelocity = velocity;
        // lastTime = time;
	}
	
	@Override
	public void testInit() {
	}
	
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public double getPressure() {
		return (250 * (pressureSensor.getVoltage() / 4.95));
	}
	
	public HashMap<String, double[]> getData(){
		HashMap<String, double[]> toReturn = new HashMap<>();
		toReturn.put("Battery Voltage", new double[] {
				RobotController.getBatteryVoltage()
		});
		toReturn.put("CAN Bus Utilization", new double[] {
				RobotController.getCANStatus().percentBusUtilization
		});
		toReturn.put("Brown Out", new double[] {
				RobotController.isBrownedOut() ? 1.0 : 0.0
		});
		toReturn.put("Pressure", new double[] {
				getPressure()
		});
		toReturn.put("Low Limit Switch", new double[] {
				controls.lowLimit.get() ? 1.0 : 0.0
		});
		toReturn.put("Low Limit Switch", new double[] {
				controls.highLimit.get() ? 1.0 : 0.0
		});
		return toReturn;
	}
}
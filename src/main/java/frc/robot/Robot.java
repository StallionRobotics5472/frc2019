/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.Autonomous;
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.subsystems.*;

import frc.robot.util.DataLogger;
import frc.robot.util.DataProvider;

public class Robot extends TimedRobot implements DataProvider {

    // private Autonomous auto;

    public static Controls controls;
    public static DriveSubsystem drive;
    public static LiftSubsystem lift;
    // public static LedSubsystem led;
    public static Limelight limelight;
    public static Cameras cameras;
    private static DataLogger logger;
    public static DiskPushSubsystem diskPush;
    public static DiskSubsystem disk;
    public static BallSubsystem ball;
    public static ArmSubsystem arm;
    //public static WristSubsystem wrist;
    public static BottomPistonSubsystem bottomPistons;
    private AnalogInput pressureSensor;

    @Override
    public void robotInit() {
        pressureSensor = new AnalogInput(0);
        limelight = new Limelight();
        cameras = new Cameras();
        lift = new LiftSubsystem();
        logger = new DataLogger();
        ball = new BallSubsystem();
        disk = new DiskSubsystem();
        diskPush = new DiskPushSubsystem();
        arm = new ArmSubsystem();
        //wrist = new WristSubsystem();
        bottomPistons = new BottomPistonSubsystem();
        controls = new Controls();
        drive = new DriveSubsystem();
        // auto = new Autonomous();
        Robot.controls.initTargeting();
       
    }

    public void cleanup() {

        drive.resetEncoders();
        drive.resetHeading();
        drive.drive(0.0, 0.0);
        lift.disable();
        arm.disable();
        //wrist.disable();
    }

    @Override
    public void disabledInit() {
        // auto.cancel();
        logger.end();
        cleanup();
        limelight.setLed(false);

        lift.resetEncoder();
        //wrist.resetEncoder();
        arm.resetEncoder();
    }

    @Override
    public void disabledPeriodic() {

        SmartDashboard.putNumber("Pressure", getPressure());
        SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
        SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());

        lift.resetEncoder();
        //wrist.resetEncoder();
        arm.resetEncoder();

        limelight.setLed(false);
    }

    @Override
    public void autonomousInit() {
        cleanup();
        lift.resetEncoder();
        //wrist.resetEncoder();
        arm.resetEncoder();
        // lift.autoPeakOutput();
        // logger.start();
        // auto.init();
        // auto.start();
        new JoystickDriveCommand().start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        logger.appendData(drive);
        logger.appendData(lift);
        logger.appendData(limelight);
        logger.appendData(this);
        logger.writeFrame();

        SmartDashboard.putNumber("Pressure", getPressure());
        SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
        SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());

        SmartDashboard.putNumber("Left Encoder", drive.getLeftPosition());
        SmartDashboard.putNumber("Right Encoder", drive.getRightPosition());
        SmartDashboard.putNumber("Arm Setpoint", Robot.arm.getSetpoint());
        SmartDashboard.putNumber("Lift Encoder", lift.getPosition());

        SmartDashboard.putNumber("Arm Encoder", Robot.arm.getPosition());
        SmartDashboard.putNumber("Arm Output", Robot.arm.getPercentOutput());
    }

    @Override
    public void teleopInit() {
        // auto.cancel();
        limelight.setLed(false);
        cleanup();
        lift.teleopPeakOutput();
        drive.highGear();
        logger.start();
        lift.disable();
        //wrist.disable();
        arm.disable();
        new JoystickDriveCommand().start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        logger.appendData(drive);
        logger.appendData(lift);
        logger.appendData(limelight);
        logger.appendData(this);
        logger.writeFrame();

        SmartDashboard.putNumber("Pressure", getPressure());
        SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
        SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());
        SmartDashboard.putBoolean("Ball Limit", Robot.ball.getLimit());

        //SmartDashboard.putNumber("Wrist Pos", Robot.wrist.getPosition());
        SmartDashboard.putNumber("Arm Pos", Robot.arm.getPosition());
        SmartDashboard.putNumber("Lift Encoder", Robot.lift.getEncoder());
        //SmartDashboard.putNumber("Wrist Encoder", Robot.wrist.getEncoderOutput());
        SmartDashboard.putNumber("Arm Encoder", Robot.arm.getEncoder());


        // SmartDashboard.putNumber("Arm Error", Robot.arm.getPIDController().getError());
        // SmartDashboard.putNumber("Wrist Error", Robot.wrist.getPIDController().getError());
        

        SmartDashboard.putNumber("End Effector Height (m)", Robot.lift.estimateEndEffectorHeight());
        SmartDashboard.putBoolean("At Third Level (CJ)",
                Robot.lift.estimateEndEffectorHeight() < 1.92 && Robot.lift.estimateEndEffectorHeight() > 1.84);
        SmartDashboard.putNumber("Target area", limelight.getTargetArea());
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

    public HashMap<String, double[]> getData() {
        HashMap<String, double[]> toReturn = new HashMap<>();
        toReturn.put("Battery Voltage", new double[] { RobotController.getBatteryVoltage() });
        toReturn.put("CAN Bus Utilization", new double[] { RobotController.getCANStatus().percentBusUtilization });
        toReturn.put("Brown Out", new double[] { RobotController.isBrownedOut() ? 1.0 : 0.0 });
        toReturn.put("Pressure", new double[] { getPressure() });
        toReturn.put("Low Limit Switch", new double[] { controls.lowLimit.get() ? 1.0 : 0.0 });
        toReturn.put("High Limit Switch", new double[] { controls.highLimit.get() ? 1.0 : 0.0 });
        return toReturn;
    }
}

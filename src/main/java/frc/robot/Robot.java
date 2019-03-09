/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.Autonomous;
import frc.robot.subsystems.ArmPIDSubsystem;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.BottomPistonSubsystem;
import frc.robot.subsystems.DiskPushSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LiftPIDSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class Robot extends TimedRobot implements DataProvider {

    private Autonomous auto;

    public static Controls controls;
    public static DriveSubsystem drive;
    public static LiftPIDSubsystem lift;
    // public static LedSubsystem led;
    public static Limelight limelight;
    // public static Cameras cameras;
    private static DataLogger logger;
    public static DiskPushSubsystem diskPush;
    public static BallSubsystem ball;
    public static ArmPIDSubsystem arm;
    public static WristSubsystem wrist;
    public static BottomPistonSubsystem bottomPistons;
    private AnalogInput pressureSensor;

    @Override
    public void robotInit() {
        drive = new DriveSubsystem();
        lift = new LiftPIDSubsystem();
        // led = new LedSubsystem();
        limelight = new Limelight();
        // cameras = new Cameras();
        auto = new Autonomous();
        logger = new DataLogger();
        ball = new BallSubsystem();
        pressureSensor = new AnalogInput(0);
        diskPush = new DiskPushSubsystem();
        arm = new ArmPIDSubsystem();
        wrist = new WristSubsystem();
        bottomPistons = new BottomPistonSubsystem();
        controls = new Controls();
    }

    public void cleanup() {
        drive.resetEncoders();
        drive.resetHeading();
        drive.drive(0.0, 0.0);
        lift.disable();
        arm.disable();
        wrist.disable();
    }

    @Override
    public void disabledInit() {
        auto.cancel();
        logger.end();
        cleanup();
        limelight.setLed(false);

        lift.resetEncoder();
        wrist.resetEncoder();
        arm.resetEncoder();
    }

    @Override
    public void disabledPeriodic() {

        SmartDashboard.putNumber("Pressure", getPressure());
        SmartDashboard.putBoolean("Upper Lift Limit", controls.highLimit.get());
        SmartDashboard.putBoolean("Lower Lift Limit", controls.lowLimit.get());

        lift.resetEncoder();
        wrist.resetEncoder();
        arm.resetEncoder();

        limelight.setLed(false);
    }

    @Override
    public void autonomousInit() {
        cleanup();
        lift.resetEncoder();
        wrist.resetEncoder();
        arm.resetEncoder();
        lift.autoPeakOutput();
//        logger.start();
        auto.init();
        auto.start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
//        logger.appendData(drive);
//        logger.appendData(lift);
//        logger.appendData(limelight);
//        logger.appendData(this);
//        logger.writeFrame();

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
        auto.cancel();
        limelight.setLed(false);
        cleanup();
        lift.teleopPeakOutput();
        drive.highGear();
        logger.start();
        lift.disable();
        wrist.disable();
        arm.disable();
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
        SmartDashboard.putNumber("Heading", Robot.drive.getHeading());
        SmartDashboard.putBoolean("Ball Limit", Robot.ball.getLimit());
        SmartDashboard.putNumber("Arm Encoder", Robot.arm.getPosition());
        SmartDashboard.putNumber("Arm Output", Robot.arm.getPercentOutput());
        SmartDashboard.putNumber("Wrist Encoder", Robot.wrist.getPosition());

        SmartDashboard.putNumber("End Effector Height (m)", Robot.lift.estimateEndEffectorHeight());
        SmartDashboard.putBoolean("At Third Level (CJ)", Robot.lift.estimateEndEffectorHeight() < 1.92 && Robot.lift.estimateEndEffectorHeight() > 1.84);
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
        toReturn.put("Battery Voltage", new double[]{RobotController.getBatteryVoltage()});
        toReturn.put("CAN Bus Utilization", new double[]{RobotController.getCANStatus().percentBusUtilization});
        toReturn.put("Brown Out", new double[]{RobotController.isBrownedOut() ? 1.0 : 0.0});
        toReturn.put("Pressure", new double[]{getPressure()});
        toReturn.put("Low Limit Switch", new double[]{controls.lowLimit.get() ? 1.0 : 0.0});
        toReturn.put("High Limit Switch", new double[]{controls.highLimit.get() ? 1.0 : 0.0});
        return toReturn;
    }
}
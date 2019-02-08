/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.Autonomous;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.DataLogger;
import frc.robot.util.DataProvider;
import frc.robot.util.Vec;

import java.util.HashMap;

public class Robot extends TimedRobot implements DataProvider {

    private Autonomous auto;

    public static Controls controls;
    public static DriveSubsystem drive;
    private static DataLogger logger;

    private AnalogInput pressureSensor;

    @Override
    public void robotInit() {
        drive = new DriveSubsystem();
        auto = new Autonomous();
        controls = new Controls();
        logger = new DataLogger();

        pressureSensor = new AnalogInput(0);
    }

    private void resetBearings() {
        drive.resetEncoders();
        drive.resetHeading();
        drive.drive(0.0, 0.0);
    }

    @Override
    public void disabledInit() {
        auto.end();
        resetBearings();
        drive.stopStateEstimation();
        logger.end();
    }

    @Override
    public void disabledPeriodic() {
        SmartDashboard.putNumber("Pressure", getPressure());
    }

    @Override
    public void autonomousInit() {
        resetBearings();
        logger.start();
        auto.start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        logger.appendData(drive);
        logger.appendData(this);
        logger.writeFrame();

        Vec angulars = drive.getAngularVelocities();
        SmartDashboard.putNumber("Left Velocity", angulars.getX());
        SmartDashboard.putNumber("Rght Velocity", angulars.getY());
    }

    @Override
    public void teleopInit() {
        auto.end();
        resetBearings();
        drive.highGear();
        drive.startStateEstimation();
        logger.start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        logger.appendData(drive);
        logger.appendData(this);
        logger.writeFrame();

        SmartDashboard.putNumber("Heading", Robot.drive.getHeading());
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
        toReturn.put("Battery Voltage", new double[]{
                RobotController.getBatteryVoltage()
        });
        toReturn.put("CAN Bus Utilization", new double[]{
                RobotController.getCANStatus().percentBusUtilization
        });
        toReturn.put("Brown Out", new double[]{
                RobotController.isBrownedOut() ? 1.0 : 0.0
        });
        toReturn.put("Pressure", new double[]{
                getPressure()
        });
        return toReturn;
    }
}

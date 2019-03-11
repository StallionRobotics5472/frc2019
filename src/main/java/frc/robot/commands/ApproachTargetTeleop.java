package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ApproachTargetTeleop extends Command {

    /*
     * Approaches a target given the target is within the camera's fov
     */

    private boolean finished = false;
    private Limelight limelight;
    private DriveSubsystem drive;
    private double targetStamp;
    private double targetLost;
    private double time;

    public ApproachTargetTeleop() {
        requires(Robot.drive);
    }

    @Override
    public void initialize() {
        drive = Robot.drive;
        limelight = Robot.limelight;
        targetStamp = 0.0;
        targetLost = 0.0;
        time = Timer.getFPGATimestamp();
        limelight.setWallTargetPipeline();
    }

    @Override
    public void execute() {
//		if(!limelight.isConnected() && this.timeSinceInitialized() > 1.0) {
//			finished = true;
//			System.out.println("No connection to Limelight Camera");
//			return;
        //Limelight is off and more than a second has passed. Give up.
//		} else if ((this.timeSinceInitialized() > 1.0) && (targetStamp == 0.0) && (Timer.getFPGATimestamp() - targetLost > 0.2)) {
//			finished = true;
//			System.out.println("time" + this.timeSinceInitialized() + " target " + limelight.targetExists());
//			return;
        //No target and more than a second has passed. Give up.
//    }
//		if(!limelight.targetExists()) {
//			targetStamp = 0.0;
//			if(targetLost == 0.0)
//				targetLost = Timer.getFPGATimestamp();
//			System.err.println("Target Lost");
//			return;
//		}
//		if(limelight.targetExists()) {
//		if(true){
//			targetLost = 0.0;
//			if(targetStamp == 0.0)
//				targetStamp = Timer.getFPGATimestamp();
        //TODO: FIX End conditions
        double area = Constants.LIMELIGHT_APPROACH_A * Math.pow(limelight.getTargetArea(), Constants.LIMELIGHT_APPROACH_B)
                + Constants.LIMELIGHT_APPROACH_C * limelight.getTargetArea() + Constants.LIMELIGHT_APPROACH_D;
        double horizontalError = limelight.getHorizontalAngle();
        double turn = horizontalError * Constants.LIMELIGHT_APPROACH_TARGET_TURNP;
        drive.drive(area + turn, area - turn);

        SmartDashboard.putNumber("Drive Right", area - turn);
        SmartDashboard.putNumber("Drive Left", area + turn);
        if(Robot.controls.getPlayerOne().getRawButton(Constants.BUTTON_B) == false) {
            finished = true;
        }
        


//		}
    }

    @Override
    public void end() {
        limelight.disableWallTargetPipeline();
		new JoystickDriveCommand().start();
    }

    protected boolean isFinished() {
        return finished;
    }
}

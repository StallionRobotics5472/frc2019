package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Cameras {
	
	private UsbCamera cam0;
	// private UsbCamera cam1;


	public Cameras() {
		
		cam0 = CameraServer.getInstance().startAutomaticCapture(0);
		cam0.setResolution(320, 240);
		// cam1 = CameraServer.getInstance().startAutomaticCapture(1);
		// cam1.setResolution(320, 240);
	}
	
}

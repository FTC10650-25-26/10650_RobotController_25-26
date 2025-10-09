package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {
    static {

        ThreeWheelIMUConstants.forwardTicksToInches = .001989436789;
        ThreeWheelIMUConstants.strafeTicksToInches = .001989436789;
        ThreeWheelIMUConstants.turnTicksToInches = .001989436789;
        ThreeWheelIMUConstants.leftY = 3.5;
        ThreeWheelIMUConstants.rightY = -3.5;
        ThreeWheelIMUConstants.strafeX = -4.5;
        ThreeWheelIMUConstants.leftEncoder_HardwareMapName = "leftEncoder";
        ThreeWheelIMUConstants.rightEncoder_HardwareMapName = "rightEncoder";
        ThreeWheelIMUConstants.strafeEncoder_HardwareMapName = "strafeEncoder";
        ThreeWheelIMUConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelIMUConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelIMUConstants.strafeEncoderDirection = Encoder.REVERSE;
        ThreeWheelIMUConstants.IMU_HardwareMapName = "imu";
        ThreeWheelIMUConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
//
//        ThreeWheelConstants.forwardTicksToInches = 0.006431607007;
//        ThreeWheelConstants.strafeTicksToInches = 0.006431607007;
//        ThreeWheelConstants.turnTicksToInches = .001989436789;
//        ThreeWheelConstants.leftY = 1;
//        ThreeWheelConstants.rightY = -1;
//        ThreeWheelConstants.strafeX = -2.5;
//        ThreeWheelConstants.leftEncoder_HardwareMapName = "leftFront";
//        ThreeWheelConstants.rightEncoder_HardwareMapName = "rightRear";
//        ThreeWheelConstants.strafeEncoder_HardwareMapName = "rightFront";
//        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
//        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
//        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
//
//        TwoWheelConstants.forwardTicksToInches = 0.006431607007;
//        TwoWheelConstants.strafeTicksToInches = 0.006431607007;
//        TwoWheelConstants.forwardEncoder_HardwareMapName = "leftFront";
//        TwoWheelConstants.strafeEncoder_HardwareMapName = "rightFront";
//        TwoWheelConstants.forwardEncoderDirection = Encoder.REVERSE;
//        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
//
//        TwoWheelConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
//       // TwoWheelConstants.
//
//        TwoWheelConstants.forwardY = 1;
//        TwoWheelConstants.strafeX = -2.5;
    }
}





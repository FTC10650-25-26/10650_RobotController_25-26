package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.constants.ThreeWheelIMUConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.MyChemicalRobot;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.MyChemicalRobot;

public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(5.85)
            .forwardZeroPowerAcceleration(-33.41775)
            .lateralZeroPowerAcceleration(-52.0491666)
            .useSecondaryTranslationalPIDF(false)
            .useSecondaryHeadingPIDF(false)
            .useSecondaryDrivePIDF(true)
            .centripetalScaling(0.000065)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0, 0.01, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(0.8, 0, 0.09, 0))
            .drivePIDFCoefficients(
                    new FilteredPIDFCoefficients(0.0075, 0, 0.0001, 0.6, 0)
            )
            .secondaryDrivePIDFCoefficients(
                    new FilteredPIDFCoefficients(0.001, 0, 0.0001, 0.6, 0)
            );

    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("leftFront")
            .leftRearMotorName("leftRear")
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(80.87332)
            .yVelocity(74.11616);
    public static PinpointConstants localizerConstants =
            new PinpointConstants()
                .forwardPodY(-4)
                .strafePodX(0.5)
                .distanceUnit(DistanceUnit.INCH)
                .hardwareMapName("pinpoint")
                .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
                .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
                .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
//    public static ThreeWheelIMUConstants localizerConstants =
//            new ThreeWheelIMUConstants()
//                    .forwardTicksToInches(.001989436789)
//                    .strafeTicksToInches(.001989436789)
//                    .turnTicksToInches(.001989436789)
//                    .leftPodY(3.5)
//                    .rightPodY(-3.5)
//                    .strafePodX(-4.5)
//                    .leftEncoder_HardwareMapName("leftEncoder")
//                    .rightEncoder_HardwareMapName("rightEncoder")
//                    .strafeEncoder_HardwareMapName("strafeEncoder")
//                    .leftEncoderDirection(Encoder.REVERSE)
//                    .rightEncoderDirection(Encoder.REVERSE)
//                    .strafeEncoderDirection(Encoder.REVERSE)
//                    .IMU_HardwareMapName("imu")
//                    .IMU_Orientation(
//                            new RevHubOrientationOnRobot(
//                                    RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
//                                    RevHubOrientationOnRobot.UsbFacingDirection.UP
//                            )
//                    );

    public static PathConstraints pathConstraints = new PathConstraints(
            0.995,
            500,
            1,
            1
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}
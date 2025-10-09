//package org.firstinspires.ftc.teamcode.pedroPathing;
//
//import com.pedropathing.localization.Localizers;
//import com.pedropathing.follower.FollowerConstants;
//import com.pedropathing.util.CustomFilteredPIDFCoefficients;
//import com.pedropathing.util.CustomPIDFCoefficients;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//public class FConstants {
//    static {
//        FollowerConstants.localizers = Localizers.THREE_WHEEL_IMU;
//
//        FollowerConstants.leftFrontMotorName = "leftFront";
//        FollowerConstants.leftRearMotorName = "leftRear";
//        FollowerConstants.rightFrontMotorName = "rightFront";
//        FollowerConstants.rightRearMotorName = "rightRear";
//
//        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
//        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
//        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
//        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;
//
//        FollowerConstants.mass = 5.85;
//
//        FollowerConstants.xMovement = 80.87332;
//        FollowerConstants.yMovement = 74.11616;
//
//        FollowerConstants.forwardZeroPowerAcceleration = -33.41775;
//        FollowerConstants.lateralZeroPowerAcceleration = -52.0491666; // This is an estimated value; the LZPATuner was broken :(
//
//        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.06,0,0.01,0);
//        FollowerConstants.useSecondaryTranslationalPID = false;
//        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID
//
//        FollowerConstants.headingPIDFCoefficients.setCoefficients(.8,0,0.09,0.001);
//        FollowerConstants.useSecondaryHeadingPID = false;
//        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(5,0,0.08,0.01); // Not being used, @see useSecondaryHeadingPID
//
//        FollowerConstants.drivePIDFCoefficients.setCoefficients(.0075,0,0.0001,0.6,0.001);
//        FollowerConstants.useSecondaryDrivePID = true;
//        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.001,0,0.0001,0.6,0); // Not being used, @see useSecondaryDrivePID
//
//        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
//        FollowerConstants.centripetalScaling = 0.000065;//0.000065
//        //between 65 & 60
//
//        FollowerConstants.pathEndTimeoutConstraint = 500;
//        FollowerConstants.pathEndTValueConstraint = 0.995;
//        FollowerConstants.pathEndVelocityConstraint = 0.1;
//        FollowerConstants.pathEndTranslationalConstraint = 0.1;
//        FollowerConstants.pathEndHeadingConstraint = 0.007;
//    }
//}

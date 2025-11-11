package org.firstinspires.ftc.teamcode.utils;

import static com.sun.tools.javac.jvm.ByteCodes.ret;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.xr.runtime.math.Vector3;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

import java.util.ArrayDeque;

public class MyChemicalRobot {

    public HardwareMap hardwareMap;

    Telemetry telemetry;

    public MyChemicalRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public DcMotor leftFront;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor rightRear;

    public Limelight3A limelight;
    public DcMotorEx wheel1;
    public DcMotorEx wheel2;
    public CRServo intake;
    //public Servo intake2;
    public DcMotorEx belt;

    public IMU imu;

    public GoBildaPinpointDriver pinpoint;

    public CRServo intakePush;

    public Servo shooterServo;

    public OpenCvCamera camera;
    WebcamName webcamName;
    int cameraMonitorViewId = 2131230820;

    double currentLLPoseX = 0;
    double currentLLPoseY = 0;

    double currentImuPose = 0;

    public int weightedAvgLLPoseCapacity = 200;

    public ArrayDeque<Pose> LLPosDeque =  new ArrayDeque<>(weightedAvgLLPoseCapacity);
    //public ArrayDeque<Double> imuPoses = new ArrayDeque<>()


    //double re

    public void initHardware(boolean useMotors) {
        if (useMotors) {

            //drivetrain
            {
                leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
                leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
                rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
                rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

                leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
                rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

                leftFront.setPower(0);
                leftRear.setPower(0);
                rightFront.setPower(0);
                rightRear.setPower(0);

                leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            //outtake
            {
                wheel1 = hardwareMap.get(DcMotorEx.class, "wheelL");
                wheel1.setDirection(DcMotorSimple.Direction.REVERSE);
                //wheel1.setVelocity(0);
                wheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                wheel2 = hardwareMap.get(DcMotorEx.class, "wheelR");
                wheel2.setDirection(DcMotorSimple.Direction.FORWARD);
                //wheel2.setVelocity(0);
                wheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            //intake
            {
                intake = hardwareMap.get(CRServo.class, "intake");
                intake.setDirection(CRServo.Direction.REVERSE);



                shooterServo = hardwareMap.get(Servo.class, "shooterServo");
                shooterServo.setDirection(Servo.Direction.REVERSE);
                shooterServo.scaleRange(0.2211, 0.8);

                belt = hardwareMap.get(DcMotorEx.class, "belt");
                belt.setDirection(DcMotorSimple.Direction.REVERSE);
                belt.setVelocity(0);
                belt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            }


        }


        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Now initialize the IMU with this mounting orientation
        // This sample expects the IMU to be in a REV Hub and named "imu".
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));


        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);//camera block
        pinpoint.setOffsets(4, 0.5, DistanceUnit.INCH);
        pinpoint.recalibrateIMU();
        {
//            webcamName = hardwareMap.get(WebcamName.class, "camera");
//            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        }


    }

    public void loopLimelightPoseData(boolean useLLTelem) {

        double x = 0, y = 0, z=0;
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {

            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            if (useLLTelem) {
                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);

            }
        } else if (useLLTelem) {
            telemetry.addData("Limelight", "No Targets");

        }


        // First, tell Limelight which way your robot is facing
        double hubYaw = imu.getRobotYawPitchRollAngles().getYaw();
        double pinpointYaw = pinpoint.getHeading(AngleUnit.DEGREES);


        double robotYaw =imu.getRobotYawPitchRollAngles().getYaw();//in rads
        limelight.updateRobotOrientation(robotYaw);
        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            if (botpose_mt2 != null) {
                x = botpose_mt2.getPosition().x;
                y = botpose_mt2.getPosition().y;


                if (useLLTelem) {
                    telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
                }
            }
        }
        z =(pinpointYaw*.75)+(hubYaw*.25);




        if (useLLTelem) {
            telemetry.update();
        }

        currentLLPoseX = x;
        currentLLPoseY = y;
        currentImuPose = z;

        LLPosDeque.addLast(getLLPose());
        if (LLPosDeque.size()>=weightedAvgLLPoseCapacity){
            LLPosDeque.removeFirst();
        }


    }

    //non-weighted average
    public Pose getLLPoseSimpleAvg(int avgCount){

        if (avgCount > weightedAvgLLPoseCapacity){
            avgCount = weightedAvgLLPoseCapacity;
        } else if (avgCount == 0 || avgCount < 0){
            return new Pose(currentLLPoseX, currentLLPoseY, currentImuPose);
        }

        Pose sum = new Pose(0,0,0);
        for (Pose pose : LLPosDeque){
            sum = sum.plus(pose);
        }
        Pose avgdPose = sum.div(avgCount);

        return  avgdPose;

    }

    public Pose getLLPoseWeightedAvg(int weightSteps){
        int currentIndex = 0;

        if (weightSteps > weightedAvgLLPoseCapacity){
            weightSteps = weightedAvgLLPoseCapacity;
        } else if (weightSteps == 0 || weightSteps < 0){
            return new Pose(currentLLPoseX, currentLLPoseY, currentImuPose);
        }

        Pose sum = new Pose(0,0,0);
        for (Pose pose : LLPosDeque){
            double mult = Math.pow(.5, currentIndex+1);
            if (currentIndex == 0){
                mult += Math.pow(.5, LLPosDeque.size());
            }
            Pose multedPose = pose.times(mult);
            sum = sum.plus(multedPose);

            currentIndex++;
        }

        return sum;
    }

    public Pose getLLPose(){
        return getLLPoseSimpleAvg(0);
    }



//    public void chooseName(double x, double y,double theta){
//        if (LLPosDeque.size() == maxCapacity){
//            sumLLPosX-= LLPosDeque.
//        }
//
//    }

}

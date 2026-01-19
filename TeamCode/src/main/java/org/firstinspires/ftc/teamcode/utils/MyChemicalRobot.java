package org.firstinspires.ftc.teamcode.utils;

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
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayDeque;

public class MyChemicalRobot {

    public HardwareMap hardwareMap;

    Telemetry telemetry;

    public MyChemicalRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public DcMotorEx leftFront;
    public DcMotorEx leftRear;
    public DcMotorEx rightFront;
    public DcMotorEx rightRear;

    public boolean isOverridingMotorControl = false;
    public double findTagStartingHeading = 0;
    public double STARTING_HEADING_RELATIVE_TO_OPPOSITE_GOAL_WALL = (-3*Math.PI)/2; // starting heading as viewed by looking at the goals from the opposite wall, can change per auto

    public Limelight3A limelight;
    public DcMotorEx wheel1;
    public DcMotorEx wheel2;
    public DcMotor intake;
    //public Servo intake2;
    public DcMotorEx belt;

    public IMU imu;

    public GoBildaPinpointDriver pinpoint;

    public CRServo intakePush;

    public Servo pusherServo;

    public Servo shooterServo;

    public Servo LED;

    public OpenCvCamera camera;
    WebcamName webcamName;
    int cameraMonitorViewId = 2131230820;

    public boolean found = true;

    double currentLLPoseX = 0;
    double currentLLPoseY = 0;

    double currentImuPose = 0;

    public int weightedAvgLLPoseCapacity = 200;

    public ArrayDeque<Pose> LLPosDeque =  new ArrayDeque<>(weightedAvgLLPoseCapacity);


    public double encoderDegrees;

    public LLResult result;

    public double tx = 0;
    public double ty = 0;

    public int turnSignAlign;
    public double txTele = 0;

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

                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                //
            }

            //outtake
            {
                wheel1 = hardwareMap.get(DcMotorEx.class, "wheelL");
                wheel1.setDirection(DcMotorSimple.Direction.FORWARD);
                //wheel1.setVelocity(0);
                wheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                wheel2 = hardwareMap.get(DcMotorEx.class, "wheelR");
                wheel2.setDirection(DcMotorSimple.Direction.REVERSE);
                //wheel2.setVelocity(0);
                wheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);



                intake = hardwareMap.get(DcMotorEx.class, "intake");
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
                intake.setPower(0);
                intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



            }

            //intake
            {
//                intake = hardwareMap.get(CRServo.class, "intake");
//                intake.setDirection(CRServo.Direction.REVERSE);

                pusherServo = hardwareMap.get(Servo.class, "pusherServo");
                pusherServo.setDirection(Servo.Direction.FORWARD);
                pusherServo.scaleRange(0.0917, 0.3065);

//                LED = hardwareMap.get(Servo.class, "LED");
//                pusherServo.setDirection(Servo.Direction.FORWARD);
//

                shooterServo = hardwareMap.get(Servo.class, "shooterServo");
                shooterServo.setDirection(Servo.Direction.REVERSE);
                shooterServo.scaleRange(0.1011, 0.9228);


                belt = hardwareMap.get(DcMotorEx.class, "belt");
                belt.setDirection(DcMotorSimple.Direction.REVERSE);
                belt.setVelocity(0);
                belt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            }


        }

//
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
//        limelight.start(); // This tells Limelight to start looking!

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
        pinpoint.initialize();
        {
//            webcamName = hardwareMap.get(WebcamName.class, "camera");
//            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        }


    }
//
//    public void loopLimelightPoseData(boolean useLLTelem) {
//
//        double x = -Integer.MAX_VALUE, y = -Integer.MAX_VALUE, z= 0;
//        LLResult result = limelight.getLatestResult();
//        if (result != null && result.isValid()) {
//
//            tx = result.getTx(); // How far left or right the target is (degrees)
//            ty = result.getTy(); // How far up or down the target is (degrees)
//            double ta = result.getTa(); // How big the target looks (0%-100% of the image)
//
//            if (useLLTelem) {
//                telemetry.addData("Target X", tx);
//                telemetry.addData("Target Y", ty);
//                telemetry.addData("Target Area", ta);
//
//            }
//        } else if (useLLTelem) {
//            telemetry.addData("Limelight", "No Targets");
//
//        }
//
//
//        // First, tell Limelight which way your robot is facing
//        //double hubYaw = imu.getRobotYawPitchRollAngles().getYaw();
//        double pinpointYaw = pinpoint.getHeading(AngleUnit.RADIANS);
//
//
//        double robotYaw =imu.getRobotYawPitchRollAngles().getYaw();//in rads
//        z =(pinpointYaw*.75)+(robotYaw*.25);
//
//        limelight.updateRobotOrientation(pinpointYaw);
//        if (result != null && result.isValid()) {
//            Pose3D botpose_mt1 = result.getBotpose();
//
//            if (botpose_mt1 != null) {
//
//                x = botpose_mt1.getPosition().x;
//                y = botpose_mt1.getPosition().y;
//
//
//                if (useLLTelem) {
//                    telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
//                }
//            }
//
//        }
//
//        if (useLLTelem) {
//            telemetry.update();
//        }
//
//
//        currentLLPoseX = x;
//        currentLLPoseY = y;
//        currentImuPose = z;
//
//        LLPosDeque.addLast(getLLPose());
//        if (LLPosDeque.size()>=weightedAvgLLPoseCapacity){
//            LLPosDeque.removeFirst();
//        }
//
//
//    }
//
//
//    //non-weighted average
//    public Pose getLLPoseSimpleAvg(int avgCount){
//
//        if (avgCount > weightedAvgLLPoseCapacity){
//            avgCount = weightedAvgLLPoseCapacity;
//        } else if (avgCount == 0 || avgCount < 0){
//            return new Pose(currentLLPoseX, currentLLPoseY, currentImuPose);
//        }
//
//        Pose sum = new Pose(0,0,0);
//        for (Pose pose : LLPosDeque){
//            sum = sum.plus(pose);
//        }
//        Pose avgdPose = sum.div(avgCount);
//
//        return  avgdPose;
//
//    }
//
//    public Pose getLLPoseWeightedAvg(int weightSteps){
//        int currentIndex = 0;
//
//        if (weightSteps > weightedAvgLLPoseCapacity){
//            weightSteps = weightedAvgLLPoseCapacity;
//        } else if (weightSteps == 0 || weightSteps < 0){
//            return new Pose(currentLLPoseX, currentLLPoseY, currentImuPose);
//        }
//
//        Pose sum = new Pose(-Integer.MAX_VALUE,-Integer.MAX_VALUE,0);
//        for (Pose pose : LLPosDeque){
//
//            // If the value of the Pose ia BAD (-Integer.MAX_VALUE), we skip it
//            if(pose.getX() == -Integer.MAX_VALUE && pose.getY()== -Integer.MAX_VALUE ){
//                //nothing happens
//            } else{
//                if (sum.getX()==Integer.MAX_VALUE && sum.getY()== -Integer.MAX_VALUE){
//                    sum = new Pose(0,0,0);
//                }
//                double mult = Math.pow(.5, currentIndex+1);
//                if (currentIndex == 0){
//                    mult += Math.pow(.5, LLPosDeque.size());
//                }
//                Pose multedPose = pose.times(mult);
//                sum = sum.plus(multedPose);
//
//                currentIndex++;
//            }
//        }
//
//        return sum;
//    }
//
//    public Pose getLLPose(){
//        return getLLPoseSimpleAvg(0);
//    }
//
//
////   // public double getServoDegrees(double servoPos, Color color){
////        servoPos = servo.getPosition();
////        encoderDegrees = (servo.getPosition/1)*1800;
////        while(encoderDegrees>360){
////            encoderDegrees-=360;
////        }
////        encoderDegrees = encoderDegrees + pinpoint.getHeading(AngleUnit.DEGREES);
////
////    }
//
//    public final int BLUE_TAG_ID = 20;
//    public final int RED_TAG_ID = 24;
//
//
//    public void findTag(Color color){
//
//        if (color== Color.RED) {
//            //LimelightHelpers.SetFiducialIDFiltersOverride("limelight", BLUE_TAG_ID);
//            limelight.pipelineSwitch(1);
//
//            //double turnAngle = Math.atan(pinpoint.getPosX(DistanceUnit.INCH)/pinpoint.getPosY(DistanceUnit.INCH));
//
//        }else if (color==Color.BLUE){
//            limelight.pipelineSwitch(0);
//        }
//        if (limelight.getLatestResult()==null){
//            //shooterServo.setPosition(.getPosition()+ Math.copySign(0.005, tx));
//        }
//    }
//
//    public void findTagTele(Color color){
//       // double heading = pinpoint.getHeading(AngleUnit.DEGREES);
//        if (found==true){
//            //isOverridingMotorControl = false;
//            return;
//        }
//
//        LLResult latestResult = limelight.getLatestResult();
//        if(latestResult.isValid()){
//            centerTagInView(color);
////            isOverridingMotorControl = false;
////            leftFront.setVelocity(0);
////            rightFront.setVelocity(0);
////            leftRear.setVelocity(0);
////            rightRear.setVelocity(0);
//            return;
//
//        }
//        //isOverridingMotorControl = true;
//
//        if (color== Color.RED) {
//            limelight.pipelineSwitch(1);
//        }else if (color==Color.BLUE){
//            limelight.pipelineSwitch(0);
//        }
//        //double error = Math.abs(-pinpoint.getHeading(AngleUnit.RADIANS)-Math.PI);
//       // double error = Math.PI-Math.abs(-pinpoint.getHeading(AngleUnit.RADIANS)-Math.PI);
//        double error = Math.abs(-pinpoint.getHeading(AngleUnit.RADIANS)-Math.PI);
//
//
//        double magnitude = (290* error)+150;
//
//
//        //double sign = 1;
//        // TODO: Add offset for the starting heading of the auto. The "0" that this function is comparing itself to should be offset based on where the robot was rotated starting-wise.
////        if(findTagStartingHeading < -Math.PI) {//STARTING_HEADING_RELATIVE_TO_OPPOSITE_GOAL_WALL > 0) {
////            sign = -1;
////        }
////        if (findTagStartingHeading<-Math.PI && findTagStartingHeading>-(3*(Math.PI/2))){
////            magnitude = 800;
////        }
//        leftFront.setVelocity(-magnitude*turnSignAlign);
//        rightFront.setVelocity(magnitude*turnSignAlign);
//        leftRear.setVelocity(-magnitude*turnSignAlign);
//        rightRear.setVelocity(magnitude*turnSignAlign);
//
//
//    }
//
//    public void centerTagInView(Color color) {
//        double shift = 8;
//
//        if (color== Color.RED) {
//            shift = 12;
//        }else if (color==Color.BLUE){
//            shift = -22;
//        }
//        LLResult result = limelight.getLatestResult();
//
//        // 1. Find the position of the April Tag in the camera space
//
//        double tx = result.getTx()+shift;
//        telemetry.addData("TXXXXXX", tx);
//
//        // 2. Calculate the horizontal offset from the center
//        double offset = tx;
//
//        // 3. Find sign (left or right)
//        double sign = -tx/Math.abs(tx);
//
//        // 4. Estimate velocity needed based on distance from center
//        double velocity = Math.abs(tx)*12+10;
//        if (Math.abs(tx)<2.5){
//
//            velocity=0;
//            isOverridingMotorControl=false;
//            found= true;
//
//            telemetry.addData("tx < 2 ???", tx);
//            telemetry.update();
//
//        }
//
//        // 5. Apply motor velocities
//
//        leftFront.setVelocity(-velocity*sign);
//        rightFront.setVelocity(velocity*sign);
//        leftRear.setVelocity(-velocity*sign);
//        rightRear.setVelocity(velocity*sign);
//        // 6. Zero velocities if the distance is within threshold
//
//        telemetry.addData("tx", tx);
//        telemetry.addData("sign", sign);
//        telemetry.addData("velocity", velocity);
//        telemetry.update();
//
//
//    }
//
//    public void findTagTelePinpoint(Color color){
//        double heading  = pinpoint.getHeading(AngleUnit.DEGREES);
//        heading = heading+180;
//        double turnAngle = 0;
//
//        LLResult latestResult = limelight.getLatestResult();
//        if(latestResult.isValid()){
//            isOverridingMotorControl = false;
//            leftFront.setVelocity(0);
//            rightFront.setVelocity(0);
//            leftRear.setVelocity(0);
//            rightRear.setVelocity(0);
//            return;
//        }
//
//        isOverridingMotorControl = true;
//
//        if (color== Color.RED) {
//            limelight.pipelineSwitch(1);
//            turnAngle = Math.atan(pinpoint.getPosY(DistanceUnit.INCH)/(144-pinpoint.getPosX(DistanceUnit.INCH)));
//
//        }else if (color==Color.BLUE){
//            limelight.pipelineSwitch(0);
//            turnAngle = 360-Math.atan((144-pinpoint.getPosY(DistanceUnit.INCH))/(144-pinpoint.getPosX(DistanceUnit.INCH)));
//        }
//
//        double maxFlipped = Math.abs(Math.max(turnAngle, heading)-360);
//        if((heading>turnAngle)||(turnAngle>heading+180)){//counterclock
//
//        } //else if ((heading<=turnAngle)-){
//
//        //}
//            //counterclock
////        if (pinpoint.getHeading(AngleUnit.DEGREES)>-90 && pinpoint.getHeading(AngleUnit.DEGREES)<90 && limelight.getLatestResult()==null){
////            leftFront.setVelocity(200);
////            rightFront.setVelocity(200);
////            leftRear.setVelocity(-200);
////            rightRear.setVelocity(-200);
////        }else if ((heading>=90 && heading<180) || (heading<=-90 && heading>=-180) && limelight.getLatestResult()==null){
//        leftFront.setVelocity(-400);
//        rightFront.setVelocity(400);
//        leftRear.setVelocity(-400);
//        rightRear.setVelocity(400);
////        }
//
//
//    }
//
//    public enum Color{
//        RED,BLUE
//    }
////    public void chooseName(double x, double y,double theta){
////        if (LLPosDeque.size() == maxCapacity){
////            sumLLPosX-= LLPosDeque.
////        }
////
////    }
//
////    public double getAlignAngle(Color color){
////        pinpoint.update();
////        double xPos = pinpoint.getPosX(DistanceUnit.INCH);
////        double yPos = pinpoint.getPosY(DistanceUnit.INCH);
////        double angle = pinpoint.getHeading(AngleUnit.RADIANS);
////        pinpoint.update();
////        double alignAngle = 0;
////
////        if (color==Color.RED){
////            alignAngle = Math.atan((144-xPos)/(144-yPos));
////        }else if (color==Color.BLUE){
////            alignAngle = -Math.atan(xPos/(144-yPos));
////        }
////
////        return alignAngle;
////    }
//
//
//    public double getAlignAngle(Color color) {
//        pinpoint.update();
//        double xPos = pinpoint.getPosX(DistanceUnit.INCH);
//        double yPos = pinpoint.getPosY(DistanceUnit.INCH);
//        double angle = pinpoint.getPosition().getHeading(AngleUnit.RADIANS);
//        pinpoint.update();
//        double alignAngle = 0;
//
//        if (color == Color.RED) {
//            alignAngle = Math.atan((144 - xPos) / (144 - yPos));
//        } else if (color == Color.BLUE) {
//            alignAngle = -Math.atan(xPos / (144 - yPos));
//        }
//
//        return alignAngle;
//    }
//
//    public int initTurnSign(double heading) {
//        int sign;
//
//            if (heading >= 3.14){
//
//                //turn left
//                sign= 1;
//
//            } else {
//
//                //turn right
//                sign= -1;
//
//            }
//
//        return sign;
//    }



}


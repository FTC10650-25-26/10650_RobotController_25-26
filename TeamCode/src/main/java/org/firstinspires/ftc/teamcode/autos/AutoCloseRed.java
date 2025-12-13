package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "Auto close red", preselectTeleOp = "Meet1Tele")
public class AutoCloseRed extends LinearOpMode {
    public ElapsedTime time = new ElapsedTime();

    Follower follower;
    public DcMotor leftFront;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor rightRear;

    public DcMotorEx wheel1;
    public DcMotorEx wheel2;
    public Servo shooterServo;
    public DcMotorEx belt;



    @Override
    public void runOpMode() throws InterruptedException {
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);


        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);



        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition(0);
        leftRear.setTargetPosition(0);
        rightFront.setTargetPosition(0);
        rightRear.setTargetPosition(0);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterServo.setDirection(Servo.Direction.REVERSE);
        shooterServo.scaleRange(0.2678, 0.790);


        belt = hardwareMap.get(DcMotorEx.class, "belt");
        belt.setDirection(DcMotorSimple.Direction.REVERSE);
        belt.setVelocity(0);
        belt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        time.reset();
//        while (time.seconds()<30){
//            move(0.7, 1095, -964, -982, 1084);
//            wait(500);
//        }
//        move(0.3, -622, 557, 612, -538);
//        //sleep(3000);
//        move(0.3, 484, 475, 479, 479);
//
//        move(0.3, 62, -59, 61, -56);
//        startUpWheels(0, 6, 15);



        //move(0.3, -479, 445, 487, -477);

        //move(0.2, -582, 571, 566, -524);
        //move(0.2, -589, 543, 593, -582);
        //move(0.2, -882, 906, 936, -772);
        move(0.2, -819, 733, 790, -792);
        leftFront.setPower(0);
        rightFront.setPower(0);

        leftRear.setPower(0);
        rightRear.setPower(0);
        sleep(3000);


        shooterServo.setPosition(0.2009);
        startUpWheels(0, 6, 15);







//
//        while(((Math.abs(leftFront.getCurrentPosition())+Math.abs(rightFront.getCurrentPosition())+Math.abs(leftRear.getCurrentPosition())+Math.abs(rightRear.getCurrentPosition()))/4)<800&& opModeIsActive()){
//            leftFront.setPower(.25);
//            leftRear.setPower(-.25);
//            rightFront.setPower(-.25);
//            rightRear.setPower(.25);
//        }



        //telemetry.addData("viewID", hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
       // telemetry.addData("Webcam Name",hardwareMap.get(WebcamName.class, "NAME_OF_CAMERA_IN_CONFIG_FILE"));

        telemetry.update();

        //telemetry.addData("avg pos", ((Math.abs(leftFront.getCurrentPosition())+Math.abs(rightFront.getCurrentPosition())+Math.abs(leftRear.getCurrentPosition())+Math.abs(rightRear.getCurrentPosition()))/4));










//        leftFront = hardwareMap.get(DcMotorEx.class, leftFrontMotorName);
//        leftRear = hardwareMap.get(DcMotorEx.class, leftRearMotorName);
//        rightRear = hardwareMap.get(DcMotorEx.class, rightRearMotorName);
//        rightFront = hardwareMap.get(DcMotorEx.class, rightFrontMotorName);
//        leftFront.setDirection(leftFrontMotorDirection);
//        leftRear.setDirection(leftRearMotorDirection);
//        rightFront.setDirection(rightFrontMotorDirection);
//        rightRear.setDirection(rightRearMotorDirection);

    }
    public void startUpWheels(double setVel, double durationSpin, double durationBelt){
        time.reset();
        setVel = 0;
        while (wheel1.getVelocity()<1345 && time.seconds()<durationSpin && opModeIsActive()){
            setVel +=40;
            if (setVel>1345){
                setVel=1345;
            }
            wheel1.setVelocity(setVel);
            wheel2.setVelocity((setVel));

        }
        time.reset();
        while(time.seconds()<durationBelt && opModeIsActive()){
            belt.setVelocity(900);
        }


    }

    public void move(double power, int setPosFR, int setPosFL, int setPosBR, int setPosBL){
        time.reset();
        telemetry.addLine("1");
        telemetry.update();
        rightFront.setTargetPosition(setPosFR);
        leftFront.setTargetPosition(setPosFL);
        rightRear.setTargetPosition(setPosBR);
        leftRear.setTargetPosition(setPosBL);

        rightFront.setPower(power);
        leftFront.setPower(power);
        rightRear.setPower(power);
        leftRear.setPower(power);

        while (
                (rightFront.isBusy()&&leftFront.isBusy()&&rightRear.isBusy()&&leftRear.isBusy())&&time.seconds()<7 && opModeIsActive()) {
            // Display it for the driver.
            telemetry.addData("Running to",  leftFront.getTargetPosition());
            telemetry.addData("Currently at",  leftFront.getCurrentPosition());
            telemetry.update();
        }
        leftFront.setPower(0);
        leftFront.setPower(0);
        leftFront.setPower(0);
        leftFront.setPower(0);


        telemetry.addLine("reached");
//        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
////        double differenceFR = Math.abs(currentPosFR-setPosFR);
////        double differenceFL = Math.abs(currentPosFL-setPosFL);
////        double differenceBR = Math.abs(currentPosBR-setPosBR);
////        double differenceBL = Math.abs(currentPosBL-setPosBL);
//        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.update();

    }
}

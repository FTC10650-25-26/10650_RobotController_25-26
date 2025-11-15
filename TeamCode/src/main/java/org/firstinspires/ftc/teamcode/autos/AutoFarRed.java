package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto far red", preselectTeleOp = "Meet1Tele")
public class AutoFarRed extends LinearOpMode {
    public ElapsedTime time = new ElapsedTime();

    Follower follower;
    public DcMotor leftFront;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor rightRear;

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


        waitForStart();
        time.reset();
//        while (time.seconds()<30){
//            move(0.7, 1095, -964, -982, 1084);
//            wait(500);
//        }
        move(0.3, 249, -264, -254, 258);
        //sleep(3000);
        move(0.3, 991, 787, 778, 1008);



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
                (rightFront.isBusy()&&leftFront.isBusy()&&rightRear.isBusy()&&leftRear.isBusy())&&time.seconds()<7) {
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
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        double differenceFR = Math.abs(currentPosFR-setPosFR);
//        double differenceFL = Math.abs(currentPosFL-setPosFL);
//        double differenceBR = Math.abs(currentPosBR-setPosBR);
//        double differenceBL = Math.abs(currentPosBL-setPosBL);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.update();

    }
}

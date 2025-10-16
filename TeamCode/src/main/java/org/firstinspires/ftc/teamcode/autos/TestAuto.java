package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous(name = "Test Auto")
public class TestAuto extends LinearOpMode {
    public ElapsedTime time = new ElapsedTime();

    Follower follower;

    @Override
    public void runOpMode() throws InterruptedException {
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);

        DcMotorEx leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        DcMotorEx leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        DcMotorEx rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

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

        waitForStart();
        time.reset();
        while (time.seconds()<7){
            leftFront.setPower(0.6);
            leftRear.setPower(0.6);
            rightFront.setPower(0.6);
            rightRear.setPower(0.6);
        }
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);

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
}

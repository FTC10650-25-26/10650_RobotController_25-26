package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;

@Disabled
@TeleOp (name = "servo testing")
public class ServoTele extends LinearOpMode {
    Servo servo;

    boolean beltOn = false;

    DcMotorEx belt;

    CRServo intake;
    DcMotorEx wheel1;
    DcMotorEx wheel2;
    int wheelVel = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        init();

        servo = hardwareMap.get(Servo.class,"shootservo");
        //servo.setPosition(1);
        servo.setDirection(Servo.Direction.REVERSE);

        wheel1 = hardwareMap.get(DcMotorEx.class, "wheel1");
        wheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        wheel1.setVelocity(0);
        wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        wheel2 = hardwareMap.get(DcMotorEx.class, "wheel2");
        wheel2.setDirection(DcMotorSimple.Direction.REVERSE);
        wheel2.setVelocity(0);
        wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        intake = hardwareMap.get(CRServo.class, "intake");
        intake.setDirection(CRServo.Direction.REVERSE);

        belt = hardwareMap.get(DcMotorEx.class, "belt");
        belt.setDirection(DcMotorSimple.Direction.REVERSE);
        belt.setVelocity(0);
        belt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

         while (opModeIsActive()){
             if (gamepad1.dpad_up){
                 servo.setPosition(servo.getPosition()+0.001);
             } else if (gamepad1.dpad_down){
                 servo.setPosition((servo.getPosition()-0.001));
             }

             if(gamepad1.dpad_right){//shoot
                 if (wheelVel<=2200) {/// <- this number is the max shooting speed
                     wheelVel = wheelVel + 20;
                 }else {
                     wheelVel = 2200;
                 }
             }
             if(gamepad1.dpad_left){
                 if (wheelVel>=20) {/// <- this number is the max shooting speed
                     wheelVel = wheelVel - 20;
                 }else {
                     wheelVel = 0;
                 }
             }

             if (gamepad1.square){
               intake.setPower(.5);
             } else{
                intake.setPower(0);
             }

             if (gamepad2.crossWasPressed()) { //belt speed set at very top, only change it there
                 if (!beltOn) {
                     beltOn = true;
                     belt.setVelocity(1200);
                 } else if (beltOn) {
                     belt.setVelocity(0);
                     beltOn = false;
                 }
             }

             wheel1.setVelocity(wheelVel);
             wheel2.setVelocity(wheelVel);

             telemetry.addData("wheel vel",wheelVel);
             telemetry.addData("actual wheel 1", wheel1.getVelocity());
             telemetry.addData("actual wheel 2", wheel2.getVelocity());
             telemetry.addLine();
             telemetry.addData("differemce",  Math.abs(wheel1.getVelocity()-wheel2.getVelocity()));



             telemetry.addData("servo pos", servo.getPosition());
             telemetry.update();


         }

    }



}


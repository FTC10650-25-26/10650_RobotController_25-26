package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "servo testing")
public class ServoTele extends LinearOpMode {
    Servo servo;

    DcMotorEx wheel1;
    DcMotorEx wheel2;
    int wheelVel = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        init();

        servo = hardwareMap.get(Servo.class,"shootservo");
        servo.setPosition(1);
        servo.setDirection(Servo.Direction.FORWARD);

        wheel1 = hardwareMap.get(DcMotorEx.class, "wheelL");
        wheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        wheel1.setVelocity(0);
        wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        wheel2 = hardwareMap.get(DcMotorEx.class, "wheelR");
        wheel2.setDirection(DcMotorSimple.Direction.REVERSE);
        wheel2.setVelocity(0);
        wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

         while (opModeIsActive()){
             if (gamepad1.dpad_up){
                 servo.setPosition(servo.getPosition()+0.0001);
             } else if (gamepad1.dpad_down){
                 servo.setPosition((servo.getPosition()-0.0001));
             }

             if(gamepad2.dpad_right){//shoot
                 if (wheelVel<=2200) {/// <- this number is the max shooting speed
                     wheelVel = wheelVel + 20;
                 }else {
                     wheelVel = 2200;
                 }
             }
             if(gamepad2.dpad_left){
                 if (wheelVel>=20) {/// <- this number is the max shooting speed
                     wheelVel = wheelVel - 20;
                 }else {
                     wheelVel = 0;
                 } 
             }

             wheel1.setVelocity(wheelVel);
             wheel2.setVelocity(wheelVel);

             telemetry.addData("wheel vel",wheelVel);
             telemetry.addData("actual wheel 1", wheel1.getVelocity());
             telemetry.addData("actual wheel 2", wheel2.getVelocity());
             telemetry.addLine();





             telemetry.addData("servo pos", servo.getPosition());
             telemetry.update();


         }

    }



}


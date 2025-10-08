package org.firstinspires.ftc.teamcode.utils;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.Math;

public abstract class ToxicTele extends LinearOpMode {
   public MyChemicalRobot robot = new MyChemicalRobot(hardwareMap);


    double max;
    double speed;
    double normalSpeed;

    // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
    double x;  // Note: pushing stick forward gives negative value
    double y;
    double z;

    // Combine the joystick requests for each axis-motion to determine each wheel's power.
    // Set up a variable for each drive wheel to save the power level for telemetry.
    double leftFrontPower;
    double rightFrontPower;
    double leftRearPower;
    double rightRearPower;



    // Normalize the values so no wheel power exceeds 100%
    // This ensures that the robot maintains the desired motion.

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initHardware(true);

        while(opModeIsActive()) {
            teleLoop();
        }
    }

    abstract public void teleLoop();

    public double calcXPow(){
        x = Math.pow(-gamepad1.left_stick_y, 3)*speed;  // Note: pushing stick forward gives negative value
        return x;
    }
    public double calcYPow(){
        y = Math.pow(-gamepad1.left_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value
        return y;
    }
    public double calcZPow(){
        z = Math.pow(-gamepad1.right_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value
        return z;
    }

    public void calcMotorPow(){
        leftFrontPower = calcXPow() + calcYPow() + calcZPow();
        rightFrontPower = calcXPow() - calcYPow() - calcZPow();
        leftRearPower = calcXPow() - calcYPow() + calcZPow();
        rightRearPower = calcXPow() + calcYPow() - calcZPow();
        if (leftFrontPower>1){
            leftFrontPower=1;
        }
        if (leftRearPower>1){
            leftRearPower=1;
        }
        if (rightFrontPower>1){
            rightFrontPower=1;
        }
        if (rightRearPower>1){
            rightRearPower=1;
        }
    }

    public void displayData(){
        telemetry.addData("", robot.leftFront.getCurrentPosition());;//tbd what to add
        telemetry.addData("", robot.leftRear.getCurrentPosition());//tbd what to add
        telemetry.addData("", robot.rightFront.getCurrentPosition());//tbd what to add
        telemetry.addData("", robot.rightRear.getCurrentPosition());//tbd what to add

        telemetry.update();
    }

    public void setAllPower(){
        calcMotorPow();
        robot.leftFront.setPower(leftFrontPower);
        robot.leftRear.setPower(leftRearPower);
        robot.rightRear.setPower(rightRearPower);
        robot.rightFront.setPower(rightFrontPower);
    }

    public void setFastSpeed(double multiplierValue, Boolean speedButton){
        while (speedButton){
            speed = multiplierValue;
        }
        speed = normalSpeed;
    }
    public void setSlowSpeed(double multiplierValue, Boolean slowButton){
        while (slowButton){
            speed = multiplierValue;
        }
        speed = normalSpeed;
    }

    public void rest(){
        robot.leftFront.setPower(0);
        robot.leftRear.setPower(0);
        robot.rightRear.setPower(0);
        robot.rightFront.setPower(0);
    }


    public void moveIncrememntaly(Servo servo, double incrementValue, Boolean isForward){
        if(isForward){
            servo.setPosition(servo.getPosition() + incrementValue);
        }else{
            servo.setPosition(servo.getPosition() - incrementValue);
        }
    }

    public void shootALl(){

    }

//    public void velIncrease(DcMotorEx motor, double finalVel, int secs){
//        time.reset();
//        while(opModeIsActive()){
//            if (time.seconds()<=secs) {
//                motor.setVelocity((finalVel /secs)*time.seconds());
//                motor.setVelocity((finalVel /secs)*time.seconds());
//
//            }else{
//                motor.setVelocity(805);
//                motor.setVelocity(805);
//                telemetry.addData("vel status", "reached");
//
//            }
//            telemetry.update();
//
//        }
//    }






    public void pauseCamera(){
       // camera.pauseViewport();
    }
    public void resumeCamera(){
      //  webcam.resumeViewport();
    }









}

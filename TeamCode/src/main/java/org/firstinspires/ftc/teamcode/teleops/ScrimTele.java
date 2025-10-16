package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;


@TeleOp(name = "scrimmage teleop")
public class ScrimTele extends ToxicTele {

    double x;  // Note: pushing stick forward gives negative value
    double y;
    double z;

    double leftFrontPower;
    double rightFrontPower;
    double leftRearPower;
    double rightRearPower;
    double speed = 0;

    double wheelVel=0; //dont change this
    final int MAXWHEELVEL = 1900;
    final double BELTVEL = 600; //this can be changed
    final double INTAKEVEL = 30; //this can be changed
    public ElapsedTime time = new ElapsedTime();

    Boolean beltOn = false;
    Boolean wasMax = false;

    Boolean shootOn = false;

    @Override
    public void initialize() {
        robot.belt.setVelocity(0);
    }

    @Override
    public void teleLoop() {


        //below must be <=1

        if (gamepad1.dpad_up){//fast speed
            speed = .9;//this can be changed
        } else if (gamepad1.dpad_down){ //slow speed
            speed = .15;//this can be changed
        } else { //normal speed
            speed = .5;//this can be changed
        }

        //ignore all of whats below
        x = Math.pow(-gamepad1.left_stick_y, 3)*speed;  // Note: pushing stick forward gives negative value
        y = Math.pow(-gamepad1.left_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value
        z = -Math.pow(-gamepad1.right_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value

        leftFrontPower = x+y+z;
        rightFrontPower = x-y-z;
        leftRearPower = x-y+z;
        rightRearPower = x+y-z;

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
        robot.leftFront.setPower(leftFrontPower);
        robot.leftRear.setPower(leftRearPower);
        robot.rightRear.setPower(rightRearPower);
        robot.rightFront.setPower(rightFrontPower);


        //this button powers off drivetrain
        if (gamepad1.right_bumper) {//force stop
            robot.leftFront.setPower(0);
            robot.leftRear.setPower(0);
            robot.rightRear.setPower(0);
            robot.rightFront.setPower(0);
        }




        if (gamepad2.left_trigger>0){//intake
            robot.intake.setPower(1.0);
        } else{
            robot.intake.setPower(0.0);
        }

        //finalVel = x (current time)
        //final Vel/curent time = x


        if(gamepad2.x){//shoot
            if (wheelVel < 1900) {/// <- this number is the max shooting speed
                wheelVel = wheelVel + 20;
            } else {
                if (!wasMax) {
                    gamepad2.rumble(400);
                    //gamepad2.rumbleBlips(15);
                    wasMax=true;
                }

                wheelVel = 1900;
            }

            //time.reset();

//            if (!shootOn) {
//                shootOn = true;
//                if (wheelVel<1900) {
//                    wheelVel = wheelVel+2;
//                } else {
//                    wheelVel = 1900;
//                }
//
//            } else{
//                shootOn = false;
//                wheelVel = 0;
//            }
        } else{
            wheelVel = 0;
            wasMax=false;
        }

        robot.wheel1.setVelocity(wheelVel);
        robot.wheel2.setVelocity(wheelVel);



        telemetry.addData("Vel 1",wheelVel);
        telemetry.addData("actualVel 1", robot.wheel1.getVelocity());
        telemetry.addData("actualVel 2", robot.wheel2.getVelocity());
        telemetry.update();

        if (gamepad2.crossWasPressed()) { //belt speed set at very top, only change it there
            if (!beltOn) {
                beltOn = true;
                robot.belt.setVelocity(BELTVEL);
            } else if (beltOn) {
                robot.belt.setVelocity(0);
                beltOn = false;
            }
        }

        if (gamepad2.triangle){
            robot.intakePush.setPower(.5);
        }else{
            robot.intakePush.setPower(0.0);
        }

    }





}

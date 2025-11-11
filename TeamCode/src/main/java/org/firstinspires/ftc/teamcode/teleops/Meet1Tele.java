package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;


@TeleOp(name = "meet 1 teleop")
public class Meet1Tele extends ToxicTele {

    double x;  // Note: pushing stick forward gives negative value
    double y;
    double z;

    double leftFrontPower;
    double rightFrontPower;
    double leftRearPower;
    double rightRearPower;
    double speed = 0;

    double wheelVel=0; //dont change this
    final int MAX_SHOOTING_SPEED = 2200;
    final double BELTVEL = 600; //this can be changed
    final double INTAKEVEL = 30; //this can be changed
    public ElapsedTime time = new ElapsedTime();

    double xPos = 0;
    double yPos = 0;

    double zPos = 0;

    double shootAngle = 0;


    double launchElevation = 0;

    final double MAXELEV = 0;
    final double MINELEV = 0;


    Boolean beltOn = false;
    Boolean wasMax = false;

    Boolean shootOn = false;

    @Override
    public void initialize() {
        //Frobot.belt.setVelocity(0);
    }

    @Override
    public void teleLoop() {



//        xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
//        yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);
//        zPos = robot.pinpoint.getHeading(AngleUnit.DEGREES);
//
//        if (gamepad1.triangle){
//            shootAngle = Math.atan((yPos/xPos));
//
//        }

        if (gamepad2.dpad_up){
            if (robot.shooterServo.getPosition()<MAXELEV){
                robot.shooterServo.setPosition(robot.shooterServo.getPosition()+0.001);
            }
        }else if (gamepad2.dpad_down){
            if (robot.shooterServo.getPosition()>MINELEV){
                robot.shooterServo.setPosition(robot.shooterServo.getPosition()-0.001);
            }
        }


        if (gamepad1.dpad_left){
           robot.leftFront.setPower(.5);

        }
        if (gamepad1.dpad_right){
            robot.rightFront.setPower(.5);

        }
        if (gamepad1.left_stick_button){
            robot.leftRear.setPower(.5);

        }
        if (gamepad1.right_stick_button){
            robot.rightRear.setPower(.5);

        }






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
            if (wheelVel<MAX_SHOOTING_SPEED) {/// <- this number is the max shooting speed
                wheelVel = wheelVel + 20;
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
            //wasMax=false;
        }

        robot.wheel1.setVelocity(wheelVel);
        robot.wheel2.setVelocity(wheelVel);


        if (gamepad2.crossWasPressed()) { //belt speed set at very top, only change it there
            if (!beltOn) {
                beltOn = true;
                robot.belt.setVelocity(BELTVEL);
            } else if (beltOn) {
                robot.belt.setVelocity(0);
                beltOn = false;
            }
        }



        telemetry.addData("pinpoint status", robot.pinpoint.getDeviceStatus());
        telemetry.addData("x", xPos);
        telemetry.addData("y", yPos);
        telemetry.addData("theta", zPos);
        telemetry.addLine();


        telemetry.addData("wheel vel",wheelVel);
        telemetry.addData("actual wheel 1", robot.wheel1.getVelocity());
        telemetry.addData("actual wheel 2", robot.wheel2.getVelocity());
        telemetry.addLine();

        telemetry.addData("angle servo pos", robot.shooterServo.getPosition());
        telemetry.addLine();

        telemetry.addData("harper vel", Math.abs(leftFrontPower));

        telemetry.update();


    }
}

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;


@TeleOp(name = "wheelPID")
public class WheelPID extends ToxicTele {

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

    double p = 15;
    double d = 0.2;
    double i = 0.65;
    double f = 3;
    double launchElevation = 0;

    final double MAXELEV = 0;
    final double MINELEV = 0;


    Boolean beltOn = false;
    Boolean wasMax = false;

    Boolean shootOn = false;

    @Override
    public void initialize() {
        //Frobot.belt.setVelocity(0);
        PIDFCoefficients wheel1coef = robot.wheel1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        p = 25;
        d = 1.06;
        i = .7;
        f =3;
    }

    @Override
    public void teleLoop() {

        if (gamepad1.dpad_up){
            robot.shooterServo.setPosition(robot.shooterServo.getPosition()+0.01);
        } else if (gamepad1.dpad_down){
            robot.shooterServo.setPosition((robot.shooterServo.getPosition()-0.01));
        }


//        xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
//        yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);
//        zPos = robot.pinpoint.getHeading(AngleUnit.DEGREES);
//
//        if (gamepad1.triangle){
//            shootAngle = Math.atan((yPos/xPos));
//
//        }


        if (gamepad2.left_trigger>0){//intake
            robot.intake.setPower(1.0);
        } else{
            robot.intake.setPower(0.0);
        }

        //finalVel = x (current time)
        //final Vel/curent time = x


        if(gamepad1.right_bumper){//shoot
            if (wheelVel<2200) {/// <- this number is the max shooting speed
                wheelVel = wheelVel + 40;
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
        } else if (gamepad1.left_bumper){
            wheelVel = 0;
        }

        robot.wheel1.setVelocity(wheelVel);
        robot.wheel2.setVelocity(wheelVel);


        if (gamepad1.crossWasPressed()) { //belt speed set at very top, only change it there
            if (!beltOn) {
                beltOn = true;
                robot.belt.setVelocity(BELTVEL);
            } else if (beltOn) {
                robot.belt.setVelocity(0);
                beltOn = false;
            }
        }

        if (gamepad2.dpad_up){
            p += 0.04;
        }
        if (gamepad2.dpad_down){
            p -= 0.04;
        }

        if (gamepad2.dpad_left){
            d -= 0.001;
        }

        if (gamepad2.dpad_right){
            d += 0.001;
        }

        if (gamepad2.triangle){
            i +=0.001;
        }
        if (gamepad2.cross){
            i -= 0.001;
        }

        if (gamepad2.square){
            f -= 0.001;
        }
        if (gamepad2.circle){
            f += 0.001;
        }

        if (gamepad1.left_stick_button){
            d=0;
        }
        if (gamepad1.right_stick_button){
            p=0;
        }
        if (gamepad2.left_stick_button){
            f = 0;
        }
       if (gamepad2.right_stick_button){
           i = 0;
       }


       robot.wheel1.setVelocityPIDFCoefficients(p,i,d,f);
       robot.wheel2.setVelocityPIDFCoefficients(p,i,d,f);


        telemetry.addData("servo pos", robot.shooterServo.getPosition());
        telemetry.addLine();
        telemetry.addData("set vel",wheelVel);
        telemetry.addData("actual wheel 1", robot.wheel1.getVelocity());
        telemetry.addData("actual wheel 2", robot.wheel2.getVelocity());
        telemetry.addLine();


        telemetry.addLine();

        //telemetry.addData("harper vel", Math.abs(leftFrontPower));
        telemetry.addLine();

        telemetry.addData("p", p);
        telemetry.addData("d", d);
        telemetry.addData("i", i);
        telemetry.addData("f", f);
        telemetry.addLine();

        telemetry.addData("differemce",  Math.abs(robot.wheel1.getVelocity()-robot.wheel2.getVelocity()));


        telemetry.update();


    }
}

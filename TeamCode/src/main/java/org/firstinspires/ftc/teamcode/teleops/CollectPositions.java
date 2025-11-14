package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;


@TeleOp(name = "meet 1 teleop")
public class CollectPositions extends ToxicTele {

    double x;  // Note: pushing stick forward gives negative value
    double y;
    double z;

    double leftFrontPower;
    double rightFrontPower;
    double leftRearPower;
    double rightRearPower;
    double speed = 0;

    double wheelVel = 0; //dont change this
    final int MAX_SHOOTING_SPEED = 2200;
    final double BELTVEL = 1600; //this can be changed
    final double INTAKEVEL = 30; //this can be changed
    public ElapsedTime time = new ElapsedTime();

    double xPos = 0;
    double yPos = 0;

    double zPos = 0;

    double shootAngle = 0;
    double p = 0;
    double i = 0;
    double d = 0;
    double f = 0;

    double p2 = 0;
    double i2 = 0;
    double d2 = 0;
    double f2 = 0;

    double launchElevation = 0;

    final double MAXELEV = 1;
    final double MINELEV = 0;
    PIDFCoefficients pidCoefficients1;
    PIDFCoefficients pidCoefficients2;


    Boolean beltOn = false;
    Boolean triggerWasNotOn = true;

    Boolean incrementalSpeedUp = false;

    Boolean slowingDown = false;
    Boolean leftBumperPressed;
    Boolean shootOn = false;

    double nominalVoltage = 12.0;
    double currentVoltage;
    double adjustedVel;

    PIDFCoefficients pidfCoefficients1  = new PIDFCoefficients(0.0, 0.0, 0.186996, 11.932999);
    PIDFCoefficients pidfCoefficients2  = new PIDFCoefficients(0.0, 0.0, 0.186996, 13.154999);

    double wheel1Vel;
    double wheel2Vel;

    double prevVel;

    double voltage;
    double feedforward;
    //double feedback = kP * (targetRPM - measuredRPM);
    public ElapsedTime timer = new ElapsedTime();



    @Override
    public void initialize() {
        //Frobot.belt.setVelocity(0);

        robot.pinpoint.initialize();
        robot.pinpoint.resetPosAndIMU();
        robot.pinpoint.recalibrateIMU();


    }

    @Override
    public void teleLoop() {


        telemetry.addData("pid default", robot.wheel1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("pid defaul2t", robot.wheel2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        //pidCoefficients1 = robot.wheel1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, );
        //pidCoefficients2 = robot.wheel2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.wheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients1);
        robot.wheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients2);
//        p = pidCoefficients1.p;
//        i = pidCoefficients1.i;
//        d = pidCoefficients1.d;
//        f = pidCoefficients1.f;
//
//        p2 = pidCoefficients2.p;
//        i2 = pidCoefficients2.i;
//        d2 = pidCoefficients2.d;
//        f2 = pidCoefficients2.f;


//        robot.wheel1.setVelocityPIDFCoefficients(p,i,0.187, f);
//        robot.wheel2.setVelocityPIDFCoefficients(p2, i2, 0.187, f2);
//


        xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
        yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);
        zPos = robot.pinpoint.getHeading(AngleUnit.DEGREES);
        robot.pinpoint.update();
//
//        if (gamepad1.triangle){
//            shootAngle = Math.atan((yPos/xPos));
//
//        }


        //harper controls
        {
            if (gamepad1.dpad_up) {//fast speed
                speed = .9;//this can be changed
            } else if (gamepad1.dpad_down) { //slow speed
                speed = .15;//this can be changed
            } else { //normal speed
                speed = .5;//this can be changed
            }

            //ignore all of whats below
            x = Math.pow(-gamepad1.left_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value
            y = Math.pow(-gamepad1.left_stick_y, 3) * speed;  // Note: pushing stick forward gives negative value
            z = -Math.pow(-gamepad1.right_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value

            leftFrontPower = x + y + z;
            rightFrontPower = x - y - z;
            leftRearPower = x - y + z;
            rightRearPower = x + y - z;

            if (leftFrontPower > 1) {
                leftFrontPower = 1;
            }
            if (leftRearPower > 1) {
                leftRearPower = 1;
            }
            if (rightFrontPower > 1) {
                rightFrontPower = 1;
            }
            if (rightRearPower > 1) {
                rightRearPower = 1;
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

        }

        //lobangang controls
        if (gamepad2.dpadUpWasPressed()){
            robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addData("pinpoint status", robot.pinpoint.getDeviceStatus());
        telemetry.addData("x", xPos);
        telemetry.addData("y", yPos);
        telemetry.addData("theta", zPos);
        telemetry.addLine();


        telemetry.addData("FR Pos", robot.rightFront.getCurrentPosition());
        telemetry.addData("FL Pos", robot.leftFront.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("BR Pos", robot.rightRear.getCurrentPosition());
        telemetry.addData("BL Pos", robot.leftRear.getCurrentPosition());

        telemetry.addLine();

        telemetry.addData("Nominal Voltage", nominalVoltage);

        telemetry.addData("actual wheel 1", robot.wheel1.getVelocity());
        telemetry.addData("actual wheel 2", robot.wheel2.getVelocity());
        telemetry.addLine();

        telemetry.addLine();

        telemetry.addData("differemce", Math.abs(robot.wheel1.getVelocity() - robot.wheel2.getVelocity()));

        telemetry.addLine();


        telemetry.addData("current1", robot.wheel1.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("current2", robot.wheel1.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addLine();
        telemetry.addData("current1Alert", robot.wheel1.getCurrentAlert(CurrentUnit.MILLIAMPS));
        telemetry.addData("current2 Alert", robot.wheel2.getCurrentAlert(CurrentUnit.MILLIAMPS));
        telemetry.addLine();
        telemetry.addLine();

        telemetry.addData("angle servo pos", robot.shooterServo.getPosition());
        telemetry.addLine();
       // robot.wheel2.
        //telemetry.addData("harper vel", Math.abs(leftFrontPower));

        telemetry.addData("pid default", robot.wheel1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("pid defaul2t", robot.wheel2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));


        telemetry.update();


    }

}

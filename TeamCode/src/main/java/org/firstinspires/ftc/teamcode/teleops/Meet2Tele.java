package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;

@Disabled
@TeleOp(name = "Meet2Tele")
public class Meet2Tele extends ToxicTele {

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
    final double BELTVEL = 1900; //this can be changed
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
    Boolean incrementalSpeedUp1345 = false;
    Boolean incrementalSpeedUp1375 = false;
    Boolean incrementalSpeedUp1460 = false;

    Boolean monitor = false;

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

        if(gamepad1.dpad_up){
            x = robot.pinpoint.getPosX(DistanceUnit.INCH);
            y = robot.pinpoint.getPosY(DistanceUnit.INCH);
            //turnAngle = Math.atan(robot.pinpoint.getPosX(DistanceUnit.INCH)/robot.pinpoint.getPosY(DistanceUnit.INCH));//different for red & blue
        }




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
            if (gamepad1.left_bumper) {//fast speed
                speed = .9;//this can be changed
            } else if (gamepad1.left_trigger>0) { //slow speed
                speed = .19;//this can be changed
            } else { //normal speed
                speed = .8;//this can be changed
            }

            //ignore all of whats below
            x = Math.pow(-gamepad1.left_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value
            y = Math.pow(-gamepad1.left_stick_y, 3) * speed;  // Note: pushing stick forward gives negative value
            z = -Math.pow(-gamepad1.right_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value


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
        {
            //intake
            if (gamepad2.left_trigger > 0) {
                robot.intake.setPower(1.0);
            } else {
                robot.intake.setPower(0.0);
            }

            //belt
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
                robot.belt.setVelocity(-BELTVEL);
            }


            //servo
            if (gamepad2.dpad_down) {
//            if (robot.shooterServo.getPosition()<MAXELEV){
                robot.shooterServo.setPosition(robot.shooterServo.getPosition() + 0.01);
//            }
            } else if (gamepad2.dpad_up) {
//            if (robot.shooterServo.getPosition()>MINELEV){
                robot.shooterServo.setPosition(robot.shooterServo.getPosition() - 0.01);
//            }
            }


            //start/stop flywheels


            if (gamepad2.squareWasPressed()) {
                if (wheelVel > 0) {
                    incrementalSpeedUp = false;
                    wheelVel = 0;
                    //slowingDown = true;
                    //incrementalSpeedUp = ;
                } else {
                    incrementalSpeedUp = true;
                    robot.shooterServo.setPosition(0.2009);

                }
            }
            if (monitor) {
                if (wheel1Vel < wheelVel || wheel1Vel>wheelVel) {

                } else {
                    monitor = false;
                    gamepad2.rumble(500);
                    //triggerWasNotOn=true;
                }
            }

            //incremental speed increase
            if (incrementalSpeedUp) {
                if (wheelVel < 1345) {
                    wheelVel = wheelVel + 40;
                } else {
                    incrementalSpeedUp = false;
                    gamepad2.rumble(500);

                    //triggerWasNotOn=true;
                }
            }
            if (incrementalSpeedUp1345) {
                if (wheelVel < 1345) {
                    wheelVel = wheelVel + 40;
                    if (wheelVel>1345){
                        wheelVel= 1345;
                    }
                } else {
                    incrementalSpeedUp1345 = false;
                    gamepad2.rumble(500);
                    //triggerWasNotOn=true;
                }
            }
            if (incrementalSpeedUp1375) {
                if (wheelVel < 1375) {
                    wheelVel = wheelVel + 40;
                    if (wheelVel>1375){
                        wheelVel= 1375;
                    }
                } else {
                    incrementalSpeedUp1375 = false;
                    gamepad2.rumble(500);
                    //triggerWasNotOn=true;
                }
            }

            if (incrementalSpeedUp1460) {
                if (wheelVel < 1460) {
                    wheelVel = wheelVel + 40;
                    if (wheelVel>1460){
                        wheelVel= 1460;
                    }
                } else {
                    incrementalSpeedUp1460 = false;
                    gamepad2.rumble(500);
                    //triggerWasNotOn=true;
                }
            }


            if (incrementalSpeedUp) {
                if (wheelVel < 1345) {
                    wheelVel = wheelVel + 40;
                } else {
                    incrementalSpeedUp = false;
                    gamepad2.rumble(500);

                    //triggerWasNotOn=true;
                }
            }

            //+|- flywheel speed
            if (gamepad2.right_bumper) {
                if (wheelVel < 1200) {
                    wheelVel += 20;
                    monitor = true;
                } else if (wheelVel < 1720) {/// <- this number is the max shooting speed
                    wheelVel = wheelVel + 5;
                    monitor = true;
                }

            } else if (gamepad2.left_bumper) {//shoot
                if (wheelVel > 0) {/// <- this number is the max shooting speed
                    wheelVel = wheelVel - 5;
                }
            }

            if (gamepad1.triangle) {//up close shooting
                wheelVel = 1345;
                monitor = true;
                //incrementalSpeedUp1330 = true;

                robot.shooterServo.setPosition(0.2009);
            }
            if (gamepad1.circle){ //medium close
                wheelVel = 1375;
                monitor = true;
                //incrementalSpeedUp1375 = true;

                robot.shooterServo.setPosition(0);

            }
            if (gamepad1.cross){//middle
                wheelVel = 1460;
                monitor = true;
                //incrementalSpeedUp1460 = true;
                robot.shooterServo.setPosition(0);

            }
            if (gamepad1.square){
                wheelVel = 1720;
                monitor = true;
                robot.shooterServo.setPosition(0);
            }

            currentVoltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            adjustedVel = wheelVel * (nominalVoltage / currentVoltage);
            prevVel = adjustedVel;

//            if (gamepad2.dpad_right){
//                if (Math.abs(wheelVel - robot.wheel1.getVelocity()) >= 10){
//                    nominalVoltage += .01;
//                }
//            }
            nominalVoltage = 12.43;

            currentVoltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            adjustedVel = wheelVel * (nominalVoltage / currentVoltage);
            prevVel = adjustedVel;

            if (Math.abs(wheelVel - robot.wheel1.getVelocity()) >= 5 && Math.abs(wheelVel - robot.wheel1.getVelocity())<40 && wheelVel!= 0) {
                double errorScaled = Math.abs(adjustedVel - robot.wheel1.getVelocity());

                wheel1Vel = adjustedVel + Math.copySign(10, wheelVel - robot.wheel1.getVelocity());//Math.copySign(errorScaled, wheelVel - robot.wheel1.getVelocity());
            }
            if (Math.abs(wheelVel - robot.wheel2.getVelocity()) >= 5 && Math.abs(wheelVel - robot.wheel2.getVelocity())<40 && wheelVel!= 0) {
                double errorScaled = Math.abs(adjustedVel - robot.wheel1.getVelocity())*1;

                wheel2Vel = adjustedVel +(Math.copySign(10, wheelVel - robot.wheel2.getVelocity()));//(Math.copySign(errorScaled, wheelVel - robot.wheel2.getVelocity()));
            }
//            if (Math.abs(robot.wheel1.getVelocity() - robot.wheel2.getVelocity()) > 60 && Math.abs(wheelVel - robot.wheel2.getVelocity())>100) {
//                if (robot.wheel1.getVelocity()>robot.wheel2.getVelocity()){
//                    wheel1Vel = robot.wheel2.getVelocity();
//
//                } else if (robot.wheel1.getVelocity()<robot.wheel2.getVelocity()){
//                    wheel2Vel = robot.wheel1.getVelocity();
//                }
//            }

            //if ()

            wheel1Vel = wheelVel;
            wheel2Vel = wheelVel;


        }

        telemetry.addData("pinpoint status", robot.pinpoint.getDeviceStatus());
        telemetry.addData("x", xPos);
        telemetry.addData("y", yPos);
        telemetry.addData("theta", zPos);
        telemetry.addLine();


        telemetry.addData("wheel vel", wheelVel);
        telemetry.addData("adjusted vel", adjustedVel);
        telemetry.addLine();
        telemetry.addData("wheel vel1", wheel1Vel);
        telemetry.addData("wheel vel2", wheel2Vel);

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

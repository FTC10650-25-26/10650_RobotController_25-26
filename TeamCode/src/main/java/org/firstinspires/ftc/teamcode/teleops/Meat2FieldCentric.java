package org.firstinspires.ftc.teamcode.teleops;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.Color;
import org.firstinspires.ftc.teamcode.utils.MyChemicalRobot;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;

import java.util.Objects;


@TeleOp(name = "Meat2FieldCentric")
public class Meat2FieldCentric extends ToxicTele {

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
    boolean align = false;


    Boolean beltOn = false;
    Boolean triggerWasNotOn = true;

    Boolean incrementalSpeedUp = false;
    Boolean incrementalSpeedUpStart = false;

    double vel = 0;

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



    double scorePos = 144;
    double scoreHeight = 53.8;
    double launcherHeight = 14;
    double vertMultilierX = 0.823;
    double vertMultiplierY = 0.91;
    double currentPos = 0;
    double dist = 0;
    double midDist = 0;
    double finalHeight = 0;

    double a;
    double launchVel;
    double velY;
    double launchAngle;

    double degrees;
    double blueAlignAngle = 0;

    boolean stopTurn = false;
    double add = 0;

    double tx=0;
    String autoAlignDirection;



    public void calcLaunchVel(){
        dist = scorePos - currentPos;
        midDist = (vertMultilierX *dist) + currentPos;
        finalHeight = vertMultiplierY*scoreHeight;

        a = (launcherHeight-finalHeight)/(-(Math.pow(a-midDist, 2)));
        launchVel = -2*a*(-midDist);
    }

    public void calcLaunchAngle(){
        launchAngle = Math.asin(velY/launchVel);
    }

    @Override
    public void initialize() {
        //Frobot.belt.setVelocity(0);

        robot.pinpoint.initialize();
        robot.pinpoint.resetPosAndIMU();
        robot.pinpoint.recalibrateIMU();
        robot.imu.resetYaw();
        telemetry.addData("pitch servo pos", robot.shooterServo.getPosition());
        telemetry.addData("push servo pos", robot.pusherServo.getPosition());
        telemetry.update();

//        robot.limelight.pipelineSwitch(1);

    }

    @Override
    public void teleLoop() {





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
////


//        important code down here v
//        robot.loopLimelightPoseData(false);
//        Pose weightedPose  = robot.getLLPoseWeightedAvg(3);
//        if (weightedPose.getX()==-Integer.MAX_VALUE){
//            //ll hasnt found anything
//            telemetry.addData("LL hasn't found anything :(", 1);
//        } else{
//            robot.pinpoint.setPosX(weightedPose.getX(), DistanceUnit.INCH);
//            robot.pinpoint.setPosY(weightedPose.getY(), DistanceUnit.INCH);
//            telemetry.addData("LL HAS found smth :)", 1);
//
//
//        }

//        LLResult result = robot.limelight.getLatestResult();
//        if (result != null && result.isValid()) {
//            tx = result.getTx(); // How far left or right the target is (degrees)
//        } else{
//            tx = Integer.MAX_VALUE;
//        }
//        telemetry.addData("tx: ", tx);
//        telemetry.addData("delta", (tx*Math.PI/180)-robot.pinpoint.getHeading(AngleUnit.RADIANS));
//        telemetry.addLine();
//
//        telemetry.addLine();


        xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
        yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);
        zPos = robot.pinpoint.getHeading(AngleUnit.RADIANS);
        robot.pinpoint.update();

//        telemetry.addData("wighted x", weightedPose.getX());
//        telemetry.addData("wighted y", weightedPose.getY());
        telemetry.addLine();
        telemetry.addData("pinpoint status", robot.pinpoint.getDeviceStatus());
        telemetry.addData("x", xPos);
        telemetry.addData("y", yPos);
        telemetry.addData("z", zPos);

        telemetry.addLine();
        //telemetry.addData("difference abs", Math.abs(blueAlignAngle-zPos));
        telemetry.addData("add", add);
        telemetry.addData("drive vel", leftFrontPower);
        telemetry.addData("stopTurn", stopTurn);




       // blueAlignAngle = robot.getAlignAngle(MyChemicalRobot.Color.BLUE);
        //telemetry.addData("alignAngle", blueAlignAngle);



//        if (gamepad1.triangleWasPressed()){
//            stopTurn = false;
//        }





//        if (!stopTurn){ //if align pressed
//            if(Math.abs(blueAlignAngle-zPos)<=.5){
//                double difference = blueAlignAngle-zPos;
//                add = (robot.initTurnSign(blueAlignAngle, MyChemicalRobot.Color.BLUE)*Math.log(difference)*10)+80;
//            } else{
//                stopTurn = true;
//                add = 0;
//            }
//        }
//        if (stopTurn){
//            add = 0;
//        }
//
//        if (gamepad1.triangle){
//            shootAngle = Math.atan((yPos/xPos));
//
//        }


        //harper controls
        {
            if (gamepad1.cross) {//fast speed
                speed = .9;//this can be changed
            } else if (gamepad1.circle) { //slow speed
                speed = .19;//this can be changed
            } else { //normal speed
                speed = .8;//this can be changed
            }

            //ignore all of whats below
            y = Math.pow(-gamepad1.left_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value
            x = Math.pow(-gamepad1.left_stick_y, 3) * speed;  // Note: pushing stick forward gives negative value
            z = -Math.pow(-gamepad1.right_stick_x, 3) * speed;  // Note: pushing stick forward gives negative value


            double botHeading = robot.pinpoint.getHeading(AngleUnit.RADIANS); //+- 90


            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotY = rotY;//* 1.2;  // Counteract imperfect strafing


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(z), 1);
//            leftFrontPower = (rotY + rotX + z) / denominator;
//            leftRearPower = (rotY - rotX + z) / denominator;
//            rightFrontPower = (rotY - rotX - z) / denominator;
//            rightRearPower = (rotY + rotX - z) / denominator;

            {
            leftFrontPower = (rotY + rotX + z)/denominator+add;
            leftRearPower = (rotY - rotX + z)/denominator+add;
            rightFrontPower = (rotY - rotX - z)/denominator;
            rightRearPower = (rotY + rotX - z)/denominator;
            }


            if (gamepad1.left_trigger > 0) {
                robot.intake.setPower(1.0);
            } else {
                robot.intake.setPower(0.0);
            }

//            frontLeftMotor.setPower(frontLeftPower);
//            backLeftMotor.setPower(backLeftPower);
//            frontRightMotor.setPower(frontRightPower);
//            backRightMotor.setPower(backRightPower);
//
//
//
//            leftFrontPower = x + y + z;
//            rightFrontPower = x - y - z;
//            leftRearPower = x - y + z;
//            rightRearPower = x + y - z;

//            if (leftFrontPower > 1) {
//                leftFrontPower = 1;
//            }
//            if (leftRearPower > 1) {
//                leftRearPower = 1;
//            }
//            if (rightFrontPower > 1) {
//                rightFrontPower = 1;
//            }
//            if (rightRearPower > 1) {
//                rightRearPower = 1;
//            }

//            if (gamepad1.dpad_right) {//red
//                robot.found = false;
//                autoAlignDirection = "right";
//                robot.turnSignAlign = robot.initTurnSign(-robot.pinpoint.getHeading(AngleUnit.RADIANS));
//                robot.findTagStartingHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//            } else if (gamepad1.dpad_left){//blue
//                robot.found = false;
//                autoAlignDirection = "left";
//                robot.turnSignAlign = robot.initTurnSign(-robot.pinpoint.getHeading(AngleUnit.RADIANS));
//                robot.findTagStartingHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//            }
//            if (!robot.found){
//                if (Objects.equals(autoAlignDirection, "right")){
//                    robot.findTagTele(MyChemicalRobot.Color.RED);
//
//                } else{
//                    robot.findTagTele(MyChemicalRobot.Color.BLUE);
//
//                }
//            } else{

                robot.leftFront.setPower(leftFrontPower);
                robot.leftRear.setPower(leftRearPower);
                robot.rightRear.setPower(rightRearPower);
                robot.rightFront.setPower(rightFrontPower);

            //}




            if (gamepad1.optionsWasPressed()){
                robot.pinpoint.resetPosAndIMU();
            }


            //this button powers off drivetrain
//            if(!robot.isOverridingMotorControl) {
//                robot.leftFront.setPower(leftFrontPower);
//                robot.leftRear.setPower(leftRearPower);
//                robot.rightRear.setPower(rightRearPower);
//                robot.rightFront.setPower(rightFrontPower);
//                align = false;
//            }

//
//            if (gamepad1.dpad_down){
//                align = true;
//            }
//            if (align){
//                robot.findTagTele(MyChemicalRobot.Color.RED);
//            }

        }

        //lobangang controls
        {
            //intake


            //belt

            if(gamepad2.left_stick_y<0){//go up
                robot.belt.setVelocity(BELTVEL);
            }else if(gamepad2.left_stick_y>0) {//go down
                robot.belt.setVelocity(-BELTVEL);
            } else{
                robot.belt.setVelocity(0);
            }

//            if (gamepad2.crossWasPressed()) { //belt speed set at very top, only change it there
//                if (!beltOn) {
//                    beltOn = true;
//                    robot.belt.setVelocity(BELTVEL);
//
//                } else if (beltOn) {
//                    robot.belt.setVelocity(0);
//                    beltOn = false;
//                }
//            }

//            if (gamepad2.triangle){
//                robot.belt.setVelocity(-BELTVEL);
//            }


            //servo
//            if (gamepad1.triangle) {
////            if (robot.shooterServo.getPosition()<MAXELEV){
//                robot.shooterServo.setPosition(robot.shooterServo.getPosition() + 0.01);
////            }
//            }
//            if (gamepad1.square) {
////            if (robot.shooterServo.getPosition()<MAXELEV){
//                robot.shooterServo.setPosition(robot.shooterServo.getPosition() - 0.01);
////            }
//            }
            if (gamepad2.cross) {
//            if (robot.shooterServo.getPosition()>MINELEV){
                robot.pusherServo.setPosition(0);
                telemetry.addData("should be goin", 1);
//            }
            } else {
                robot.pusherServo.setPosition(1);
            }

//            if (gamepad2.right_stick_x>0.5){
//                robot.shooterServo.setPosition(robot.shooterServo.getPosition()+0.01);
//            } else if(gamepad2.right_stick_x<0.5){
//                robot.shooterServo.setPosition(robot.shooterServo.getPosition()-0.01);
//            }
            //start/stop flywheels


            if (gamepad2.squareWasPressed()) {
                if (wheelVel > 0) {
                    incrementalSpeedUpStart = false;
                    wheelVel = 0;
                    //slowingDown = true;
                    //incrementalSpeedUp = ;
                } else {
                    incrementalSpeedUpStart = true;

                }
            }

            if (monitor) {
                if (wheel1Vel < wheelVel || wheel1Vel>wheelVel) {

                } else {
                    monitor = false;
                    gamepad2.rumble(100);
                    //triggerWasNotOn=true;
                }
            }

            //incremental speed increase


            //+|- flywheel speed
            if (gamepad2.right_bumper) {
                if (wheelVel < 2000) {
                    wheelVel += 10;
                    monitor = true;
//                } else if (wheelVel < 2000) {/// <- this number is the max shooting speed
//                    wheelVel += 20;
//                    monitor = true;
                }

            } else if (gamepad2.left_bumper) {//shoot
                if (wheelVel >= 20) {///
                    wheelVel = wheelVel - 10;
                }
            }



           // if

            if (gamepad2.dpad_up){
                incrementalSpeedUp = true;
                vel = 1320;
                robot.shooterServo.setPosition(0.4);
            }
            if (gamepad2.dpad_right) {
                incrementalSpeedUp = true;
                vel = 1540;
                robot.shooterServo.setPosition(0.55);
            }



            if (gamepad2.dpad_left){
                incrementalSpeedUp = true;
                vel = 1540;
                robot.shooterServo.setPosition(0.1312);


            }
            if (gamepad2.dpad_down){
                incrementalSpeedUp = true;
                vel = 1745;

                robot.shooterServo.setPosition(0.539);

            }


            if (incrementalSpeedUp){
                incSpeedUp(vel);
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
//
//            if (Math.abs(wheelVel - robot.wheel1.getVelocity()) >= 5 && Math.abs(wheelVel - robot.wheel1.getVelocity())<40 && wheelVel!= 0) {
//                double errorScaled = Math.abs(adjustedVel - robot.wheel1.getVelocity());
//
//                wheel1Vel = adjustedVel + Math.copySign(10, wheelVel - robot.wheel1.getVelocity());//Math.copySign(errorScaled, wheelVel - robot.wheel1.getVelocity());
//            }
//            if (Math.abs(wheelVel - robot.wheel2.getVelocity()) >= 5 && Math.abs(wheelVel - robot.wheel2.getVelocity())<40 && wheelVel!= 0) {
//                double errorScaled = Math.abs(adjustedVel - robot.wheel1.getVelocity())*1;
//
//                wheel2Vel = adjustedVel +(Math.copySign(10, wheelVel - robot.wheel2.getVelocity()));//(Math.copySign(errorScaled, wheelVel - robot.wheel2.getVelocity()));
//            }
//            if (Math.abs(robot.wheel1.getVelocity() - robot.wheel2.getVelocity()) > 60 && Math.abs(wheelVel - robot.wheel2.getVelocity())>100) {
//                if (robot.wheel1.getVelocity()>robot.wheel2.getVelocity()){
//                    wheel1Vel = robot.wheel2.getVelocity();
//
//                } else if (robot.wheel1.getVelocity()<robot.wheel2.getVelocity()){
//                    wheel2Vel = robot.wheel1.getVelocity();
//                }
//            }

            //if ()
            wheel2Vel = Math.max(0,wheelVel-100);

            robot.wheel1.setVelocity(wheelVel);
            robot.wheel2.setVelocity(wheel2Vel);
            wheel1Vel = wheelVel;
            wheel2Vel = Math.max(0,wheelVel-100);


        }




        telemetry.addData("wheel vel", wheelVel);
        telemetry.addData("adjusted vel", adjustedVel);
        telemetry.addLine();
        telemetry.addData("actual wheel 1", robot.wheel1.getVelocity());
        telemetry.addData("actual wheel 2", robot.wheel2.getVelocity());
        telemetry.addData("differemce", Math.abs(robot.wheel1.getVelocity() - robot.wheel2.getVelocity()));



        //telemetry.addData("Nominal Voltage", nominalVoltage);


        telemetry.addLine();

        telemetry.addLine();


        telemetry.addLine();


        telemetry.addData("theta", zPos);
        telemetry.addLine();


        telemetry.addData("current1", robot.wheel1.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("current2", robot.wheel1.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addLine();
        telemetry.addData("current1Alert", robot.wheel1.getCurrentAlert(CurrentUnit.MILLIAMPS));
        telemetry.addData("current2 Alert", robot.wheel2.getCurrentAlert(CurrentUnit.MILLIAMPS));
        telemetry.addLine();
        telemetry.addLine();

        telemetry.addData("pitch servo pos", robot.shooterServo.getPosition());
        telemetry.addData("push servo pos", robot.pusherServo.getPosition());

        telemetry.addLine();
       // robot.wheel2.
        //telemetry.addData("harper vel", Math.abs(leftFrontPower));

        telemetry.addData("pid default", robot.wheel1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("pid defaul2t", robot.wheel2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));




        telemetry.update();


    }

    public void incSpeedUp(double velocity){
        if (wheelVel < velocity) {
            wheelVel = wheelVel + 120;
            if (wheelVel>velocity){
                wheelVel= velocity;
            }
        } else if (wheelVel>velocity){
            wheelVel= velocity;
            wheel2Vel = velocity;
        }else {
            incrementalSpeedUp = false;
            gamepad2.rumble(250);
            //triggerWasNotOn=true;
        }
    }

}

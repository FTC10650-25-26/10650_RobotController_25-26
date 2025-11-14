package org.firstinspires.ftc.teamcode.autos;

import com.bylazar.field.Line;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.MyChemicalRobot;
import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

@Autonomous (name = "meet 1 auto")
abstract public class Meet1Auto extends RadioactiveAuto {

    public MyChemicalRobot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        //init        robot.pinpoint.update();
        waitForStart();
    }

    @Override
    public void initialize() {
        robot.leftFront.setTargetPosition(0);
        robot.leftRear.setTargetPosition(0);
        robot.rightFront.setTargetPosition(0);
        robot.rightRear.setTargetPosition(0);

        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.leftFront.setPower(0);
        robot.leftFront.setPower(0);
        robot.leftFront.setPower(0);
        robot.leftFront.setPower(0);
    }

    @Override
    public void begin() {
        move(0.7, 100,100,100,100);
        sleep(1000000000);
    }
    public void move(double power, int setPosFR, int setPosFL, int setPosBR, int setPosBL){
        robot.rightFront.setTargetPosition(setPosFR);
        robot.leftFront.setTargetPosition(setPosFL);
        robot.rightRear.setTargetPosition(setPosBR);
        robot.leftRear.setTargetPosition(setPosBL);

        robot.rightFront.setPower(power);
        robot.leftFront.setPower(power);
        robot.rightRear.setPower(power);
        robot.leftRear.setPower(power);

        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        double differenceFR = Math.abs(currentPosFR-setPosFR);
//        double differenceFL = Math.abs(currentPosFL-setPosFL);
//        double differenceBR = Math.abs(currentPosBR-setPosBR);
//        double differenceBL = Math.abs(currentPosBL-setPosBL);
        robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveLine(){

    }


}

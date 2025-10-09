package org.firstinspires.ftc.teamcode.teleops;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;

import java.util.function.Supplier;


@TeleOp(name = "scrimmage teleop w pedro")
public class ScrimTeleWPedro extends ToxicTele {

    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> pathChain;
    private boolean slowMode = false;
    private double slowModeMultiplier = 0.5;


    double x;  // Note: pushing stick forward gives negative value
    double y;
    double z;

    double leftFrontPower;
    double rightFrontPower;
    double leftRearPower;
    double rightRearPower;
    double speed = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

    }

    @Override
    public void teleLoop() {

        if (gamepad1.dpad_up){//fast
            speed = .9;
        } else if (gamepad1.dpad_down){ //slow
            speed = .15;
        } else { //normal
            speed = .5;
        }

        x = Math.pow(-gamepad1.left_stick_y, 3)*speed;  // Note: pushing stick forward gives negative value
        y = Math.pow(-gamepad1.left_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value
        z = Math.pow(-gamepad1.right_stick_x, 3)*speed;  // Note: pushing stick forward gives negative value

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

        if (gamepad1.right_bumper) {//force stop
            robot.leftFront.setPower(0);
            robot.leftRear.setPower(0);
            robot.rightRear.setPower(0);
            robot.rightFront.setPower(0);
        }

        if (gamepad1.circle) {//to shoot pos

        }
    }
}

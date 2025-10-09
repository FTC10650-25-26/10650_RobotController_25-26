package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    int wheelVel=0;
    double beltVel = 20;
    double intakeVel = 30;

    Boolean beltOn = false;

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



        if (gamepad2.left_trigger>0){//intake
            //robot.intake1.setVelocity(intakeVel);
        } else{
            //robot.intake1.setVelocity(0);
        }

        if(gamepad2.square){//shoot

            if (wheelVel<1900) {
                wheelVel = wheelVel + 2;
            } else {
                wheelVel = 1900;
            }

        } else{

            wheelVel = 0;
        }
        robot.wheel1.setVelocity(wheelVel);
        robot.wheel2.setVelocity(wheelVel);

        if (gamepad2.x) {
            if (!beltOn) {//belt
                beltOn = true;
                robot.belt.setVelocity(beltVel);
            } else if (beltOn) {
                robot.belt.setVelocity(0);
                beltOn = false;
            }
        }
    }


}

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;

@TeleOp (name = "ZeroPusherServo")
public class ZeroPushServo extends ToxicTele {

    @Override
    public void initialize() {

    }

    @Override
    public void teleLoop() {
        if (gamepad2.dpad_down){
            robot.pusherServo.setPosition(robot.pusherServo.getPosition()-.0002);
        }else if (gamepad2.dpad_up){
            robot.pusherServo.setPosition(robot.pusherServo.getPosition()+.0002);
        }

        if (gamepad2.triangle){
            robot.pusherServo.setPosition(robot.pusherServo.getPosition()+.01);
        }else if (gamepad2.cross){
            robot.pusherServo.setPosition(robot.pusherServo.getPosition()-.01);
        }

        if (gamepad2.square){
            robot.pusherServo.setPosition(0);
        }
        if (gamepad2.circle){
            robot.pusherServo.setPosition(1);
        }

        if (gamepad2.left_bumper){
            robot.intake.setPower(1.0);
        } else {
            robot.intake.setPower(0);
        }
        if(gamepad2.left_stick_y<0){//go up
            robot.belt.setVelocity(1800);
        }else if(gamepad2.left_stick_y>0) {//go down
            robot.belt.setVelocity(-1800);
        } else{
            robot.belt.setVelocity(0);
        }

        telemetry.addData("servoPos", robot.pusherServo.getPosition());
        telemetry.update();
    }
}

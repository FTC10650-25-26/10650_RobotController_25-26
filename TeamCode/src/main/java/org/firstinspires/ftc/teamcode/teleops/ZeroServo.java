package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;

@TeleOp (name = "ZeroServo")
public class ZeroServo extends ToxicTele {

    @Override
    public void initialize() {

    }

    @Override
    public void teleLoop() {
        if (gamepad2.dpad_down){
            robot.shooterServo.setPosition(robot.shooterServo.getPosition()-.0002);
        }else if (gamepad2.dpad_up){
            robot.shooterServo.setPosition(robot.shooterServo.getPosition()+.0002);
        }

        if (gamepad2.triangle){
            robot.shooterServo.setPosition(robot.shooterServo.getPosition()+.01);
        }else if (gamepad2.cross){
            robot.shooterServo.setPosition(robot.shooterServo.getPosition()-.01);
        }

        if (gamepad2.square){
            robot.shooterServo.setPosition(0);
        }
        if (gamepad2.square){
            robot.shooterServo.setPosition(1);
        }

        if (gamepad2.left_bumper){
            robot.intake.setPower(1.0);
        } else {
            robot.intake.setPower(0);
        }
        telemetry.addData("servoPos", robot.shooterServo.getPosition());
        telemetry.update();
    }
}

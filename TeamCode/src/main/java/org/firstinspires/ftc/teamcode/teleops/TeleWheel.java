package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.ToxicTele;
@Disabled
@TeleOp (name = "spin wheel")
public class TeleWheel extends LinearOpMode {
    public DcMotorEx wheel;
    public ElapsedTime time = new ElapsedTime();





    @Override
    public void runOpMode() throws InterruptedException {
        init();
        wheel = hardwareMap.get(DcMotorEx.class,"wheel" );
        wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        wheel.setVelocity(0);
        wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//1125
        //875
        waitForStart();

        time.reset();
        while(opModeIsActive()){
            if (time.seconds()<=20) {
                wheel.setVelocity(Math.pow(time.seconds(), 2.76));
            }else{
                wheel.setVelocity(4000);
            }
            telemetry.addData("vel", wheel.getVelocity());
            telemetry.update();

        }

    }

}

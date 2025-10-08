package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "grant  wheel")
public class GrantWheels extends LinearOpMode {
    public DcMotorEx wheel1;
    public DcMotorEx wheel2;

    public double speed = 0;
    public double savedSpeed = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        init();
        wheel1 = hardwareMap.get(DcMotorEx.class,"wheel1" );
        wheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        wheel1.setVelocity(0);
        wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        wheel2 = hardwareMap.get(DcMotorEx.class,"wheel2" );
        wheel2.setDirection(DcMotorSimple.Direction.REVERSE);
        wheel2.setVelocity(0);
        wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//1125
        //875
        waitForStart();

        while(opModeIsActive()){

           if (gamepad1.dpad_up){
               telemetry.addData("it pressed", "-");

               speed = speed+ 2;
           }
           if (gamepad1.dpad_down){
               speed = speed- 2;
           }
           if(gamepad1.dpad_right){
               savedSpeed = speed;
           }
           if (gamepad1.dpad_right){
               speed = savedSpeed;
           }

            wheel1.setVelocity(speed);
            wheel2.setVelocity(speed);
            telemetry.addData("vel", speed);

            //telemetry.addData("vel", wheel1.getVelocity());
           // telemetry.addData("vel2", wheel2.getVelocity());

            telemetry.update();

        }

    }

}

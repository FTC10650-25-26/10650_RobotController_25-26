package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

@Autonomous(name = "Test Auto2")
public class TestAuto2 extends RadioactiveAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

    }

    @Override
    public void initialize() {
        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void begin() {

    }


}

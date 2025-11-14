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
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void begin() {

    }


}

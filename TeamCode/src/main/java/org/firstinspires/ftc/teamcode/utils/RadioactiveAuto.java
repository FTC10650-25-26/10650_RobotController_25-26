package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class RadioactiveAuto extends LinearOpMode {

    MyChemicalRobot robot = new MyChemicalRobot(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initHardware();
    }
}

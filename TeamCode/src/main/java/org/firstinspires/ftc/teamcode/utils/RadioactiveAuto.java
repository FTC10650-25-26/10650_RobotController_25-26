package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class RadioactiveAuto extends LinearOpMode {

    public MyChemicalRobot robot = new MyChemicalRobot(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initHardware(true); // we do need motors here...
        initialize();
        waitForStart();
        begin();
    }

    abstract public void initialize();

    abstract public void begin();

    //abstract public void end(int pose);



}
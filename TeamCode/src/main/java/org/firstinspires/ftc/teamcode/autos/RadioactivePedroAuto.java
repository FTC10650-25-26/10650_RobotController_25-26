package org.firstinspires.ftc.teamcode.autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;
import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

abstract public class RadioactivePedroAuto extends RadioactiveAuto {
    Follower follower;
    private Timer pathTimer, opModeTimer;

    int pathState;

    Pose startingPose;

    abstract public void buildPaths();
    abstract public void autonomousPathUpdate();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initHardware(false); // do NOT double declare the motors!
        initialize();
        waitForStart();
        begin();
    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void pathingLoop(){
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void initialize() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        buildPaths();
        follower.setStartingPose(startingPose);
    }

    @Override
    public void begin(){
        opModeTimer.resetTimer();
        setPathState(0);

        while(opModeIsActive()) {
            pathingLoop();
        }
    }

}


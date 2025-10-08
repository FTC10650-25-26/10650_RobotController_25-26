package org.firstinspires.ftc.teamcode.autos;


import static com.sun.tools.doclint.HtmlTag.B;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;
import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

abstract public class RadioactivePedroAuto extends RadioactiveAuto {
    Follower follower;
    private Timer pathTimer, opModeTimer;

    private Telemetry telemetryA;

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

        follower.telemetryDebug(telemetryA);
    }

    @Override
    public void initialize() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

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

    public void endPath(Follower follower){
        if (!follower.isBusy()){
            stop();
        }
    }

    public void goNextPath(int nextPath, Path path){
        if (!follower.isBusy()){
            follower.followPath(path);
            setPathState(nextPath);
        }
    }



}


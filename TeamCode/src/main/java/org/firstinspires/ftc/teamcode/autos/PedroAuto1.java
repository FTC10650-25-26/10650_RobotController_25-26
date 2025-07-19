package org.firstinspires.ftc.teamcode.autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;
import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

@Autonomous (name = "pedro auto 1")
public class PedroAuto1 extends RadioactiveAuto {
    private Follower follower;
    private Timer pathTimer, actionTimer, opModeTimer;

    private int pathState;


    private final Pose pose0 = new Pose(0, 0);
    private final Pose pose1 = new Pose(-7, 28, 0);
    private final Pose pose2 = new Pose();
    private final Pose pose3 = new Pose();

    private Path path1;
   // private PathChain goPath1;

    public void buildPaths(){

        path1 = new Path ( new BezierCurve(new Point(pose0), new Point(pose1)));
        path1.setConstantHeadingInterpolation(pose1.getHeading());


    }
    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                follower.followPath(path1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()){
                   stop();
                }
                break;
        }
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
        follower.setStartingPose(pose1);

        buildPaths();
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


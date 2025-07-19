package org.firstinspires.ftc.teamcode.autos;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;

@Autonomous (name = "pedro auto 1")
public class PedroTest1 extends RadioactivePedroAuto {

    private Path path1,path2;

    @Override
    public void buildPaths(){

        // First, set the starting pose
        startingPose = new Pose(7.5, 54.5, 0);

        Pose pose1 = new Pose(35.5,47,0);
        Pose pose2 = new Pose(42,32,Math.PI/3.6);

        path1 = new Path(new BezierLine(startingPose, pose1));
        path1.setConstantHeadingInterpolation(pose1.getHeading());

        path2 = new Path(new BezierLine(pose1, pose2));
        path2.setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading());

    }

    @Override
    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                follower.followPath(path1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()){
                    follower.followPath(path2);
                   setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()){
                    stop();
                }
                break;
        }
    }

}


package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "scrimmage auto")
public class PedroScrimmage extends RadioactivePedroAuto{

    private Path path1,path2, path3, path4, path5, path6, path7, path8;

    @Override
    public void buildPaths(){


        // First, set the starting pose
        startingPose = new Pose(7.5, 54.5, 0);

        Pose pose1 = new Pose(35.125,46.375,0);
        Pose pose2 = new Pose(41.5,33,Math.PI/3.6);
        Pose pose3 =  new Pose(121.5,17,((Math.PI/4)+Math.PI+0.06));

        path1 = new Path(new BezierLine(startingPose, pose1));
        path1.setConstantHeadingInterpolation(pose1.getHeading());

        path2 = new Path(new BezierLine(pose1, pose2));
        path2.setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading());

        path3 = new Path(new BezierLine(pose2, pose3));
        path3.setLinearHeadingInterpolation(pose2.getHeading(), pose3.getHeading());



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
                goNextPath(2, path3);
                break;
            case 3:
                endPath(follower);
                break;
        }
    }

}

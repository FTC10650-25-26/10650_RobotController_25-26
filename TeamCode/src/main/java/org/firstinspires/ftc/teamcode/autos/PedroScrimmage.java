package org.firstinspires.ftc.teamcode.autos;

//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.Path;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.geometry.*;

@Autonomous (name = "scrimmage auto")
public class PedroScrimmage extends RadioactivePedroAuto{

    private Path path1,path2, path3, path4, path5, path6, path7, path8, path9;
    //private PathChain


    @Override
    public void buildPaths(){
        final Pose startPose, pose2, pose3, pose4shoot1, pose5, pose6, pose7shoot2, pose8, pose9, pose10shoot3, pose11;
        final Pose cntrlPose1, cntrlPose2, cntrlPose3;

        // First, set the starting pose
        startPose = new Pose(9, 84, 0);

        pose2 = new Pose(33,101,0);
        pose3 =  new Pose(33,114, 0);

        pose4shoot1 =  new Pose(57, 84, inRads(35.5));

        pose5 =  new Pose(57, 101, 0);
        pose6 =  new Pose(57, 114, 0);

        pose7shoot2 =  new Pose(81, 84, inRads(45));

        pose8 =  new Pose(81, 101, 0);
        pose9 =  new Pose(81, 114, 0);

        pose10shoot3 =  pose7shoot2;




        path1 = new Path(new BezierCurve(startPose, pose2));
        path1.setConstantHeadingInterpolation(startPose.getHeading());

        path2 = new Path(new BezierLine(pose2, pose3));
        path2.setConstantHeadingInterpolation(pose2.getHeading());

        path3 = new Path(new BezierCurve(pose3, pose4shoot1));
        path3.setLinearHeadingInterpolation(pose3.getHeading(), pose4shoot1.getHeading());

        path4 = new Path(new BezierLine(pose4shoot1, pose5));
        path4.setConstantHeadingInterpolation(pose4shoot1.getHeading());

        path5 = new Path(new BezierLine(pose5, pose6));
        path5.setLinearHeadingInterpolation(pose5.getHeading(), pose6.getHeading());

        path6 = new Path(new BezierCurve(pose6, pose7shoot2));
        path6.setLinearHeadingInterpolation(pose6.getHeading(), pose7shoot2.getHeading());

        path7 = new Path(new BezierLine(pose7shoot2, pose8));
        path7.setLinearHeadingInterpolation(pose7shoot2.getHeading(), pose8.getHeading());

        path8 = new Path(new BezierLine(pose8, pose9));
        path8.setConstantHeadingInterpolation(pose8.getHeading());

        path9 = new Path(new BezierCurve(pose9, pose10shoot3));
        path9.setLinearHeadingInterpolation(pose9.getHeading(), pose10shoot3.getHeading());


    }

    @Override
    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                startPath(path1);
                break;
            case 1:
                if (!follower.isBusy()){
                    //start intaking?
                    endPath(follower);
                    goNextPath(path2, 2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    //let outtake start running?
                    goNextPath(path3, 3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    //start belt action
                    goNextPath(path4, 4);
                    //stop belt
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    //start intake
                    goNextPath(path5, 5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    goNextPath(path6, 6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    goNextPath(path7, 7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    goNextPath(path8, 8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    goNextPath(path9, 9);
                }
                break;
            case 9:
                endPath(follower);
                break;
        }
    }

}

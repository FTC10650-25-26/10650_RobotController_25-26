package org.firstinspires.ftc.teamcode.autos;

//import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.telemetryM;
//
//import com.bylazar.configurables.PanelsConfigurables;
//import com.bylazar.configurables.annotations.Configurable;
//import com.bylazar.configurables.annotations.IgnoreConfigurable;
//import com.bylazar.field.FieldManager;
//import com.bylazar.field.PanelsField;
//import com.bylazar.field.Style;
//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
//import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;


@Autonomous (name = "Meat2RedFulPath")
public class Meat2RedFullPath extends RadioactivePedroAuto {
    private Path path1,path2, path3, path4, path5, path6, path7, path8, path9;

    public ElapsedTime time = new ElapsedTime();
    Boolean isFullSpeed = false;




    @Override
    public void buildPaths(){



        // First, set the starting pose
//        startingPose = new Pose(111.125, 144, inRads(90));
//        follower.setPose(startingPose);
        startingPose = new Pose(0, 0, inRads(0));
        follower.setPose(startingPose);

        Pose pose1 = new Pose(-24.25,-50.5,inRads(-49));//shoot
        Pose pose2 = new Pose(-10.25,-50,inRads(90));//prepare   //y=51
        Pose pose3 =  new Pose(9.25,-50,inRads(90));//final intake

        Pose pose4 = new Pose(-24.25, -50.5, inRads(-45));//shoot  //x = 55-> -22,
        Pose pose5 = new Pose(-10.25, -72.5, inRads(90));//prepare
        Pose pose6 = new Pose(9.25, -72.5, inRads(90));//final intake

        Pose pose7 = new Pose(-22.5, -53.5, inRads(-45));//shoot
        Pose pose8 = new Pose(-10.25, -98, inRads(93));//prepare
        Pose pose9 =  new Pose(9.25,-98,inRads(93));//final intake





//

//        Pose pose1 = new Pose(96,96,inRads(45));
//        Pose pose2 = new Pose(101,84,0);
//        Pose pose3 =  new Pose(120.5,84,inRads(180));
//        Pose pose4 = new Pose(89, 88.5, inRads(45));
//        Pose pose5 = new Pose(120.5, 60, inRads(180));



        path1 = new Path(new BezierLine(startingPose, pose1));
        path1.setLinearHeadingInterpolation(startingPose.getHeading(), pose1.getHeading());

        path2 = new Path(new BezierLine(pose1, pose2));
        path2.setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading());


//        Path slowPath = follower.pathBuilder(
//                .setLinearConstra`
//        )

        path3 = new Path(new BezierLine(pose2, pose3));
        path3.setLinearHeadingInterpolation(pose2.getHeading(), pose3.getHeading());
        path3.setVelocityConstraint(.0000000000001);
        //path3.setVelocityConstraint();

        path4 = new Path(new BezierLine(pose3, pose4));
        path4.setLinearHeadingInterpolation(pose3.getHeading(), pose4.getHeading());

        path5 = new Path(new BezierLine(pose4, pose5));
        path5.setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading());

        path6 = new Path(new BezierLine(pose5, pose6));
        path6.setLinearHeadingInterpolation(pose5.getHeading(), pose6.getHeading());
        path6.setVelocityConstraint(0.0000000001);

        path7 = new Path(new BezierLine(pose6, pose7));
        path7.setLinearHeadingInterpolation(pose6.getHeading(), pose7.getHeading());


        path8 = new Path(new BezierLine(pose7, pose8));
        path8.setLinearHeadingInterpolation(pose7.getHeading(), pose8.getHeading());

        path9 = new Path(new BezierLine(pose8, pose9));
        path9.setLinearHeadingInterpolation(pose8.getHeading(), pose9.getHeading());
        path9.setVelocityConstraint(0.000001);
//
//        path10 = new Path(new BezierLine(pose9, pose10));
//        path10.setLinearHeadingInterpolation(pose6.getHeading(), pose7.getHeading());
//



    }

    @Override
    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                follower.followPath(path1);
                setPathState(1);
                break;

            case 1:
                //going shoot
                if (!follower.isBusy()){
                    shoot3(1360, .3);//.192
                    follower.followPath(path2);
                    setPathState(2);
                    robot.intake.setPower(1);

                    //follower.wait(10);
                }
                break;


            case 2:
                robot.wheel2.setVelocity(0);
                robot.wheel1.setVelocity(0);
                //prepare intake
                if (!follower.isBusy()){
                    robot.belt.setVelocity(2000);
                    follower.followPath(path3);
                    setPathState(3);
                }

                break;
            case 3:
                //to final intake pos
                if (!follower.isBusy()){
                    follower.followPath(path4);
                    setPathState(4);

                }
                break;
            case 4:
                robot.intake.setPower(0);
                robot.belt.setVelocity(0);
                //to shoot
                if (!follower.isBusy()){
                    shoot3(1360, .2);
                    follower.followPath(path5);
                    setPathState(5);
                }
                break;
            case 5:
                //prepare intake
                if (!follower.isBusy()){
                    robot.intake.setPower(1);
                    robot.belt.setVelocity(2000);
                    follower.followPath(path6);
                    setPathState(6);

                }
                break;
            case 6:
                if (!follower.isBusy()){
                    follower.followPath(path7);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()){
                    follower.followPath(path8);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()){
                    follower.followPath(path9);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()){
                    stop();
                }
                break;

//                if (!follower.isBusy()){
//                    follower.followPath(path7);
//                    setPathState(7);
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()){
//                    stop();
//                }
//                break;

        }
    }

    public void shoot3(double velocity, double servoPos){
        isFullSpeed = false;
        time.reset();
        double incrementalVel = 0;

        while(time.seconds()<3){
            robot.shooterServo.setPosition(servoPos);

            if (incrementalVel< velocity){
                incrementalVel +=300;
                if (incrementalVel>velocity||incrementalVel==velocity){
                    incrementalVel = velocity;
                    if (robot.wheel1.getVelocity()==velocity){
                        isFullSpeed = true;
                    }
                }
            }
            robot.wheel1.setVelocity(incrementalVel);
            robot.wheel2.setVelocity(incrementalVel);
            if (isFullSpeed){
                robot.belt.setVelocity(2000);
            }
        }
        while(time.seconds()<3.5){
            robot.pusherServo.setPosition(1);
        }
        robot.pusherServo.setPosition(0);
        robot.wheel1.setVelocity(0);
        robot.wheel2.setVelocity(0);
    }

}


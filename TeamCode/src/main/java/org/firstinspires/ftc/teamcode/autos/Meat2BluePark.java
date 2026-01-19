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


@Autonomous (name = "Meat2BluePark")
public class Meat2BluePark extends RadioactivePedroAuto {
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

        Pose pose1 = new Pose(-24,0,inRads(0));//shoot
        //Pose pose2 = new Pose(31,-2,inRads(107));//shoot


        //Pose pose2 = new Pose(-2,-39,inRads(-90));//prepare   //y=51
//        Pose pose3 =  new Pose(9.25,-50,inRads(90));//final intake
//
//
        path1 = new Path(new BezierLine(startingPose, pose1));
        path1.setLinearHeadingInterpolation(startingPose.getHeading(), pose1.getHeading());

//        path2 = new Path(new BezierLine(pose1, pose2));
//        path2.setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading());
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
                    stop();
                    robot.belt.setVelocity(0);
                    //shoot3(1320, .4);//.192
//                    follower.followPath(path2);
//                    setPathState(2);
//                    //robot.intake.setPower(1);

                    //follower.wait(10);
                }
                break;
//
//
//            case 2:
//                robot.wheel2.setVelocity(0);
//                robot.wheel1.setVelocity(0);
//                //prepare intake
//                if (!follower.isBusy()){
//                    stop();
//                    robot.belt.setVelocity(0);
//                    //robot.belt.setVelocity(2000);
////                    follower.followPath(path3);
////                    setPathState(3);
//                }
//
//                break;
//            case 3:
//                //to final intake pos
//                if (!follower.isBusy()){
//                    stop();
//                    robot.belt.setVelocity(0);
//
//                }
//                break;
//                if (!follower.isBusy()){
//                    follower.followPath(path4);
//                    setPathState(4);
//
//                }
//                break;
//            case 4:
//                robot.intake.setPower(0);
//                robot.belt.setVelocity(0);
//                //to shoot
//                if (!follower.isBusy()){
//                    shoot3(1360, .2);
//                    follower.followPath(path5);
//                    setPathState(5);
//                }
//                break;
//            case 5:
//                //prepare intake
//                if (!follower.isBusy()){
//                    robot.intake.setPower(1);
//                    robot.belt.setVelocity(2000);
//                    follower.followPath(path6);
//                    setPathState(6);
//
//                }
//                break;
//            case 6:
//                if (!follower.isBusy()){
//                    follower.followPath(path7);
//                    setPathState(7);
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()){
//                    follower.followPath(path8);
//                    setPathState(8);
//                }
//                break;
//            case 8:
//                if (!follower.isBusy()){
//                    follower.followPath(path9);
//                    setPathState(9);
//                }
//                break;
//            case 9:
//                if (!follower.isBusy()){
//                    stop();
//                }
//                break;

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
        int timesRun = 0;
        double incrementalVel = 0;

        while(time.seconds()<3){
            robot.shooterServo.setPosition(servoPos);

            if (incrementalVel< velocity){
                incrementalVel +=300;
                if (incrementalVel>velocity||incrementalVel==velocity){
                    incrementalVel = velocity;
                    isFullSpeed = true;
                }
            }
            telemetry.addData("wheel curent vel", robot.wheel1.getVelocity());

            telemetry.addData("isFullSpeed", isFullSpeed);
            telemetry.update();
            robot.wheel1.setVelocity(incrementalVel);
            robot.wheel2.setVelocity(incrementalVel);

        }
        while(time.seconds()<5){
            robot.wheel1.setVelocity(velocity-80);
            robot.wheel2.setVelocity(velocity-80);

            robot.belt.setVelocity(2000);
            robot.wheel1.setVelocity(velocity-80);
            robot.wheel2.setVelocity(velocity-80);

        }
        robot.pusherServo.setPosition(0);
        while (time.seconds()<10){
            robot.pusherServo.setPosition(0);
            robot.belt.setVelocity(2000);
        }

        //robot.pusherServo.setPosition(1);
        robot.wheel1.setVelocity(0);
        robot.wheel2.setVelocity(0);
    }

}


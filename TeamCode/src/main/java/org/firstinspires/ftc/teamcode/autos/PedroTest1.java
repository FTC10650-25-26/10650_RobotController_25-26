package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.changes;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.drawOnlyCurrent;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.draw;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.stopRobot;
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
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.math.*;
import com.pedropathing.paths.*;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
//import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;

@Autonomous (name = "pedro auto 1")
public class PedroTest1 extends RadioactivePedroAuto {
    private Path path1,path2, path3;

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
                if (!follower.isBusy()){
                    follower.followPath(path3);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()){
                    stop();
                }
                break;
        }
    }

}


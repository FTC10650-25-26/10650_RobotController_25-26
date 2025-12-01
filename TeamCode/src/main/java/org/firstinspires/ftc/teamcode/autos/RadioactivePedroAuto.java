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
import static com.sun.tools.doclint.HtmlTag.B;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
//import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;
import org.firstinspires.ftc.teamcode.utils.MyChemicalRobot;
import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

abstract public class RadioactivePedroAuto extends RadioactiveAuto {
    Follower follower;
    private Timer pathTimer, actionTimer, opModeTimer;

    private Telemetry telemetryA;

    int pathState;

    int LLPoseCorrectionFreq= Integer.MAX_VALUE;
    int lastLLPoseCorrectionRefreshTime = (int) System.currentTimeMillis();
    Pose startingPose;



    @Override
    public void runOpMode() throws InterruptedException {
        robot = new MyChemicalRobot(hardwareMap, telemetry);
        robot.initHardware(false); // do NOT double declare the motors!
        initialize();
        waitForStart();
        begin();
    }

    @Override
    public void initialize() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);

        //follower = org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose);

        buildPaths();

//        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
//
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
//        buildPaths();
//        follower.setStartingPose(startingPose);
    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void pathingLoop(){

        robot.loopLimelightPoseData(true);


        if (System.currentTimeMillis()-lastLLPoseCorrectionRefreshTime>=LLPoseCorrectionFreq) {
            follower.setPose(robot.getLLPose());
            lastLLPoseCorrectionRefreshTime = (int) System.currentTimeMillis();
        }
        follower.update();
        autonomousPathUpdate();

        //add get limleight data metatag2





        //follower.telemetryDebug(telemetryA);
        //try telemetry.addata instead
    }



    abstract public void buildPaths();
    abstract public void autonomousPathUpdate();
    @Override
    public void begin(){
        opModeTimer.resetTimer();
        setPathState(0);

        while(opModeIsActive()) {
            pathingLoop();
        }
    }

    public void startPath(Path startPath){
        follower.followPath(startPath);
        setPathState(1);
    }

    public void goNextPath(Path currentPath, int nextCaseNum){
       follower.followPath(currentPath);
       setPathState(nextCaseNum);
    }



    public void endPath(Follower follower){
        if (!follower.isBusy()){
            stop();
        }
    }
    public double inRads(double deg){
        double radians = deg * (Math.PI/180);
        return radians;
    }





}


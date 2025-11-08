//package org.firstinspires.ftc.teamcode.teleops;
package org.firstinspires.ftc.teamcode.teleops;

//import com.bylazar.panels.Panels;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.graph.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;

//import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

//import com.bylazar.field
import com.bylazar.fullpanels.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.bylazar.ftcontrol.LoopTimer;
import com.bylazar.ftcontrol.panels.Panels;


import com.bylazar.ftcontrol.panels.configurables.annotations.Configurable;


import com.bylazar.configurables.PanelsConfigurables;
//import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.math.*;
import com.pedropathing.paths.*;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (name= "tune wheel pid")
public class TuneWheels extends ToxicTele {

    private static final FieldManager panelsField = new FieldManager();

    public TelemetryManager panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();


    public static double wheel1Vel = 0;

    public static double wheel2Vel = 0;
    public static double setWheelVel= 0;




    @Override
    public void initialize() {
        robot.limelight.pipelineSwitch(0); // Switch to pipeline number 0
        panelsTelemetry.debug("Init was ran!");
        panelsTelemetry.update(telemetry);

       // panelsTelemetry.getFtcTelemetry().;
//        panelsField.setStyle();
//        panelsField.moveCursor(pose.getX(), pose.getY());
//        panelsField.circle(ROBOT_RADIUS);

    }

    @Override
    public void teleLoop() {

        wheel1Vel = robot.wheel1.getVelocity();
        wheel2Vel = robot.wheel2.getVelocity();


        if (gamepad1.right_trigger>0){
            setWheelVel = setWheelVel+10;
        }
        robot.wheel1.setVelocity(setWheelVel);
        robot.wheel2.setVelocity(setWheelVel);


        panelsTelemetry.debug("yyyyy");

        //panelsTelemetry.g
        panelsTelemetry.update();

        //telemetry.update();


        //panelsField.update();

    }


}

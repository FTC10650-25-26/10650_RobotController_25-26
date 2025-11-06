package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
@Configurable

@TeleOp (name= "tune wheel pid")
public class TuneWheels extends ToxicTele {

    private static final FieldManager panelsField = new FieldManager();


    public static double wheel1Vel = 0;

    public static double wheel2Vel = 0;
    public static double setWheelVel= 0;

    private val panelsTelemetry: TelemetryManager = Panels.getTelemetry()



    @Override
    public void initialize() {
        robot.limelight.pipelineSwitch(0); // Switch to pipeline number 0

        panelsField.setStyle();
        panelsField.moveCursor(pose.getX(), pose.getY());
        panelsField.circle(ROBOT_RADIUS);

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




        telemetry.update();


        panelsField.update();

    }


}

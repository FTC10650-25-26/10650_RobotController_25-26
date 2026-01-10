package org.firstinspires.ftc.teamcode.teleops;

import android.view.accessibility.AccessibilityNodeInfo;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;

import java.util.List;

@TeleOp (name= "LimelightTestTele")
public class LimelightTestTele extends ToxicTele {

    //Color color = RED;
    @Override
    public void initialize() {
        robot.limelight.pipelineSwitch(1); // Switch to pipeline number 0


    }

    @Override
    public void teleLoop() {
        LLResult result = robot.limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }


        // First, tell Limelight which way your robot is facing
        double robotYaw = -robot.pinpoint.getHeading(AngleUnit.DEGREES);

//        double robotYaw = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        robot.limelight.updateRobotOrientation(robotYaw);
        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            Pose3D botpose_mt1 =  result.getBotpose();
            if (botpose_mt2 != null) {
                double x = botpose_mt2.getPosition().x;
                double y = botpose_mt2.getPosition().y;
                double x1 = botpose_mt1.getPosition().x;
                double y1 = botpose_mt1.getPosition().y;
                double z1 = botpose_mt1.getOrientation().getYaw();
                telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
                telemetry.addData("MT1 Location:", "(" + x1 + ", " + y1 + ")");
                telemetry.addData("mt1Yaw", z1);
                telemetry.addData("robotYaw", robotYaw);
            }
        }
        telemetry.update();
    }



}

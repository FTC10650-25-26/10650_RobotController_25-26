package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.utils.ToxicTele;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
@TeleOp(name = "Vision Test")
public class VisionTestTele extends ToxicTele {


    @Override
    public void runOpMode() throws InterruptedException {
//        robot.initHardware(false);
//        //robot.initCamera();
//
//        robot.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
//
//        {
//            @Override
//            public void onOpened() {
//                robot.camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
//            }
//            @Override
//            public void onError(int errorCode) {
//                telemetry.addData("Camera Error", errorCode);
//                telemetry.update();
//            }
//        }
//
//        while (opModeIsActive()){
//            telemetry.addData("viewID", 4);
//            telemetry.update();
//
//        }

    }

    @Override
    public void initialize() {

    }

    @Override
    public void teleLoop() {

    }

}

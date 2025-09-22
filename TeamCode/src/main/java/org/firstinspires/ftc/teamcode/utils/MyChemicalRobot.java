package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvInternalCamera2;

public class MyChemicalRobot {

    HardwareMap hardwareMap;

    public MyChemicalRobot(HardwareMap hardwareMap){

        this.hardwareMap = hardwareMap;
    }
    DcMotor leftFront;
    DcMotor leftRear;
    DcMotor rightFront;
    DcMotor rightRear;
    public OpenCvCamera camera;
    WebcamName webcamName;
    int cameraMonitorViewId = 2131230820;

    public Limelight3A limelight;


    public void initHardware(boolean useMotors){

        if(useMotors) {
            leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
            leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
            rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
            rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
            rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

            leftFront.setPower(0);
            leftRear.setPower(0);
            rightFront.setPower(0);
            rightRear.setPower(0);

            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.setPollRateHz(100);

            telemetry.setMsTransmissionInterval(11);
        }


        //camera block
        {
//            webcamName = hardwareMap.get(WebcamName.class, "camera");
//            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        }




    }

}

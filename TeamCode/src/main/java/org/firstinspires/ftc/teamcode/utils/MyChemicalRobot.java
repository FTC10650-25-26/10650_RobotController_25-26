package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

public class MyChemicalRobot {

    HardwareMap hardwareMap;

    public MyChemicalRobot(HardwareMap hardwareMap){

        this.hardwareMap = hardwareMap;
    }
    public DcMotor leftFront;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor rightRear;

    public DcMotorEx wheel1;
    public DcMotorEx wheel2;

    public Servo intake1;
    public Servo intake2;
    public DcMotorEx belt;


    public OpenCvCamera camera;
    WebcamName webcamName;
    int cameraMonitorViewId = 2131230820;


    public void initHardware(boolean useMotors){
        if (useMotors) {

            //drivetrain
            {
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
            }

            //outtake
            {
                wheel1 = hardwareMap.get(DcMotorEx.class, "wheel1");
                wheel1.setDirection(DcMotorSimple.Direction.FORWARD);
                wheel1.setVelocity(0);
                wheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                wheel2 = hardwareMap.get(DcMotorEx.class, "wheel2");
                wheel2.setDirection(DcMotorSimple.Direction.REVERSE);
                wheel2.setVelocity(0);
                wheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                wheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            //intake
            {
//                intake1 = hardwareMap.get(DcMotorEx.class, "intake");
//                intake1.setDirection(DcMotorSimple.Direction.FORWARD);
//                intake1.setVelocity(0);
//                intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

                intake1 = hardwareMap.get(Servo.class, "intake1");
                intake1.setDirection(Servo.Direction.FORWARD);
                //intake1.setPosition(0);
                intake2 = hardwareMap.get(Servo.class, "intake2");
                intake2.setDirection(Servo.Direction.FORWARD);
                //intake2.setPosition(0);



                belt = hardwareMap.get(DcMotorEx.class, "belt");
                belt.setDirection(DcMotorSimple.Direction.FORWARD);
                belt.setVelocity(0);
                belt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            }




        }

        //camera block
        {
            webcamName = hardwareMap.get(WebcamName.class, "camera");
            camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        }




    }

}

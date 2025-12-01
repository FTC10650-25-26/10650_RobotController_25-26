package org.firstinspires.ftc.teamcode.autos;

//import com.bylazar.field.FieldManager;
//import com.bylazar.field.PanelsField;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.utils.RadioactiveAuto;

//import com.bylazar.configurables.annotations.Configurable;
//import com.bylazar.configurables.annotations.IgnoreConfigurable;
//import com.bylazar.configurables.PanelsConfigurables;
//import com.bylazar.field.FieldManager;
//import com.bylazar.field.PanelsField;
//import com.bylazar.field.Style;
//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
//


@Autonomous(name = "limelight test")
public class LimeLightTest extends RadioactiveAuto {





    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

    }

    @Override
    public void initialize() {
        //robot.limelight.pipelineSwitch(0);

        //robot.limelight.start();

    }

    @Override
    public void begin() {
        while (opModeIsActive()){
            LLResult result = robot.limelight.getLatestResult();
            if (result != null){
                if(result.isValid()){


                    //Pose3d botpose = result.getBotpose();
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("ta", result.getTa());

                    //telemetry.addData("Botpose", botpose.toString());

                }
            }
        }

    }



}

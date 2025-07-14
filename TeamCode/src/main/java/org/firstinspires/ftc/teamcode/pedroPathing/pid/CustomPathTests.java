package org.firstinspires.ftc.teamcode.pedroPathing.pid;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.LConstants;


/**
 * This is the CurvedBackAndForth autonomous OpMode. It runs the robot in a specified distance
 * forward and to the left. On reaching the end of the forward Path, the robot runs the backward
 * Path the same distance back to the start. Rinse and repeat! This is good for testing a variety
 * of Vectors, like the drive Vector, the translational Vector, the heading Vector, and the
 * centripetal Vector. Remember to test your tunings on StraightBackAndForth as well, since tunings
 * that work well for curves might have issues going in straight lines.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/13/2024
 */
@Config
@Autonomous (name = "custom path tests", group = "PIDF Testing")
public class CustomPathTests extends OpMode {
    private Telemetry telemetryA;

    public static double DISTANCE = 15;

    private boolean tringle1 = true;
    private boolean tringle2= false;
    private boolean tringle3 = false;
    private boolean tringle4 = false;


    private Follower follower;


    private Path circlePaste;
    private Path circleSquare1;
    private Path circleSquare2;
    private Path triangle;
    private Path triangle1;

    private Path triangle2;

    private Path triangle3;
    private Path triangle4;





    /**
     * This initializes the Follower and creates the forward and backward Paths. Additionally, this
     * initializes the FTC Dashboard telemetry.
     */
    @Override
    public void init() {

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);


        //circleCartesian = new Path(new BezierCurve(new Point(15, 15, 0 ), new Point (0, 30, 0), new Point(-15, 15, 0), new Point(0, 0, 0));
        //circlePolar = new Path (new BezierCurve(new Point(0, 0, Point.POLAR), new Point(15, 0, Point.POLAR), new Point(15, 1.571, Point.POLAR), new Point(15, 3.141, Point.POLAR), new Point(15, 4.712, Point.POLAR),new Point(15, 6.28, Point.POLAR)));
        //circlePaste = new Path(new BezierCurve(new Point(0,0, Point.CARTESIAN), new Point(20,0, Point.CARTESIAN), new Point(20,20, Point.CARTESIAN)));
        //circleSquare1 = new Path( new BezierCurve( new Point(24, 0, Point.CARTESIAN), new Point(24, 24, Point.CARTESIAN), new Point(0, 24, Point.CARTESIAN), new Point(0, 0, Point.CARTESIAN)));
       // circleSquare2 = new Path( new BezierCurve(  new Point(0, 0, Point.CARTESIAN), new Point(-24, -24, Point.CARTESIAN), new Point(-24, -24, Point.CARTESIAN), new Point(0, -24, Point.CARTESIAN), new Point(0, 0, Point.CARTESIAN)));
        triangle1 =new Path( new BezierLine(new Point( new Pose(0, 0, 0)), new Point(new Pose(24, 0, 0))));
        triangle1.setLinearHeadingInterpolation(0, 5.4977);
        triangle2 =new Path( new BezierLine(new Point( new Pose(24, 0, 0)), new Point(new Pose(12, 24, 0))));
        triangle2.setLinearHeadingInterpolation(5.4977, 3.926);
        triangle3 =new Path( new BezierLine(new Point( new Pose(12, 24, 0)), new Point(new Pose(0, 0, 0))));
        triangle3.setLinearHeadingInterpolation(3.926, 0);
//        triangle4 =new Path( new BezierLine(new Point( new Pose(-12, 0, 0)), new Point(new Pose(0, 0, 0))));
//




        //backwards.setReversed(true);



        follower.followPath(triangle1);

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("This will run the robot in a curve going " + DISTANCE + " inches"
                            + " to the left and the same number of inches forward. The robot will go"
                            + "forward and backward continuously along the path. Make sure you have"
                            + "enough room.");
        telemetryA.update();
    }

    /**
     * This runs the OpMode, updating the Follower as well as printing out the debug statements to
     * the Telemetry, as well as the FTC Dashboard.
     */
    @Override
    public void loop() {
        follower.update();
        if (!follower.isBusy()) {
            if (tringle1) {
                tringle1 = false;
                tringle2=true;
                follower.followPath(triangle2);

            } else if (tringle2) {
                tringle2=false;
                tringle3 = true;
                follower.followPath(triangle3);
            } //else if (tringle3) {
//                tringle3 = false;
//                tringle4 = true;
//                follower.followPath(triangle4);
//            }

        }

       // telemetryA.addData("going circle", circleSquare1);
        follower.telemetryDebug(telemetryA);
    }
}

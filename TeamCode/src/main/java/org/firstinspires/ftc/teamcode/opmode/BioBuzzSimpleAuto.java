
package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "BioBuzz Forward Back Park", group = "BioBuzz")
public class BioBuzzSimpleAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Starting position: x = 0, y = 0, heading = 0
        Pose2d startPose = new Pose2d(0, 0, 0);

        MecanumDrive drive = new MecanumDrive(
                hardwareMap, startPose
        );

        telemetry.addLine("BioBuzz Auto Ready");
        telemetry.addLine("Forward 1 meter, return, stop");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        // 1 meter = approximately 39.37 inches.
        Actions.runBlocking(
                drive.actionBuilder(startPose)
                        .lineToX(39.37)
                        .lineToX(0)
                        .build()
        );

        drive.setDrivePowers(
                new com.acmerobotics.roadrunner.PoseVelocity2d(
                        new com.acmerobotics.roadrunner.Vector2d(0, 0),
                        0
                )
        );

        telemetry.addLine("Auto complete - returned to start");
        telemetry.update();
    }
}

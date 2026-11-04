package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class BioBuzzAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d startPose = new Pose2d(0, 0, 0);

        MecanumDrive drive = new MecanumDrive(
                hardwareMap,
                startPose
        );

        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        Actions.runBlocking(
                drive.actionBuilder(startPose)

                        // Drive forward approximately 1 meter
                        .lineToX(39.37)

                        // Return approximately 1 meter
                        .lineToX(0)

                        .build()
        );

        drive.setDrivePowers(
                new com.acmerobotics.roadrunner.PoseVelocity2d(
                        new com.acmerobotics.roadrunner.Vector2d(0, 0),
                        0
                )
        );

        telemetry.update();
    }
}
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name = "BioBuzz TeleOp", group = "BioBuzz")
public class BioBuzzTeleOp extends LinearOpMode {

    private Drive drive;
    private Intake intake;
    private Indexer indexer;
    private Shooter shooter;

    @Override
    public void runOpMode() {

        // =====================================
        // INITIALIZE SUBSYSTEMS
        // =====================================

        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        indexer = new Indexer(hardwareMap);
        shooter = new Shooter(hardwareMap);

        telemetry.addLine("==============================");
        telemetry.addLine("       NEBULA 10862");
        telemetry.addLine("          BIOBUZZ");
        telemetry.addLine("==============================");
        telemetry.addLine("");
        telemetry.addLine("Drive Centric");
        telemetry.addLine("One Gamepad");
        telemetry.addLine("");
        telemetry.addLine("RB = Shooter");
        telemetry.addLine("LB = Intake + Indexer");
        telemetry.addLine("D-Pad Down = OUTTAKE");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // =====================================
            // DRIVE CENTRIC
            // =====================================

            double drivePower = -gamepad1.left_stick_y;
            double strafePower = gamepad1.left_stick_x;
            double turnPower = gamepad1.right_stick_x;

            drive.drive(
                    drivePower,
                    strafePower,
                    turnPower
            );

            // =====================================
            // OUTTAKE HAS PRIORITY
            // =====================================

            if (gamepad1.dpad_down) {

                // Everything runs backwards
                intake.outtake();
                indexer.outtake();
                shooter.outtake();

            } else {

                // =================================
                // SHOOTER
                // =================================

                if (gamepad1.right_bumper) {
                    shooter.shoot();
                } else {
                    shooter.stop();
                }

                // =================================
                // INTAKE + INDEXER
                // =================================

                if (gamepad1.left_bumper) {

                    intake.intake();
                    indexer.feed();

                } else {

                    intake.stop();
                    indexer.stop();
                }
            }

            // =====================================
            // TELEMETRY
            // =====================================

            telemetry.addLine("NEBULA 10862 - BIOBUZZ");
            telemetry.addLine("------------------------------");

            telemetry.addData(
                    "Drive",
                    "Drive Centric"
            );

            telemetry.addData(
                    "Shooter L",
                    "%.0f",
                    shooter.getLeftVelocity()
            );

            telemetry.addData(
                    "Shooter R",
                    "%.0f",
                    shooter.getRightVelocity()
            );

            telemetry.addData(
                    "Intake",
                    "%.2f",
                    intake.getPower()
            );

            telemetry.addData(
                    "Indexer",
                    "%.2f",
                    indexer.getPower()
            );

            telemetry.update();
        }

        // =====================================
        // STOP EVERYTHING
        // =====================================

        drive.stop();
        intake.stop();
        indexer.stop();
        shooter.stop();
    }
}
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp(name = "BioBuzz Field Centric", group = "BioBuzz")
public class FieldCentric extends LinearOpMode {

    @Override
    public void runOpMode() {

        // =========================
        // GET DRIVETRAIN MOTORS
        // =========================

        DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
        DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
        DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
        DcMotor bR = hardwareMap.get(DcMotor.class, "bR");


        // =========================
        // GET IMU
        // =========================

        IMU imu = hardwareMap.get(IMU.class, "imu");


        // =========================
        // MOTOR DIRECTIONS
        // =========================

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);

        fR.setDirection(DcMotorSimple.Direction.FORWARD);
        bR.setDirection(DcMotorSimple.Direction.FORWARD);


        // =========================
        // BRAKE MODE
        // =========================

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // =========================
        // IMU INITIALIZATION
        // =========================

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);


        // =========================
        // READY
        // =========================

        telemetry.addLine("NEBULA 10862");
        telemetry.addLine("BioBuzz Field Centric");
        telemetry.addLine("One Gamepad");
        telemetry.addLine("IMU Ready");
        telemetry.addLine("Ready to Start");

        telemetry.update();


        // =========================
        // WAIT FOR START
        // =========================

        waitForStart();


        // =========================
        // FIELD CENTRIC DRIVE
        // =========================

        while (opModeIsActive()) {

            // =========================
            // RESET HEADING
            // =========================

            if (gamepad1.y) {
                imu.resetYaw();
            }


            // =========================
            // GAMEPAD INPUT
            // =========================

            // Forward / Backward
            double y = gamepad1.left_stick_y;

            // Strafe Left / Right
            double x = gamepad1.left_stick_x;

            // Rotate Left / Right
            double rx = gamepad1.right_stick_x;


            // =========================
            // GET ROBOT HEADING
            // =========================

            double botHeading = imu
                    .getRobotYawPitchRollAngles()
                    .getYaw(AngleUnit.RADIANS);


            // =========================
            // FIELD CENTRIC CALCULATION
            // =========================

            double rotX = x * Math.cos(-botHeading)
                    - y * Math.sin(-botHeading);

            double rotY = x * Math.sin(-botHeading)
                    + y * Math.cos(-botHeading);


            // =========================
            // MECANUM DRIVE
            // =========================

            double denominator = Math.max(
                    Math.abs(rotY)
                            + Math.abs(rotX)
                            + Math.abs(rx),
                    1.0
            );


            double fLPower =
                    (rotY + rotX + rx) / denominator;

            double bLPower =
                    (rotY - rotX + rx) / denominator;

            double fRPower =
                    (rotY - rotX - rx) / denominator;

            double bRPower =
                    (rotY + rotX - rx) / denominator;


            // =========================
            // SET MOTOR POWER
            // =========================

            fL.setPower(fLPower);
            bL.setPower(bLPower);

            fR.setPower(fRPower);
            bR.setPower(bRPower);


            // =========================
            // TELEMETRY
            // =========================

            telemetry.addData(
                    "Heading",
                    "%.1f°",
                    Math.toDegrees(botHeading)
            );

            telemetry.addData(
                    "Front Left",
                    "%.2f",
                    fLPower
            );

            telemetry.addData(
                    "Back Left",
                    "%.2f",
                    bLPower
            );

            telemetry.addData(
                    "Front Right",
                    "%.2f",
                    fRPower
            );

            telemetry.addData(
                    "Back Right",
                    "%.2f",
                    bRPower
            );

            telemetry.addLine("Y = Reset Heading");

            telemetry.update();
        }


        // =========================
        // STOP DRIVETRAIN
        // =========================

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
}
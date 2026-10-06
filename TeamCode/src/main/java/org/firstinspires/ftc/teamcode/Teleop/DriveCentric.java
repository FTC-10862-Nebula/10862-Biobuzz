package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "BioBuzz Drive Centric", group = "BioBuzz")
public class DriveCentric extends LinearOpMode {

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
        // READY TELEMETRY
        // =========================

        telemetry.addLine("NEBULA 10862");
        telemetry.addLine("BioBuzz Drive Centric");
        telemetry.addLine("One Gamepad");
        telemetry.addLine("Ready");

        telemetry.update();


        // =========================
        // WAIT FOR START
        // =========================

        waitForStart();


        // =========================
        // MAIN DRIVE LOOP
        // =========================

        while (opModeIsActive()) {

            // =========================
            // GAMEPAD 1
            // =========================

            // Left Stick Y = Forward / Backward
            double drive = gamepad1.left_stick_y;

            // Left Stick X = Strafe
            double strafe = gamepad1.left_stick_x;

            // Right Stick X = Turn
            double turn = gamepad1.right_stick_x;


            // =========================
            // MECANUM CALCULATIONS
            // =========================

            double fLPower = drive + strafe + turn;
            double bLPower = drive - strafe + turn;

            double fRPower = drive - strafe - turn;
            double bRPower = drive + strafe - turn;


            // =========================
            // NORMALIZE POWERS
            // =========================

            double max = Math.max(
                    1.0,
                    Math.max(
                            Math.abs(fLPower),
                            Math.max(
                                    Math.abs(bLPower),
                                    Math.max(
                                            Math.abs(fRPower),
                                            Math.abs(bRPower)
                                    )
                            )
                    )
            );

            fLPower /= max;
            bLPower /= max;
            fRPower /= max;
            bRPower /= max;


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

            telemetry.addData("Front Left", "%.2f", fLPower);
            telemetry.addData("Back Left", "%.2f", bLPower);
            telemetry.addData("Front Right", "%.2f", fRPower);
            telemetry.addData("Back Right", "%.2f", bRPower);

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

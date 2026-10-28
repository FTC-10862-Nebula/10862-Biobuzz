
package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "BioBuzz Drive Centric", group = "BioBuzz")
public class DriveCentric extends LinearOpMode {

    private DcMotorEx fL, bL, bR, fR;

    @Override
    public void runOpMode() {
        fL = hardwareMap.get(DcMotorEx.class, "fL");
        bL = hardwareMap.get(DcMotorEx.class, "bL");
        bR = hardwareMap.get(DcMotorEx.class, "bR");
        fR = hardwareMap.get(DcMotorEx.class, "fR");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.FORWARD);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.FORWARD);

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("BioBuzz Drive-Centric Ready");
        telemetry.addLine("Left stick: Move | Right stick: Turn");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double fl = forward + strafe + turn;
            double bl = forward - strafe + turn;
            double br = forward + strafe - turn;
            double fr = forward - strafe - turn;

            double denominator = Math.max(
                    1.0,
                    Math.max(Math.abs(fl),
                            Math.max(Math.abs(bl),
                                    Math.max(Math.abs(br), Math.abs(fr))))
            );

            fL.setPower(fl / denominator);
            bL.setPower(bl / denominator);
            bR.setPower(br / denominator);
            fR.setPower(fr / denominator);
        }

        stopDrive();
    }

    private void stopDrive() {
        fL.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
        fR.setPower(0);
    }
}

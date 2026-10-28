
package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "BioBuzz Field Centric", group = "BioBuzz")
public class FieldCentric extends LinearOpMode {

    private DcMotorEx fL, bL, bR, fR;
    private IMU imu;

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

        imu = hardwareMap.get(IMU.class, "imu");

        // These directions must match your Control Hub's
        // actual physical mounting orientation.
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();

        telemetry.addLine("BioBuzz Field-Centric Ready");
        telemetry.addLine("Point robot forward before starting");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double heading = imu.getRobotYawPitchRollAngles()
                    .getYaw(AngleUnit.RADIANS);

            // Rotate joystick movement into field coordinates.
            double rotX = x * Math.cos(-heading)
                    - y * Math.sin(-heading);
            double rotY = x * Math.sin(-heading)
                    + y * Math.cos(-heading);

            double fl = rotY + rotX + turn;
            double bl = rotY - rotX + turn;
            double br = rotY + rotX - turn;
            double fr = rotY - rotX - turn;

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

            telemetry.addData("Heading", "%.1f",
                    Math.toDegrees(heading));
            telemetry.update();
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

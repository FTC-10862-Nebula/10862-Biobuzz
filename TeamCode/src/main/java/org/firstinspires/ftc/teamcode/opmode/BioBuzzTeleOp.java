
package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.BioBuzzHardware;
import org.firstinspires.ftc.teamcode.hardware.BioBuzzMechanisms;

@TeleOp(name = "BioBuzz TeleOp", group = "BioBuzz")
public class BioBuzzTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        BioBuzzHardware robot = new BioBuzzHardware(hardwareMap);
        BioBuzzMechanisms mechanisms = new BioBuzzMechanisms();

        telemetry.addLine("BioBuzz initialized");
        telemetry.addLine("Drive: mecanum");
        telemetry.addLine("Intake: not configured");
        telemetry.addLine("Shooter: not configured");
        telemetry.addLine("Mechanisms will remain inactive");
        telemetry.update();

        waitForStart();

        try {
            while (opModeIsActive()) {

                double forward = -gamepad1.left_stick_y;
                double strafe = gamepad1.left_stick_x;
                double turn = gamepad1.right_stick_x;

                double fl = forward + strafe + turn;
                double bl = forward - strafe + turn;
                double br = forward + strafe - turn;
                double fr = forward - strafe - turn;

                double max = Math.max(
                        1.0,
                        Math.max(
                                Math.max(Math.abs(fl), Math.abs(bl)),
                                Math.max(Math.abs(br), Math.abs(fr))
                        )
                );

                robot.frontLeft.setPower(fl / max);
                robot.backLeft.setPower(bl / max);
                robot.backRight.setPower(br / max);
                robot.frontRight.setPower(fr / max);

                // Requested controls. These are no-ops until
                // the real motors are assigned to mechanisms.
                mechanisms.setIntakePower(
                        gamepad1.left_bumper ? 1.0 : 0.0
                );

                mechanisms.setShooterPower(
                        gamepad1.right_bumper ? 1.0 : 0.0
                );

                telemetry.addData("Intake configured",
                        mechanisms.hasIntake());
                telemetry.addData("Shooter configured",
                        mechanisms.hasShooter());
                telemetry.addData("Forward", "%.2f", forward);
                telemetry.addData("Strafe", "%.2f", strafe);
                telemetry.addData("Turn", "%.2f", turn);
                telemetry.update();
            }
        } finally {
            robot.stopDrive();
            mechanisms.stopAll();
        }
    }
}

package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

@TeleOp(name = "Field Centric", group = "Nebula")
public class FieldCentric1 extends LinearOpMode {

    private DcMotorEx fL, fR, bL, bR;
    private Servo servoL, servoR;
    private IMU imu;

    private Intake intake;
    private Shooter shooter;

    private double servoPosition = 0.5;
    private static final double SERVO_SPEED = 0.5;

    @Override
    public void runOpMode() {

        fL = hardwareMap.get(DcMotorEx.class, "fL");
        fR = hardwareMap.get(DcMotorEx.class, "fR");
        bL = hardwareMap.get(DcMotorEx.class, "bL");
        bR = hardwareMap.get(DcMotorEx.class, "bR");

        fL.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.FORWARD);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.FORWARD);

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);

        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        servoL = hardwareMap.get(Servo.class, "ServoL");
        servoR = hardwareMap.get(Servo.class, "ServoR");

        servoL.setPosition(servoPosition);
        servoR.setPosition(servoPosition);

        telemetry.addLine("Field Centric Ready");
        telemetry.addLine("Using Gamepad 1");
        telemetry.addLine("Press OPTIONS to reset heading");
        telemetry.update();

        waitForStart();

        double lastTime = getRuntime();

        while (opModeIsActive()) {

            // RESET HEADING
            if (gamepad1.options) {
                imu.resetYaw();
            }

            // FIELD-CENTRIC DRIVETRAIN
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double heading = imu.getRobotYawPitchRollAngles()
                    .getYaw(AngleUnit.RADIANS);

            double rotatedX = x * Math.cos(-heading)
                    - y * Math.sin(-heading);

            double rotatedY = x * Math.sin(-heading)
                    + y * Math.cos(-heading);

            double denominator = Math.max(
                    Math.abs(rotatedY)
                            + Math.abs(rotatedX)
                            + Math.abs(turn),
                    1.0
            );

            fL.setPower((rotatedY + rotatedX + turn) / denominator);
            bL.setPower((rotatedY - rotatedX + turn) / denominator);
            fR.setPower((rotatedY - rotatedX - turn) / denominator);
            bR.setPower((rotatedY + rotatedX - turn) / denominator);

            // INTAKE
            if (gamepad1.dpad_down) {
                intake.outtake();
            } else if (gamepad1.right_bumper) {
                intake.intake();
            } else {
                intake.stop();
            }

            // SHOOTER
            if (gamepad1.dpad_down) {
                shooter.reverse();
            } else if (gamepad1.left_bumper) {
                shooter.shoot();
            } else {
                shooter.stop();
            }

            // SERVOS
            double currentTime = getRuntime();
            double deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            if (gamepad1.right_trigger > 0.05) {
                servoPosition += SERVO_SPEED * deltaTime
                        * gamepad1.right_trigger;
            }

            if (gamepad1.left_trigger > 0.05) {
                servoPosition -= SERVO_SPEED * deltaTime
                        * gamepad1.left_trigger;
            }

            servoPosition = Math.max(0.0, Math.min(1.0, servoPosition));

            servoL.setPosition(servoPosition);
            servoR.setPosition(servoPosition);

            telemetry.addData("Heading", Math.toDegrees(heading));
            telemetry.addData("Servo Position", servoPosition);
            telemetry.addData("Mode", "Field Centric");
            telemetry.update();
        }

        stopRobot();
    }

    private void stopRobot() {
        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
        intake.stop();
        shooter.stop();
    }
}
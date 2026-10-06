package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private final DcMotor intake;

    public Intake(HardwareMap hardwareMap) {

        intake = hardwareMap.get(DcMotor.class, "intake");

        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void intake() {
        intake.setPower(1.0);
    }

    public void outtake() {
        intake.setPower(-1.0);
    }

    public void stop() {
        intake.setPower(0);
    }

    public double getPower() {
        return intake.getPower();
    }
}
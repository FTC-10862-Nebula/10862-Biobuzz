package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    private final DcMotorEx shooterL;
    private final DcMotorEx shooterR;

    // Starting velocity target.
    // This is encoder ticks per second, NOT RPM.
    private static final double SHOOTER_VELOCITY = 3000;

    public Shooter(HardwareMap hardwareMap) {

        shooterL = hardwareMap.get(DcMotorEx.class, "shooterL");
        shooterR = hardwareMap.get(DcMotorEx.class, "shooterR");

        shooterL.setDirection(DcMotor.Direction.REVERSE);
        shooterR.setDirection(DcMotor.Direction.FORWARD);

        shooterL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shooterL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void shoot() {

        shooterL.setVelocity(SHOOTER_VELOCITY);
        shooterR.setVelocity(SHOOTER_VELOCITY);
    }

    public void outtake() {

        shooterL.setVelocity(-SHOOTER_VELOCITY);
        shooterR.setVelocity(-SHOOTER_VELOCITY);
    }

    public void stop() {

        shooterL.setVelocity(0);
        shooterR.setVelocity(0);
    }

    public double getLeftVelocity() {
        return shooterL.getVelocity();
    }

    public double getRightVelocity() {
        return shooterR.getVelocity();
    }
}
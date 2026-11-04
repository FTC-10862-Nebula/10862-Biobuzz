package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.NebulaMotor;

public class Shooter {

    private final NebulaMotor shooterL;
    private final NebulaMotor shooterR;

    // Change a direction to REVERSE if that motor spins the wrong way.
    private static final DcMotorSimple.Direction SHOOTER_L_DIRECTION =
            DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction SHOOTER_R_DIRECTION =
            DcMotorSimple.Direction.FORWARD;

    public Shooter(HardwareMap hardwareMap) {
        shooterL = new NebulaMotor(
                hardwareMap,
                "ShooterL",
                SHOOTER_L_DIRECTION,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        shooterR = new NebulaMotor(
                hardwareMap,
                "ShooterR",
                SHOOTER_R_DIRECTION,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );
    }

    public void shoot() {
        setPower(1.0);
    }

    public void reverse() {
        setPower(-1.0);
    }

    public void stop() {
        setPower(0);
    }

    public void setPower(double power) {
        shooterL.setPower(power);
        shooterR.setPower(power);
    }
}
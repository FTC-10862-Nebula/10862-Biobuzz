package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.NebulaMotor;

public class Intake {

    private final NebulaMotor intakeL;
    private final NebulaMotor intakeR;

    // Change a direction to REVERSE if that motor spins the wrong way.
    private static final DcMotorSimple.Direction INTAKE_L_DIRECTION =
            DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction INTAKE_R_DIRECTION =
            DcMotorSimple.Direction.FORWARD;

    public Intake(HardwareMap hardwareMap) {
        intakeL = new NebulaMotor(
                hardwareMap,
                "IntakeL",
                INTAKE_L_DIRECTION,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        intakeR = new NebulaMotor(
                hardwareMap,
                "IntakeR",
                INTAKE_R_DIRECTION,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );
    }

    public void intake() {
        setPower(1.0);
    }

    public void outtake() {
        setPower(-1.0);
    }

    public void stop() {
        setPower(0);
    }

    public void setPower(double power) {
        intakeL.setPower(power);
        intakeR.setPower(power);
    }
}
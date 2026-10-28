package org.firstinspires.ftc.teamcode.util.nebulaHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class NebulaMotor {
    private final DcMotorEx motor;
    private final boolean isEnabled;

    public NebulaMotor(HardwareMap hardwareMap, String deviceId,
                       DcMotorSimple.Direction direction,
                       DcMotor.ZeroPowerBehavior behavior,
                       boolean isEnabled) {

        motor = hardwareMap.get(DcMotorEx.class, deviceId);
        this.isEnabled = isEnabled;

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor.setZeroPowerBehavior(behavior);
        motor.setDirection(direction);

        motor.setPower(0);
    }

    /** Power control */
    public void setPower(double power) {
        if (!isEnabled) motor.setPower(0);
        else motor.setPower(power);
    }
    public double getVelocity() {
        if (!isEnabled) return 0;
        return motor.getVelocity(); // reads from encoder
    }

    /** Position control (RUN_TO_POSITION mode) */
    public void setTargetPosition(double ticks) {
        if (!isEnabled) return;

        motor.setTargetPosition((int) ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);  // full power by default; you could make this configurable
    }

    /** Return encoder position */
    public int getPosition() {
        return motor.getCurrentPosition();
    }

    /** Reset encoder */
    public void resetEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Set PIDF coefficients for RUN_USING_ENCODER */
    public void setPIDFCoefficients(PIDFCoefficients coeffs) {
        if (!isEnabled) return;
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
    }
}
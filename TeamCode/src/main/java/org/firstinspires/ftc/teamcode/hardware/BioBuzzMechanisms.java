
package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

public class BioBuzzMechanisms {

    private NebulaMotor intake;
    private NebulaMotor shooter;

    public BioBuzzMechanisms() {
        // Hardware remains unassigned until its names are confirmed.
        intake = null;
        shooter = null;
    }

    public void setIntakeMotor(NebulaMotor motor) {
        intake = motor;
        if (intake != null) {
            intake.setPower(0);
        }
    }

    public void setShooterMotor(NebulaMotor motor) {
        shooter = motor;
        if (shooter != null) {
            shooter.setPower(0);
        }
    }

    public boolean hasIntake() {
        return intake != null;
    }

    public boolean hasShooter() {
        return shooter != null;
    }

    public void setIntakePower(double power) {
        if (intake != null) {
            intake.setPower(power);
        }
    }

    public void setShooterPower(double power) {
        if (shooter != null) {
            shooter.setPower(power);
        }
    }

    public void stopAll() {
        setIntakePower(0);
        setShooterPower(0);
    }
}

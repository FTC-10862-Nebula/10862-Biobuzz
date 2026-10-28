
package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

public class BioBuzzHardware {

    public final NebulaMotor frontLeft;
    public final NebulaMotor backLeft;
    public final NebulaMotor backRight;
    public final NebulaMotor frontRight;

    public final IMU imu;

    public BioBuzzHardware(HardwareMap hardwareMap) {

        frontLeft = new NebulaMotor(
                hardwareMap,
                "fL",
                DcMotorSimple.Direction.REVERSE,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        backLeft = new NebulaMotor(
                hardwareMap,
                "bL",
                DcMotorSimple.Direction.FORWARD,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        backRight = new NebulaMotor(
                hardwareMap,
                "bR",
                DcMotorSimple.Direction.REVERSE,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        frontRight = new NebulaMotor(
                hardwareMap,
                "fR",
                DcMotorSimple.Direction.FORWARD,
                DcMotor.ZeroPowerBehavior.BRAKE,
                true
        );

        imu = hardwareMap.get(IMU.class, "imu");
    }

    public void stopDrive() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontRight.setPower(0);
    }
}

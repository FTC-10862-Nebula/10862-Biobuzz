package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    private final DcMotor fL;
    private final DcMotor fR;
    private final DcMotor bL;
    private final DcMotor bR;

    public Drive(HardwareMap hardwareMap) {

        fL = hardwareMap.get(DcMotor.class, "fL");
        fR = hardwareMap.get(DcMotor.class, "fR");
        bL = hardwareMap.get(DcMotor.class, "bL");
        bR = hardwareMap.get(DcMotor.class, "bR");

        fL.setDirection(DcMotor.Direction.REVERSE);
        bL.setDirection(DcMotor.Direction.REVERSE);

        fR.setDirection(DcMotor.Direction.FORWARD);
        bR.setDirection(DcMotor.Direction.FORWARD);

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double drive, double strafe, double turn) {

        double frontLeft = drive + strafe + turn;
        double backLeft = drive - strafe + turn;
        double frontRight = drive - strafe - turn;
        double backRight = drive + strafe - turn;

        double max = Math.max(
                1.0,
                Math.max(
                        Math.abs(frontLeft),
                        Math.max(
                                Math.abs(backLeft),
                                Math.max(
                                        Math.abs(frontRight),
                                        Math.abs(backRight)
                                )
                        )
                )
        );

        fL.setPower(frontLeft / max);
        bL.setPower(backLeft / max);
        fR.setPower(frontRight / max);
        bR.setPower(backRight / max);
    }

    public void stop() {
        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
}
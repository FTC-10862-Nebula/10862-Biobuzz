package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Indexer {

    private final DcMotor indexer;

    public Indexer(HardwareMap hardwareMap) {

        indexer = hardwareMap.get(DcMotor.class, "indexer");

        indexer.setDirection(DcMotor.Direction.FORWARD);
        indexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void feed() {
        indexer.setPower(1.0);
    }

    public void outtake() {
        indexer.setPower(-1.0);
    }

    public void stop() {
        indexer.setPower(0);
    }

    public double getPower() {
        return indexer.getPower();
    }
}
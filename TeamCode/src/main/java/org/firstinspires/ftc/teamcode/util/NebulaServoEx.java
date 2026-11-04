package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dalvik.bytecode.Opcodes;

@TeleOp public class NebulaServoEx extends OpMode {
    NebulaServo bench = new NebulaServo();
    double leftTrigger, rightTrigger;
    @Override
    public void init() {
        bench.init(hardwareMap);
        leftTrigger = 0.0;
        rightTrigger = 0.0;
    }

    @Override
    public void loop() {
       leftTrigger = gamepad2.left_trigger;
       rightTrigger = gamepad2.right_trigger;

       bench.setServoRot(rightTrigger);
       bench.setServoPos(leftTrigger);

        if (gamepad1.a) {
            bench.setServoPos(0.5);
        } else {
            bench.setServoPos(1.0);

        }
        if (gamepad1.b) {
            bench.setServoRot(1.0);
        } else {
            bench.setServoRot(0);
        }
    }
}
package org.firstinspires.ftc.teamcode.util.nebulaHardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class NebulaServo {
    public enum Direction { Forward, Reverse }

    private final ServoImplEx servo;
    private final boolean isEnabled;

    public NebulaServo(HardwareMap hM, String deviceId,
                       Direction direction, boolean isEnabled) {
        servo = (ServoImplEx) hM.get(Servo.class, deviceId);
        this.isEnabled = isEnabled;

        servo.setDirection(direction == Direction.Reverse ?
                Servo.Direction.REVERSE : Servo.Direction.FORWARD);
    }

    public void setPosition(double position) {
        // Standard servo: clamp 0..1
        position = Math.max(0, Math.min(1, position));
        servo.setPosition(position);
    }

    public double getPosition() {
        return servo.getPosition();
    }
    public double getSpeed() {
        return (servo.getPosition() - 0.5) * 2.0;
    }
}
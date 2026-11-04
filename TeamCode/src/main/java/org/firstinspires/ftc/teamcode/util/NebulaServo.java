package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class NebulaServo {
    private Servo servoPos;
    private CRServo servoRot;




public void init(HardwareMap hardwareMap){
    servoPos = hardwareMap.get(Servo.class, "NebulaServo");
    servoRot = hardwareMap.get(CRServo.class, "NebulaCRServo");
    servoPos.scaleRange(0.5,1.0);
    servoPos.setDirection(Servo.Direction.REVERSE);
    servoRot.setDirection(DcMotorSimple.Direction.REVERSE);
}
public void setServoPos(double angle){
    servoPos.setPosition(angle);
}
public void setServoRot(double power){
    servoRot.setPower(power);
}

}

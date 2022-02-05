package org.firstinspires.ftc.teamcode.attachment;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opmode.AttachableOpmode;

public class ClawAttachment implements IAttachment {
    private DcMotor linearSlidePulley;
    private Servo leftClaw;
    private Servo rightClaw;
    private AttachableOpmode opmode;

    public ClawAttachment() {}

    @Override
    public void initialize(AttachableOpmode opmode) {
        this.opmode = opmode;

        linearSlidePulley = opmode.hardwareMap.get(DcMotor.class, "LinearSlide");
        leftClaw = opmode.hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = opmode.hardwareMap.get(Servo.class, "rightClaw");

        linearSlidePulley.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlidePulley.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlidePulley.setDirection(DcMotor.Direction.FORWARD);

        leftClaw.setDirection(Servo.Direction.FORWARD);
        rightClaw.setDirection(Servo.Direction.FORWARD);

        leftClaw.scaleRange(0, 1);
        rightClaw.scaleRange(0, 1);
    }

    @Override
    public void update(long dt) {
        float pulleyPower = 0;
        if (opmode.gamepad1.left_bumper ^ opmode.gamepad1.right_bumper) { // left_bumper XOR right_bumper
            pulleyPower = opmode.gamepad1.left_bumper ? -1 : 1;
        }

        if (opmode.gamepad1.a) {
            leftClaw.setPosition(1);
            rightClaw.setPosition(1);
        } else if (opmode.gamepad1.b) {
            leftClaw.setPosition(0);
            rightClaw.setPosition(0);
        }

        linearSlidePulley.setPower(pulleyPower);

        //opmode.telemetry.addData("ClawAttachment", "pulley %.3f | lclaw %.3f | rclaw %.3f", linearSlidePulley.getPower(), leftClaw.getPosition(), rightClaw.getPosition());
    }
}

package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

@TeleOp(name = "Read Servos")
public class ReadServoOpmode extends OpMode {
    private final long updateRate = 50;

    private List<Servo> servos;
    private int currentIdx = 0;

    private boolean gpa_lastState = false;
    private long lastUpdate;

    private FtcDashboard dashboard;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        servos = hardwareMap.getAll(Servo.class);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        if (gamepad1.a && !gpa_lastState) {
            currentIdx++;
        }
        gpa_lastState = gamepad1.a;

        Servo currentServo = servos.get(currentIdx % servos.size());

        if (System.currentTimeMillis() - lastUpdate > updateRate) {
            lastUpdate = System.currentTimeMillis();
            double dp = (gamepad1.right_trigger - gamepad1.left_trigger) * 0.05;
            currentServo.setPosition(currentServo.getPosition() + dp);
        }

        telemetry.addData("Current Servo", "%s", hardwareMap.getNamesOf(currentServo));
        telemetry.addData("Position", "%.3f", currentServo.getPosition());
        telemetry.update();
    }
}

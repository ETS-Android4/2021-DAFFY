package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.attachment.HolonomicDrivetrain;

@TeleOp(name = "TESSRACT")
public class TesseractTeleOp extends BaseOpmode {
    private FtcDashboard dashboard;

    @Override
    public void init(int err) {
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        addAttachment(new HolonomicDrivetrain());
    }

    @Override
    public void loop(long dt) {
        telemetry.addData("LS:", "X[%.3f] Y[%.3f]", gamepad1.left_stick_x, gamepad1.left_stick_y);
        telemetry.addData("RS:", "X[%.3f] Y[%.3f]", gamepad1.right_stick_x, gamepad1.right_stick_y);
        telemetry.update();
    }
}

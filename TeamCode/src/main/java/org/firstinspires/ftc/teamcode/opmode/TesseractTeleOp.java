package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.attachment.ClawAttachment;
import org.firstinspires.ftc.teamcode.attachment.HolonomicDrivetrainAttachment;

@TeleOp(name = "TESSRACT")
public class TesseractTeleOp extends AttachableOpmode {
    @Override
    public void init(int err) {
        addAttachment(new HolonomicDrivetrainAttachment());
        addAttachment(new ClawAttachment());
    }

    @Override
    public void loop(long dt) {
        telemetry.addData("LS:", "X[%.3f] Y[%.3f]", gamepad1.left_stick_x, gamepad1.left_stick_y);
        telemetry.addData("RS:", "X[%.3f] Y[%.3f]", gamepad1.right_stick_x, gamepad1.right_stick_y);
        telemetry.update();
    }
}

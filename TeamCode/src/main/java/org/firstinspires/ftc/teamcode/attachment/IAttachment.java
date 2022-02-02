package org.firstinspires.ftc.teamcode.attachment;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public interface IAttachment {
    void initialize(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2);
    void update(long dt);
}

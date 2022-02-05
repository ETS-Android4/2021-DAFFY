package org.firstinspires.ftc.teamcode.attachment;

import org.firstinspires.ftc.teamcode.opmode.AttachableOpmode;

public interface IAttachment {
    void initialize(AttachableOpmode opmode);
    void update(long dt);
}

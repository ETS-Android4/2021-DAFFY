package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.attachment.IAttachment;

import java.util.HashSet;

public abstract class BaseOpmode extends OpMode {
    private HashSet<IAttachment> attachments;
    private long lastUpdate;

    public void addAttachment(IAttachment attachment) {
        attachments.add(attachment);
    }

    protected void initializeAttachments() {
        for (IAttachment attachment : attachments) {
            attachment.initialize(hardwareMap, gamepad1, gamepad2);
        }
    }

    protected void updateAttachments() {
        for (IAttachment attachment : attachments) {
            attachment.update(System.currentTimeMillis() - lastUpdate);
        }
    }

    public final void init() {
        initializeAttachments();
        init(0);
    }

    public final void loop() {
        updateAttachments();
        loop(System.currentTimeMillis() - lastUpdate);
        lastUpdate = System.currentTimeMillis();
    }

    public abstract void init(int err);
    public abstract void loop(long dt);
}

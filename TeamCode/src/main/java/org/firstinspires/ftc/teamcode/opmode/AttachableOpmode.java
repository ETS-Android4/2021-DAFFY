package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.attachment.IAttachment;

import java.util.HashSet;

public abstract class AttachableOpmode extends OpMode {
    private HashSet<IAttachment> attachments;
    private long lastUpdate;

    protected FtcDashboard dashboard;

    public void addAttachment(IAttachment attachment) {
        attachments.add(attachment);
    }

    protected void initializeAttachments() {
        for (IAttachment attachment : attachments) {
            telemetry.log().add("Initializing " + attachment.getClass().getSimpleName());
            attachment.initialize(this);
            /*try {
                attachment.initialize(this);
                telemetry.log().add("Initialized " + attachment.getClass().getSimpleName() + " attachment");
            } catch (Exception e) {
                attachments.remove(attachment);
                telemetry.log().add("FAILED to initialize " + attachment.getClass().getSimpleName());
                telemetry.log().add(String.valueOf(e));
            }*/
        }
    }

    protected void updateAttachments() {
        for (IAttachment attachment : attachments) {
            attachment.update(System.currentTimeMillis() - lastUpdate);
        }
    }

    public final void init() {
        attachments = new HashSet<>();
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        init(0); // Call subclass init
        initializeAttachments();
    }

    public final void loop() {
        updateAttachments();
        loop(System.currentTimeMillis() - lastUpdate);
        lastUpdate = System.currentTimeMillis();
    }

    public abstract void init(int err);
    public abstract void loop(long dt);
}

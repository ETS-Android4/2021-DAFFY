package org.firstinspires.ftc.teamcode.attachment;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.firstinspires.ftc.teamcode.opmode.AttachableOpmode;
import org.firstinspires.ftc.teamcode.util.EnhancedGamepad;

public class HolonomicDrivetrainAttachment implements IAttachment {
    private static final RealMatrix INVERSEMATRIX = MatrixUtils.createRealMatrix(new double[][] {
            {1,  1, -1}, // FL
            {1, -1,  1}, // FR
            {1, -1, -1}, // RL
            {1,  1,  1}, // RR
    });

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor RL;
    private DcMotor RR;

    private AttachableOpmode opmode;
    private EnhancedGamepad eGamepad1;

    public HolonomicDrivetrainAttachment() {}

    @Override
    public void initialize(AttachableOpmode opmode) {
        this.opmode = opmode;
        this.eGamepad1 = EnhancedGamepad.getInstance(opmode.gamepad1);

        FR = opmode.hardwareMap.get(DcMotor.class, "FR");
        FL = opmode.hardwareMap.get(DcMotor.class, "FL");
        RR = opmode.hardwareMap.get(DcMotor.class, "RR");
        RL = opmode.hardwareMap.get(DcMotor.class, "RL");

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);
        RR.setDirection(DcMotor.Direction.FORWARD);
        RL.setDirection(DcMotor.Direction.FORWARD);

        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void update(long dt) {
        //double[] powerArr = INVERSEMATRIX.multiply(MatrixUtils.createColumnRealMatrix(new double[] {gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x})).getColumn(0);
        double[] powerArr = INVERSEMATRIX.operate(new double[] {
            -eGamepad1.get_left_stick_y(),
            eGamepad1.get_left_stick_x(),
            eGamepad1.get_left_trigger() - eGamepad1.get_right_trigger()
        });

        // Apparently the gamepad allows the joystick (x, y) to be on a circle larger than radius 1, so absolute motor power can exceed 1. This scales it down.
        // This could probably also be accomplished by correcting the gamepad's (x, y) before passing it here, but this was quicker.
        // See https://math.stackexchange.com/questions/127613/closest-point-on-circle-edge-from-point-outside-inside-the-circle
        double largestPower = 1;
        for (double pwr : powerArr) { largestPower = Math.max(Math.abs(pwr), largestPower); }
        FL.setPower(powerArr[0] / largestPower);
        FR.setPower(powerArr[1] / largestPower);
        RL.setPower(powerArr[2] / largestPower);
        RR.setPower(powerArr[3] / largestPower);

        opmode.telemetry.addData("drive", "FL %.3f FR %.3f RL %.3f RR %.3f", FL.getPower(), FR.getPower(), RL.getPower(), RR.getPower());
    }
}

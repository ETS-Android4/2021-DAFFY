package org.firstinspires.ftc.teamcode.attachment;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.firstinspires.ftc.teamcode.opmode.AttachableOpmode;

public class HolonomicDrivetrainAttachment implements IAttachment {
    //private double exponent;
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

    public HolonomicDrivetrainAttachment() {}

    @Override
    public void initialize(AttachableOpmode opmode) {
        this.opmode = opmode;

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
            this.opmode.gamepad1.left_stick_y,
            this.opmode.gamepad1.left_stick_x,
            this.opmode.gamepad1.right_stick_x
        });

        FL.setPower(powerArr[0]);
        FR.setPower(powerArr[1]);
        RL.setPower(powerArr[2]);
        RR.setPower(powerArr[3]);
    }
}

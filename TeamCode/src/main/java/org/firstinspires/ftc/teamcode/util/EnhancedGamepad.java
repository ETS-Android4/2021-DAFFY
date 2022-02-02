package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;

public class EnhancedGamepad extends Gamepad {
    private static HashMap<Gamepad, EnhancedGamepad> instances = new HashMap<>();

    public float LeftStickExponent = 1;
    public float RightStickExponent = 1;
    public float LeftTriggerExponent = 1;
    public float RightTriggerExponent = 1;

    private Gamepad gamepad;

    private EnhancedGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    // Static methods
    public static EnhancedGamepad getInstance(Gamepad gamepad) {
        EnhancedGamepad enhancedgp = instances.get(gamepad.getGamepadId());
        if (enhancedgp != null) {
            return enhancedgp;
        }
        return new EnhancedGamepad(gamepad);
    }

    public float get_left_stick_x() {
        float v = gamepad.left_stick_x;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, LeftStickExponent)); // Logarithmic control scale
        return v;
    }

    public float get_left_stick_y() {
        float v = gamepad.left_stick_y;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, LeftStickExponent)); // Logarithmic control scale
        return v;
    }

    public float get_right_stick_x() {
        float v = gamepad.right_stick_x;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, RightStickExponent)); // Logarithmic control scale
        return v;
    }

    public float get_right_stick_y() {
        float v = gamepad.right_stick_y;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, RightStickExponent)); // Logarithmic control scale
        return v;
    }

    public float get_left_trigger() {
        float v = gamepad.left_trigger;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, LeftTriggerExponent)); // Logarithmic control scale
        return v;
    }

    public float get_right_trigger() {
        float v = gamepad.right_trigger;
        v = Range.clip(v, -1, 1); // Clamp
        v = (float) (Math.signum(v) * Math.pow(v, RightTriggerExponent)); // Logarithmic control scale
        return v;
    }
}

package org.firstinspires.ftc.teamcode.autonomous.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {
    private Servo wrist;
    final double WRIST_FOLDED_IN   = 0.33;
    final double WRIST_FOLDED_OUT  = 0.02;

    public Wrist(HardwareMap hardwareMap) {
        wrist = hardwareMap.get(Servo.class, "wrist");

        wrist.setPosition(WRIST_FOLDED_IN);
    }

    public class FoldOut implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            wrist.setPosition(WRIST_FOLDED_OUT);
            return false;
        }
    }

    public Action foldOut() {
        return new FoldOut();
    }

    public class FoldIn implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            wrist.setPosition(WRIST_FOLDED_IN);
            return false;
        }
    }

    public Action foldIn() {
        return new FoldIn();
    }
}

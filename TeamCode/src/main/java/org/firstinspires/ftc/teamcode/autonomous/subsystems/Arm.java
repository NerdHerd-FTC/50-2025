package org.firstinspires.ftc.teamcode.autonomous.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private DcMotorEx arm;
    final double ARM_TICKS_PER_DEGREE = 19.7924893140647;
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 250 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 230 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_HOOK_SPECIMEN         = 165 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 15  * ARM_TICKS_PER_DEGREE;
    final double ARM_TOUCH_BAR             = 150 * ARM_TICKS_PER_DEGREE;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public class LiftToSpecimen implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setTargetPosition((int) ARM_SCORE_SPECIMEN);
            arm.setVelocity(1750);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (Math.abs(arm.getCurrentPosition() - ARM_SCORE_SPECIMEN) < 10) {
                return false;
            } else {
                return true;
            }
        }
    }

    public class ScoreSpecimen implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setTargetPosition((int) ARM_HOOK_SPECIMEN);
            arm.setVelocity(1750);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (Math.abs(arm.getCurrentPosition() - ARM_HOOK_SPECIMEN) < 10) {
                return false;
            } else {
                return true;
            }
        }
    }

    public class ClearGround implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setTargetPosition((int) ARM_WINCH_ROBOT);
            arm.setVelocity(1750);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (Math.abs(arm.getCurrentPosition() - ARM_WINCH_ROBOT) < 10) {
                return false;
            } else {
                return true;
            }
        }
    }

    public class TouchBottomBar implements Action {

        @Override

        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setTargetPosition((int) ARM_TOUCH_BAR);
            arm.setVelocity(1750);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (Math.abs(arm.getCurrentPosition() - ARM_TOUCH_BAR) < 10) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Action liftToSpecimen() {
        return new LiftToSpecimen();
    }
    public Action scoreSpecimen() {
        return new ScoreSpecimen();
    }
    public Action clearGround() {
        return new ClearGround();
    }
    public Action touchBottomBar() { return  new TouchBottomBar(); }
}

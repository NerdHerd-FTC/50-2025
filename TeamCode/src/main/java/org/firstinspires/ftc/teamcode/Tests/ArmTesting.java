package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name="Arm Tuning", group="Tests")

public class ArmTesting extends LinearOpMode {

    public DcMotor arm      = null;

    public final double armTicksPerDegree = 19.7924893140647; //exact fraction is (194481/9826)


    @Override
    public void runOpMode() {

        arm = hardwareMap.get(DcMotor.class, "arm");

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            arm.setPower(0);

            telemetry.addData("Arm Ticks", arm.getCurrentPosition());
            telemetry.addData("Arm Degrees", arm.getCurrentPosition() / armTicksPerDegree);

            telemetry.update();

        }
    }
}
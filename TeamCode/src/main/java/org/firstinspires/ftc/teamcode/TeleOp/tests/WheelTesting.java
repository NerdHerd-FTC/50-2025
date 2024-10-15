package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(group = "Tests", name = "Wheel Test")
public class WheelTesting extends OpMode {
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor[] motors = null;
    private String[] motorNames = null;

    private Gamepad gamepad1Previous = new Gamepad();
    private Gamepad gamepad1Current = new Gamepad();

    private short motorNumberToTest = 0;


    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new DcMotor[] {frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor};
        motorNames = new String[] {"Front Left Motor", "Back Left Motor", "Front Right Motor", "Back Right Motor"};
    }

    @Override
    public void loop() {
        gamepad1Previous.copy(gamepad1Current);
        gamepad1Current.copy(gamepad1);

        if (gamepad1Current.dpad_up && !gamepad1Previous.dpad_up) {
            motorNumberToTest = (short) ((motorNumberToTest + 1) % 4);
        }

        if (gamepad1Current.dpad_down && !gamepad1Previous.dpad_down) {
            if (motorNumberToTest == 0) {
                motorNumberToTest = (short) (motors.length - 1);
            } else {
                motorNumberToTest = (short) ((motorNumberToTest - 1) % 4);
            }
        }

        if (gamepad1Current.dpad_left) {
            motors[motorNumberToTest].setDirection(DcMotor.Direction.REVERSE);
        }

        if (gamepad1Current.dpad_right) {
            motors[motorNumberToTest].setDirection(DcMotor.Direction.FORWARD);
        }

        if (gamepad1Current.b) {
            motors[motorNumberToTest].setPower(1);
        } else {
            motors[motorNumberToTest].setPower(0);
        }

        telemetry.addData("Motor Number: ", motorNumberToTest);
        telemetry.addData("Motor Name: ", motorNames[motorNumberToTest]);
        telemetry.addData("Motor Direction: ", motors[motorNumberToTest].getDirection());
        telemetry.addData("Motor Power: ", motors[motorNumberToTest].getPower());
    }
}

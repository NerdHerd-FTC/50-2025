/*   MIT License
 *   Copyright (c) [2024] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

/*
 * This OpMode is an example driver-controlled (TeleOp) mode for the goBILDA 2024-2025 FTC
 * Into The Deep Starter Robot
 * The code is structured as a LinearOpMode
 *
 * This robot has a two-motor differential-steered (sometimes called tank or skid steer) drivetrain.
 * With a left and right drive motor.
 * The drive on this robot is controlled in an "Arcade" style, with the left stick Y axis
 * controlling the forward movement and the right stick X axis controlling rotation.
 * This allows easy transition to a standard "First Person" control of a
 * mecanum or omnidirectional chassis.
 *
 * The drive wheels are 96mm diameter traction (Rhino) or omni wheels.
 * They are driven by 2x 5203-2402-0019 312RPM Yellow Jacket Planetary Gearmotors.
 *
 * This robot's main scoring mechanism includes an arm powered by a motor, a "wrist" driven
 * by a servo, and an intake driven by a continuous rotation servo.
 *
 * The arm is powered by a 5203-2402-0051 (50.9:1 Yellow Jacket Planetary Gearmotor) with an
 * external 5:1 reduction. This creates a total ~254.47:1 reduction.
 * This OpMode uses the motor's encoder and the RunToPosition method to drive the arm to
 * specific setpoints. These are defined as a number of degrees of rotation away from the arm's
 * starting position.
 *
 * Make super sure that the arm is reset into the robot, and the wrist is folded in before
 * you run start the OpMode. The motor's encoder is "relative" and will move the number of degrees
 * you request it to based on the starting position. So if it starts too high, all the motor
 * setpoints will be wrong.
 *
 * The wrist is powered by a goBILDA Torque Servo (2000-0025-0002).
 *
 * The intake wheels are powered by a goBILDA Speed Servo (2000-0025-0003) in Continuous Rotation mode.
 */


@TeleOp(name="Meet 1 TeleOp", group="Main")
//@Disabled
public class StarterBotTeleOpWithIMU extends OpMode {

    /* Declare OpMode members. */
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;

    DcMotor armMotor    = null; //the arm motor
    CRServo intake      = null; //the active intake servo
    Servo   wrist       = null; //the wrist servo
    IMU     imu         = null; //the imu


    /* This constant is the number of encoder ticks for each degree of rotation of the arm.
    To find this, we first need to consider the total gear reduction powering our arm.
    First, we have an external 20t:100t (5:1) reduction created by two spur gears.
    But we also have an internal gear reduction in our motor.
    The motor we use for this arm is a 117RPM Yellow Jacket. Which has an internal gear
    reduction of ~50.9:1. (more precisely it is 250047/4913:1)
    We can multiply these two ratios together to get our final reduction of ~254.47:1.
    The motor's encoder counts 28 times per rotation. So in total you should see about 7125.16
    counts per rotation of the arm. We divide that by 360 to get the counts per degree.


    We use a 312 RPM Motor instead, which has a 19.2:1 gear ratio. (more precisely, it is 194481/9826:1)
    So we multiply 19.2 by 5 to get a total gear ratio of 96:1.
    The motor's encoder counts 28 times per rotation. So in total you should see about 2688 counts per rotation of the arm.
    We divide that by 360 to get the counts per degree. Which is 7.46666666667.

    */
    final double ARM_TICKS_PER_DEGREE = 19.7924893140647;  //7.46666666667; //exact fraction is (194481/9826)
//
//
//    /* These constants hold the position that the arm is commanded to run to.
//    These are relative to where the arm was located when you start the OpMode. So make sure the
//    arm is reset to collapsed inside the robot before you start the program.
//
//    In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
//    This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
//    set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
//    160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
//    If you'd like it to move further, increase that number. If you'd like it to not move
//    as far from the starting position, decrease it. */
//
    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 247 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 225 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 154 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 120 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 15  * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.74;
    final double WRIST_FOLDED_OUT  = 0.4;

    /* A number in degrees that the triggers can adjust the arm position by */
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;



    /* Variables that are used to set the arm to a specific position */
    double armPosition = (int) ARM_WINCH_ROBOT;
    double armPositionFudgeFactor;

    Gamepad gamepad1Previous = new Gamepad();
    Gamepad gamepad1Current = new Gamepad();

    Gamepad gamepad2Previous = new Gamepad();
    Gamepad gamepad2Current = new Gamepad();

    final double DRIVE_SPEED = 0.75;


    @Override
    public void init() {

        /* Define and Initialize Motors */
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");


        armMotor = hardwareMap.get(DcMotor.class, "arm"); //the arm motor

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        /* Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to slow down
        much faster when it is coasting. This creates a much more controllable drivetrain. As the robot
        stops much quicker. */
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*This sets the maximum current that the control hub will apply to the arm before throwing a flag */
        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);


        /* Before starting the armMotor. We'll make sure the TargetPosition is set to 0.
        Then we'll set the RunMode to RUN_TO_POSITION. And we'll ask it to stop and reset encoder.
        If you do not have the encoder plugged into this motor, it will not run in this code. */
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /* Define and initialize servos.*/
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist = hardwareMap.get(Servo.class, "wrist");

        /* Make sure that the intake is off, and the wrist is folded in. */
        intake.setPower(INTAKE_OFF);

        /* Send telemetry message to signify robot waiting */
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        //Retrieve the IMU from the hardware map
        imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
    }

    @Override
    public void start() {
        wrist.setPosition(WRIST_FOLDED_OUT);
        telemetry.addLine("Starting");
    }

    /* Run until the driver presses stop */
    @Override
    public void loop() {

        telemetry.addLine("Looping");

        gamepad1Previous.copy(gamepad1Current);
        gamepad1Current.copy(gamepad1);

        gamepad2Previous.copy(gamepad2Current);
        gamepad2Current.copy(gamepad2);

        double y = 0;
        double x = 0;
        double rx = 0;

        double gamepadActiveValue = 0.1;

        boolean gamepad2LeftStickActive = gamepad2Current.left_stick_y > gamepadActiveValue || gamepad2Current.left_stick_y < -gamepadActiveValue || gamepad2Current.left_stick_x > gamepadActiveValue || gamepad2Current.left_stick_x < -gamepadActiveValue;
        boolean gamepad2RightStickActive = gamepad2Current.right_stick_x > 0.05 || gamepad2Current.right_stick_x < -gamepadActiveValue;

        //Drive Code
        if (gamepad2LeftStickActive || gamepad2RightStickActive) {
            y = -gamepad2Current.left_stick_y; // Remember, Y stick value is reversed
            x = gamepad2Current.left_stick_x;
            rx = gamepad2Current.right_stick_x;
        } else {
            y = -gamepad1Current.left_stick_y; // Remember, Y stick value is reversed
            x = gamepad1Current.left_stick_x;
            rx = gamepad1Current.right_stick_x;
        }

        final double rotationFudge = 0.2;

        if (gamepad1Current.dpad_right || gamepad2Current.dpad_right) {
            if (rx <= 1 - rotationFudge) {
                rx += rotationFudge;
            }
        }

        if (gamepad1Current.dpad_left || gamepad2Current.dpad_left) {
            if (rx >= -1 + rotationFudge) {
                rx -= rotationFudge;
            }
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


        if (gamepad1Current.start) {
            imu.resetYaw();
        }

//        if (gamepad2Current.start) {
//            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            armPosition = 0;
//        }

//        if (gamepad2Current.left_bumper) {
//            armPosition -= 3 * ARM_TICKS_PER_DEGREE;
//        }
//
//        if (gamepad2Current.right_bumper) {
//            armPosition += 3 * ARM_TICKS_PER_DEGREE;
//        }


        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower * DRIVE_SPEED);
        backLeftMotor.setPower(backLeftPower * DRIVE_SPEED);
        frontRightMotor.setPower(frontRightPower * DRIVE_SPEED);
        backRightMotor.setPower(backRightPower * DRIVE_SPEED);

        /* Here we create a "fudge factor" for the arm position.
        This allows you to adjust (or "fudge") the arm position slightly with the gamepad triggers.
        We want the left trigger to move the arm up, and right trigger to move the arm down.
        So we add the right trigger's variable to the inverse of the left trigger. If you pull
        both triggers an equal amount, they cancel and leave the arm at zero. But if one is larger
        than the other, it "wins out". This variable is then multiplied by our FUDGE_FACTOR.
        The FUDGE_FACTOR is the number of degrees that we can adjust the arm by with this function. */

        double increaseArmPositionFudgeFactor = 0;
        double decreaseArmPositionFudgeFactor = 0;

        if (gamepad1Current.y || gamepad1Current.dpad_down) {
            increaseArmPositionFudgeFactor = 1;
        }

        if (gamepad1Current.a || gamepad1Current.dpad_up) {
            decreaseArmPositionFudgeFactor = 1;
        }

        if (gamepad2Current.right_trigger > 0.1) {
            increaseArmPositionFudgeFactor = gamepad2Current.right_trigger;
        }

        if (gamepad2Current.left_trigger > 0.1) {
            decreaseArmPositionFudgeFactor = gamepad2Current.left_trigger;
        }

        armPositionFudgeFactor = FUDGE_FACTOR * (increaseArmPositionFudgeFactor + (-decreaseArmPositionFudgeFactor));



        /* Here we implement a set of if else loops to set our arm to different scoring positions.
        We check to see if a specific button is pressed, and then move the arm (and sometimes
        intake and wrist) to match. For example, if we click the right bumper we want the robot
        to start collecting. So it moves the armPosition to the ARM_COLLECT position,
        it folds out the wrist to make sure it is in the correct orientation to intake, and it
        turns the intake on to the COLLECT mode.*/

        //TODO: Fix Controls
        if (gamepad1Current.b && !gamepad1Previous.b){
            /* This is the correct height to score the SPECIMEN in the HIGH RUNG */
            armPosition = ARM_SCORE_SPECIMEN;
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        if ((gamepad1Current.right_bumper && !gamepad1Previous.right_bumper) || (gamepad2Current.right_bumper && !gamepad2Previous.right_bumper)){
            /* This is the intaking/collecting arm position */
            if (Math.abs(armPosition-ARM_CLEAR_BARRIER) <= 5)  {
                armPosition = ARM_COLLECT;
            } else {
                armPosition = ARM_CLEAR_BARRIER;
            }

            wrist.setPosition(WRIST_FOLDED_OUT);
            intake.setPower(INTAKE_COLLECT);
        }

        else if (gamepad1Current.right_trigger > 0.1 && gamepad1Previous.right_trigger <= 0.1) {
            if (Math.abs(intake.getPower() - INTAKE_COLLECT) < 0.1) {
                intake.setPower(INTAKE_OFF);
            } else {
                intake.setPower(INTAKE_COLLECT);
            }

        }

        else if (gamepad1Current.left_bumper || gamepad2Current.left_bumper){
            /* This is the correct height to score the sample in the LOW BASKET */

            armPosition = ARM_SCORE_SAMPLE_IN_LOW;
            wrist.setPosition(WRIST_FOLDED_OUT);
        }

        //TODO: Fix Arm Position

        else if (gamepad1Current.left_trigger > 0.1 && gamepad1Previous.left_trigger <= 0.1) {
            if (Math.abs(intake.getPower() - INTAKE_DEPOSIT) < 0.1) {
                intake.setPower(INTAKE_OFF);
            } else {
                intake.setPower(INTAKE_DEPOSIT);
            }

        }

        else if (gamepad1Current.back) {
                /* This turns off the intake, folds in the wrist, and moves the arm
                back to folded inside the robot. This is also the starting configuration */
            armPosition = ARM_COLLAPSED_INTO_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

//        else if (gamepad1Current.dpad_right){
//            /* This is the correct height to score SPECIMEN on the HIGH CHAMBER */
//            armPosition = ARM_SCORE_SPECIMEN;
//            wrist.setPosition(WRIST_FOLDED_IN);
//        }

        if (gamepad2Current.dpad_up){
            /* This sets the arm to vertical to hook onto the LOW RUNG for hanging */
            armPosition = ARM_ATTACH_HANGING_HOOK;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        if (gamepad2Current.b) {
            armPosition = ARM_ATTACH_HANGING_HOOK + (20 * ARM_TICKS_PER_DEGREE);
        }

        if (gamepad2Current.dpad_down){
            /* this moves the arm down to lift the robot up once it has been hooked */
            armPosition = ARM_WINCH_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }



        /* Here we set the target position of our arm to match the variable that was selected
        by the driver.
        We also set the target velocity (speed) the motor runs at, and use setMode to run it.*/
        armMotor.setTargetPosition((int) (armPosition  +armPositionFudgeFactor));

        ((DcMotorEx) armMotor).setVelocity(1750);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /* TECH TIP: Encoders, integers, and doubles
        Encoders report when the motor has moved a specified angle. They send out pulses which
        only occur at specific intervals (see our ARM_TICKS_PER_DEGREE). This means that the
        position our arm is currently at can be expressed as a whole number of encoder "ticks".
        The encoder will never report a partial number of ticks. So we can store the position in
        an integer (or int).
        A lot of the variables we use in FTC are doubles. These can capture fractions of whole
        numbers. Which is great when we want our arm to move to 122.5°, or we want to set our
        servo power to 0.5.

        setTargetPosition is expecting a number of encoder ticks to drive to. Since encoder
        ticks are always whole numbers, it expects an int. But we want to think about our
        arm position in degrees. And we'd like to be able to set it to fractions of a degree.
        So we make our arm positions Doubles. This allows us to precisely multiply together
        armPosition and our armPositionFudgeFactor. But once we're done multiplying these
        variables. We can decide which exact encoder tick we want our motor to go to. We do
        this by "typecasting" our double, into an int. This takes our fractional double and
        rounds it to the nearest whole number.
        */

        /* Check to see if our arm is over the current limit, and report via telemetry. */
        if (((DcMotorEx) armMotor).isOverCurrent()){
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }


        /* send telemetry to the driver of the arm's current position and target position */
        telemetry.addData("Heading: : ", botHeading);
        telemetry.addData("armTarget: ", armMotor.getTargetPosition());
        telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
        telemetry.addData("Wrist Position: ", wrist.getPosition());
        telemetry.update();
    }
}
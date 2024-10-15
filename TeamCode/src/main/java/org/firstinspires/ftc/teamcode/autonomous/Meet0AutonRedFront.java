package org.firstinspires.ftc.teamcode.autonomous;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.subsystems.Arm;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Intake;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Wrist;

@Config
@Autonomous(name="Meet 0 Auton", group="Autonomous")
@Disabled

public class Meet0AutonRedFront extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(36, -63.5, 90);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        TrajectoryActionBuilder main = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(24, -34), Math.toRadians(90))
                .waitSeconds(2)
                .strafeTo(new Vector2d(24, -36))
                .waitSeconds(2)
                .strafeTo(new Vector2d(22, -36));

        Action moveToSubmersible = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(24, -43), Math.toRadians(90))
                .build();




        Action reverseAndScoreInSubmersible = drive.actionBuilder(new Pose2d(24, -43, 90))
                .waitSeconds(2)
                .strafeTo(new Vector2d(24, -52))
                .waitSeconds(2)
                .build();


        Action moveLeftAfterEjection = drive.actionBuilder(new Pose2d(24, -52, 90))
                .strafeTo(new Vector2d(4, -56))
                .build();

        Action park = drive.actionBuilder(new Pose2d(4, -56, 90))
                //.setTangent(0)
                .splineToSplineHeading(new Pose2d(-10, -12, 0), Math.PI / 2)
                .build();

        SequentialAction auto = new SequentialAction(
                new ParallelAction(
                        moveToSubmersible,
                        arm.liftToSpecimen()
                ),
                new ParallelAction(
                        //arm.scoreSpecimen(),
                        reverseAndScoreInSubmersible
                ),
                new ParallelAction(
                        intake.deposit(),
                        moveLeftAfterEjection
                ),
                new ParallelAction(
                        arm.clearGround(),
                        park
                ),
                arm.liftToSpecimen()
        );

        Actions.runBlocking(arm.clearGround());

        Actions.runBlocking(wrist.foldIn());
        Actions.runBlocking(intake.collect());

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(auto);
    }

}

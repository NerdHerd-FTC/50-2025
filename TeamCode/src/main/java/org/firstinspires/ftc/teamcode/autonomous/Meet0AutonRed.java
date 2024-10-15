package org.firstinspires.ftc.teamcode.autonomous;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.QuinticSpline1d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Arm;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Intake;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Wrist;

@Config
@Autonomous(name="Meet 0 Auton Both", group="Autonomous")

public class Meet0AutonRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(12, -63.5, 90);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        TrajectoryActionBuilder main = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(0, -34), Math.toRadians(90))
                .waitSeconds(2)
                .strafeTo(new Vector2d(0, -36))
                .waitSeconds(2)
                .strafeTo(new Vector2d(-2, -36));

        Action moveToSubmersible = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(0, -41), Math.toRadians(90))
                .build();

        Action reverseAndScoreInSubmersible = drive.actionBuilder(new Pose2d(0, -41, 90))
                .waitSeconds(2)
                .strafeTo(new Vector2d(0, -52))
                .waitSeconds(2)
                .build();


        Action moveLeftAfterEjection = drive.actionBuilder(new Pose2d(0, -52, 90))
                .strafeTo(new Vector2d(-20, -56))
                .build();

        Action park = drive.actionBuilder(new Pose2d(-20, -56, 90))
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(-34, -12, 0), Math.PI / 2)
                .strafeTo(new Vector2d(-30, -12))
                .build();

        SequentialAction auto = new SequentialAction(
                new ParallelAction(
                        moveToSubmersible,
                        arm.liftToSpecimen()
                ),
                new ParallelAction(
                        reverseAndScoreInSubmersible,
                        arm.scoreSpecimen()
                ),
                new ParallelAction(
                        intake.deposit(),
                        moveLeftAfterEjection
                ),
                new ParallelAction(
                        arm.clearGround(),
                        park
                ),
                arm.touchBottomBar()
        );

        Actions.runBlocking(arm.clearGround());

        Actions.runBlocking(wrist.foldIn());
        Actions.runBlocking(intake.collect());

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(auto);
    }

}

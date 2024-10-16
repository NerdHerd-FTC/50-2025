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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Arm;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Intake;
import org.firstinspires.ftc.teamcode.autonomous.subsystems.Wrist;

@Config
@Autonomous(name="Red", group="Autonomous")

public class AutonRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(12, -63.5, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        Action approachSubmersible = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(9, -39))
                .build();
                //move arm to specimen hanging position

        Action moveForwardToScoreSpecimen = drive.actionBuilder(new Pose2d(9, -39, 90))
                .strafeTo(new Vector2d(9, -33))
                .build();
                //move arm down a little bit


        Action reverseAndScoreInSubmersible = drive.actionBuilder(new Pose2d(9, -33, 90))
                .strafeTo(new Vector2d(9, -43))
                .build();

        SequentialAction auto = new SequentialAction(
                new ParallelAction(
                        approachSubmersible,
                        arm.liftToSpecimen()
                ),
                new ParallelAction(
                        arm.scoreSpecimen(),
                        moveForwardToScoreSpecimen
                ),
                reverseAndScoreInSubmersible
        );

        Actions.runBlocking(arm.clearGround());

        Actions.runBlocking(wrist.foldIn());
        Actions.runBlocking(intake.collect());

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(auto);
    }

}

package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static Action submersibleScoring(double offset, TrajectoryActionBuilder previousTrajectory) {
        TrajectoryActionBuilder ApproachHPStation = previousTrajectory
                .strafeToLinearHeading(new Vector2d(28, -44), Math.toRadians(-45))
                .endTrajectory();

        TrajectoryActionBuilder IntakeFromHP = ApproachHPStation.fresh()
                .strafeTo(new Vector2d(34, -50))
                .endTrajectory();

        TrajectoryActionBuilder approachSubmersible = IntakeFromHP.fresh()
                .strafeToLinearHeading(new Vector2d(8.5 - offset, -46), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder moveToSubmersible = approachSubmersible.fresh()
                .strafeToLinearHeading(new Vector2d(8.5 - offset, -42), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder reverseAndScoreSpecimenSlight = moveToSubmersible.fresh()
                .strafeTo(new Vector2d((8.5 - offset) + 1, -48))
                .endTrajectory();

        TrajectoryActionBuilder reverseAndScoreSpecimenFull = reverseAndScoreSpecimenSlight.fresh()
                .strafeTo(new Vector2d((8.5 - offset) + 2, -50))
                .endTrajectory();

        Action auto = new SequentialAction(
                ApproachHPStation.build(),
                IntakeFromHP.build(),
                approachSubmersible.build(),
                moveToSubmersible.build(),
                reverseAndScoreSpecimenSlight.build(),
                reverseAndScoreSpecimenFull.build()
        );
        return auto;
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 16.5)
                .setDimensions(18, 17)
                .build();

        Pose2d startPose = new Pose2d(12, -63.5, Math.toRadians(90));

        TrajectoryActionBuilder moveToSubmersibleForPreload = myBot.getDrive().actionBuilder(startPose)
                .strafeTo(new Vector2d(8.5, -42))
                .endTrajectory();

        TrajectoryActionBuilder reverseAndScorePreloadSlight = moveToSubmersibleForPreload.fresh()
                .strafeTo(new Vector2d(9.5, -48))
                .endTrajectory();

        TrajectoryActionBuilder reverseAndScorePreloadFull = reverseAndScorePreloadSlight.fresh()
                .strafeTo(new Vector2d(10.5, -50))
                .endTrajectory();

        TrajectoryActionBuilder park = reverseAndScorePreloadFull.fresh()
                .strafeTo(new Vector2d(32, -36))

                .strafeToLinearHeading(new Vector2d(32, -12), Math.toRadians(270))
                .strafeTo(new Vector2d(45, -12))
                .strafeTo(new Vector2d(45, -57))
                .strafeTo(new Vector2d(50, -12))
                .strafeTo(new Vector2d(53, -12))
                .strafeTo(new Vector2d(53, -56))
                .strafeTo(new Vector2d(58, -12))
                .strafeTo(new Vector2d(61.5, -12))
                .strafeTo(new Vector2d(61.5, -56))

                .endTrajectory();


        Action auto = new SequentialAction(
                moveToSubmersibleForPreload.build(),
                reverseAndScorePreloadSlight.build(),
                reverseAndScorePreloadFull.build(),
                park.build(),
                submersibleScoring(2, park.fresh())
        );

        myBot.runAction(auto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
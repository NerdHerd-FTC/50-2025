package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingCopy {
//    public static Action submersibleScoring(double offset, TrajectoryActionBuilder previousTrajectory) {
//        TrajectoryActionBuilder ApproachHPStation = previousTrajectory
//                .strafeToLinearHeading(new Vector2d(28, -44), Math.toRadians(-45))
//                .endTrajectory();
//
//        TrajectoryActionBuilder IntakeFromHP = ApproachHPStation.fresh()
//                .strafeTo(new Vector2d(34, -50))
//                .endTrajectory();
//
//        TrajectoryActionBuilder approachSubmersible = IntakeFromHP.fresh()
//                .strafeToLinearHeading(new Vector2d(8.5 - offset, -46), Math.toRadians(90))
//                .endTrajectory();
//
//        TrajectoryActionBuilder moveToSubmersible = approachSubmersible.fresh()
//                .strafeToLinearHeading(new Vector2d(8.5 - offset, -42), Math.toRadians(90))
//                .endTrajectory();
//
//        TrajectoryActionBuilder reverseAndScoreSpecimenSlight = moveToSubmersible.fresh()
//                .strafeTo(new Vector2d((8.5 - offset) + 1, -48))
//                .endTrajectory();
//
//        TrajectoryActionBuilder reverseAndScoreSpecimenFull = reverseAndScoreSpecimenSlight.fresh()
//                .strafeTo(new Vector2d((8.5 - offset) + 2, -50))
//                .endTrajectory();
//
//        Action auto = new SequentialAction(
//                ApproachHPStation.build(),
//                IntakeFromHP.build(),
//                approachSubmersible.build(),
//                moveToSubmersible.build(),
//                reverseAndScoreSpecimenSlight.build(),
//                reverseAndScoreSpecimenFull.build()
//        );
//        return auto;
//    }
    public static Action noHome(RoadRunnerBotEntity bot) {
        Pose2d startPose = new Pose2d(12, -63.5, Math.toRadians(90));

        TrajectoryActionBuilder moveToSubmersibleForPreload = bot.getDrive().actionBuilder(startPose)
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

        SequentialAction auto1 = new SequentialAction(
                new ParallelAction(
                        moveToSubmersibleForPreload.build()
                ),
                reverseAndScorePreloadSlight.build(),
                new ParallelAction(
                        reverseAndScorePreloadFull.build()
                ),
                new ParallelAction(
                        park.build()
                )
        );

        double offset = 2;

        TrajectoryActionBuilder ApproachHPStation = bot.getDrive().actionBuilder(new Pose2d(61.5, -56, Math.toRadians(270)))
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
                .strafeToLinearHeading(new Vector2d(61, -55), Math.toRadians(90))
                .endTrajectory();

        Action auto2 = new SequentialAction(
                ApproachHPStation.build(),
                IntakeFromHP.build(),
                approachSubmersible.build(),
                new SequentialAction(
                        moveToSubmersible.build(),
                        reverseAndScoreSpecimenSlight.build()
                ),
                reverseAndScoreSpecimenFull.build()
        );

        Action auto = new SequentialAction(
                auto1,
                auto2
        );

        return auto;
    }

    public static Action home(RoadRunnerBotEntity bot) {
        Pose2d startPose = new Pose2d(12, -63.5, Math.toRadians(90));

        TrajectoryActionBuilder moveToSubmersibleForPreload = bot.getDrive().actionBuilder(startPose)
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

        SequentialAction auto1 = new SequentialAction(
                new ParallelAction(
                        moveToSubmersibleForPreload.build()
                ),
                reverseAndScorePreloadSlight.build(),
                new ParallelAction(
                        reverseAndScorePreloadFull.build()
                ),
                new ParallelAction(
                        park.build()
                )
        );

        double offset = 2;

        TrajectoryActionBuilder home = bot.getDrive().actionBuilder(new Pose2d(61.5, -56, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(12, -58), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder ApproachHPStation = home.fresh()
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
                .strafeToLinearHeading(new Vector2d(61, -55), Math.toRadians(90))
                .endTrajectory();

        Action auto2 = new SequentialAction(
                home.build(),
                ApproachHPStation.build(),
                IntakeFromHP.build(),
                approachSubmersible.build(),
                new SequentialAction(
                        moveToSubmersible.build(),
                        reverseAndScoreSpecimenSlight.build()
                ),
                reverseAndScoreSpecimenFull.build()
        );

        Action auto = new SequentialAction(
                auto1,
                auto2
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

        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 16.5)
                .setDimensions(18, 17)
                .build();



        myBot.runAction(noHome(myBot));
        myBot2.runAction(home(myBot2));

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myBot2)
                .start();
    }
}
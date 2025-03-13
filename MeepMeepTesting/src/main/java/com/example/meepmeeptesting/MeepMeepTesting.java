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

public class MeepMeepTesting {
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



        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(15.125, -63, Math.toRadians(90)))
//                        .splineToLinearHeading(new Pose2d(5.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -43, Math.toRadians(90)), Math.toRadians(90))
////                        .strafeTo(new Vector2d(5.0, -43.0))
//                        .splineToLinearHeading(new Pose2d(49, -42.5, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(59.3, -42.5, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(56.17, -27.87, Math.toRadians(0)), Math.toRadians(0))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -40.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -40.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -40.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.8, -58.5, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.8, -48.8, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(5.0, -40.0, Math.toRadians(90)), Math.toRadians(90))
////                        .strafeTo(new Vector2d(5, -35))
//                //first sample
//                .strafeTo(new Vector2d(49, -42.5))
//                //hp
//                .strafeTo(new Vector2d(48.8, -58.5))
//                //hp reverse
//                .strafeTo(new Vector2d(48.8, -48.8))
//
//                //2nd sample
//                .strafeTo(new Vector2d(59.3, -42.5))
//                //hp
//                .strafeTo(new Vector2d(48.8, -58.5))
//                //hp reverse
//                .strafeTo(new Vector2d(48.8, -48.8))
//                // 3rd sample
//                .strafeTo(new Vector2d(56.17, -27.87))

//                        .splineToLinearHeading(new Pose2d(3.0, -35.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(3.0, -40.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.5, -39.7, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(48.5, -60, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48.5, -40, Math.toRadians(90)), Math.toRadians(90))
//
//                // sweep
//
//                        .splineToLinearHeading(new Pose2d(52.8, -10, Math.toRadians(90)), Math.toRadians(0))
//
//                //sweep in
//
//                        .splineToLinearHeading(new Pose2d(54.5, -52.5, Math.toRadians(90)), Math.toRadians(90))
//
//                //sweep  2
//
//                        .splineToLinearHeading(new Pose2d(60, -10, Math.toRadians(90)), Math.toRadians(0))
//
//                        //sweep in 2
//
//                        .splineToLinearHeading(new Pose2d(48.5, -60, Math.toRadians(90)), Math.toRadians(90))
////
//                        .splineToLinearHeading(new Pose2d(62, -52.5, Math.toRadians(90)), Math.toRadians(90))

                            //score preload
                        .splineToLinearHeading(new Pose2d(35, -56.0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(35, -10, Math.toRadians(90)), Math.toRadians(90))

                        .splineToLinearHeading(new Pose2d(42, -10, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(42, -56, Math.toRadians(90)), Math.toRadians(90))

                        .splineToLinearHeading(new Pose2d(42, -10, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(50, -10, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(50, -56, Math.toRadians(90)), Math.toRadians(90))

                        .splineToLinearHeading(new Pose2d(50, -10, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(58, -10, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(58, -56, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(40, -57, Math.toRadians(90)), Math.toRadians(270))
//
//                        .splineToLinearHeading(new Pose2d(40, -20, Math.toRadians(90)), Math.toRadians(270))
//                        .splineToLinearHeading(new Pose2d(48, -20, Math.toRadians(90)), Math.toRadians(270))
//
//                        .splineToLinearHeading(new Pose2d(47, -57, Math.toRadians(90)), Math.toRadians(270))
//                        .splineToLinearHeading(new Pose2d(47, -20, Math.toRadians(90)), Math.toRadians(270))


                //spec
//                        .splineToLinearHeading(new Pose2d(48, -47, Math.toRadians(90)), Math.toRadians(270))
//                        .splineToLinearHeading(new Pose2d(3.0, -33.0, Math.toRadians(90)), Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(3.0, -52.0, Math.toRadians(90)), Math.toRadians(90))
//
//                        .splineToLinearHeading(new Pose2d(48, -50, Math.toRadians(90)), Math.toRadians(0))
//                        .waitSeconds(0.5)
//                        .splineToLinearHeading(new Pose2d(48, -53, Math.toRadians(90)), Math.toRadians(0))
//                        .waitSeconds(1)
//                        .splineToLinearHeading(new Pose2d(48, -47, Math.toRadians(90)), Math.toRadians(0))

//                        // 1st sample pickup
//                        .splineToLinearHeading(new Pose2d(48.5, -42.0, Math.toRadians(90)), Math.toRadians(90))
////
//                //bring first sample back
//                        .splineToLinearHeading(new Pose2d(48.5, -60, Math.toRadians(90)), Math.toRadians(90))
////
////                        .splineToLinearHeading(new Pose2d(48.5, -40, Math.toRadians(90)), Math.toRadians(90))
////                        .splineToLinearHeading(new Pose2d(46, -40, Math.toRadians(90)), Math.toRadians(90))
////                        .waitSeconds(1)
//                        // go to 2nd sample sweep
//
//                        .splineToLinearHeading(new Pose2d(52.5, -10, Math.toRadians(90)), Math.toRadians(0))
//                //sweep
//                        .strafeTo(new Vector2d(52.5, -52.5))
//
//                        .splineToLinearHeading(new Pose2d(56, -47, Math.toRadians(90)), Math.toRadians(0))
////
////
////                        .splineToLinearHeading(new Pose2d(50.5, -32.5, Math.toRadians(90)), Math.toRadians(90))
//                        //sweep prep 3
//                        .splineToLinearHeading(new Pose2d(54.5, -10, Math.toRadians(90)), Math.toRadians(0))
//                        //sweep 3
//                        .strafeTo(new Vector2d(54.5, -52.5))



                        .build()
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myBot2)
                .start();
    }
}
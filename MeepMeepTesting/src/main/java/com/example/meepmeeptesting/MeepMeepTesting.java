package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -63.5, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(28, -44), Math.toRadians(-45))
                .strafeTo(new Vector2d(32, -48))
                .strafeToLinearHeading(new Vector2d(8.5, -42), Math.toRadians(90))
                .strafeTo(new Vector2d(9.5, -48))
                .strafeTo(new Vector2d(10.5, -50))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(myBot)
                .start();
    }
}
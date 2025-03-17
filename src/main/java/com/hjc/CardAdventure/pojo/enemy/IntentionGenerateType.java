package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.effects.Effect;

import java.util.ArrayList;
import java.util.Random;

public enum IntentionGenerateType {
    //正常随机触发
    NORMAL,
    //效果触发
    EFFECT,
    //回合触发（某回合必定触发）
    ROUND;

    //生成一个意图
    public static void generateIntention(Enemy enemy) {
        //可用意图
        ArrayList<Intention> usableIntention = new ArrayList<>();

        //判断有无回合触发
        for (Intention intention : enemy.getIntentions()) {
            //回合必须触发的意图
            if (intention.getIntentionGenerateType() == ROUND) {
                int round = roundIntention(intention);
                if (BattleInformation.rounds == round) {
                    enemy.setNowIntention(intention);
                    return;
                }
            }
            //已有意图，无需生成
            if (enemy.getNowIntention() != null) continue;
            //需要效果触发的意图
            if (intention.getIntentionGenerateType() == EFFECT) continue;
            //已连续执行2次的意图
            if (intention.getConsecutiveStarts() == 2) {
                Intention.interrupt(intention);
                continue;
            }
            //满足条件的意图
            usableIntention.add(intention);
        }

        //从可用的意图中随机生成一个意图
        Intention nowIntention = usableIntention.get(new Random().nextInt(usableIntention.size()));
        //可用意图移除该意图
        usableIntention.remove(nowIntention);
        //打断其他意图的连续执行次数
        for (Intention intention : usableIntention) {
            Intention.interrupt(intention);
        }
        //将随机生成的意图设为当前意图
        enemy.setNowIntention(nowIntention);
    }

    //解析回合触发意图,每几个回合触发一次,"1"只代表第一回合触发
    public static int roundIntention(Intention intention) {
        int effect = intention.getEffects()[0];
        int value = effect / IntentionType.TYPE_DIVISION;
        int interval = value % 10;
        int rounds = value / 10;
        //如果为1，仅生成一次
        if (interval == 1) return rounds;
        //不为1，多次间隔生成
        while (rounds < BattleInformation.rounds) {
            rounds += interval;
        }
        return rounds;
    }
}

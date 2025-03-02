package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.pojo.effects.*;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;

public enum IntentionType {
    //攻击类意图
    ATTACK,
    //防御类意图
    DEFENSE,
    //强化类意图
    STRENGTHEN,
    //削弱类意图
    WEAKEN,
    //准备类意图
    PREPARE,
    //其他类意图
    OTHER;

    public static final int TYPE_DIVISION = 1000;

    //意图效果数组解析器
    public static ArrayList<Effect> intentionEffects(Enemy enemy, int[] effects) {
        ArrayList<Effect> enemyEffects = new ArrayList<>();
        for (int effect : effects) {
            Effect enemyEffect = intentionEffect(enemy, effect);
            if (enemyEffect != null) enemyEffects.add(enemyEffect);
        }
        return enemyEffects;
    }

    //意图效果解析器
    public static Effect intentionEffect(Enemy enemy, int effect) {
        int num = effect % TYPE_DIVISION;
        int value = effect / TYPE_DIVISION;
        return switch (num) {
            //1.造成一次物理伤害x001（x为伤害数值）
            case 1 -> new PhysicalDamage(enemy, PlayerInformation.player, value);
            //98.意图生成效果
            case 98 -> new IntentionGenerate(enemy, enemy, value);
            //99.意图移除效果
            case 99 -> new IntentionClear(enemy, enemy, value);
            //100.意图回合触发效果，仅标志作用，无意义
            case 100 -> null;
            default -> null;
        };
    }
}

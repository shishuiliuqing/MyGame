package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.pojo.effects.*;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.AttributeUtil;

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
    //攻击防御类
    ATTACK_DEFENSE,
    //攻击附加效果类
    ATTACK_EFFECT,
    //防御强化类
    DEFENSE_STRENGTHEN,
    //防御削弱类
    DEFENSE_WEAK,
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
            //10.回复效果x010（x为回复血量）
            case 10 -> new RestoreBlood(enemy, enemy, value);
            //20.护甲添加x020（x为护甲添加量）
            case 20 -> new ArmorAdd(enemy, enemy, value);
            //98.意图生成效果
            case 98 -> new IntentionGenerate(enemy, enemy, value);
            //99.意图移除效果
            case 99 -> new IntentionClear(enemy, enemy, value);
            //100.意图回合触发效果，仅标志作用，无意义
            case 100 -> null;
            default -> null;
        };
    }

    //伤害意图伤害解析器
    public static int[] getAttackValue(Enemy enemy, int[] effects) {
        int[] value = new int[2];
        for (int effect : effects) {
            if (effect % TYPE_DIVISION == 1) {
                value[0] = 0;
                value[1] = AttributeUtil.mathPhysicalDamage(new PhysicalDamage(enemy, enemy, effect / TYPE_DIVISION));
            }
        }
        return value;
    }
}

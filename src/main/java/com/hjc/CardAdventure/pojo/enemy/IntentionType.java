package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.effects.*;
import com.hjc.CardAdventure.pojo.effects.complexEffect.AllTargetEffect;
import com.hjc.CardAdventure.pojo.effects.complexEffect.OpportunityEffect;
import com.hjc.CardAdventure.pojo.effects.complexEffect.VulnerabilityEffect;
import com.hjc.CardAdventure.pojo.effects.complexEffect.WeakenEffect;
import com.hjc.CardAdventure.pojo.effects.other.weakenIntentionEffect;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.AttributeUtil;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

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
    //攻击弱化类
    ATTACK_WEAKEN,
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
            //2.多段伤害xy002（x为伤害数值，y为段数）
            case 2 -> new PhysicalDamageMany(enemy, player, value);
            //10.回复效果x010（x为回复血量）
            case 10 -> new RestoreBlood(enemy, enemy, value);
            //20.护甲添加x020（x为护甲添加量）
            case 20 -> new ArmorAdd(enemy, enemy, value);
            //50.力量增强
            case 50 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.POWER_UP);
            //51.智力增强
            case 51 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.INTELLIGENCE_UP);
            //52.防御增强
            case 52 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.DEFENSE_UP);
            //53.敏捷增强
            case 53 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.AGILITY_UP);
            //54.纯洁增强
            case 54 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.PURITY_UP);
            //55.速度增强
            case 55 -> new AttributeUpEffect(enemy, enemy, value, AttributeUp.SPEED_UP);
            //56.力量下降
            case 56 -> new AttributeDownEffect(enemy, player, value, AttributeDown.POWER_DOWN);
            //57.智力下降
            case 57 -> new AttributeDownEffect(enemy, player, value, AttributeDown.INTELLIGENCE_DOWN);
            //58.防御下降
            case 58 -> new AttributeDownEffect(enemy, player, value, AttributeDown.DEFENSE_DOWN);
            //59.敏捷下降
            case 59 -> new AttributeDownEffect(enemy, player, value, AttributeDown.AGILITY_DOWN);
            //60.纯洁下降
            case 60 -> new AttributeDownEffect(enemy, player, value, AttributeDown.PURITY_DOWN);
            //61.速度下降
            case 61 -> new AttributeDownEffect(enemy, player, value, AttributeDown.SPEED_DOWN);
            //98.意图生成效果
            case 98 -> new IntentionGenerate(enemy, enemy, value);
            //99.意图移除效果
            case 99 -> new IntentionClear(enemy, enemy, value);
            //100.意图回合触发效果，仅标志作用，无意义
            case 100 -> null;
            //101.群体效果x101（x为执行代码）
            case 101 -> new AllTargetEffect(enemy, enemy, value);
            //103.虚弱效果
            case 103 -> new WeakenEffect(enemy, player, value);
            //106.易伤效果x106（x为易伤效果持续次数）
            case 106 -> new VulnerabilityEffect(enemy, player, value);
            //300.受到伤害时（自身），x300，永久效果，不可叠加
            case 300 -> new OpportunityEffect(enemy, enemy, value, OpportunityType.HURT_TIME, false, 1);
            //800.弱化意图特效
            case 800 -> new weakenIntentionEffect(enemy, enemy, value);
            //998.暂停效果x998（x为暂停时间）
            case 998 -> new PauseEffect(enemy, player, value, value * 1.0 / 10);
            default -> null;
        };
    }


    //意图文本解析
    public static String intentionParse(Enemy enemy) {
        if (enemy.getNowIntention() == null) return "";
        if (enemy.getNowIntention().getIntentionType() == ATTACK) {
            return "这个敌人将进行攻击，对你造成物理伤害";
        } else if (enemy.getNowIntention().getIntentionType() == DEFENSE) {
            return "这个敌人将进行防御，获得护甲";
        } else if (enemy.getNowIntention().getIntentionType() == STRENGTHEN) {
            return "这个敌人将进行一项或多项强化";
        } else if (enemy.getNowIntention().getIntentionType() == WEAKEN) {
            return "这个敌人将给你添加负面状态";
        } else if (enemy.getNowIntention().getIntentionType() == PREPARE) {
            return "这个敌人正在进行下一意图的准备，此意图下敌人无法行动";
        } else if (enemy.getNowIntention().getIntentionType() == ATTACK_DEFENSE) {
            return "这个敌人将进行攻击，同时获得护甲";
        } else if (enemy.getNowIntention().getIntentionType() == ATTACK_WEAKEN) {
            return "这个敌人将进行攻击，同时给你添加负面状态";
        } else if (enemy.getNowIntention().getIntentionType() == DEFENSE_STRENGTHEN) {
            return "这个敌人将进行防御，同时强化自己";
        } else if (enemy.getNowIntention().getIntentionType() == DEFENSE_WEAK) {
            return "这个敌人将进行防御，同时给你添加负面效果";
        } else return "未知意图";
    }

    //伤害意图伤害解析器
    public static int[] getAttackValue(Enemy enemy, int[] effects) {
        int[] value = new int[2];
        for (int effect : effects) {
            if (effect % TYPE_DIVISION == 1) {
                value[0] = 0;
                value[1] = AttributeUtil.mathPhysicalDamage(new PhysicalDamage(enemy, player, effect / TYPE_DIVISION));
            } else if (effect % TYPE_DIVISION == 2) {
                value[0] = (effect / TYPE_DIVISION) % 10;
                value[1] = AttributeUtil.mathPhysicalDamage(new PhysicalDamage(enemy, player, (effect / TYPE_DIVISION) / 10 * 100 + 1));
            }
        }
        return value;
    }
}

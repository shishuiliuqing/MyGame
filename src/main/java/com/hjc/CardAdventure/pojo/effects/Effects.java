package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.complexEffect.*;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


//效果解析：后3位效果编号，其他数值
//卡牌效果集合，卡牌效果构造器
public enum Effects {
    //1.造成物理伤害x001（x为伤害数值）
    PHYSICAL_DAMAGE,
    //2.群体物理伤害x002（x为伤害数值）
    PHYSICAL_DAMAGE_ALL,
    //9.死亡效果
    DEATH_EFFECT,
    //10.抽牌效果x010（x为抽牌数，抽不够洗牌）
    DRAW,
    //11.抽牌效果x011（x为抽牌数，抽不够不洗牌）
    DRAW_NO_SHUFFLE,
    //12.洗牌效果x012（x为补回抽牌数，不洗牌抽牌）
    SHUFFLE,
    //13.寻卡效果xy013（x为要寻卡的编号,y--1位，要找的牌堆，1为抽牌堆）
    FIND_CARD,
    //20.血量回复
    RESTORE_BLOOD,
    //21.血量失去效果
    LOST_BLOOD,
    //40.护盾添加
    ARMOR_ADD,
    //50.力量提升x050（x为提升的数值）
    POWER_UP,
    //51.智力提升x051（x为提升的数值）
    INTELLIGENCE_UP,
    //52.防御提升x052（x为提升的数值）
    DEFENSE_UP,
    //53.敏捷提升x053（x为提升的数值）
    AGILITY_UP,
    //54.纯洁提升x054（x为提升的数值）
    PURITY_UP,
    //55.速度提升x055（x为提升的数值）
    SPEED_UP,
    //56.力量下降x056（x为下降的数值）
    POWER_DOWN,
    //57.智力下降x057（x为下降的数值）
    INTELLIGENCE_DOWN,
    //58.防御下降x058（x为下降的数值）
    DEFENSE_DOWN,
    //59.敏捷下降x059（x为下降的数值）
    AGILITY_DOWN,
    //60.纯洁下降x060（x为下降的数值）
    PURITY_DOWN,
    //61.速度下降x061（x为下降的数值）
    SPEED_DOWN,
    //62.力量翻倍x062
    DOUBLE_POWER,
    //63.智力翻倍x063
    DOUBLE_INTELLIGENCE,
    //64.防御翻倍x064
    DOUBLE_DEFENSE,
    //65.敏捷翻倍x065
    DOUBLE_AGILITY,
    //66.纯洁翻倍x066
    DOUBLE_PURITY,
    //67.速度翻倍x067
    DOUBLE_SPEED,
    //68.失去所有力量x068
    LOST_POWER,
    //69.失去所有智力x069
    LOST_INTELLIGENCE,
    //70.失去所有防御x070
    LOST_DEFENSE,
    //71.失去所有敏捷x071
    LOST_AGILITY,
    //72.失去所有纯洁x072
    LOST_PURITY,
    //73.失去所有速度x073
    LOST_SPEED,
    //74.全属性上升x074（x为全属性上升的数值）
    ALL_ATTRIBUTE_UP,
    //75.全属性下降x075（x为全属性下降的数值）
    ALL_ATTRIBUTE_DOWN,
    //80.弃牌效果--弃掉当前行动的牌x080（x无效果）
    ABANDON_ACTION,
    //81.回抽效果--将当前行动的牌放回抽牌堆x081（x无效果）
    GO_Draw_CARDS,
    //82.消耗--将当前行动的牌放到消耗牌堆x082（x无效果）
    CONSUME_CARD,
    //83.出牌数减少效果x083（x为减少次数）
    REDUCE_PRODUCE,
    //84.刷新本回合出牌数x084（x无意义）
    SHUFFLE_PRODUCE,
    //85.不消耗出牌次数效果x085（x无意义）
    NO_REDUCE_PRODUCE,
    //98.结束角色当前回合
    ACTION_OVER,
    //99.角色额外行动x099（x代表获得行动的次数）
    ROLE_ACTION,
    //101.伤害翻倍（有限次）x101（x为次数）
    DOUBLE_DAMAGE,
    //102.伤害翻倍效果结束
    DOUBLE_END,
    //103.虚弱效果x103（x为次数）
    WEAKEN,
    //104.虚弱效果结束
    WEAKEN_END,
    //200.满足条件触发效果x200（x为执行的效果代码），该条件为目标无护甲
    CONDITION_EFFECT,
    //300.护盾完全防御时机创造，永久时机效果，不可叠加，仅一层
    ARMOR_DEFENSE_NO,
    //303.自身回合开始时机，永久时机效果，可叠加，仅一层
    OWN_ROUND_BEGIN_YES,
    ;


    public static final int TYPE_DIVISION = 1000;

    //效果数组解析
    public static ArrayList<Effect> getEffects(int[] cardEffects, Role from, Role to) {
        if (cardEffects == null) return new ArrayList<>();
        ArrayList<Effect> effects = new ArrayList<>();

        for (int cardEffect : cardEffects) {
            effects.add(getEffect(cardEffect, from, to));
        }
        return effects;
    }

    //效果解析
    public static Effect getEffect(int cardEffect, Role from, Role to) {
        int effectNumber = cardEffect % TYPE_DIVISION;
        int value = cardEffect / TYPE_DIVISION;

        return switch (effectNumber) {
            //1.造成物理伤害
            case 1 -> new PhysicalDamage(from, to, value);
            //2.群体物理伤害
            case 2 -> new PhysicalDamageAll(from, to, value);
            //9.死亡效果
            case 9 -> new DeathEffect(from, to, value);
            //10.抽牌效果，抽不够洗牌
            case 10 -> new DrawEffect(from, player, value);
            //11.抽牌效果，抽不够不洗牌
            case 11 -> new DrawNoShuffle(from, player, value);
            //12.洗牌效果，将弃牌堆的牌放回抽牌堆并打乱
            case 12 -> new ShuffleEffect(from, player, value);
            //13.寻卡
            case 13 -> new FindCard(from, player, value);
            //20.血量回复效果
            case 20 -> new RestoreBlood(from, player, value);
            //21.血量失去效果
            case 21 -> new LostBlood(from, player, value);
            //40.护盾添加
            case 40 -> new ArmorAdd(from, player, value);
            //50.力量增强
            case 50 -> new AttributeUpEffect(from, to, value, AttributeUp.POWER_UP);
            //51.智力增强
            case 51 -> new AttributeUpEffect(from, to, value, AttributeUp.INTELLIGENCE_UP);
            //52.防御增强
            case 52 -> new AttributeUpEffect(from, to, value, AttributeUp.DEFENSE_UP);
            //53.敏捷增强
            case 53 -> new AttributeUpEffect(from, to, value, AttributeUp.AGILITY_UP);
            //54.纯洁增强
            case 54 -> new AttributeUpEffect(from, to, value, AttributeUp.PURITY_UP);
            //55.速度增强
            case 55 -> new AttributeUpEffect(from, to, value, AttributeUp.SPEED_UP);
            //56.力量下降
            case 56 -> new AttributeDownEffect(from, to, value, AttributeDown.POWER_DOWN);
            //57.智力下降
            case 57 -> new AttributeDownEffect(from, to, value, AttributeDown.INTELLIGENCE_DOWN);
            //58.防御下降
            case 58 -> new AttributeDownEffect(from, to, value, AttributeDown.DEFENSE_DOWN);
            //59.敏捷下降
            case 59 -> new AttributeDownEffect(from, to, value, AttributeDown.AGILITY_DOWN);
            //60.纯洁下降
            case 60 -> new AttributeDownEffect(from, to, value, AttributeDown.PURITY_DOWN);
            //61.速度下降
            case 61 -> new AttributeDownEffect(from, to, value, AttributeDown.SPEED_DOWN);
            //62.力量翻倍
            case 62 -> new DoubleAttribute(from, to, value, AttributeUp.POWER_UP);
            //63.智力翻倍
            case 63 -> new DoubleAttribute(from, to, value, AttributeUp.INTELLIGENCE_UP);
            //64.防御翻倍
            case 64 -> new DoubleAttribute(from, to, value, AttributeUp.DEFENSE_UP);
            //65.敏捷翻倍
            case 65 -> new DoubleAttribute(from, to, value, AttributeUp.AGILITY_UP);
            //66.纯洁翻倍
            case 66 -> new DoubleAttribute(from, to, value, AttributeUp.PURITY_UP);
            //67.速度翻倍
            case 67 -> new DoubleAttribute(from, to, value, AttributeUp.SPEED_UP);
            //68.失去所有力量
            case 68 -> new LostAttribute(from, to, value, AttributeDown.POWER_DOWN);
            //69.失去所有智力
            case 69 -> new LostAttribute(from, to, value, AttributeDown.INTELLIGENCE_DOWN);
            //70.失去所有防御
            case 70 -> new LostAttribute(from, to, value, AttributeDown.DEFENSE_DOWN);
            //71.失去所有敏捷
            case 71 -> new LostAttribute(from, to, value, AttributeDown.AGILITY_DOWN);
            //72.失去所有纯洁
            case 72 -> new LostAttribute(from, to, value, AttributeDown.PURITY_DOWN);
            //73.失去所有速度
            case 73 -> new LostAttribute(from, to, value, AttributeDown.SPEED_DOWN);
            //74.全属性上升
            case 74 -> new AllAttributeUp(from, to, value);
            //75.全属性下降
            case 75 -> new AllAttributeDown(from, to, value);
            //80.弃牌效果--弃掉当前行动的牌
            case 80 -> new AbandonAction(from, player, value);
            //81.回抽效果--将当前行动的牌放回抽牌堆
            case 81 -> new GoDrawCards(from, player, value);
            //82.消耗效果--将当前行动的牌放到消耗牌堆
            case 82 -> new ConsumeEffect(from, player, value);
            //83.出牌数减少效果x083（x为减少的出牌数）
            case 83 -> new ReduceProduce(from, player, value);
            //84.刷新本回合出牌数
            case 84 -> new ShuffleProduce(from, player, value);
            //85.不占用出牌数效果
            case 85 -> new NoProduce(from, player, value);
            //98.结束当前回合
            case 98 -> new ActionOver(from, to, value);
            //99.玩家额外行动
            case 99 -> new RoleAction(from, to, value);
            //101.有限次伤害翻倍
            case 101 -> new DoubleDamage(from, to, value);
            //102.伤害翻倍效果结束
            case 102 -> new DoubleDamageEnd(from, to, value);
            //103.虚弱效果
            case 103 -> new WeakenEffect(from, to, value);
            //104.虚弱效果结束
            case 104 -> new WeakenEnd(from, to, value);
            //105.群体虚弱效果
            case 105 -> new AllWeaken(from, to, value);
            //200.条件满足效果
            case 200 -> new ConditionEffect(from, to, value, 1, getEffect(value, from, to));
            //300.创造护盾完全防御时机，永久时机效果，不可叠加，仅一层
            case 300 -> new OpportunityEffect(from, to, value, OpportunityType.ARMOR_DEFENSE, false, 1);
            //303.自身回合开始时机，永久时机效果，可叠加，仅一层
            case 303 -> new OpportunityEffect(from, to, value, OpportunityType.OWN_ROUND_BEGIN, true, 1);
            //305.失去血量时，永久时机效果，可叠加，仅一层
            case 305 -> new OpportunityEffect(from, to, value, OpportunityType.LOST_BLOOD, true, 1);
            //999.暂停时机
            case 999 -> new PauseEffect(null, null, 0, 999);
            default -> null;
        };

    }

    //卡牌效果数组序列转字符串
    public static String CardEffectsToString(Card card) {
        StringBuilder sb = new StringBuilder();
        int[] cardEffects = card.getCardEffects();
        ArrayList<Effect> effects = getEffects(cardEffects, player, player);
        for (Effect effect : effects) {
            sb.append(effect.toString());
            if (effect != effects.get(effects.size() - 1) && !effect.toString().isEmpty()) sb.append("；");
        }
        return sb.toString();
    }

    //效果数组转详细数据
    public static String effectsDetail(int[] effects) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Effect> effectList = getEffects(effects, player, player);
        for (Effect effect : effectList) {
            sb.append(effect.describeInDetail());
        }
        return sb.toString();
    }
}

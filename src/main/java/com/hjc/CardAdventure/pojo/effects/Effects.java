package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.complexEffect.*;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


//效果解析：后3位效果编号，其他数值
//卡牌效果集合，卡牌效果构造器
public enum Effects {
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
        //if (RecordUtil.isOpenPhy) value += RecordUtil.getInteger();
        //System.out.println(RecordUtil.isOpenPhy);

        return switch (effectNumber) {
            //1.造成物理伤害x001
            case 1 -> new PhysicalDamage(from, to, value);
            //2.群体物理伤害x002
            case 2 -> new PhysicalDamageAll(from, to, value);
            //5.受到火焰伤害效果x005
            case 5 -> new FireDamage(from, to, value);
            //9.死亡效果x009
            case 9 -> new DeathEffect(from, to, value);
            //10.抽牌效果，抽不够洗牌x010
            case 10 -> new DrawEffect(from, player, value);
            //11.抽牌效果，抽不够不洗牌x011
            case 11 -> new DrawNoShuffle(from, player, value);
            //12.洗牌效果，将弃牌堆的牌放回抽牌堆并打乱x012
            case 12 -> new ShuffleEffect(from, player, value);
            //13.寻卡xy013（x为所找牌编号，y为寻找牌堆，1为抽牌堆）
            case 13 -> new FindCard(from, player, value);
            //20.血量回复效果x020,自身
            case 20 -> new RestoreBlood(from, from, value);
            //21.血量失去效果x021,自身
            case 21 -> new LostBlood(from, from, value);
            //22.血量设置效果x022
            case 22 -> new SetBloodEffect(from, to, value);
            //30.消耗牌堆顶的牌x030
            case 30 -> new ConsumeDrawTop(from, player, value);
            //40.护盾添加x040,自身
            case 40 -> new ArmorAdd(from, from, value);
            //41.设置目标护甲值x041
            case 41 -> new SetArmor(from, to, value);
            //42.使玩家失去护盾值x042，自身
            case 42 -> new SetArmor(from, from, 0);
            //43.使目标自身回合开始不会失去护盾x043
            case 43 -> new NoLostArmor(from, to, value);
            //44.使目标护盾翻倍x044
            case 44 -> new DoubleArmor(from, to, value);
            //50.力量增强x050
            case 50 -> new AttributeUpEffect(from, to, value, AttributeUp.POWER_UP);
            //51.智力增强x051
            case 51 -> new AttributeUpEffect(from, to, value, AttributeUp.INTELLIGENCE_UP);
            //52.防御增强x052
            case 52 -> new AttributeUpEffect(from, to, value, AttributeUp.DEFENSE_UP);
            //53.敏捷增强x053
            case 53 -> new AttributeUpEffect(from, to, value, AttributeUp.AGILITY_UP);
            //54.纯洁增强x054
            case 54 -> new AttributeUpEffect(from, to, value, AttributeUp.PURITY_UP);
            //55.速度增强x055
            case 55 -> new AttributeUpEffect(from, to, value, AttributeUp.SPEED_UP);
            //56.力量下降x056，目标
            case 56 -> new AttributeDownEffect(from, to, value, AttributeDown.POWER_DOWN);
            //57.智力下降x057，目标
            case 57 -> new AttributeDownEffect(from, to, value, AttributeDown.INTELLIGENCE_DOWN);
            //58.防御下降x058，目标
            case 58 -> new AttributeDownEffect(from, to, value, AttributeDown.DEFENSE_DOWN);
            //59.敏捷下降x059，目标
            case 59 -> new AttributeDownEffect(from, to, value, AttributeDown.AGILITY_DOWN);
            //60.纯洁下降x060，目标
            case 60 -> new AttributeDownEffect(from, to, value, AttributeDown.PURITY_DOWN);
            //61.速度下降x061，目标
            case 61 -> new AttributeDownEffect(from, to, value, AttributeDown.SPEED_DOWN);
            //62.力量翻倍x062，目标
            case 62 -> new DoubleAttribute(from, to, value, AttributeUp.POWER_UP);
            //63.智力翻倍x063，目标
            case 63 -> new DoubleAttribute(from, to, value, AttributeUp.INTELLIGENCE_UP);
            //64.防御翻倍x064，目标
            case 64 -> new DoubleAttribute(from, to, value, AttributeUp.DEFENSE_UP);
            //65.敏捷翻倍x065，目标
            case 65 -> new DoubleAttribute(from, to, value, AttributeUp.AGILITY_UP);
            //66.纯洁翻倍x066，目标
            case 66 -> new DoubleAttribute(from, to, value, AttributeUp.PURITY_UP);
            //67.速度翻倍x067，目标
            case 67 -> new DoubleAttribute(from, to, value, AttributeUp.SPEED_UP);
            //68.失去所有力量x068，目标
            case 68 -> new LostAttribute(from, to, value, AttributeDown.POWER_DOWN);
            //69.失去所有智力x069，目标
            case 69 -> new LostAttribute(from, to, value, AttributeDown.INTELLIGENCE_DOWN);
            //70.失去所有防御x070，目标
            case 70 -> new LostAttribute(from, to, value, AttributeDown.DEFENSE_DOWN);
            //71.失去所有敏捷x071，目标
            case 71 -> new LostAttribute(from, to, value, AttributeDown.AGILITY_DOWN);
            //72.失去所有纯洁x072，目标
            case 72 -> new LostAttribute(from, to, value, AttributeDown.PURITY_DOWN);
            //73.失去所有速度x073，目标
            case 73 -> new LostAttribute(from, to, value, AttributeDown.SPEED_DOWN);
            //74.全属性上升x074，目标
            case 74 -> new AllAttributeUp(from, to, value);
            //75.全属性下降x075，目标
            case 75 -> new AllAttributeDown(from, to, value);
            //80.弃牌效果--弃掉当前行动的牌x080
            case 80 -> new AbandonAction(from, player, value);
            //81.回抽效果--将当前行动的牌放回抽牌堆x081
            case 81 -> new GoDrawCards(from, player, value);
            //82.消耗效果--将当前行动的牌放到消耗牌堆x082
            case 82 -> new ConsumeEffect(from, player, value);
            //83.出牌数减少效果x083（x为减少的出牌数）x083
            case 83 -> new ReduceProduce(from, player, value);
            //84.刷新本回合出牌数x084
            case 84 -> new ShuffleProduce(from, player, value);
            //85.不占用出牌数效果x085
            case 85 -> new NoProduce(from, player, value);
            //86.获取本回合出牌数x086
            case 86 -> new GetProduce(from, player, value);
            //97.群体效果x097（x为执行的效果）
            case 97 -> new AllTargetEffect(from, to, value);
            //98.结束当前回合x098
            case 98 -> new ActionOver(from, to, value);
            //99.玩家额外行动x099
            case 99 -> new RoleAction(from, to, value);
            //101.有限次伤害翻倍x101
            case 101 -> new DoubleDamage(from, to, value);
            //102.伤害翻倍效果结束x102
            case 102 -> new DoubleDamageEnd(from, to, value);
            //103.虚弱效果x103
            case 103 -> new WeakenEffect(from, to, value);
            //104.虚弱效果结束x104
            case 104 -> new WeakenEnd(from, to, value);
            //105.群体虚弱效果x105
            case 105 -> new AllWeaken(from, to, value);
            //106.易伤效果x106
            case 106 -> new VulnerabilityEffect(from, to, value);
            //107.易伤结束效果x107
            case 107 -> new VulnerabilityEnd(from, to, value);
            //108.群体易伤效果x108
            case 108 -> new AllVulnerability(from, to, value);
            //109.点燃效果x109
            case 109 -> new LightEffect(from, to, value);
            //110.群体点燃效果x110
            case 110 -> new AllLightEffect(from, to, value);
            //111.伤害提升效果x111（x为提升的数值）
            case 111 -> new DamageAdd(from, to, value);
            //112.伤害减免效果结束x112（x为结束的各数）
            case 112 -> new ReduceDamageEnd(from, to, value);
            //200.条件满足效果（条件1：目标无护甲）x200
            case 200 -> new ConditionEffect(from, to, value, 1, getEffect(value, from, to));
            //201.条件满足效果（条件2：目标力量低于发动者）x201
            case 201 -> new ConditionEffect(from, to, value, 2, getEffect(value, from, to));
            //202.条件满足效果（条件3：若目标血量低于记录值）
            case 202 -> new ConditionEffect(from, to, value, 3, getEffect(value, from, to));
            //300.创造护盾完全防御时机，永久时机效果，不可叠加，仅一层x300
            case 300 -> new OpportunityEffect(from, to, value, OpportunityType.ARMOR_DEFENSE, false, 1);
            //300.创造护盾完全防御时机，永久时机效果，叠加，仅一层x30
            case 301 -> new OpportunityEffect(from, to, value, OpportunityType.ARMOR_DEFENSE, true, 1);
            //302.自身回合开始时机，永久时机效果，不可叠加，仅一层x302
            case 302 -> new OpportunityEffect(from, to, value, OpportunityType.OWN_ROUND_BEGIN, false, 1);
            //303.自身回合开始时机，永久时机效果，可叠加，仅一层x303
            case 303 -> new OpportunityEffect(from, to, value, OpportunityType.OWN_ROUND_BEGIN, true, 1);
            //304.失去血量时，永久时机效果，不可叠加，仅一层x304
            case 304 -> new OpportunityEffect(from, to, value, OpportunityType.LOST_BLOOD, false, 1);
            //305.失去血量时，永久时机效果，可叠加，仅一层x305
            case 305 -> new OpportunityEffect(from, to, value, OpportunityType.LOST_BLOOD, true, 1);
            //306.
            //307.攻击时，永久时机效果，可叠加，仅一层x307
            case 307 -> new OpportunityEffect(from, to, value, OpportunityType.ATTACK_TIME, true, 1);
            //308.
            //309.
            //310.
            //311.自身回合结束，永久时机效果，可叠加，仅一层x311
            case 311 -> new OpportunityEffect(from, to, value, OpportunityType.OWN_ROUNDS_OVER, true, 1);
            //312.
            //313.每使用一张牌后，永久时机效果，可叠加，仅一层x313
            case 313 -> new OpportunityEffect(from, to, value, OpportunityType.PRODUCE_CARD, true, 1);
            //400.
            //401.
            //402.
            //403.自身回合开始时机，有限次时机效果，不可叠加xy403（y一位，次数）
            case 403 ->
                    new NumOpportunityEffect(from, to, value / 10, OpportunityType.OWN_ROUND_BEGIN, value % 10, false, 1);
            //404.自身回合开始时机，有限次时机效果，可叠加xy404（y一位，次数）
            case 404 ->
                    new NumOpportunityEffect(from, to, value / 10, OpportunityType.OWN_ROUND_BEGIN, value % 10, true, 1);
            //405.
            //406.攻击时，有限次时机效果，不可叠加xy406（y一位，次数）
            case 406 ->
                    new NumOpportunityEffect(from, to, value / 10, OpportunityType.ATTACK_TIME, value % 10, false, 1);
            //407.
            //408.
            //409.受到伤害时，有限次时机效果，可叠加
            //411.自身回合结束，有限时机效果，可叠加xy411（y一位，次数）
            case 411 ->
                    new NumOpportunityEffect(from, to, value / 10, OpportunityType.OWN_ROUNDS_OVER, value % 10, true, 1);
            //504.
            //509.受到伤害时，带回合的无限次效果，可叠加xy509（y一位，持续回合数）
            case 509 ->
                    new RoundsOpportunityEffect(from, to, value / 10, OpportunityType.HURT_TIME, value % 10, true, 1);
            //900.记录器操作x0900（x为操作码）
            case 900 -> new RecordEffect(from, to, value);
            //998暂缓秒数x998
            case 998 -> new PauseEffect(null, null, 0, value * 1.0 / 10);
            //999.暂停时机x999
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

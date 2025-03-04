package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


//效果解析：后3位效果编号，其他数值
//卡牌效果集合，卡牌效果构造器
public enum Effects {
    //1.造成物理伤害x001（x为伤害数值）
    PHYSICAL_DAMAGE,
    //9.死亡效果
    DEATH_EFFECT,
    //10.抽牌效果x010（x为抽牌数，抽不够洗牌）
    DRAW,
    //11.抽牌效果x011（x为抽牌数，抽不够不洗牌）
    DRAW_NO_SHUFFLE,
    //12.洗牌效果x012（x为补回抽牌数，不洗牌抽牌）
    SHUFFLE,
    //20.血量回复
    RESTORE_BLOOD,

    //40.护盾添加
    ARMOR_ADD,

    //80.弃牌效果--弃掉当前行动的牌x080（x无效果）
    ABANDON_ACTION,
    //81.回抽效果--将当前行动的牌放回抽牌堆x081（x无效果）
    GoDrawCards,
    //82.消耗--将当前行动的牌放到消耗牌堆x082（x无效果）
    ConsumeCard,
    //98.结束角色当前回合
    ACTION_OVER,
    //99.角色额外行动x099（x代表获得行动的次数）
    ROLE_ACTION;


    public static final int TYPE_DIVISION = 1000;

    //卡牌效果数组解析
    public static ArrayList<Effect> getCardEffects(int[] cardEffects) {
        ArrayList<Effect> effects = new ArrayList<>();

        for (int cardEffect : cardEffects) {
            effects.add(getCardEffect(cardEffect));
        }
        return effects;
    }

    //卡牌效果解析
    public static Effect getCardEffect(int cardEffect) {
        int effectNumber = cardEffect % TYPE_DIVISION;
        int value = cardEffect / TYPE_DIVISION;

        return switch (effectNumber) {
            //1.造成物理伤害
            case 1 -> new PhysicalDamage(player, null, value);
            //9.死亡效果
            case 9 -> new DeathEffect(player, player, value);
            //10.抽牌效果，抽不够洗牌
            case 10 -> new DrawEffect(player, player, value);
            //11.抽牌效果，抽不够不洗牌
            case 11 -> new DrawNoShuffle(player, player, value);
            //12.洗牌效果，将弃牌堆的牌放回抽牌堆并打乱
            case 12 -> new ShuffleEffect(player, player, value);
            //20.血量回复效果
            case 20 -> new RestoreBlood(player, player, value);
            //40.护盾添加
            case 40 -> new ArmorAdd(player, player, value);
            //80.弃牌效果--弃掉当前行动的牌
            case 80 -> new AbandonAction(player, player, value);
            //81.回抽效果--将当前行动的牌放回抽牌堆
            case 81 -> new GoDrawCards(player, player, value);
            //82.消耗效果--将当前行动的牌放到消耗牌堆
            case 82 -> new ConsumeEffect(player, player, value);
            //98.结束当前玩家回合
            case 98 -> new ActionOver(player, player, value);
            //99.玩家额外行动
            case 99 -> new RoleAction(player, player, value);
            default -> null;
        };

    }

    //卡牌效果数组序列转字符串
    public static String CardEffectsToString(Card card) {
        StringBuilder sb = new StringBuilder();
        int[] cardEffects = card.getCardEffects();
        ArrayList<Effect> effects = getCardEffects(cardEffects);
        for (Effect effect : effects) {
            sb.append(effect.toString());
            if (effect != effects.get(effects.size() - 1)) sb.append("；");
        }
        return sb.toString();
    }

    //效果数组转详细数据
    public static String effectsDetail(int[] effects) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Effect> effectList = getCardEffects(effects);
        for (Effect effect : effectList) {
            sb.append(effect.describeInDetail());
        }
        return sb.toString();
    }
}

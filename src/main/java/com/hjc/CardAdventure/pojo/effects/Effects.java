package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;

//卡牌效果集合，卡牌效果构造器
public enum Effects {
    //1.造成物理伤害
    PHYSICAL_DAMAGE,

    //10.抽牌效果
    DRAW,

    //80.弃牌效果--弃掉当前行动的牌
    ABANDON_ACTION,

    //99.角色额外行动
    ROLE_ACTION;


    public static final int TYPE_DIVISION = 100;

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
            case 1 -> new PhysicalDamage(PlayerInformation.player, null, value);
            //10.抽牌效果
            case 10 -> new DrawEffect(PlayerInformation.player, PlayerInformation.player, value);
            //80.弃牌效果--弃掉当前行动的牌
            case 80 -> new AbandonAction(PlayerInformation.player, PlayerInformation.player, value);
            //99.玩家额外行动
            case 99 -> new RoleAction(PlayerInformation.player, PlayerInformation.player, value);
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

package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;

import java.util.ArrayList;

public class ReuseEffect extends Effect {
    public ReuseEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null || getFrom() == null) return;
        //获取当前行动卡牌
        Card card = CardComponent.actionCard.getCard();
        if (card == null) return;
        //获取卡牌的有效效果数
        int n = card.getEffectiveEffect();
        //创建效果序列
        int[] effects = new int[n + 1];
        //添加暂停效果，0.5秒
        effects[0] = 5998;
        System.arraycopy(card.getCardEffects(), 0, effects, 1, effects.length - 1);
        //解析效果序列
        ArrayList<Effect> effectArrayList = Effects.getEffects(effects, getFrom(), getTo());
        //插入此效果
        BattleInformation.insetEffect(effectArrayList);
    }

    @Override
    public String describeInDetail() {
        return "复用：再次使用当前卡牌";
    }

    @Override
    public String toString() {
        return "复用";
    }
}

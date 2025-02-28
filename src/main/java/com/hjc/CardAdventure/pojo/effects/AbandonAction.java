package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.Role;

//丢弃当前执行效果的牌
public class AbandonAction extends Effect {

    public AbandonAction(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        CardComponent.actionCard.abandonAnimation3();
        CardComponent.actionCard = null;
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}

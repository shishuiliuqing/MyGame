package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

//抽牌效果
public class DrawEffect extends Effect {
    public DrawEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        //无目标，不触发
        if (super.getTo() == null) return;
        int lessDraw = 0;
        for (int i = 0; i < super.getValue(); i++) {
            lessDraw += BattleEntities.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
        if (lessDraw > 0)
            BattleInformation.insetEffect(new ShuffleEffect(PlayerInformation.player, PlayerInformation.player, lessDraw));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "抽" + super.getValue() + "张牌";
    }
}

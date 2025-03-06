package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.util.AttributeUtil;

public class DoubleDamageEnd extends Effect {
    public DoubleDamageEnd(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        AttributeUtil.isDouble.remove(super.getTo());
        //更新手牌显示
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
        //更新怪物显示
        for (Entity enemy : BattleEntities.enemies) {
            if (enemy == null) continue;
            enemy.getComponent(EnemyComponent.class).update();
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "伤害翻倍效果结束";
    }
}

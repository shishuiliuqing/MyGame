package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.util.AttributeUtil;


public class DoubleDamage extends Effect {
    public DoubleDamage(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        //伤害翻倍效果打开
        AttributeUtil.isDouble.add(super.getTo());
        //更新手牌显示
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
        //更新怪物显示
        for (Entity enemy : BattleEntities.enemies) {
            if (enemy == null) continue;
            enemy.getComponent(EnemyComponent.class).update();
        }
        //添加攻击后移除伤害翻倍时机
        Opportunity opportunity = new Opportunity("伤害翻倍", OpportunityType.ATTACK_TIME, 1, getValue(), getFrom(), null, new int[1]);
        opportunity.getEndEffects()[0] = 102;
        Opportunity.addOpportunity(opportunity, super.getTo());
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "使你下" + getValue() + "次攻击伤害翻倍";
    }
}

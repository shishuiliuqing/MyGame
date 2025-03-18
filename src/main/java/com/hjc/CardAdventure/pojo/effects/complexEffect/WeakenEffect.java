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
import com.hjc.CardAdventure.util.EffectUtil;

//虚弱效果
public class WeakenEffect extends Effect {
    public WeakenEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        //产生特效
        EffectUtil.generateWeaken(getTo());
        //虚弱效果打开
        AttributeUtil.isWeaken.add(super.getTo());
        //更新手牌显示
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
        //更新怪物显示
        for (Entity enemy : BattleEntities.enemies) {
            if (enemy == null) continue;
            enemy.getComponent(EnemyComponent.class).update();
        }
        //添加攻击后移除虚弱效果时机
        Opportunity opportunity = new Opportunity("虚弱", OpportunityType.ATTACK_TIME, 0, getValue(), getTo(), null, new int[1], false, 1);
        opportunity.getEndEffects()[0] = 104;
        Opportunity.addOpportunity(opportunity, super.getTo());
    }

    @Override
    public String describeInDetail() {
        return "虚弱" + getValue() + "：下" + getValue() + "次攻击伤害减少25%";
    }

    @Override
    public String toString() {
        return "给予目标虚弱" + getValue();
    }
}

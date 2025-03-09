package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.util.RecordUtil;

public class DamageAdd extends Effect {
    public DamageAdd(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        RecordUtil.PHYIntegers.add(getValue());
        int[] effects = new int[1];
        effects[0] = 1000900;
        Opportunity opportunity = new Opportunity("伤害提升", OpportunityType.ATTACK_TIME, 0, 1, getTo(), null, effects, true, 1);
        Opportunity.addOpportunity(opportunity, getTo());
        //刷新手牌
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "使你下一次伤害增加" + getValue() + "点";
    }
}

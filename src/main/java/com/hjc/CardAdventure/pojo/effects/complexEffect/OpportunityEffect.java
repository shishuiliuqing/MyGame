package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.Objects;

//时机效果
public class OpportunityEffect extends Effect {
    //时机类型
    private final OpportunityType opportunityType;


    public OpportunityEffect(Role from, Role to, int value, OpportunityType opportunityType) {
        super(from, to, value);
        this.opportunityType = opportunityType;
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        int[] effects = new int[1];
        effects[0] = getValue();
        Opportunity opportunity = new Opportunity("找卡", opportunityType, 0, 1, PlayerInformation.player, effects, null);
        Opportunity.addOpportunity(opportunity, PlayerInformation.player);

    }

    @Override
    public String describeInDetail() {
        String front = "";
        if (opportunityType == OpportunityType.ARMOR_DEFENSE) front = "时机1：当受到伤害被护盾完全格挡时";
        Effect effect = Effects.getEffect(getValue(), getFrom(), getTo());
        if (effect != null) {
            front = front + (effect.describeInDetail().isEmpty() ? Effect.NEW_LINE : effect.describeInDetail());
            return front;
        }
        return front + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        String front = "";
        if (opportunityType == OpportunityType.ARMOR_DEFENSE) front = "时机1，";
        return front + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo())) + "（每次打出生效1次）";
    }
}

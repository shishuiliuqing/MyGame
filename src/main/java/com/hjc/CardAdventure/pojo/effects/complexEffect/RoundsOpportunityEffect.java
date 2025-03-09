package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;

import java.util.Objects;

public class RoundsOpportunityEffect extends Effect {
    //触发次数
    private final int rounds;
    //触发时机种类
    private final OpportunityType opportunityType;
    //是否可以叠加
    private final boolean stackable;
    //层数
    private final int layer;

    public RoundsOpportunityEffect(Role from, Role to, int value, OpportunityType opportunityType, int rounds, boolean stackable, int layer) {
        super(from, to, value);
        this.opportunityType = opportunityType;
        this.rounds = rounds;
        this.stackable = stackable;
        this.layer = layer;
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        int[] effects = new int[1];
        effects[0] = getValue();
        Opportunity opportunity = new Opportunity(OpportunityType.getTypeName(opportunityType) + "*" + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo())), opportunityType, rounds, 999, getTo(), effects, null, stackable, layer);
        Opportunity.addOpportunity(opportunity, getTo());

    }

    @Override
    public String describeInDetail() {
        String front;
        if (stackable) {
            front = "可叠加\n";
        } else {
            front = "多张此类卡仅一张触发此效果\n";
        }
        front += OpportunityType.detailInformation(opportunityType);
        Effect effect = Effects.getEffect(getValue(), getFrom(), getTo());
        if (effect != null) {
            front = front + (effect.describeInDetail().isEmpty() ? Effect.NEW_LINE : "\n" + effect.describeInDetail());
            return front;
        }
        return front + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return OpportunityType.getTypeName(opportunityType) + "，" + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo()));
    }
}

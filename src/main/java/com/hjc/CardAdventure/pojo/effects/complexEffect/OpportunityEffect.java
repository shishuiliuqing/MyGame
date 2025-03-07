package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.Objects;

//永久时机效果
public class OpportunityEffect extends Effect {
    //时机类型
    private final OpportunityType opportunityType;
    //时机是否可以重叠
    private final boolean stackable;
    //时机层数
    private final int layer;

    public OpportunityEffect(Role from, Role to, int value, OpportunityType opportunityType, boolean stackable, int layer) {
        super(from, to, value);
        this.opportunityType = opportunityType;
        this.stackable = stackable;
        this.layer = layer;
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        int[] effects = new int[1];
        effects[0] = getValue();
        Opportunity opportunity = new Opportunity(OpportunityType.getTypeName(opportunityType) + "*" + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo())), opportunityType, 0, 999, PlayerInformation.player, effects, null, stackable, layer);
        Opportunity.addOpportunity(opportunity, PlayerInformation.player);

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

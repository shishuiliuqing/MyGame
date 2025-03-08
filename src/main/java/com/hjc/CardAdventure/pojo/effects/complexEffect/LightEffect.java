package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.util.AttributeUtil;

public class LightEffect extends Effect {
    public LightEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null || getFrom() == null) return;
        Role to;
        if (getTo() == getFrom()) {
            to = TargetComponent.target;
        } else to = getTo();
        //计算点燃层数
        int value = AttributeUtil.mathMagic(this);
        int[] effects = {1005, 2998};
        Opportunity.addOpportunity(new Opportunity("燃烧", OpportunityType.OWN_ROUND_BEGIN, 0, 1, to, effects, null, true, value), to);
    }

    @Override
    public String describeInDetail() {
        return "点燃：使目标燃烧，叠加层数取决于智力属性" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "点燃";
    }
}

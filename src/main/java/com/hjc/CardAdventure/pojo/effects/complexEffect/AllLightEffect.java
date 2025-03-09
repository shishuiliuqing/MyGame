package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.util.AttributeUtil;

public class AllLightEffect extends Effect {
    public AllLightEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        if (AttributeUtil.mathMagic(this) == 0) return;
        for (Enemy enemy : BattleInformation.ENEMIES) {
            //计算点燃层数
            int value = AttributeUtil.mathMagic(this);
            int[] effects = {1005, 2998};
            Opportunity.addOpportunity(new Opportunity("燃烧", OpportunityType.OWN_ROUND_BEGIN, 0, 1, enemy, effects, null, true, value), enemy);
        }
    }

    @Override
    public String describeInDetail() {
        return "点燃：使目标获得燃烧" + AttributeUtil.mathMagic(this) + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "群体点燃";
    }
}

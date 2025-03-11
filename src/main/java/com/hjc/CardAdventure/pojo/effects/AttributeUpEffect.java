package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.ActionComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.Role;

public class AttributeUpEffect extends Effect {
    private final AttributeUp attributeUp;

    public AttributeUpEffect(Role from, Role to, int value, AttributeUp attributeUp) {
        super(from, to, value);
        this.attributeUp = attributeUp;
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        Attribute attribute = getTo().getRoleAttribute();
        int value = getValue();
        if (value <= 0) return;
        if (attributeUp == AttributeUp.POWER_UP) {
            attribute.setPower(attribute.getPower() + value);
            if (attribute.getPower() > 999) attribute.setPower(999);
        } else if (attributeUp == AttributeUp.INTELLIGENCE_UP) {
            attribute.setIntelligence(attribute.getIntelligence() + value);
            if (attribute.getIntelligence() > 999) attribute.setIntelligence(999);
        } else if (attributeUp == AttributeUp.DEFENSE_UP) {
            attribute.setDefense(attribute.getDefense() + value);
            if (attribute.getDefense() > 999) attribute.setDefense(999);
        } else if (attributeUp == AttributeUp.AGILITY_UP) {
            attribute.setAgility(attribute.getAgility() + value);
            if (attribute.getAgility() > 999) attribute.setAgility(999);
        } else if (attributeUp == AttributeUp.PURITY_UP) {
            attribute.setPurity(attribute.getPurity() + value);
            if (attribute.getPurity() > 999) attribute.setPurity(999);
        } else {
            attribute.setSpeed(attribute.getSpeed() + value);
            if (attribute.getSpeed() > 999) attribute.setSpeed(999);
            BattleInformation.sort(BattleInformation.NEXT_ACTION);
            BattleEntities.actionBox.getComponent(ActionComponent.class).update();
        }
        getTo().upAttribute(attributeUp);
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "获得" + getValue() + switch (attributeUp) {
            case POWER_UP -> "点力量";
            case INTELLIGENCE_UP -> "点智力";
            case DEFENSE_UP -> "点防御";
            case AGILITY_UP -> "点敏捷";
            case PURITY_UP -> "点纯洁";
            case SPEED_UP -> "点速度";
        };
    }
}

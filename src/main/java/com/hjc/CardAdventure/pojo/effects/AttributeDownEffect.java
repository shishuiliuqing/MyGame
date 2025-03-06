package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;


public class AttributeDownEffect extends Effect {
    private final AttributeDown attributeDown;

    public AttributeDownEffect(Role from, Role to, int value, AttributeDown attributeDown) {
        super(from, to, value);
        this.attributeDown = attributeDown;
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        Attribute attribute = getTo().getRoleAttribute();
        int value = getValue();
        if (attributeDown == AttributeDown.POWER_DOWN) {
            attribute.setPower(attribute.getPower() - value);
        } else if (attributeDown == AttributeDown.INTELLIGENCE_DOWN) {
            attribute.setIntelligence(attribute.getIntelligence() - value);
        } else if (attributeDown == AttributeDown.DEFENSE_DOWN) {
            attribute.setDefense(attribute.getDefense() - value);
        } else if (attributeDown == AttributeDown.AGILITY_DOWN) {
            attribute.setAgility(attribute.getAgility() - value);
        } else if (attributeDown == AttributeDown.PURITY_DOWN) {
            attribute.setPurity(attribute.getPurity() - value);
        } else {
            attribute.setSpeed(attribute.getSpeed() - value);
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "降低目标" + getValue() + "点" + switch (attributeDown) {
            case POWER_DOWN -> "力量";
            case INTELLIGENCE_DOWN -> "智力";
            case DEFENSE_DOWN -> "防御";
            case AGILITY_DOWN -> "敏捷";
            case PURITY_DOWN -> "纯洁";
            case SPEED_DOWN -> "速度";
        };
    }

    public AttributeDown getAttributeDown() {
        return this.attributeDown;
    }
}

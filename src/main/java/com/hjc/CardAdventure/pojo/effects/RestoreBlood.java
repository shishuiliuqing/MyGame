package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.util.AttributeUtil;

//目标回复血量
public class RestoreBlood extends Effect {
    public RestoreBlood(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        super.getTo().restoreBlood(AttributeUtil.mathRestoreBlood(this));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "回复血量" + AttributeUtil.mathRestoreBlood(this) + "点";
    }
}

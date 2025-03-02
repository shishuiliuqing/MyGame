package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;

public class RestoreBlood extends Effect{
    public RestoreBlood(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {

    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "回复" + null;
    }
}

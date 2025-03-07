package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.player.Player;

public class NoProduce extends Effect {
    public NoProduce(Role from, Player player, int value) {
        super(from,player,value);
    }

    @Override
    public void action() {
        return;
    }

    @Override
    public String describeInDetail() {
        return "轻盈：打出此牌不减少出牌次数";
    }

    @Override
    public String toString() {
        return "轻盈";
    }
}

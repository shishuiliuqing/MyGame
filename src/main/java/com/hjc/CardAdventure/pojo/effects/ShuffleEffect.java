package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.AbandonCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.OutUtil;

//洗牌效果，把弃牌堆的牌丢入抽牌堆，将抽牌堆的牌打乱，value是再抽牌次数
public class ShuffleEffect extends Effect {
    public ShuffleEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        //将弃牌堆的牌放回抽牌堆
        BattleInformation.DRAW_CARDS.addAll(BattleInformation.ABANDON_CARDS);
        //清空弃牌堆的牌
        BattleInformation.ABANDON_CARDS.clear();
        //打乱抽牌堆的牌
        OutUtil.disruptCards(BattleInformation.DRAW_CARDS);
        //补回抽牌,不洗牌抽牌
        BattleInformation.insetEffect(new DrawNoShuffle(PlayerInformation.player, PlayerInformation.player, super.getValue()));
        //更新弃牌堆
        BattleEntities.abandonCards.getComponent(AbandonCardsComponent.class).update();
    }

    @Override
    public String describeInDetail() {
        return "洗牌：将弃牌堆的牌打乱放回抽牌堆" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "洗牌";
    }
}

package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;

public class FindCard extends Effect {
    public FindCard(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        //判断手牌区是否满了
        if (CardComponent.HAND_CARDS.size() >= 10) return;
        if (getValue() % 10 == 1) {
            int num = getValue() / 10;
            Card find = null;
            //找该编号的卡
            for (Card drawCard : BattleInformation.DRAW_CARDS) {
                if (drawCard.getCardNum() == num) {
                    find = drawCard;
                    BattleInformation.DRAW_CARDS.remove(drawCard);
                    break;
                }
            }
            //没找到，结束该效果
            if (find == null) return;
            //中断效果执行器
            BattleInformation.insetEffect(new PauseEffect(null, null, 0, 999));
            //将该牌放到牌顶
            BattleInformation.DRAW_CARDS.add(0, find);
            //标记特殊打出效果
            CardComponent.specialProduce = true;
            //抽取此牌
            BattleEntities.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
    }

    @Override
    public String describeInDetail() {
        String s = "寻回/";
        if (getValue() % 10 == 1) {
            s = s + "抽：从抽牌堆找到此牌，将其“抽取”并选择是否打出";
        } else if(getValue() % 10 == 2) {
            s  = s + "弃：从弃牌堆找到此牌，将其置于抽牌堆顶，“抽取”并选择是否打出";
        } else if(getValue() % 10 == 3) {
            s  = s + "消：从消耗牌堆找到此牌，将其置于抽牌堆顶，“抽取”并选择是否打出";
        }
        return s + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        String s = "寻回/";
        if (getValue() % 10 == 1) {
            s = s + "抽";
        }
        return s;
    }
}

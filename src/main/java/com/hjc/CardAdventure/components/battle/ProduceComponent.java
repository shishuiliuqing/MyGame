package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProduceComponent extends Component {
    public static boolean isLight = false;

    @Override
    public void onAdded() {
        //创建使用按钮
        Texture button = FXGL.texture(isLight ? "buttonLight.png" : "buttonDark.png", 270 / 2.0, 98 / 2.0);
        button.setTranslateX(CardAdventureApp.APP_WITH - 150);
        button.setTranslateY(CardAdventureApp.APP_HEIGHT - 250);
        entity.getViewComponent().addChild(button);
        //创建使用文本
        Text useText = new Text("使    用");
        useText.setFont(new Font("华文行楷", 29));
        useText.setTranslateX(CardAdventureApp.APP_WITH - 150 + 25);
        useText.setTranslateY(CardAdventureApp.APP_HEIGHT - 250 + 35);
        entity.getViewComponent().addChild(useText);

        //按钮亮的时候可点击
        if (isLight) {
            entity.getViewComponent().addOnClickHandler(e -> produce());
        }
    }

    //执行出牌效果
    private void produce() {
        //不允许继续点击使用按钮
        resetButton(false);
        //不可更改指定目标
        TargetComponent.needTarget = false;
        //不可选择卡牌
        CardComponent.selectable = false;
        //卡牌移出被选择数组
        CardComponent cardComponent = CardComponent.CARD_COMPONENTS.get(0);
        CardComponent.CARD_COMPONENTS.remove(0);
        //执行卡牌效果
        cardComponent.action();
        //触发出牌后时机
        Opportunity.launchOpportunity(PlayerInformation.player, OpportunityType.PRODUCE_CARD);
        BattleInformation.effectExecution();
    }

    //重置按钮
    public void resetButton(boolean isLight) {
        entity.removeFromWorld();
        ProduceComponent.isLight = isLight;
        BattleEntities.produce = FXGL.spawn("produce");
    }
}

package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.entityFactory.ImgEntityFactory;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


public class TargetComponent extends Component {
    //可否指定目标
    public static boolean needTarget = false;
    //群体判断
    public static boolean isAll = false;
    //随机判断
    public static boolean isRam = false;
    //目标指定
    public static Role target;

    @Override
    public void onAdded() {
        //创建指定目标标题
        Text targetText = new Text("<当前指定目标>");
        targetText.setFont(new Font("华文行楷", 22));
        targetText.setTranslateX(ImgEntityFactory.CARD_BOX_X + 10 + 15);
        targetText.setTranslateY(CardAdventureApp.APP_HEIGHT - ImgEntityFactory.CARD_BOX_Y + 28);
        targetText.setFill(Color.WHITE);

        //创建指定目标文本
        Rectangle targetRect = new Rectangle(ImgEntityFactory.CARD_BOX_Y - 16, 30, Color.valueOf(player.getColorS()));
        Text text = new Text(targetJudgment());
        text.setFont(new Font("华文行楷", 27));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(targetRect);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(ImgEntityFactory.CARD_BOX_X + 18);
        stackPane.setTranslateY(CardAdventureApp.APP_HEIGHT - ImgEntityFactory.CARD_BOX_Y + 37);
        entity.getViewComponent().addChild(stackPane);
        entity.getViewComponent().addChild(targetText);
    }

    //更新指定目标
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }

    //判断指定目标
    private String targetJudgment() {
        if (isAll) return "全体敌方目标";
        if (isRam) return "随机敌方目标";
        if (target == null) return "未指定目标";
        return target.getRoleName();
    }
}

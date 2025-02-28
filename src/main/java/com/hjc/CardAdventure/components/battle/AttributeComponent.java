package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.entityFactory.ImgEntityFactory;
import com.hjc.CardAdventure.pojo.Attribute;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;

public class AttributeComponent extends Component {
    public static Attribute attribute = BattleInformation.attribute;

    @Override
    public void onAdded() {
        //创建属性文本
        TextArea attributeText = new TextArea("你当前的属性\n" + attribute.displayAttribute());
        attributeText.setBackground(Background.EMPTY);
        attributeText.setBorder(Border.EMPTY);
        double scaleBig = 8;
        attributeText.setPrefSize(ImgEntityFactory.CARD_BOX_Y - 2 * scaleBig, ImgEntityFactory.CARD_BOX_Y - 2 * scaleBig - 80);
        attributeText.setTranslateX(ImgEntityFactory.CARD_BOX_X + 10 + scaleBig);
        attributeText.setTranslateY(CardAdventureApp.APP_HEIGHT - ImgEntityFactory.CARD_BOX_Y + scaleBig + 70);
        attributeText.setFont(new Font("华文仿宋", 15.4));
        attributeText.setEditable(false);
        entity.getViewComponent().addChild(attributeText);
//        Text attributeText = new Text("你当前的属性\n" + attribute.displayAttribute());
//        attributeText.setFont(new Font("微软雅黑",15.4));
//
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(attributeText);
//        stackPane.setTranslateX(ImgEntityFactory.CARD_BOX_X + 10 + 8);
//        stackPane.setTranslateY(CardAdventureApp.APP_HEIGHT - ImgEntityFactory.CARD_BOX_Y + 8 + 70);
//        entity.getViewComponent().addChild(stackPane);
    }

    //更新属性
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}

package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.components.BarComponent;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class TipBarComponent extends Component {
    //提示文本框
    public static final TextArea TIP_BAR = new TextArea();
    //提示文本
//    public static String tipText = "你好时间fqwfaawsfwsssssdafsaaffffffffffffffffasdasddddddddddddddddddddddddddddddddddddddd";

    @Override
    public void onAdded() {
        //不可编辑
        TIP_BAR.setEditable(false);
        //自动换行
        TIP_BAR.setWrapText(true);
        //设置字体
        TIP_BAR.setFont(new Font("微软雅黑", 20));
        //设置文本框背景
        //TIP_BAR.setStyle("-fx-background-color: #0007");
        //TIP_BAR.setStyle("-fx-control-inner-background: lightgray;-fx-text-fill: black");
        TIP_BAR.setStyle("-fx-control-inner-background: #aec6cf;-fx-background-color: rgba(0,0,0,0);-fx-text-fill: black;-fx-border-color: transparent");
        TIP_BAR.setPrefSize(400, 500);
        TIP_BAR.setTranslateX(20);
        TIP_BAR.setTranslateY(BarComponent.BAR_HEIGHT + 100);
        entity.getViewComponent().addChild(TIP_BAR);
    }

    public static void update(String tipText) {
        if (TIP_BAR.getText().equals(tipText)) return;
        TIP_BAR.clear();
        TIP_BAR.setText(tipText);
    }
}

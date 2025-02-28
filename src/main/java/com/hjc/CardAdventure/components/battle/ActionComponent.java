package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import static com.hjc.CardAdventure.pojo.BattleInformation.*;

public class ActionComponent extends Component {

    //行动框偏移距离
    public static final double X_MOVE = 1405;
    public static final double Y_MOVE = 15;
    //行动框大小
    public static final double SIDE_LEN = 40;

    @Override
    public void onAdded() {
        addActionBox(1);
    }

    //添加边框
    private void addActionBox(int n) {
        if (n >= 7) return;
        int size = THIS_ACTION.size();
        int nextSize = NEXT_ACTION.size();
        String text;
        if (n > size) {
            int index = (n - size - 1) % nextSize;
            Role role = NEXT_ACTION.get(index);
            text = ROLE_LOCATION.get(role);
        } else {
            Role role = THIS_ACTION.get(n - 1);
            text = ROLE_LOCATION.get(role);
        }

        //创建行动框
        Rectangle rectangle = new Rectangle(SIDE_LEN, SIDE_LEN, Color.WHITE);
        Text numText = new Text(text);
        numText.setFont(new Font("华文行楷", 30));
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setMaxSize(SIDE_LEN, SIDE_LEN);
        stackPane.setTranslateX(X_MOVE + 50 * (n - 1));
        stackPane.setTranslateY(Y_MOVE);
        stackPane.getChildren().add(numText);

        entity.getViewComponent().addChild(stackPane);

        addActionBox(n + 1);
    }

    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}

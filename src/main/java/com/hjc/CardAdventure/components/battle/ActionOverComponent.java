package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.util.Duration;

public class ActionOverComponent extends Component {

    //判断是否是玩家回合
    public static boolean isPlayer = false;
    //是否执行下一阶段
    public static boolean nextStage = false;
    //沙漏组件
    public static AnimatedTexture animatedTexture;

    @Override
    public void onAdded() {
        //加载沙漏序列帧图片
        AnimationChannel ac = new AnimationChannel(FXGL.image("hourglass.jpg"), Duration.seconds(1), 62);

        //生成组件
        animatedTexture = new AnimatedTexture(ac);
        animatedTexture.setScaleX(0.1);
        animatedTexture.setScaleY(0.1);
//        animatedTexture.setFitWidth(30);
//        animatedTexture.setFitHeight(30);
//        animatedTexture.setX(30);
//        animatedTexture.setY(30);
        animatedTexture.setTranslateX(1450);
        animatedTexture.setTranslateY(-345);
        //animatedTexture.play();
        //animatedTexture.loop();
        entity.getViewComponent().addChild(animatedTexture);
        entity.getViewComponent().addOnClickHandler(e -> update());
        //entity.getViewComponent().addOnClickHandler(e-> System.out.println("你好"));
    }

    @Override
    public void onUpdate(double tpf) {
        if (nextStage) {
            //动画播放
            animatedTexture.play();
            //更新行动序列
            BattleEntities.actionBox.getComponent(ActionComponent.class).update();
            //下一目标行动
            BattleInformation.battle();
            //停止进入下一阶段
            nextStage = false;
        }
    }

    //玩家回合结束
    public void update() {
        if (isPlayer) {
            //animatedTexture.play();
            isPlayer = false;
            //不可选牌
            CardComponent.selectable = false;

            PlayerInformation.player.abandon();
        }
    }
}

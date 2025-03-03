package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.ActionOver;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ActionOverComponent extends Component {

    //判断是否是玩家回合
    public static boolean isPlayer = false;
    //沙漏组件
    public static AnimatedTexture animatedTexture;
    //回合结束判断间隔器
    //private static LocalTimer overJudge = FXGL.newLocalTimer();

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
        entity.getViewComponent().addOnClickHandler(e -> onOver());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        //entity.getViewComponent().addOnClickHandler(e-> System.out.println("你好"));
    }

    //结束按钮查看
    private void lookInformation() {
        TipBarComponent.update("结束回合");
    }

    //回合结束（自动）
    public static void actionOver(Role role) {
        //System.out.println(BattleInformation.EFFECTS);
        //添加回合结束效果
        BattleInformation.EFFECTS.add(new ActionOver(role, role, 1));
        //运行效果器
        BattleInformation.effectExecution();
    }

    //玩家回合结束（点击触发）
    public void onOver() {
        if (isPlayer) {
            //animatedTexture.play();
            isPlayer = false;
            //不可选牌
            CardComponent.selectable = false;

            PlayerInformation.player.abandon();
        }
    }

    //回合结束动画
    public void over() {
        //动画播放
        animatedTexture.play();
        //更新行动序列
        BattleEntities.actionBox.getComponent(ActionComponent.class).update();
        //下一个目标行动
        BattleInformation.battle();
    }
}

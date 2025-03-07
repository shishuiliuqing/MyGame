package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;

//暂停效果，无特别意义
public class PauseEffect extends Effect {
    //暂停秒数
    private final double second;

    public PauseEffect(Role from, Role to, int value, double second) {
        super(from, to, value);
        this.second = second;
    }

    @Override
    public void action() {
        if (second == 999) return;
        TranslateTransition tt = new TranslateTransition(Duration.seconds(second), new Text());
        tt.setOnFinished(e -> BattleInformation.effectExecution());
        tt.play();
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}

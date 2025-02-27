package com.hjc.CardAdventure.pojo.card;

import com.hjc.CardAdventure.pojo.Attribute;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    //卡牌名字
    private String cardName;
    //卡牌属性
    private Attribute attribute;
    //卡牌品质
    private CardQuality cardQuality;
    //卡牌颜色
    private String colorS;
    //卡牌效果序列
    private int[] cardEffects;
    //卡牌指定目标类型
    private TargetType targetType;

    public String cardToString() {
        return cardName +
                Effect.NEW_LINE +
                "该卡牌打出需要属性\n" +
                attribute.displayAttribute() +
                Effect.NEW_LINE +
                Effects.CardEffectsToString(this) +
                Effect.NEW_LINE +
                Effects.effectsDetail(this.cardEffects);
    }
}

package com.hjc.CardAdventure.util;

import com.hjc.CardAdventure.pojo.effects.ArmorAdd;
import com.hjc.CardAdventure.pojo.effects.PhysicalDamage;
import com.hjc.CardAdventure.pojo.effects.RestoreBlood;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

public class AttributeUtil {

    private AttributeUtil() {
    }

    //计算人物物理伤害
    public static int mathPhysicalDamage(PhysicalDamage physicalDamage) {
        int baseValue = physicalDamage.getValue();

        //添加发动者的力量
        baseValue += physicalDamage.getFrom().getRoleAttribute().getPower();

        return baseValue;
    }

    //计算人物的初始抽牌数
    public static int drawNum() {
        int agility = PlayerInformation.player.getAttribute().getAgility();
        if (agility <= 3) return agility;
        return 3 + (agility - 3) % 2;
    }

    //计算回血效果
    public static int mathRestoreBlood(RestoreBlood restoreBlood) {
        int baseValue = restoreBlood.getValue();

        int purity = restoreBlood.getFrom().getRoleAttribute().getPurity();
        if (purity <= 3) baseValue += purity;
        else baseValue += 3 + (purity - 3) % 3;
        return baseValue;
    }

    //计算护盾添加效果
    public static int mathArmorAdd(ArmorAdd armorAdd) {
        int baseValue = armorAdd.getValue();

        //添加发动者的防御
        baseValue += armorAdd.getFrom().getRoleAttribute().getDefense();
        return baseValue;
    }
}

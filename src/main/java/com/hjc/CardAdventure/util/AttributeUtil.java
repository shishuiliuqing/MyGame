package com.hjc.CardAdventure.util;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.ArmorAdd;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.PhysicalDamage;
import com.hjc.CardAdventure.pojo.effects.RestoreBlood;
import com.hjc.CardAdventure.pojo.player.Level;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

public class AttributeUtil {

    private AttributeUtil() {
    }


    //计算人物物理伤害
    //角色翻倍判断集合
    public static ArrayList<Role> isDouble = new ArrayList<>();
    //角色虚弱效果判断集合
    public static ArrayList<Role> isWeaken = new ArrayList<>();

    public static int mathPhysicalDamage(PhysicalDamage physicalDamage) {
        int baseValue = physicalDamage.getValue();
        int b = baseValue % 100;
        //前两位为力量倍率
        baseValue = baseValue / 100;
        //特殊加成
        if (physicalDamage.getFrom() == player) baseValue += RecordUtil.getInteger(RecordUtil.PHYIntegers);
        //添加发动者的力量
        baseValue += (physicalDamage.getFrom().getRoleAttribute().getPower() * b);
        if (isDouble.contains(physicalDamage.getFrom())) baseValue = baseValue * 2;
        if (isWeaken.contains(physicalDamage.getFrom())) baseValue = baseValue * 3 / 4;
        //减伤效果
        //if(physicalDamage.getFrom() != player) baseValue -= RecordUtil.getInteger(RecordUtil.ReduceDamageIntegers);
        return Math.max(baseValue, 0);
    }

    //计算人物的初始抽牌数
    public static int drawNum() {
        int agility = player.getAttribute().getAgility();
        if (agility <= 0) return 0;
        if (agility <= 3) return agility;
        return 3 + (agility - 3) / 3;
    }

    //计算回血效果
    public static int mathRestoreBlood(RestoreBlood restoreBlood) {
        int baseValue = restoreBlood.getValue();
        //特殊加成
        if (restoreBlood.getFrom() == player) baseValue += RecordUtil.getInteger(RecordUtil.RestBloodIntegers);
        int purity = restoreBlood.getFrom().getRoleAttribute().getPurity();
        if (purity <= 3) baseValue += purity;
        else baseValue += 3 + (purity - 3) % 3;
        return Math.max(baseValue, 0);
    }

    //计算法术效果
    public static int mathMagic(Effect magicEffect) {
        int baseValue = magicEffect.getValue();
        baseValue += magicEffect.getFrom().getRoleAttribute().getIntelligence();
        return Math.max(baseValue, 0);
    }

    //计算护盾添加效果
    public static int mathArmorAdd(ArmorAdd armorAdd) {
        int baseValue = armorAdd.getValue();

        //添加发动者的防御
        baseValue += armorAdd.getFrom().getRoleAttribute().getDefense();
        return Math.max(baseValue, 0);
    }

    //计算人物出牌数
    public static int mathProduceNum() {
        int num = 0;
        num += Level.playedCardNum(Level.getLV(player.getExperience()));

        return num;
    }

    //初始化计算工具
    public static void initUtil() {
        isDouble.clear();
        isWeaken.clear();
        RecordUtil.initRecord();
    }
}

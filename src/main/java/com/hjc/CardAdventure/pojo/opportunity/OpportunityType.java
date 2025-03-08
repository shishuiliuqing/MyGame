package com.hjc.CardAdventure.pojo.opportunity;

public enum OpportunityType {
    //1.游戏开始
    GAME_BEGIN,
    //2.回合开始
    ROUND_BEGIN,
    //3.自身回合开始
    OWN_ROUND_BEGIN,
    //4.抽牌阶段前
    DRAW_STAGE_FRONT,
    //5.抽牌阶段后
    DRAW_STAGE_BACK,
    //5.弃牌阶段前
    ABANDON_STAGE_FRONT,
    //6.弃牌阶段后
    ABANDON_STAGE_BACK,
    //7.受到伤害前
    HURT_TIME,
    //8.受到伤害后
    HURT_BACK,
    //9.物理攻击前
    ATTACK_TIME,
    //10.造成伤害后
    ATTACK_HURT_BACK,
    //11.获得护盾后
    GET_ARMOR,
    //12.护盾完全防御时
    ARMOR_DEFENSE,
    //13.失去所有护盾后
    LOST_ARMOR,
    //14.每次出牌后
    PRODUCE_CARD,
    //15.死亡时
    DEATH_TIME,
    //16.每次抽牌前
    DRAW_FRONT,
    //17.其他（非局内）
    OTHER,
    //18.失去血量
    LOST_BLOOD;

    public static String getTypeName(OpportunityType opportunityType) {
        return switch (opportunityType) {
            case OTHER -> "其他";
            case GET_ARMOR -> "获得护盾";
            case HURT_BACK -> "受到伤害后";
            case HURT_TIME -> "受到伤害前";
            case DEATH_TIME -> "死亡时";
            case DRAW_FRONT -> "抽牌前";
            case GAME_BEGIN -> "游戏开始";
            case LOST_ARMOR -> "失去护盾";
            case ATTACK_TIME -> "时机4";
            case ROUND_BEGIN -> "大回合开始";
            case PRODUCE_CARD -> "出牌后";
            case ARMOR_DEFENSE -> "时机1";
            case DRAW_STAGE_BACK -> "抽牌阶段后";
            case OWN_ROUND_BEGIN -> "时机2";
            case ATTACK_HURT_BACK -> "造成伤害后";
            case DRAW_STAGE_FRONT -> "抽牌阶段前";
            case ABANDON_STAGE_BACK -> "弃牌阶段后";
            case ABANDON_STAGE_FRONT -> "弃牌阶段前";
            case LOST_BLOOD -> "时机3";
        };
    }

    public static String detailInformation(OpportunityType opportunityType) {
        return switch (opportunityType) {
            case OTHER -> "其他";
            case GET_ARMOR -> "获得护盾";
            case HURT_BACK -> "受到伤害后";
            case HURT_TIME -> "受到伤害前";
            case DEATH_TIME -> "死亡时";
            case DRAW_FRONT -> "抽牌前";
            case GAME_BEGIN -> "游戏开始";
            case LOST_ARMOR -> "失去护盾";
            case ATTACK_TIME -> "时机4：攻击时";
            case ROUND_BEGIN -> "大回合开始";
            case PRODUCE_CARD -> "出牌后";
            case ARMOR_DEFENSE -> "时机1：当护盾完全格挡伤害时";
            case DRAW_STAGE_BACK -> "抽牌阶段后";
            case OWN_ROUND_BEGIN -> "时机2：自身回合开始时";
            case ATTACK_HURT_BACK -> "造成伤害后";
            case DRAW_STAGE_FRONT -> "抽牌阶段前";
            case ABANDON_STAGE_BACK -> "弃牌阶段后";
            case ABANDON_STAGE_FRONT -> "弃牌阶段前";
            case LOST_BLOOD -> "时机3：失去生命时";
        };
    }
}
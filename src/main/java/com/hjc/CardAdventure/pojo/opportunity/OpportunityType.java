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
    OTHER;
}

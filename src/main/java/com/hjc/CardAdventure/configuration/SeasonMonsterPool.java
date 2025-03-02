package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonMonsterPool {
    //怪池文件地址
    public static final String MONSTER_POOL_ADDRESS = "data/configuration/forest/spring.json";
    //怪物文件地址
    public static final String MONSTER_ADDRESS = "data/enemy/";

    //弱怪池
    private TimeMonsterPool weak;
    //强怪池
    private TimeMonsterPool strong;
    //精英怪池
    private TimeMonsterPool elite;
    //boss
    private String boss;

    //将怪物组合创建成对象
    public ArrayList<Enemy> getEnemies(int day, TimeStatus timeStatus) {
        ArrayList<String> monsters = generateLittleMonsterPool(day, timeStatus);
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (String monster : monsters) {
            Enemy enemy = FXGL.getAssetLoader().loadJSON(MONSTER_ADDRESS + monster + ".json", Enemy.class).get();
            enemies.add(enemy);
        }
        return enemies;
    }

    //按条件获得怪物组合
    private ArrayList<String> generateLittleMonsterPool(int day, TimeStatus timeStatus) {
        //前三天为弱怪池
        ArrayList<ArrayList<String>> monsterPool;
        if ((day - 1) % 6 + 1 <= 3) {
            monsterPool = weak.getTimeMonsterPool(timeStatus);
            //后三天为强怪池
        } else {
            monsterPool = strong.getTimeMonsterPool(timeStatus);
        }
        return randomMonster(monsterPool);
    }

    //随机从怪池选择一个怪物组合
    private ArrayList<String> randomMonster(ArrayList<ArrayList<String>> monsterPool) {
        Random r = new Random();
        return monsterPool.get(r.nextInt(monsterPool.size()));
    }
}

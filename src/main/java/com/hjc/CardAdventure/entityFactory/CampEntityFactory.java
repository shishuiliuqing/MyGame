package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.components.camp.*;

public class CampEntityFactory implements EntityFactory {

    @Spawns("battle")
    public Entity newBattle(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BattleComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("fire")
    public Entity newFire(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new FireComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("cards")
    public Entity newCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new CardsComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("playerAttribute")
    public Entity newPlayerAttribute(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new PlayerAttributeComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("rest")
    public Entity newRest(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new RestComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("adventure")
    public Entity newAdventure(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AdventureComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("event")
    public Entity newEvent(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new EventComponent())
                .neverUpdated()
                .build();
    }
}

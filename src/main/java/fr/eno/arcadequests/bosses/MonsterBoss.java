package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.runnables.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class MonsterBoss extends AQBoss
{
    public MonsterBoss(long level, EntityType entityType, double health, double attackStrength, double speed, String name)
    {
        super(level, entityType, health, attackStrength, speed, name);
    }

    @Override
    public Creature summonBoss(World world, Location location, Player invoker)
    {
        Creature bossEntity = (Creature) world.spawnEntity(location, this.entityType);
        bossEntity.setGlowing(true);
        this.setAttributeValue(bossEntity, Attribute.GENERIC_MAX_HEALTH, this.health);
        bossEntity.setHealth(this.health);
        this.setAttributeValue(bossEntity, Attribute.GENERIC_MOVEMENT_SPEED, this.speed);
        bossEntity.addScoreboardTag(AQ_BOSS_TAG);
        bossEntity.addScoreboardTag("LEVEL_" + this.level);
        bossEntity.setPersistent(true);
        bossEntity.setCustomName(name);
        bossEntity.setCustomNameVisible(true);

        BossBarRunnable.initializeBossBar(invoker, bossEntity, this.name);

        return bossEntity;
    }
}

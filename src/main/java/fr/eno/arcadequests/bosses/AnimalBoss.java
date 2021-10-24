package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.runnables.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class AnimalBoss extends AQBoss
{
    public AnimalBoss(long level, EntityType entityType, double health, double attackStrength, double speed, String name)
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
        bossEntity.addScoreboardTag("AQ_BOSS");
        bossEntity.addScoreboardTag("LEVEL_" + this.level);
        bossEntity.setPersistent(true);
        bossEntity.setCustomName(name);
        bossEntity.setCustomNameVisible(true);

        Husk attacker = (Husk) world.spawnEntity(location, EntityType.HUSK);
        attacker.setBaby();
        attacker.setCustomName(name);
        attacker.setCustomNameVisible(true);
        attacker.addScoreboardTag("AQ_BOSS_PASSENGER");
        this.setAttributeValue(attacker, Attribute.GENERIC_MAX_HEALTH, this.health);
        attacker.setHealth(this.health);
        this.setAttributeValue(attacker, Attribute.GENERIC_ATTACK_DAMAGE, this.attackStrength);
        attacker.setVisualFire(false);
        attacker.setFireTicks(0);
        attacker.setSilent(true);
        attacker.setPersistent(true);
        attacker.setInvisible(true);
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, false, false));
        attacker.setTarget(invoker);

        bossEntity.addPassenger(attacker);

        BossBarRunnable.initializeBossBar(invoker, bossEntity, this.name);

        return bossEntity;
    }
}

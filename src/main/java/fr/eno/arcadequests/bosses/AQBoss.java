package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AQBoss
{
    private final long level;
    private final EntityType entityType;
    private final double health;
    private final double attackStrength;
    private final double speed;
    private final String name;

    public AQBoss(long level, EntityType entityType, double health, double attackStrength, double speed, String name)
    {
        this.level          = level;
        this.entityType     = entityType;
        this.health         = health;
        this.attackStrength = attackStrength;
        this.speed          = speed;
        this.name           = name;
    }

    public Creature summonBoss(World world, Location location, Player invoker)
    {
        Creature bossEntity = (Creature) world.spawnEntity(location, entityType);
        bossEntity.setGlowing(true);
        bossEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.health);
        bossEntity.setHealth(this.health);
        bossEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.speed);
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
        attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.health);
        attacker.setHealth(this.health);
        attacker.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(this.attackStrength);
        attacker.setVisualFire(false);
        attacker.setFireTicks(0);
        attacker.setSilent(true);
        attacker.setPersistent(true);
        attacker.setInvisible(true);
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, false, false));
        attacker.setTarget(invoker);

        bossEntity.addPassenger(attacker);

        Utils.initializeBossBar(invoker, bossEntity, this.name);

        return bossEntity;
    }

    private String getTag()
    {
        return "AQ_" + entityType.name() + "_BOSS";
    }

    public double getHealth()
    {
        return health;
    }

    public double getAttackStrength()
    {
        return attackStrength;
    }

    public double getSpeed()
    {
        return speed;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }
}

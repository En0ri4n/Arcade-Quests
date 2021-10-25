package fr.eno.arcadequests.bosses;

import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.entity.*;

public abstract class AQBoss
{
    public static final String AQ_BOSS_TAG = "AQ_BOSS";

    protected final long level;
    protected final EntityType entityType;
    protected final double health;
    protected final double attackStrength;
    protected final double speed;
    protected final String name;

    public AQBoss(long level, EntityType entityType, double health, double attackStrength, double speed, String name)
    {
        this.level          = level;
        this.entityType     = entityType;
        this.health         = health;
        this.attackStrength = attackStrength;
        this.speed          = speed;
        this.name           = name;
    }

    protected void setAttributeValue(LivingEntity entity, Attribute attribute, double value)
    {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);

        if(attributeInstance != null)
            attributeInstance.setBaseValue(value);
    }

    public abstract Creature summonBoss(World world, Location location, Player invoker);

    public long getLevel()
    {
        return level;
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

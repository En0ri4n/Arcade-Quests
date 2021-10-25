package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.runnables.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import org.apache.commons.lang.reflect.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.*;
import org.bukkit.entity.*;

import java.lang.reflect.*;
import java.util.*;

public class AnimalBoss extends AQBoss
{
    private static final Field TARGET_FIELD = FieldUtils.getDeclaredField(EntityInsentient.class, "bV", true);
    private static final Field ATTRIBUTE_MAP_FIELD = FieldUtils.getDeclaredField(AttributeMapBase.class, "b", true);

    public AnimalBoss(long level, EntityType entityType, double health, double attackStrength, double speed, String name)
    {
        super(level, entityType, health, attackStrength, speed, name);
    }

    @Override
    public Creature summonBoss(World world, Location location, Player invoker)
    {
        Creature bossEntity = (Creature) world.spawnEntity(location, this.entityType);
        prepareBoss(bossEntity, invoker);
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

    @SuppressWarnings("unchecked")
    private void prepareBoss(Creature bossEntity, Player invoker)
    {
        EntityCreature entityBossCreature = ((CraftCreature) bossEntity).getHandle();

        try
        {
            entityBossCreature.bP.a();
            entityBossCreature.bP.a(0, new PathFinderGoalBossMeleeAttack(entityBossCreature, invoker, 1.1D));
            FieldUtils.writeField(TARGET_FIELD, entityBossCreature, ((CraftPlayer) invoker).getHandle(), true);

            AttributeMapBase attributeMapBase = entityBossCreature.getAttributeMap();
            Map<AttributeBase, AttributeModifiable> attributeModifiableMap = (Map<AttributeBase, AttributeModifiable>) FieldUtils.readField(ATTRIBUTE_MAP_FIELD, attributeMapBase);
            attributeModifiableMap.put(GenericAttributes.f, new AttributeModifiable(GenericAttributes.f, am -> am.setValue(8D)));
            FieldUtils.writeField(ATTRIBUTE_MAP_FIELD, entityBossCreature.getAttributeMap(), attributeModifiableMap);

            Map<AttributeBase, AttributeModifiable> attributeModifiableMap1 = (Map<AttributeBase, AttributeModifiable>) FieldUtils.readField(ATTRIBUTE_MAP_FIELD, attributeMapBase);
            attributeModifiableMap1.forEach((a, b) -> System.out.println(a.getName() + " : " + b.getValue()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static class PathFinderGoalBossMeleeAttack extends PathfinderGoalMeleeAttack
    {
        private final EntityPlayer target;

        public PathFinderGoalBossMeleeAttack(EntityCreature creature, Player playerTarget, double speed)
        {
            super(creature, speed, true);
            this.target = ((CraftPlayer) playerTarget).getHandle();
        }

        @Override
        protected void a(EntityLiving entityLiving, double var1)
        {
            double var3 = this.a(entityLiving);
            if(var1 <= var3 && this.h())
            {
                this.g();
                entityLiving.damageEntity(DamageSource.mobAttack(this.a), (float) Objects.requireNonNull(this.a.getAttributeInstance(GenericAttributes.f)).getValue());
                this.a.attackEntity(entityLiving);
            }
        }

        @Override
        public void d()
        {
            if(this.a.getGoalTarget() != target)
                this.a.setGoalTarget(target);
        }

        @Override
        protected double a(EntityLiving var0)
        {
            return 4.0F + var0.getWidth();
        }
    }
}

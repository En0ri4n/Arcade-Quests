package fr.eno.arcadequests.bosses;


import fr.eno.arcadequests.runnables.BossBarRunnable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

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
            attributeModifiableMap.put(GenericAttributes.f, new AttributeModifiable(GenericAttributes.f, am -> am.setValue(this.attackStrength)));
            FieldUtils.writeField(ATTRIBUTE_MAP_FIELD, entityBossCreature.getAttributeMap(), attributeModifiableMap);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static class PathFinderGoalBossMeleeAttack extends PathfinderGoalMeleeAttack
    {
        private static final Field DELAY_FIELD = FieldUtils.getDeclaredField(PathfinderGoalMeleeAttack.class, "i", true);
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

                if(entityLiving instanceof EntityPlayer)
                {
                    float damage = (float) Objects.requireNonNull(this.a.getAttributeInstance(GenericAttributes.f)).getValue();
                    Player player = (CraftPlayer) CraftPlayer.getEntity((CraftServer) Bukkit.getServer(), entityLiving);
                    entityLiving.setLastDamager(this.a);
                    player.damage(damage);
                }
            }
        }

        @Override
        protected void g()
        {
            try
            {
                FieldUtils.writeField(DELAY_FIELD, this, 10);
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void d()
        {
            if(this.a.getGoalTarget() != target) this.a.setGoalTarget(target);
        }

        @Override
        protected double a(EntityLiving var0)
        {
            return 1.5F + var0.getWidth();
        }
    }
}

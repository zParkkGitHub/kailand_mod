package net.mcreator.klv.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class CuchillaElevarProcedureProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;
        {
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (!(entity == entityiterator)) {
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 35, 1, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 1, false, false));
                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                        _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, 1, false, false));
                    {
                        Vec3 currentVelocity = entityiterator.getDeltaMovement();
                        entityiterator.setDeltaMovement(new Vec3(currentVelocity.x, 2.0, currentVelocity.z));
                    }
                    if (entity instanceof Player _player)
                        _player.getCooldowns().addCooldown(itemstack.getItem(), 600);
                    {
                        ItemStack _ist = itemstack;
                        if (_ist.hurt(3, RandomSource.create(), null)) {
                            _ist.shrink(1);
                            _ist.setDamageValue(0);
                        }
                    }
                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, new BlockPos(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.hurt")), SoundSource.NEUTRAL, 1, 1);
                        } else {
                            _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.hurt")), SoundSource.NEUTRAL, 1, 1, false);
                        }
                    }
                }
            }
        }
    }
}
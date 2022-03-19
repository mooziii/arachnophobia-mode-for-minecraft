package me.obsilabor.arachnophobiamodeforminecraft.mixin;

import me.obsilabor.arachnophobiamodeforminecraft.config.ArachnophobiaModeConfig;
import me.obsilabor.arachnophobiamodeforminecraft.config.ClothConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity {

    protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    private void useCatAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
        if (config == null) {
            return;
        }
        if (!config.isEnabled() || !config.getReplaceSounds()) {
            return;
        }
        cir.setReturnValue(SoundEvents.ENTITY_CAT_AMBIENT);
    }

    @Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
    private void useCatHurtSound(CallbackInfoReturnable<SoundEvent> cir) {
        ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
        if (config == null) {
            return;
        }
        if (!config.isEnabled() || !config.getReplaceSounds()) {
            return;
        }
        cir.setReturnValue(SoundEvents.ENTITY_CAT_HURT);
    }

    @Inject(method = "getDeathSound", at = @At("RETURN"), cancellable = true)
    private void useCatDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
        if (config == null) {
            return;
        }
        if (!config.isEnabled() || !config.getReplaceSounds()) {
            return;
        }
        cir.setReturnValue(SoundEvents.ENTITY_CAT_DEATH);
    }

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playCatStepSound(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        ArachnophobiaModeConfig config = ClothConfigManager.INSTANCE.getConfig();
        if (config == null) {
            return;
        }
        if (!config.isEnabled() || !config.getReplaceSounds()) {
            return;
        }
        ci.cancel();
        playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

}

package net.davio.create_aquatic_ambitions.mixin;

import net.davio.create_aquatic_ambitions.CreateAquaticAmbitions;

import net.minecraft.client.Minecraft;

import net.minecraft.client.main.GameConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void example$init(GameConfig gameConfig, CallbackInfo ci) {
        CreateAquaticAmbitions.LOGGER.info("Hello from {}", CreateAquaticAmbitions.NAME);
    }
}

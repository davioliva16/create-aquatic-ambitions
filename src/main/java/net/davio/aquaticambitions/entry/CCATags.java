package net.davio.aquaticambitions.entry;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class CCATags {

    public static final TagKey<Fluid> FAN_CHANNELING_PROCESSING_FLUID_TAG = addonTag("channeling_fluid", Registries.FLUID);
    public static final TagKey<Block> FAN_CHANNELING_PROCESSING_TAG = addonTag("channeling_block", Registries.BLOCK);

    private static <F> TagKey<F> addonTag(String name, ResourceKey<Registry<F>> key) {
        return TagKey.create(key, new ResourceLocation(CreateAquaticAmbitions.MODID, name));
    }

    public static void setRegister() {}

}

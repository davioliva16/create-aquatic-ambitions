package net.davio.create_aquatic_ambitions.entry;

import net.davio.create_aquatic_ambitions.CreateAquaticAmbitions;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class CCACreativeModeTab {
	public static final CreativeModeTab CCA_CREATIVE_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
			new ResourceLocation(CreateAquaticAmbitions.MODID, "s"), FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.create_aquatic_ambitions"))
					.icon(() ->  new ItemStack(CCAItems.PRISMARINE_ALLOY))
					.displayItems((parameters, output) -> {
						output.accept(CCAItems.PRISMARINE_ALLOY);
						output.accept(CCAItems.PRISMARINE_ROD);
						output.accept(CCAItems.FLINT_SHARD);
						output.accept(CCAItems.POLISHED_QUARTZ_TINE);
					})
					.build()
	);

	public static void registerItemGroups() {
		CreateAquaticAmbitions.LOGGER.info("Registering Item Groups for " + CreateAquaticAmbitions.MODID);
	}
}

package net.davio.aquaticambitions.registry;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;


public class CAACreativeTab {

    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateAquaticAmbitions.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = REGISTER.register("tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatableWithFallback("itemGroup.create_aquatic_ambitions.base", "Create Aquatic Ambitions"))
            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB .getKey())
            .icon(CAAItems.PRISMARINE_ALLOY::asStack)
            .displayItems(((itemDisplayParameters, output) -> {

                output.accept(CAAItems.PRISMARINE_ALLOY.get());
                output.accept(CAABlocks.PRISMARINE_ALLOY_BLOCK.asItem());
                output.accept(CAAItems.PRISMARINE_ROD.get());
                output.accept(CAAItems.FLINT_SHARD.get());
                output.accept(CAAItems.POLISHED_QUARTZ_TINE.get());
                output.accept(CAABlocks.MECHANICAL_CONDUIT.asItem());

            }))
            .build());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}

package net.davio.aquaticambitions.entry;

import com.simibubi.create.AllCreativeModeTabs;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CCACreativeModeTab {
        private static final DeferredRegister<CreativeModeTab> REGISTER;
        public static final RegistryObject<CreativeModeTab> CREATIVE_TAB;

        public CCACreativeModeTab() {}

        public static void register(IEventBus eventBus) {
            REGISTER.register(eventBus);
        }

        static {
            REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateAquaticAmbitions.MODID);
            CREATIVE_TAB = REGISTER.register("base",()-> {
                return CreativeModeTab.builder().title(Component.literal("Create Aquatic Ambitions"))
                        .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey(),AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                        .icon(CCAItems.PRISMARINE_ALLOY::asStack)
                        .displayItems((params,output)-> {
                            output.accept(CCAItems.PRISMARINE_ALLOY);
                            output.accept(CCABlocks.PRISMARINE_ALLOY_BLOCK);
                            output.accept(CCAItems.PRISMARINE_ROD);
                            output.accept(CCAItems.POLISHED_QUARTZ_TINE);
                            output.accept(CCAItems.FLINT_SHARD);
                        }).build();
            });
        }

}

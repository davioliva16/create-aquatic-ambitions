package net.davio.aquaticambitions.registry;

import com.simibubi.create.AllCreativeModeTabs;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CAACreativeTab {
        private static final DeferredRegister<CreativeModeTab> REGISTER;
        public static final RegistryObject<CreativeModeTab> CREATIVE_TAB;

        public CAACreativeTab() {}

        public static void register(IEventBus eventBus) {
            REGISTER.register(eventBus);
        }

        static {
            REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateAquaticAmbitions.MODID);
            CREATIVE_TAB = REGISTER.register("base",()-> {
                return CreativeModeTab.builder().title(Component.literal("Create Aquatic Ambitions"))
                        .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey(),AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                        .icon(CAAItems.PRISMARINE_ALLOY::asStack)
                        .displayItems((params,output)-> {
                            output.accept(CAAItems.PRISMARINE_ALLOY);
                            output.accept(CAABlocks.PRISMARINE_ALLOY_BLOCK);
                            output.accept(CAAItems.PRISMARINE_ALLOY_ROD);
                            output.accept(CAABlocks.MECHANICAL_CONDUIT);
                            output.accept(CAAItems.SUSPICIOUS_ROCK);
                            output.accept(CAAItems.SPIKY_SHELL);
                            output.accept(CAAItems.CALCIUM_RICH_POWDER);
                        }).build();
            });
        }

}

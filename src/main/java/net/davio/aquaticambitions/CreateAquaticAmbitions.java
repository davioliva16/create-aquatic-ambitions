package net.davio.aquaticambitions;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.RegistrateDistExecutor;
import net.createmod.catnip.lang.FontHelper;
import net.davio.aquaticambitions.content.logistics.CAAItemAttributes;
import net.davio.aquaticambitions.infrastructure.BuiltInDatapackRegistrar;
import net.davio.aquaticambitions.infrastructure.config.CAAConfig;
import net.davio.aquaticambitions.infrastructure.data.CAADatagen;
import net.davio.aquaticambitions.infrastructure.loot.CAALootModifiers;
import net.davio.aquaticambitions.registry.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateAquaticAmbitions.MODID)
public class CreateAquaticAmbitions {
    public static final String MODID = "create_aquatic_ambitions";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    static{
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public CreateAquaticAmbitions(IEventBus modEventBus, ModContainer modContainer) {

        REGISTRATE.registerEventListeners(modEventBus);

        CAABlocks.register();
        CAABlockEntityTypes.register();
        CAAItems.register();
        CAACreativeTab.register(modEventBus);
        CAATags.init();
        CAARecipeTypes.register(modEventBus);
        CAAFanProcessingTypes.register(modEventBus);
        CAAItemAttributes.register(modEventBus);
        CAALootModifiers.register(modEventBus);
        RegistrateDistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateAquaticAmbitionsClient.onCtorClient(modEventBus));

        modEventBus.addListener(this::setup);
        modEventBus.addListener(BuiltInDatapackRegistrar::registerPackRepository);
        modEventBus.addListener(EventPriority.HIGHEST, CAADatagen::gatherDataHighPriority);
        modEventBus.addListener(EventPriority.LOWEST, CAADatagen::gatherData);
        modEventBus.register(new CAAConfig(modContainer));
    }

    private void registerEntries(IEventBus modEventBus) {

    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }


}

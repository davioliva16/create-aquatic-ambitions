package net.davio.aquaticambitions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.createmod.catnip.lang.LangBuilder;
import net.davio.aquaticambitions.content.logistics.CAAItemAttributes;
import net.davio.aquaticambitions.infrastructure.config.CAAConfigs;
import net.davio.aquaticambitions.infrastructure.data.CAADatagen;
import net.davio.aquaticambitions.infrastructure.loot.CAALootModifiers;
import net.davio.aquaticambitions.registry.*;
import net.davio.aquaticambitions.registry.CAAFanProcessingTypes;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateAquaticAmbitions.MODID)
public class CreateAquaticAmbitions {
    public static final String MODID = "create_aquatic_ambitions";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateAquaticAmbitions.MODID);

    public CreateAquaticAmbitions() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CAAConfigs.register(ModLoadingContext.get());

        registerEntries(modEventBus);
        modEventBus.addListener(CreateAquaticAmbitions::onRegister);
        modEventBus.register(this);

        modEventBus.addListener(EventPriority.LOWEST, CAADatagen::gatherData);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CreateAquaticAmbitionsClient::new);
    }

    private void registerEntries(IEventBus modEventBus) {
        CAAItems.register();
        CAABlocks.register();
        CAABlockEntityTypes.register();
        CAATags.init();
        CAARecipeTypes.register(modEventBus);
        CAAItemAttributes.register(modEventBus);
        CAALootModifiers.register(modEventBus);
        CAACreativeTab.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
    }

    public static void onRegister(final RegisterEvent event) {
        CAAFanProcessingTypes.init();
    }

    public static LangBuilder lang() {
        return new LangBuilder(MODID);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID,path);
    }
}

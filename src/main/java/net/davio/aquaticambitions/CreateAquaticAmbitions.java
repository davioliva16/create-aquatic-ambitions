package net.davio.aquaticambitions;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createmod.catnip.lang.LangBuilder;
import net.davio.aquaticambitions.content.logistics.CAAItemAttributes;
import net.davio.aquaticambitions.foundation.data.CAADatagen;
import net.davio.aquaticambitions.foundation.loot.CAALootModifiers;
import net.davio.aquaticambitions.registry.*;
import net.davio.aquaticambitions.registry.CAAFanProcessingTypes;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
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
        onCtor();
    }

    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);

        CAAItems.register();
        CAABlocks.register();
        CAABlockEntityTypes.register();
        CAACreativeTab.register(modEventBus);

        CAATags.init();
        CAARecipeTypes.register(modEventBus);
        CAAItemAttributes.register(modEventBus);
        CAALootModifiers.register(modEventBus);

        modEventBus.addListener(CreateAquaticAmbitions::init);
        modEventBus.addListener(CreateAquaticAmbitions::onRegister);

        modEventBus.addListener(EventPriority.LOWEST, CAADatagen::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateAquaticAmbitionsClient.onCtorClient(modEventBus, forgeEventBus));
    }

    private static void init(final FMLCommonSetupEvent event) {
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

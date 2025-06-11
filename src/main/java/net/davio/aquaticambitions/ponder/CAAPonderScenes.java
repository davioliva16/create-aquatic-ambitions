package net.davio.aquaticambitions.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davio.aquaticambitions.ponder.scenes.MechanicalConduitScenes;
import net.davio.aquaticambitions.registry.CAABlocks;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CAAPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/awaken", MechanicalConduitScenes::awaken);
        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/processing", MechanicalConduitScenes::processing);
        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/effects", MechanicalConduitScenes::effects);
    }

}

package net.davio.aquaticambitions.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.minecraft.resources.ResourceLocation;

public class CAAPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateAquaticAmbitions.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        CAAPonderScenes.register(helper);
    }
    /*
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        CAAPonderTags.register(helper);
    }
    */
}


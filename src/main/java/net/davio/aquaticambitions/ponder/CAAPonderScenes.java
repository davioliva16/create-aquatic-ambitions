package net.davio.aquaticambitions.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davio.aquaticambitions.ponder.scenes.ConduitScenes;
import net.davio.aquaticambitions.ponder.scenes.MechanicalConduitScenes;
import net.davio.aquaticambitions.registry.CAABlocks;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public class CAAPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/awaken", MechanicalConduitScenes::awaken);
        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/processing", MechanicalConduitScenes::processing);
        HELPER.forComponents(CAABlocks.MECHANICAL_CONDUIT)
                .addStoryBoard("mechanical_conduit/effects", MechanicalConduitScenes::effects);

        //For vanilla blocks
        PonderSceneRegistrationHelper<Item> VANILLA = helper.withKeyFunction(b -> BuiltInRegistries.ITEM.getKey(b));

        VANILLA.forComponents(Items.CONDUIT)
                .addStoryBoard("conduit/processing", ConduitScenes::processing);
    }
}

package net.davio.aquaticambitions.infrastructure.data;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.processing.conduit.CAAConduitEffects;
import net.davio.aquaticambitions.registry.CAARegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates the built-in {@code conduit_effect} datapack registry entries (see {@link CAAConduitEffects}) into
 * {@code src/generated/resources}. These ship with the mod as always-present defaults; datapacks and other mods
 * add or override entries on top of them. Regenerate with {@code ./gradlew runData}.
 */
public class CAAConduitEffectsProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(CAARegistries.CONDUIT_EFFECT, CAAConduitEffects::bootstrap);

    public CAAConduitEffectsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CreateAquaticAmbitions.MODID));
    }

    @Override
    public String getName() {
        return "Create: Aquatic Ambitions Conduit Effects";
    }
}

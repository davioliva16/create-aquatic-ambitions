package net.davio.aquaticambitions.compat.KubeJS;

import dev.latvian.mods.kubejs.plugin.ClassFilter;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.compat.KubeJS.schemas.ChannelingRecipeSchema;
import net.minecraft.resources.ResourceLocation;

public class CAAKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.namespace(CreateAquaticAmbitions.MODID);
        registry.register(location("channeling"), ChannelingRecipeSchema.SCHEMA);
    }

    @Override
    public void registerClasses(ClassFilter filter) {
        filter.deny("net.davio.aquaticambitions");
        filter.allow("net.davio.aquaticambitions.api");
    }

    private static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(CreateAquaticAmbitions.MODID, path);
    }
}

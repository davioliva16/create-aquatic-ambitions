package net.davio.aquaticambitions.registry;

import java.util.function.Supplier;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.util.recipe.RecipeTypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;

public class CAARecipes {
    private static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CreateAquaticAmbitions.MODID);
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateAquaticAmbitions.MODID);


    public static final RecipeTypeInfo<ChannelingRecipe> CHANNELING = register("channeling", () ->  new StandardProcessingRecipe.Serializer<>(ChannelingRecipe::new));

    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
        SERIALIZERS.register(modBus);
    }

    private static <R extends Recipe<?>> RecipeTypeInfo<R> register(String name, Supplier<? extends RecipeSerializer<R>> serializer) {
        return new RecipeTypeInfo<>(name, serializer, SERIALIZERS, TYPES);
    }
}

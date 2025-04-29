package net.davio.aquaticambitions.data.recipe;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.createmod.catnip.registry.RegisteredObjectsHelper;

import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CCAItems;
import net.davio.aquaticambitions.registry.recipe.CCARecipeTypes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ChannelingRecipeGen extends ProcessingRecipeGen {

    GeneratedRecipe

    PRISMARINE_FROM_FLINT =  create(CreateAquaticAmbitions.asResource("prismarine_from_flint"), b -> b
            .require(Items.FLINT)
            .output(.25f, Items.PRISMARINE_SHARD, 1)),

    PRISMARINE_FROM_FLINT_SHARD = convert(CCAItems.FLINT_SHARD, Items.PRISMARINE_SHARD),
    PRISMARINE_CRYSTALS = convert(Items.GLOWSTONE, Items.PRISMARINE_CRYSTALS),
    HEART_OF_THE_SEA = convert(Items.ENDER_EYE, Items.HEART_OF_THE_SEA),

    EXPOSED_COPPER = convert(Items.COPPER_BLOCK, Items.EXPOSED_COPPER),
    WEATHERED_COPPER = convert(Items.EXPOSED_COPPER, Items.WEATHERED_COPPER),
    OXIDIZED_COPPER = convert(Items.WEATHERED_COPPER, Items.OXIDIZED_COPPER),

    TUBE_BLOCK =  convert(Items.DEAD_TUBE_CORAL, Items.TUBE_CORAL_BLOCK),
    BRAIN_BLOCK =  convert(Items.DEAD_BRAIN_CORAL, Items.BRAIN_CORAL_BLOCK),
    BUBBLE_BLOCK =  convert(Items.DEAD_BUBBLE_CORAL, Items.BUBBLE_CORAL_BLOCK),
    FIRE_BLOCK =  convert(Items.DEAD_FIRE_CORAL, Items.FIRE_CORAL_BLOCK),
    HORN_BLOCK =  convert(Items.DEAD_HORN_CORAL, Items.HORN_CORAL_BLOCK),

    TUBE = coralRevival(Items.DEAD_TUBE_CORAL, () -> Items.TUBE_CORAL),
    BRAIN = coralRevival(Items.DEAD_BRAIN_CORAL, () -> Items.BRAIN_CORAL),
    BUBBLE = coralRevival(Items.DEAD_BUBBLE_CORAL, () -> Items.BUBBLE_CORAL),
    FIRE = coralRevival(Items.DEAD_FIRE_CORAL, () -> Items.FIRE_CORAL),
    HORN = coralRevival(Items.DEAD_HORN_CORAL, () -> Items.HORN_CORAL);

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(CreateAquaticAmbitions.asResource(RegisteredObjectsHelper.getKeyOrThrow(result.get().asItem()).getPath()),
                p -> p.withItemIngredients(input.get())
                        .output(result.get()));
    }

    public GeneratedRecipe coralRevival(Item deadCoral, Supplier<ItemLike> coral) {
        return create(CreateAquaticAmbitions.asResource(RegisteredObjectsHelper.getKeyOrThrow(coral.get().asItem()).getPath()),
                b -> b
                    .require(deadCoral)
                    .output(coral.get(), 1)
                    .output(0.25f, coral.get(), 1));
    }

    public ChannelingRecipeGen(PackOutput generator, CompletableFuture<HolderLookup.Provider> registries) {
        super(generator, registries);
    }

    @Override
    protected CCARecipeTypes getRecipeType() {
        return CCARecipeTypes.CHANNELING;
    }
}

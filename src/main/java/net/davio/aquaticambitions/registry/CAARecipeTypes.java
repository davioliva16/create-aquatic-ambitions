package net.davio.aquaticambitions.registry;

import com.mojang.serialization.Codec;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.lang.Lang;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum CAARecipeTypes implements IRecipeTypeInfo, StringRepresentable {
    CHANNELING(ChannelingRecipe::new);

    public static final Predicate<RecipeHolder<?>> CAN_BE_AUTOMATED = r -> !r.id()
            .getPath()
            .endsWith("_manual_only");

    public final ResourceLocation id;
    public final Supplier<RecipeSerializer<?>> serializerSupplier;
    public final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializerObject;
    public final Supplier<RecipeType<?>> type;

    public static final Codec<CAARecipeTypes> CODEC = StringRepresentable.fromEnum(CAARecipeTypes::values);

    private boolean isProcessingRecipe;

    CAARecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(name());
        id = CreateAquaticAmbitions.asResource(name);
        serializerObject =  Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        @Nullable Supplier<RecipeType<?>> typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(id));
        type = typeObject;
        this.serializerSupplier = serializerSupplier;
        this.isProcessingRecipe = false;
    }

    CAARecipeTypes(StandardProcessingRecipe.Factory<?> processingFactory) {
        this(() -> new StandardProcessingRecipe.Serializer<>(processingFactory));
        isProcessingRecipe = true;
    }

    public static void register(IEventBus modEventBus) {
        ShapedRecipePattern.setCraftingSize(9, 9);
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {return id;}

    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {return (T) serializerObject.get(); }

    @Override
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {return (RecipeType<R>) type.get();}

    public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> find(I inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    public static boolean shouldIgnoreInAutomation(RecipeHolder<?> recipe) {
        RecipeSerializer<?> serializer = recipe.value().getSerializer();
        if (serializer != null && AllTags.AllRecipeSerializerTags.AUTOMATION_IGNORE.matches(serializer))
            return true;
        return !CAN_BE_AUTOMATED.test(recipe);
    }

    @Override
    public @NotNull String getSerializedName() {return id.toString();}

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateAquaticAmbitions.MODID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CreateAquaticAmbitions.MODID);
    }
}
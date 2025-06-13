package net.davio.aquaticambitions.util.recipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

//Credit to DragonPlusMinecraft and RaymondBlaze for this class

@SuppressWarnings("unchecked")
public class RecipeTypeInfo<R extends Recipe<?>> implements IRecipeTypeInfo {
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<R>> serializer;
    private final DeferredHolder<RecipeType<?>, RecipeType<R>> type;

    public RecipeTypeInfo(String name, Supplier<? extends RecipeSerializer<R>> serializer, DeferredRegister<RecipeSerializer<?>> serializerRegister, DeferredRegister<RecipeType<?>> typeRegister) {
        this.serializer = serializerRegister.register(name, serializer);
        this.type = typeRegister.register(name, RecipeType::simple);
    }

    @Override
    public ResourceLocation getId() {
        return serializer.getId();
    }

    @Override
    public RecipeSerializer<R> getSerializer() {
        return serializer.get();
    }

    @Override
    public RecipeType<R> getType() {
        return type.get();
    }
}

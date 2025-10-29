package net.davio.aquaticambitions.content.logistics;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.simibubi.create.content.logistics.item.filter.attribute.SingletonItemAttribute;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiPredicate;

public class CAAItemAttributes {

    //Create the DeferredRegister for ItemAttributeType
    public static final DeferredRegister<ItemAttributeType> ITEM_ATTRIBUTE_TYPES =
            DeferredRegister.create(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE, CreateAquaticAmbitions.MODID);

    //Register attribute using DeferredHolder
    public static final DeferredHolder<ItemAttributeType, ItemAttributeType> CAN_BE_CHANNELED =
            ITEM_ATTRIBUTE_TYPES.register("can_be_channeled",
                    () -> singleton("can_be_channeled", (s, w) -> testRecipe(s, w, CAARecipeTypes.CHANNELING.getType()))
            );

    // Utility method to test for a recipe
    private static <T extends Recipe<SingleRecipeInput>> boolean testRecipe(ItemStack s, Level w, RecipeType<T> type) {
        return w.getRecipeManager()
                .getRecipeFor(type, new SingleRecipeInput(s.copy()), w)
                .isPresent();
    }

    // Builds a SingletonItemAttribute with a predicate and ID
    private static ItemAttributeType singleton(String id, BiPredicate<ItemStack, Level> predicate) {
        return new SingletonItemAttribute.Type(type -> new SingletonItemAttribute(type, predicate, id));
    }

    public static void register(IEventBus modEventBus) {
        ITEM_ATTRIBUTE_TYPES.register(modEventBus);
    }
}
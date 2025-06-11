package net.davio.aquaticambitions.content.logistics;

import com.simibubi.create.Create;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import com.simibubi.create.content.logistics.item.filter.attribute.SingletonItemAttribute;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.davio.aquaticambitions.registry.recipe.CAARecipeTypes;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import java.util.function.BiPredicate;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.Container;
import net.minecraftforge.registries.RegistryObject;


public class CAAItemAttributes {
    private static final RecipeWrapper RECIPE_WRAPPER = new ChannelingRecipe.ChannelingWrapper();

    // Create the DeferredRegister
    public static final DeferredRegister<ItemAttributeType> ITEM_ATTRIBUTE_TYPES =
            DeferredRegister.create(Create.asResource("item_attribute_type"), CreateAquaticAmbitions.MODID);

    // Register the attribute
    public static final RegistryObject<ItemAttributeType> CAN_BE_CHANNELED =
            ITEM_ATTRIBUTE_TYPES.register("can_be_channeled",
                    () -> singleton("can_be_channeled", (s, w) -> testRecipe(s, w, CAARecipeTypes.CHANNELING.getType()))
            );

    // Builds a SingletonItemAttribute with a predicate and ID
    private static ItemAttributeType singleton(String id, BiPredicate<ItemStack, Level> predicate) {
        return new SingletonItemAttribute.Type(type -> new SingletonItemAttribute(type, predicate, id));
    }

    private static ItemAttributeType register(String id, ItemAttributeType type) {
        return Registry.register(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE, CreateAquaticAmbitions.asResource(id), type);
    }

    private static <T extends Recipe<Container>> boolean testRecipe(ItemStack s, Level w, RecipeType<T> type) {
        RECIPE_WRAPPER.setItem(0, s.copy());
        return w.getRecipeManager()
                .getRecipeFor(type, RECIPE_WRAPPER, w)
                .isPresent();
    }

    public static void register(IEventBus modEventBus) {
        ITEM_ATTRIBUTE_TYPES.register(modEventBus);
    }
}
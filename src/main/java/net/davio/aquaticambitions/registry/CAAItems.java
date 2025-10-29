package net.davio.aquaticambitions.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CAAItems {

    static {
        REGISTRATE.setCreativeTab(CAACreativeTab.MAIN_TAB);
    }

    public static final ItemEntry<Item>
        PRISMARINE_ALLOY = taggedIngredient("prismarine_alloy", CREATE_INGOTS.tag),
        PRISMARINE_ROD = taggedIngredient("prismarine_alloy_rod", commonItemTag("rods")),
        CALCIUM_RICH_POWDER = ingredient("calcium_rich_powder"),
        SPIKY_SHELL = ingredient("spiky_shell"),
        SUSPICIOUS_ROCK = ingredient("suspicious_rock");

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }
    private static ItemEntry<Item> ingredient(String name){
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    private static TagKey<Item> commonItemTag(String key) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", key));
    }

    public static void register() {};
}
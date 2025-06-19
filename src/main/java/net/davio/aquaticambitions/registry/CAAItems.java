package net.davio.aquaticambitions.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;
import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static com.simibubi.create.AllTags.forgeItemTag;

public class CAAItems {

    static {
        REGISTRATE.setCreativeTab(CAACreativeTab.CREATIVE_TAB);
    }

    public static final ItemEntry<Item>
        PRISMARINE_ALLOY = taggedIngredient("prismarine_alloy", CREATE_INGOTS.tag),
        PRISMARINE_ALLOY_ROD = taggedIngredient("prismarine_alloy_rod",forgeItemTag("rods")),
        CALCIUM_RICH_POWDER = normalIngredient("calcium_rich_powder"),
        SPIKY_SHELL = normalIngredient("spiky_shell"),
        SUSPICIOUS_ROCK = normalIngredient("suspicious_rock");

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }
    private static ItemEntry<Item> normalIngredient(String name){
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    public static void register() {};
}

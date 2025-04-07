package net.davio.aquaticambitions.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static com.simibubi.create.AllTags.commonItemTag;
import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CCAItems {

    static {
        REGISTRATE.setCreativeTab(CCACreativeTab.MAIN_TAB);
    }

    public static final ItemEntry<Item>
        PRISMARINE_ALLOY = taggedIngredient("prismarine_alloy", CREATE_INGOTS.tag),
        PRISMARINE_ROD = taggedIngredient("prismarine_rod",commonItemTag("rods")),
        POLISHED_QUARTZ_TINE = taggedIngredient("polished_quartz_tine",commonItemTag("gems/quartz")),
        FLINT_SHARD = ingredient("flint_shard");

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

    public static void register() {};
}
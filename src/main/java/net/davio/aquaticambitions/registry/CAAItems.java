package net.davio.aquaticambitions.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;
import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static com.simibubi.create.AllTags.forgeItemTag;

public class CAAItems {

    static {
        REGISTRATE.setCreativeTab(CAACreativeModeTab.CREATIVE_TAB);
    }

    public static final ItemEntry<Item> PRISMARINE_ALLOY = taggedIngredient("prismarine_alloy", CREATE_INGOTS.tag);
    public static final ItemEntry<Item> PRISMARINE_ROD = taggedIngredient("prismarine_rod",forgeItemTag("rods"));

    public static final ItemEntry<Item> POLISHED_QUARTZ_TINE = taggedIngredient("polished_quartz_tine",forgeItemTag("gems/quartz"));
    public static final ItemEntry<Item> FLINT_SHARD = normalIngredient("flint_shard");
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

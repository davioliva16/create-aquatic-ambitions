package net.davio.aquaticambitions.infrastructure.data.loot;

import com.simibubi.create.foundation.data.recipe.Mods;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.infrastructure.loot.AddItemModifier;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CAAGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public CAAGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateAquaticAmbitions.MODID);
    }

    @Override
    protected void start() {
        this.add("brain_coral_silkless", dropsItself(Blocks.BRAIN_CORAL));
        this.add("bubble_coral_silkless", dropsItself(Blocks.BUBBLE_CORAL));
        this.add("fire_coral_silkless", dropsItself(Blocks.FIRE_CORAL));
        this.add("horn_coral_silkless", dropsItself(Blocks.HORN_CORAL));
        this.add("tube_coral_silkless", dropsItself(Blocks.TUBE_CORAL));
        this.add("brain_coral_fan_silkless", dropsItself(Blocks.BRAIN_CORAL_FAN));
        this.add("bubble_fan_coral_fan_silkless", dropsItself(Blocks.BUBBLE_CORAL_FAN));
        this.add("fire_coral_fan_silkless", dropsItself(Blocks.FIRE_CORAL_FAN));
        this.add("horn_coral_fan_silkless", dropsItself(Blocks.HORN_CORAL_FAN));
        this.add("tube_coral_fan_silkless", dropsItself(Blocks.TUBE_CORAL_FAN));

        this.add("dead_brain_coral_silkless", dropsItself(Blocks.DEAD_BRAIN_CORAL));
        this.add("dead_bubble_coral_silkless", dropsItself(Blocks.DEAD_BUBBLE_CORAL));
        this.add("dead_fire_coral_silkless", dropsItself(Blocks.DEAD_FIRE_CORAL));
        this.add("dead_horn_coral_silkless", dropsItself(Blocks.DEAD_HORN_CORAL));
        this.add("dead_tube_coral_silkless", dropsItself(Blocks.DEAD_TUBE_CORAL));
        this.add("dead_brain_coral_fan_silkless", dropsItself(Blocks.DEAD_BRAIN_CORAL_FAN));
        this.add("dead_bubble_fan_coral_fan_silkless", dropsItself(Blocks.DEAD_BUBBLE_CORAL_FAN));
        this.add("dead_fire_coral_fan_silkless", dropsItself(Blocks.DEAD_FIRE_CORAL_FAN));
        this.add("dead_horn_coral_fan_silkless", dropsItself(Blocks.DEAD_HORN_CORAL_FAN));
        this.add("dead_tube_coral_fan_silkless", dropsItself(Blocks.DEAD_TUBE_CORAL_FAN));
        

        this.add("acan_coral_silkless", dropsItselfModded(Mods.UA.getId(), "acan_coral"));
        this.add("branch_coral_silkless", dropsItselfModded(Mods.UA.getId(), "branch_coral"));
        this.add("chrome_coral_silkless", dropsItselfModded(Mods.UA.getId(), "chrome_coral"));
        this.add("finger_coral_silkless", dropsItselfModded(Mods.UA.getId(), "finger_coral"));
        this.add("moss_coral_silkless", dropsItselfModded(Mods.UA.getId(), "moss_coral"));
        this.add("petal_coral_silkless", dropsItselfModded(Mods.UA.getId(), "petal_coral"));
        this.add("pillow_coral_silkless", dropsItselfModded(Mods.UA.getId(), "pillow_coral"));
        this.add("rock_coral_silkless", dropsItselfModded(Mods.UA.getId(), "rock_coral"));
        this.add("silk_coral_silkless", dropsItselfModded(Mods.UA.getId(), "silk_coral"));
        this.add("star_coral_silkless", dropsItselfModded(Mods.UA.getId(), "star_coral"));

        this.add("acan_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "acan_coral_fan"));
        this.add("branch_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "branch_coral_fan"));
        this.add("chrome_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "chrome_coral_fan"));
        this.add("finger_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "finger_coral_fan"));
        this.add("moss_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "moss_coral_fan"));
        this.add("petal_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "petal_coral_fan"));
        this.add("pillow_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "pillow_coral_fan"));
        this.add("rock_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "rock_coral_fan"));
        this.add("silk_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "silk_coral_fan"));
        this.add("star_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "star_coral_fan"));

        this.add("dead_acan_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_acan_coral"));
        this.add("dead_branch_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_branch_coral"));
        this.add("dead_chrome_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_chrome_coral"));
        this.add("dead_finger_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_finger_coral"));
        this.add("dead_moss_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_moss_coral"));
        this.add("dead_petal_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_petal_coral"));
        this.add("dead_pillow_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_pillow_coral"));
        this.add("dead_rock_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_rock_coral"));
        this.add("dead_silk_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_silk_coral"));
        this.add("dead_star_coral_silkless", dropsItselfModded(Mods.UA.getId(), "dead_star_coral"));

        this.add("dead_acan_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_acan_coral_fan"));
        this.add("dead_branch_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_branch_coral_fan"));
        this.add("dead_chrome_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_chrome_coral_fan"));
        this.add("dead_finger_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_finger_coral_fan"));
        this.add("dead_moss_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_moss_coral_fan"));
        this.add("dead_petal_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_petal_coral_fan"));
        this.add("dead_pillow_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_pillow_coral_fan"));
        this.add("dead_rock_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_rock_coral_fan"));
        this.add("dead_silk_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_silk_coral_fan"));
        this.add("dead_star_coral_fan_silkless", dropsItselfModded(Mods.UA.getId(), "dead_star_coral_fan"));
    
    }

    protected AddItemModifier dropsItself(Block block) {
        AddItemModifier modifier = new AddItemModifier(new LootItemCondition[]{
                invertedSilkTouch().build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).build(),
                LootItemRandomChanceCondition.randomChance(1f).build()}, block.asItem());

        return modifier;
    }

    protected AddItemModifier dropsItselfModded(String namespace, String blockId) {
        ResourceLocation itemResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, blockId);
        Item item = BuiltInRegistries.ITEM.get(itemResourceLocation);
        AddItemModifier modifier = new AddItemModifier(new LootItemCondition[] {
                invertedSilkTouch().build(),
                new LootTableIdCondition.Builder(ResourceLocation.fromNamespaceAndPath(namespace,"blocks/"+blockId)).build()
        }, item);

        return modifier;
    }

    protected LootItemCondition.Builder invertedSilkTouch() {
        final var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return InvertedLootItemCondition.invert(
                MatchTool.toolMatches(
                        ItemPredicate.Builder.item().withSubPredicate(
                                ItemSubPredicates.ENCHANTMENTS,
                                ItemEnchantmentsPredicate.enchantments(
                                        List.of(new EnchantmentPredicate(enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)))
                                )
                        )
                )
        );
    }
}
package net.davio.aquaticambitions.compat.JEI.category;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.compat.JEI.CAAJEI;
import net.davio.aquaticambitions.compat.JEI.category.animations.AnimatedConduit;
import net.davio.aquaticambitions.content.kinetics.fan.processing.ChannelingRecipe;
import net.davio.aquaticambitions.registry.CAARecipeTypes;
import net.davio.aquaticambitions.util.CAALang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class FanChannelingCategory extends ProcessingViaFanCategory<ChannelingRecipe> {

    public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<ChannelingRecipe>> TYPE = mezz.jei.api.recipe.RecipeType.createRecipeHolderType(CAARecipeTypes.CHANNELING.getId());
    private final AnimatedConduit conduit = new AnimatedConduit();

    public static FanChannelingCategory create() {
        var id = CreateAquaticAmbitions.asResource("fan_sanding");
        var title = CAALang.description("recipe", id).component();
        var background = new EmptyBackground(178, 72);
        var icon = new DoubleItemIcon(AllItems.PROPELLER::asStack, () -> new ItemStack(Items.HEART_OF_THE_SEA));
        var catalyst = AllBlocks.ENCASED_FAN.asStack();
        catalyst.set(DataComponents.CUSTOM_NAME, CAALang.description("recipe", id, "fan").component().withStyle(style -> style.withItalic(false)));
        var info = new Info<>(TYPE, title, background, icon, FanChannelingCategory::getAllRecipes, List.of(() -> catalyst));
        return new FanChannelingCategory(info);
    }

    public FanChannelingCategory(Info<ChannelingRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {conduit.draw(graphics,0,0);
    }

    private static List<RecipeHolder<ChannelingRecipe>> getAllRecipes() {
        var manager = CAAJEI.getRecipeManager();
        return manager.getAllRecipesFor(CAARecipeTypes.CHANNELING.getType());
    }
}
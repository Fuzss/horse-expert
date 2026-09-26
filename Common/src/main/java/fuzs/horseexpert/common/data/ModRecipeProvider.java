package fuzs.horseexpert.common.data;

import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v3.recipes.AbstractRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, ModRegistry.MONOCLE_ITEM.value())
                .define('#', Blocks.GLASS_PANE)
                .define('X', Items.GOLD_NUGGET)
                .pattern(" X ")
                .pattern("X#X")
                .pattern(" X ")
                .unlockedBy(getHasName(Items.GOLD_NUGGET), this.has(Items.GOLD_NUGGET))
                .save(this.output);
    }
}

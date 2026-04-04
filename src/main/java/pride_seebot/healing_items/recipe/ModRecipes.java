package pride_seebot.healing_items.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pride_seebot.healing_items.HealingItems;

public class ModRecipes {
    public static final RecipeSerializer<HerbMixingRecipe> HERB_MIXING = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(HealingItems.MOD_ID, "herb_mixing"),
            new SpecialRecipeSerializer<>(HerbMixingRecipe::new)
    );

    public static void registerRecipes() {
        HealingItems.LOGGER.info("Registering Custom Recipes for " + HealingItems.MOD_ID);
    }
}
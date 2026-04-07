package pride_seebot.resident_healing.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pride_seebot.resident_healing.ResidentHealing;

public class ModRecipes {
    public static final RecipeSerializer<HerbMixingRecipe> HERB_MIXING = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(ResidentHealing.MOD_ID, "herb_mixing"), new SpecialRecipeSerializer<>(HerbMixingRecipe::new)
    );
    public static final RecipeSerializer<SteroidsRecipe> STEROIDS_CRAFTING = Registry.register(
        Registries.RECIPE_SERIALIZER, Identifier.of(ResidentHealing.MOD_ID, "steroids_crafting"), new SpecialRecipeSerializer<>(SteroidsRecipe::new)
    );
    public static void registerRecipes() {
    }
}

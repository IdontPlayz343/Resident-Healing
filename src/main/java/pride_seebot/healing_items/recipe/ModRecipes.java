package pride_seebot.healing_items.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pride_seebot.healing_items.HealingItems;

public class ModRecipes {
    public static final RecipeSerializer<HerbMixingRecipe> HERB_MIXING = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(HealingItems.MOD_ID, "herb_mixing"),
            new RecipeSerializer<HerbMixingRecipe>() {
                @Override
                public MapCodec<HerbMixingRecipe> codec() {
                    return MapCodec.unit(() -> new HerbMixingRecipe(CraftingRecipeCategory.MISC));
                }

                @Override
                public HerbMixingRecipe read(net.minecraft.network.RegistryByteBuf buf) {
                    return new HerbMixingRecipe(CraftingRecipeCategory.MISC);
                }

                @Override
                public void write(net.minecraft.network.RegistryByteBuf buf, HerbMixingRecipe recipe) {
                }
            }
    );

    public static void registerRecipes() {
        HealingItems.LOGGER.info("Registering Custom Recipes for " + HealingItems.MOD_ID);
    }
}
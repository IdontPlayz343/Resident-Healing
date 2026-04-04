package pride_seebot.healing_items.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import pride_seebot.healing_items.component.ModDataComponentTypes;
import pride_seebot.healing_items.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class HerbMixingRecipe extends SpecialCraftingRecipe {
    public HerbMixingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        int herbCount = 0;

        for (int i = 0; i < input.getStacks().size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(ModItems.GREEN_HERB) || stack.isOf(ModItems.RED_HERB) || stack.isOf(ModItems.BLUE_HERB)) {
                herbCount++;
            } else {
                return false;
            }
        }
        return herbCount >= 2 && herbCount <= 3;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        List<String> ingredients = new ArrayList<>();

        for (int i = 0; i < input.getStacks().size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isOf(ModItems.GREEN_HERB)) ingredients.add("green");
            if (stack.isOf(ModItems.RED_HERB)) ingredients.add("red");
            if (stack.isOf(ModItems.BLUE_HERB)) ingredients.add("blue");
        }

        ItemStack result = new ItemStack(ModItems.MIXED_HERBS);
        result.set(ModDataComponentTypes.HERB_CONTENTS, ingredients);
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public net.minecraft.recipe.RecipeSerializer<?> getSerializer() {
        return ModRecipes.HERB_MIXING;
    }
}
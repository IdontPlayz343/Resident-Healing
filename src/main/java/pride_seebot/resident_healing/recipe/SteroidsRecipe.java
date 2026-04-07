package pride_seebot.resident_healing.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import pride_seebot.resident_healing.component.ModDataComponentTypes;
import pride_seebot.resident_healing.item.ModItems;
import java.util.List;

public class SteroidsRecipe extends SpecialCraftingRecipe {

    public SteroidsRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean hasInjector = false;
        boolean hasGreen = false;
        boolean hasRed = false;
        boolean hasBlue = false;
        boolean hasAllHerbs = false;

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(ModItems.EMPTY_INJECTOR)) {
                if (hasInjector) return false;
                hasInjector = true;
            } else if (stack.isOf(ModItems.MIXED_HERBS)) {
                List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
                for (String herb : contents) {
                    if (herb.equals("green")) hasGreen = true;
                    if (herb.equals("red")) hasRed = true;
                    if (herb.equals("blue")) hasBlue = true;
                }
                if (hasGreen && hasRed && hasBlue) {
                    if (hasAllHerbs) return false;
                hasAllHerbs = true;
                }
            } else {
                return false;
            }
        }

        if (!hasInjector || !hasAllHerbs) return false;
        return true;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.STEROIDS);
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(ModItems.STEROIDS);
    }

    @Override
    public net.minecraft.recipe.RecipeSerializer<?> getSerializer() {
        return ModRecipes.STEROIDS_CRAFTING;
    }
}

package pride_seebot.resident_healing.recipe;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import pride_seebot.resident_healing.Config;
import pride_seebot.resident_healing.component.ModDataComponentTypes;
import pride_seebot.resident_healing.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HerbMixingRecipe extends SpecialCraftingRecipe {

    private static final Ingredient HERBS = Ingredient.ofItems(
        ModItems.GREEN_HERB,
        ModItems.RED_HERB,
        ModItems.BLUE_HERB
    );

    private static final Map<Item, String> HERB_TO_STRING = Util.make(Maps.newHashMap(), map -> {
        map.put(ModItems.GREEN_HERB, "green");
        map.put(ModItems.RED_HERB, "red");
        map.put(ModItems.BLUE_HERB, "blue");
    });

    public HerbMixingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean hasPaper = false;
        boolean hasMixedHerbs = false;
        int greenCount = 0;
        int redCount = 0;
        int blueCount = 0;
        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(Items.PAPER) && Config.requirePaperToCraft) {
                if (hasPaper || hasMixedHerbs) return false;
                hasPaper = true;
            } else if (stack.isOf(ModItems.MIXED_HERBS)) {
                if (hasPaper || hasMixedHerbs) return false;
                List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
                if (contents.size() == 2) {
                    for (String herb : contents) {
                        if (herb.equals("green")) greenCount++;
                        if (herb.equals("red")) redCount++;
                        if (herb.equals("blue")) blueCount++;
                    }
                    hasMixedHerbs = true;
                }
            } else if (stack.isOf(ModItems.GREEN_HERB)) {
                greenCount++;
            } else if (stack.isOf(ModItems.RED_HERB)) {
                redCount++;
            } else if (stack.isOf(ModItems.BLUE_HERB)) {
                blueCount++;
            } else {
                return false;
            }
        }

        int totalHerbs = greenCount + redCount + blueCount;

        if (Config.requirePaperToCraft) {
            if ((!hasPaper && !hasMixedHerbs) || totalHerbs < 2 || totalHerbs > 3) return false;
        } else {
            if (totalHerbs < 2 || totalHerbs > 3) return false;
        }
        
        if (hasMixedHerbs && totalHerbs == 2) return false;
        if (redCount > 1 || blueCount > 1) return false; 
        return true;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        List<String> herbList = new ArrayList<>();
        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.isOf(ModItems.MIXED_HERBS)) {
                    List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
                    for (String herb : contents) {
                        herbList.add(herb);
                    }
                } else if (HERBS.test(stack)) {
                    String herbType = HERB_TO_STRING.get(stack.getItem());
                if (herbType != null) {
                    herbList.add(herbType);
                }
                }
            }
        }

        ItemStack result = new ItemStack(ModItems.MIXED_HERBS);
        result.set(ModDataComponentTypes.HERB_CONTENTS, herbList);
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(ModItems.MIXED_HERBS);
    }

    @Override
    public net.minecraft.recipe.RecipeSerializer<?> getSerializer() {
        return ModRecipes.HERB_MIXING;
    }
}

package pride_seebot.healing_items.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import pride_seebot.healing_items.component.ModDataComponentTypes;

import java.util.List;

public class MixedHerb extends Item {
    public MixedHerb(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        
        // Get the list of herbs from the data component
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);

        if (contents == null || contents.isEmpty()) {
            return TypedActionResult.pass(stack); // Nothing inside? Do nothing.
        }

        if (!world.isClient()) {
            applyMixedEffects(user, contents);
            
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    private void applyMixedEffects(PlayerEntity user, List<String> herbs) {
        float totalHeal = 0;
        boolean clearPoison = false;
        float multiplier = 1.0f;

        for (String herb : herbs) {
            if (herb.equals("green")) totalHeal += 4.0f;
            if (herb.equals("blue")) clearPoison = true;
            if (herb.equals("red")) multiplier = 2.0f; // Red doubles the heal!
        }

        user.heal(totalHeal * multiplier);
        if (clearPoison) {
            user.clearStatusEffects(); // Or use your harmful-only logic from before
        }
    }
}
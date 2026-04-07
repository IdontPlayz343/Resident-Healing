package pride_seebot.resident_healing.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class HerbItem extends Item {
    private final float healAmount = 4.0f; //to be config driven
    private final String color;

    public HerbItem(String color, Settings settings) {
        super(settings); 
        this.color = color;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if ("green".equals(this.color)) {
            tooltip.add(Text.translatable("tooltip.resident_healing.heal_amount", (int) this.healAmount/2).formatted(Formatting.GREEN));
        } else if ("blue".equals(this.color)) {
            tooltip.add(Text.translatable("tooltip.resident_healing.cleanses_poisons").formatted(Formatting.AQUA));
        } else tooltip.add(Text.translatable("tooltip.resident_healing.multiplier_herb").formatted(Formatting.RED));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if ("red".equals(this.color)) return TypedActionResult.pass(itemStack);
        if (world.isClient()) return TypedActionResult.pass(itemStack);
        if ("green".equals(this.color)) {
            if (user.getHealth() < user.getMaxHealth()) {
                user.heal(this.healAmount);
            } else return TypedActionResult.fail(itemStack);
        } else if ("blue".equals(this.color)) {
            List<RegistryEntry<StatusEffect>> badEffects = new ArrayList<>();
            for (StatusEffectInstance instance : user.getStatusEffects()) if (instance.getEffectType() == StatusEffects.POISON || instance.getEffectType() == StatusEffects.NAUSEA) badEffects.add(instance.getEffectType());
            if (!badEffects.isEmpty()) {
                for (RegistryEntry<StatusEffect> effect : badEffects) user.removeStatusEffect(effect);
            } else return TypedActionResult.fail(itemStack);
        }

        if (!user.getAbilities().creativeMode) itemStack.decrement(1);
        return TypedActionResult.success(itemStack);
    }
}

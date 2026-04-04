package pride_seebot.healing_items.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
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
    
    private final float healAmount; 
    private final String useEffect;

    public HerbItem(float healAmount, String useEffect, Settings settings) {
        super(settings); 
        this.useEffect = useEffect;
        this.healAmount = healAmount; 
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        String loreKey = this.getTranslationKey() + ".lore";
        tooltip.add(Text.translatable(loreKey).formatted(Formatting.GRAY));

        if ("heal".equals(this.useEffect)) {
            tooltip.add(Text.translatable("tooltip.healing-items.heal_amount", this.healAmount)
                .formatted(Formatting.GREEN));
        } else if ("clearEffects".equals(this.useEffect)) {
            tooltip.add(Text.translatable("tooltip.healing-items.cleanses")
                .formatted(Formatting.AQUA));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if ("none".equals(this.useEffect)) {
            return TypedActionResult.pass(itemStack);
        }

        if (world.isClient()) {
            return TypedActionResult.pass(itemStack);
        }

        boolean used = false;

        if ("heal".equals(this.useEffect) && user.getHealth() < user.getMaxHealth()) {
            user.heal(this.healAmount);
            used = true;
        } 
        
        else if ("clearEffects".equals(this.useEffect)) {
            List<RegistryEntry<StatusEffect>> badEffects = new ArrayList<>();
            for (StatusEffectInstance instance : user.getStatusEffects()) {
                if (instance.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                    badEffects.add(instance.getEffectType());
                }
            }

            if (!badEffects.isEmpty()) {
                for (RegistryEntry<StatusEffect> effect : badEffects) {
                    user.removeStatusEffect(effect);
                }
                used = true;
            }
        }

        if (used) {
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return TypedActionResult.success(itemStack);
        }

        return TypedActionResult.fail(itemStack);
    }
}
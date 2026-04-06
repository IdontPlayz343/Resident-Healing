package pride_seebot.resident_healing.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import pride_seebot.resident_healing.component.ModDataComponentTypes;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MixedHerb extends Item {
    public MixedHerb(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
        
        if (contents == null || contents.isEmpty()) {
            return Text.translatable("item.resident_healing.mixed_herbs.empty");
        }

        String suffix = contents.stream()
                .map(s -> s.substring(0, 1).toUpperCase())
                .collect(Collectors.joining("+", " (", ")"));

        return Text.translatable(this.getTranslationKey(stack))
                .append(Text.literal(suffix).formatted(Formatting.GRAY));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
        if (contents == null || contents.isEmpty()) return;

        float heal = 0;
        boolean cleanses = false;
        float multiplier = 1.0f;

        for (String herb : contents) {
            if (herb.equals("green")) heal += 4.0f;
            if (herb.equals("blue")) cleanses = true;
            if (herb.equals("red")) multiplier = 2.5f;
        }

        if (heal > 0 && cleanses && multiplier == 2.5f) {
            float total = (heal * multiplier)*2;
            tooltip.add(Text.translatable("tooltip.resident_healing.heal_amount", (int) total/2)
                    .formatted(Formatting.GREEN));
        } else if (heal > 0) {
            float total = heal * multiplier;
            tooltip.add(Text.translatable("tooltip.resident_healing.heal_amount", (int) total/2)
                    .formatted(Formatting.GREEN));
        }

        if (cleanses) {
            if (cleanses && multiplier == 2.5f) {
                tooltip.add(Text.translatable("tooltip.resident_healing.cleanses")
                    .formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("tooltip.resident_healing.cleanses_poisons")
                    .formatted(Formatting.AQUA));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);

        if (contents == null || contents.isEmpty()) {
            return TypedActionResult.pass(stack);
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
            if (herb.equals("red")) multiplier = 2.5f;
        }
        
        if (herbs.contains("green") && herbs.contains("red") && herbs.contains("blue") ) {
            totalHeal = 8.0f;
        }

        user.heal(totalHeal * multiplier);
        if (clearPoison) {
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
            }
        }
    }
}

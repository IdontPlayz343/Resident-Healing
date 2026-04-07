package pride_seebot.resident_healing.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
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

public class MixedHerbs extends Item {
    private final float healAmount = 4.0f; //to be config driven
    private final float multiplierAmount = 2.5f; //to be config driven
    private final float rgbMultiplierAmount = 5.0f; //to be config driven
    public MixedHerbs(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
        if (contents == null || contents.isEmpty()) return Text.translatable("item.resident_healing.mixed_herbs.empty");
        String suffix = contents.stream().map(s -> s.substring(0, 1).toUpperCase()).collect(Collectors.joining("+", " (", ")"));
        return Text.translatable(this.getTranslationKey(stack)).append(Text.literal(suffix).formatted(Formatting.GRAY));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
        if (contents == null || contents.isEmpty()) return;

        float heal = 0;
        float multiplier = 1.0f;

        for (String herb : contents) {
            if (herb.equals("green")) heal += healAmount;
            if (herb.equals("red")) multiplier = multiplierAmount;
        }
        
        if (contents.contains("green") && contents.contains("red") && contents.contains("blue")) multiplier = rgbMultiplierAmount;

        if (heal > 0) {
            float total = heal * multiplier;
            tooltip.add(Text.translatable("tooltip.resident_healing.heal_amount", (int) total/2)
                    .formatted(Formatting.GREEN));
        }

        if (contents.contains("blue")) {
            if (contents.contains("red")) {
                tooltip.add(Text.translatable("tooltip.resident_healing.cleanses").formatted(Formatting.AQUA));
            } else tooltip.add(Text.translatable("tooltip.resident_healing.cleanses_poisons").formatted(Formatting.AQUA));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);

        if (contents == null || contents.isEmpty()) return TypedActionResult.pass(stack);
        if (world.isClient()) return TypedActionResult.pass(stack);
        float totalHeal = 0;
        float multiplier = 1.0f;
        List<RegistryEntry<StatusEffect>> badEffects = new ArrayList<>();
        if (contents.contains("red")) {
            for (StatusEffectInstance instance : user.getStatusEffects()) if (instance.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) badEffects.add(instance.getEffectType());
        } else {
            for (StatusEffectInstance instance : user.getStatusEffects()) if (instance.getEffectType() == StatusEffects.POISON || instance.getEffectType() == StatusEffects.NAUSEA) badEffects.add(instance.getEffectType());
            }

        if (contents.contains("green") && contents.contains("blue")) {
            if (badEffects.isEmpty() && !(user.getHealth() < user.getMaxHealth())) return TypedActionResult.fail(stack);
        } else if (contents.contains("green")) {
            if (!(user.getHealth() < user.getMaxHealth())) return TypedActionResult.fail(stack);
        } else if (contents.contains("blue")) {
            if (badEffects.isEmpty()) return TypedActionResult.fail(stack);
        }

        for (String herb : contents) {
            if (herb.equals("green")) totalHeal += healAmount;
            if (herb.equals("red")) multiplier = multiplierAmount;
        }
        if (contents.contains("green") && contents.contains("red") && contents.contains("blue")) multiplier = rgbMultiplierAmount;
        user.heal(totalHeal * multiplier);
        if (contents.contains("blue")) {
            for (RegistryEntry<StatusEffect> effect : badEffects) user.removeStatusEffect(effect);
        }

        if (!user.getAbilities().creativeMode) stack.decrement(1);
        return TypedActionResult.success(stack, world.isClient());
    }
}
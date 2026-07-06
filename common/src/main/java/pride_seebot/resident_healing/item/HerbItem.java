package pride_seebot.resident_healing.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HerbItem extends Item {
    private final String color;

    public HerbItem(String color, Properties settings) {
        super(settings);
        this.color = color;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if ("green".equals(this.color)) {
            tooltip.accept(Component.translatable("tooltip.resident_healing.heal_amount", (int) 4 / 2).withStyle(ChatFormatting.GREEN));
        } else if ("blue".equals(this.color)) {
            tooltip.accept(Component.translatable("tooltip.resident_healing.cleanses_poisons").withStyle(ChatFormatting.BLUE));
        } else {
            tooltip.accept(Component.translatable("tooltip.resident_healing.multiplier_herb").withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if ("red".equals(this.color)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            if ("green".equals(this.color)) {
                if (player.getHealth() < player.getMaxHealth()) {
                    player.heal(4);
                } else {
                    return InteractionResult.FAIL;
                }
            } else if ("blue".equals(this.color)) {
                List<Holder<MobEffect>> badEffects = new ArrayList<>();
                for (MobEffectInstance instance : player.getActiveEffects()) {
                    if (instance.getEffect() == MobEffects.POISON || instance.getEffect() == MobEffects.NAUSEA) {
                        badEffects.add(instance.getEffect());
                    }
                }

                if (!badEffects.isEmpty()) {
                    for (Holder<MobEffect> effect : badEffects) {
                        player.removeEffect(effect);
                    }
                } else {
                    return InteractionResult.FAIL;
                }
            }

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}

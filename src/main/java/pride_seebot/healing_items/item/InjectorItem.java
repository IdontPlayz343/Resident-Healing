package pride_seebot.healing_items.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import pride_seebot.healing_items.HealingItems;

public class InjectorItem extends Item {
    private final String injectorType;

    public static final double HEALTH_PER_USE = 10.0;
    public static final int MAX_USES = 2;
    
    public static final Identifier STEROID_MODIFIER_ID = Identifier.of(HealingItems.MOD_ID, "steroid_uses");

    public InjectorItem(String injectorType, Settings settings) {
        super(settings); 
        this.injectorType = injectorType;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if ("steroids".equals(this.injectorType)) {
            tooltip.add(Text.translatable("tooltip.healing-items.boost_health")
                .formatted(Formatting.YELLOW));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!"steroids".equals(this.injectorType) || world.isClient()) {
            return TypedActionResult.pass(itemStack);
        }

        EntityAttributeInstance maxHealthAttr = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealthAttr == null) return TypedActionResult.fail(itemStack);

        EntityAttributeModifier currentModifier = maxHealthAttr.getModifier(STEROID_MODIFIER_ID);
        
        double currentUses = (currentModifier != null) ? currentModifier.value() / HEALTH_PER_USE : 0.0;

        if (currentUses < MAX_USES) {
            if (currentModifier != null) {
                maxHealthAttr.removeModifier(STEROID_MODIFIER_ID);
            }

            double newUseCount = currentUses + 1.0;
            double newTotalHealthBoost = newUseCount * HEALTH_PER_USE;

            EntityAttributeModifier newModifier = new EntityAttributeModifier(
                    STEROID_MODIFIER_ID,
                    newTotalHealthBoost,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            maxHealthAttr.addPersistentModifier(newModifier);

            user.setHealth(user.getMaxHealth());
            
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

            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack);
        } else {
            user.sendMessage(Text.translatable("message.healing-items.max_steroids")
                .formatted(Formatting.RED), true);
            return TypedActionResult.fail(itemStack);
        }
    }
}
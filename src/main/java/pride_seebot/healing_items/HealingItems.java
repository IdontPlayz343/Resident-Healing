package pride_seebot.healing_items;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import pride_seebot.healing_items.component.ModDataComponentTypes;
import pride_seebot.healing_items.item.InjectorItem;
import pride_seebot.healing_items.item.ModItems;
import pride_seebot.healing_items.recipe.ModRecipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealingItems implements ModInitializer {
    public static final String MOD_ID = "healing-items";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModDataComponentTypes.registerDataComponentTypes();
        ModItems.registerModItems();
        ModRecipes.registerRecipes();

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            EntityAttributeInstance oldAttr = oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance newAttr = newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (oldAttr != null && newAttr != null) {
                EntityAttributeModifier modifier = oldAttr.getModifier(InjectorItem.STEROID_MODIFIER_ID);
                
                if (modifier != null) {
                    newAttr.addPersistentModifier(modifier);
                    if (!alive) {
                        newPlayer.setHealth(newPlayer.getMaxHealth());
                    }
                }
            }
        });
    }
}
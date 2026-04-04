package pride_seebot.healing_items;

import net.fabricmc.api.ModInitializer;
import pride_seebot.healing_items.component.ModDataComponentTypes;
import pride_seebot.healing_items.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealingItems implements ModInitializer {
	public static final String MOD_ID = "healing-items";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
    public void onInitialize() {
        ModDataComponentTypes.registerDataComponentTypes();
        ModItems.registerModItems();
    }
}
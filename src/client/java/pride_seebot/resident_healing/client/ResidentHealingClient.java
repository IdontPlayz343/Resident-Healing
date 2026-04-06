package pride_seebot.resident_healing.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import pride_seebot.resident_healing.component.ModDataComponentTypes;
import pride_seebot.resident_healing.item.ModItems;

import java.util.List;

public class ResidentHealingClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        // Register the color logic for the Mixed Herb item
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            // Get the list of herbs from the stack's data component
            List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);

            // Layer 0 is the paper base (return -1 for no tint/white)
            if (tintIndex == 0) return -1;

            // If there's no data or the layer index is out of bounds, make it invisible
            if (contents == null || (tintIndex - 1) >= contents.size()) {
                return 0x00FFFFFF; // Transparent
            }

            // Get the herb string for this specific layer (tintIndex 1 = contents index 0)
            String herbType = contents.get(tintIndex - 1);

            // Return the hex color based on the herb type
            return switch (herbType) {
                case "green" -> 0xFF55FF55; // Bright Green
                case "red"   -> 0xFFFF5555; // Bright Red
                case "blue"  -> 0xFF5555FF; // Bright Blue
                default      -> -1;       // Default white
            };
        }, ModItems.MIXED_HERBS);
    }
}

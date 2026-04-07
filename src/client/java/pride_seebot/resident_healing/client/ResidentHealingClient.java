package pride_seebot.resident_healing.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import pride_seebot.resident_healing.component.ModDataComponentTypes;
import pride_seebot.resident_healing.item.ModItems;

import java.util.List;

public class ResidentHealingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS);
            if (tintIndex == 0) return -1;
            if (contents == null || (tintIndex - 1) >= contents.size()) {
                return 0x00FFFFFF;
            }
            String herbType = contents.get(tintIndex - 1);
            return switch (herbType) {
                case "green" -> 0xFF55FF55;
                case "red" -> 0xFFFF5555;
                case "blue" -> 0xFF5555FF;
                default -> -1;
            };
        }, ModItems.MIXED_HERBS);
    }
}

package pride_seebot.resident_healing.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.color.item.ItemTintSources;
import pride_seebot.resident_healing.Constants;
import pride_seebot.resident_healing.client.color.HerbTintSource;

public class ResidentHealingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTintSources.ID_MAPPER.put(Constants.id("herb_tint"), HerbTintSource.MAP_CODEC);
    }
}
package pride_seebot.resident_healing.init;

import net.minecraft.world.item.Item;
import pride_seebot.resident_healing.item.HerbItem;
import pride_seebot.resident_healing.services.Services;
import pride_seebot.resident_healing.services.util.RegistryHandle;

public final class ModItems {
    private ModItems() {}

    public static void load() {}

    public static final RegistryHandle<Item> GREEN_HERB = Services.REGISTRY.registerItem("green_herb", properties -> new HerbItem("green", properties.stacksTo(1)));
    public static final RegistryHandle<Item> RED_HERB = Services.REGISTRY.registerItem("red_herb", properties -> new HerbItem("red", properties.stacksTo(1)));
    public static final RegistryHandle<Item> BLUE_HERB = Services.REGISTRY.registerItem("blue_herb", properties -> new HerbItem("blue", properties.stacksTo(1)));
}

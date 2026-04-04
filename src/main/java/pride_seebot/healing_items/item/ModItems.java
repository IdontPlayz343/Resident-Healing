package pride_seebot.healing_items.item;

import pride_seebot.healing_items.HealingItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item GREEN_HERB = registerItem("green_herb", 
        new HerbItem(4.0f, "heal", new Item.Settings().maxCount(1)));
    public static final Item RED_HERB = registerItem("red_herb", 
        new HerbItem(0.0f, "none", new Item.Settings().maxCount(1)));
    public static final Item BLUE_HERB = registerItem("blue_herb", 
        new HerbItem(2.0f, "clearEffects", new Item.Settings().maxCount(1)));
    public static final Item MIXED_HERBS = registerItem("mixed_herbs", 
        new MixedHerb(new Item.Settings().maxCount(1)));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HealingItems.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> {
            itemGroup.add(ModItems.GREEN_HERB);
            itemGroup.add(ModItems.BLUE_HERB);
            itemGroup.add(ModItems.RED_HERB);
        });
        HealingItems.LOGGER.info("Registering Mod Items for " + HealingItems.MOD_ID);
    }
}

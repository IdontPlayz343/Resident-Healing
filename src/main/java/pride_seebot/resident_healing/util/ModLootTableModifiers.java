package pride_seebot.resident_healing.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import pride_seebot.resident_healing.item.ModItems;

public class ModLootTableModifiers {
    private static float herbChestChance = 0.7f; //to be config driven
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            if (LootTables.VILLAGE_PLAINS_CHEST.equals(key) || LootTables.VILLAGE_DESERT_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_SAVANNA_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_TAIGA_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_SNOWY_HOUSE_CHEST.equals(key) || LootTables.VILLAGE_TEMPLE_CHEST.equals(key) || LootTables.PILLAGER_OUTPOST_CHEST.equals(key) || LootTables.WOODLAND_MANSION_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f)) 
                    .conditionally(RandomChanceLootCondition.builder(herbChestChance))
                    .with(ItemEntry.builder(ModItems.GREEN_HERB).weight(50)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))))
                    .with(ItemEntry.builder(ModItems.RED_HERB).weight(20)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))))
                    .with(ItemEntry.builder(ModItems.BLUE_HERB).weight(5)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))));
                tableBuilder.pool(poolBuilder.build());
            } else if (LootTables.VILLAGE_TOOLSMITH_CHEST.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f)) 
                    .conditionally(RandomChanceLootCondition.builder(0.2f))
                    .with(ItemEntry.builder(ModItems.EMPTY_INJECTOR)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))));
                    tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
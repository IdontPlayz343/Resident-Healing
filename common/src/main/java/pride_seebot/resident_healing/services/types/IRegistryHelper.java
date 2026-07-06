package pride_seebot.resident_healing.services.types;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import pride_seebot.resident_healing.Constants;
import pride_seebot.resident_healing.services.util.RegistryHandle;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryHelper {
    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    // Add support for data components
    <T> RegistryHandle<DataComponentType<T>> registerDataComponent(String name, Supplier<DataComponentType<T>> component);

    static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }
}
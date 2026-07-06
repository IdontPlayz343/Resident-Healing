package pride_seebot.resident_healing.services;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import pride_seebot.resident_healing.Constants;
import pride_seebot.resident_healing.services.types.IRegistryHelper;
import pride_seebot.resident_healing.services.util.RegistryHandle;

import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(BuiltInRegistries.ITEM, id, item.apply(new Item.Properties().setId(key)));

        return new RegistryHandle<T>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T> RegistryHandle<DataComponentType<T>> registerDataComponent(String name, Supplier<DataComponentType<T>> component) {
        Identifier id = Constants.id(name);
        DataComponentType<T> registered = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, component.get());

        return new RegistryHandle<DataComponentType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public DataComponentType<T> get() {
                return registered;
            }
        };
    }
}
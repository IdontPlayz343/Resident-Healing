package pride_seebot.resident_healing.services;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import pride_seebot.resident_healing.Constants;
import pride_seebot.resident_healing.services.types.IRegistryHelper;
import pride_seebot.resident_healing.services.util.RegistryHandle;

import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    // Add the components deferred register
    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        COMPONENTS.register(eventBus); // Register components to the event bus
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        Identifier id = Constants.id(name);
        DeferredItem<T> deferredItem = ITEMS.registerItem(name, item);
        return new RegistryHandle<T>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return deferredItem.get();
            }
        };
    }

    @Override
    public <T> RegistryHandle<DataComponentType<T>> registerDataComponent(String name, Supplier<DataComponentType<T>> component) {
        Identifier id = Constants.id(name);
        DeferredHolder<DataComponentType<?>, DataComponentType<T>> deferredHolder = COMPONENTS.register(name, component);
        return new RegistryHandle<DataComponentType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public DataComponentType<T> get() {
                return deferredHolder.get();
            }
        };
    }
}
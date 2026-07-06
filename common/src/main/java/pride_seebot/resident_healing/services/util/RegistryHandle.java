package pride_seebot.resident_healing.services.util;

import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public interface RegistryHandle<T> extends Supplier<T> {
    Identifier id();
}

package pride_seebot.resident_healing.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import pride_seebot.resident_healing.services.Services;
import pride_seebot.resident_healing.services.util.RegistryHandle;

import java.util.List;

public class ModDataComponentTypes {

    public static final RegistryHandle<DataComponentType<List<String>>> HERB_CONTENTS = Services.REGISTRY.registerDataComponent("herb_contents",
            () -> DataComponentType.<List<String>>builder().persistent(Codec.STRING.listOf()).build()
    );

    public static void load() {}
}
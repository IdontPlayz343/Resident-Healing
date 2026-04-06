package pride_seebot.resident_healing.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pride_seebot.resident_healing.ResidentHealing;
import com.mojang.serialization.Codec;
import java.util.List;

public class ModDataComponentTypes {

    // This component stores a List of Strings (the herb types)
    public static final ComponentType<List<String>> HERB_CONTENTS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ResidentHealing.MOD_ID, "herb_contents"),
            ComponentType.<List<String>>builder().codec(Codec.STRING.listOf()).build()
    );

    public static void registerDataComponentTypes() {
        ResidentHealing.LOGGER.info("Registering Data Component Types for " + ResidentHealing.MOD_ID);
    }
}

package pride_seebot.resident_healing.services;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import pride_seebot.resident_healing.services.types.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.getCurrent().isProduction();
    }
}
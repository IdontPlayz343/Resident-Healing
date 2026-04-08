package pride_seebot.resident_healing;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {
    @Entry(category = "healing", min = 1.0f, max = 20.0f) 
    public static float greenHerbHeal = 4.0f;

    @Entry(category = "healing", min = 1.0f, max = 10.0f) 
    public static float redHerbMultiplier = 2.5f;

    @Entry(category = "healing", min = 1.0f, max = 10.0f) 
    public static float rgbMultiplier = 5.0f;

    @Entry(category = "crafting") 
    public static boolean requirePaperToCraft = true;

    //@Entry(category = "looting", min = 0f, max = 1f) 
    //public static float herbChestChance = 0.7f;
}

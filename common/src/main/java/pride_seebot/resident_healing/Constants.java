package pride_seebot.resident_healing;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final String MOD_ID = "resident_healing";
	public static final String MOD_NAME = "Resident Healing";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}
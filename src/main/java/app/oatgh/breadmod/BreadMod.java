package app.oatgh.breadmod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "breadmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("minecraft", "water_bowl")
				, BreadModItems.WATER_BOWL);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bread_powder")
				, BreadModItems.BREAD_POWDER);
		LOGGER.info("Bread mod is living!");
	}

}
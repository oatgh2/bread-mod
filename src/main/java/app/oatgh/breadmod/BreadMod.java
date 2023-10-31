package app.oatgh.breadmod;

import app.oatgh.breadmod.items.WaterBowl;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("breadmod");
	public static Item CUSTOM_ITEM = new Item(new FabricItemSettings());

	public  static Item WATER_BOWL = new WaterBowl(new FabricItemSettings().maxCount(1));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("tutorial", "custom_item"), CUSTOM_ITEM);
		Registry.register(Registry.ITEM, new Identifier("minecraft", "water_bowl"), WATER_BOWL);
		LOGGER.info("Hello Fabric world!");
	}
}
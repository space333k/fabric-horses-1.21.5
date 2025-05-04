package net.space333.horses;

import net.fabricmc.api.ModInitializer;
import net.space333.horses.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Horses implements ModInitializer {
	public static final String MOD_ID = "horses";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Horses Initialized");
		ModConfig.init();
	}
}
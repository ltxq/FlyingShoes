package com.flightboots;

import com.flightboots.component.ModDataComponents;
import com.flightboots.handler.FlightBootsTickHandler;
import com.flightboots.item.ModItems;
import com.flightboots.recipe.ModRecipeSerializers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightBootsMod implements ModInitializer {
    public static final String MOD_ID = "flightboots";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModDataComponents.initialize();
        ModItems.initialize();
        ModRecipeSerializers.initialize();
        FlightBootsTickHandler.register();
        LOGGER.info("Flight Boots Mod initialized for Minecraft 26.3");
    }
}

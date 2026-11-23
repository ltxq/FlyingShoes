package com.flightboots.item;

import com.flightboots.FlightBootsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final ResourceKey<Item> FLIGHT_BOOTS_KEY = ResourceKey.create(
        Registries.ITEM,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "flight_boots")
    );

    public static final Item FLIGHT_BOOTS = Registry.register(
        BuiltInRegistries.ITEM,
        FLIGHT_BOOTS_KEY,
        new FlightBootsItem(new Item.Properties().setId(FLIGHT_BOOTS_KEY))
    );

    public static void initialize() {}
}

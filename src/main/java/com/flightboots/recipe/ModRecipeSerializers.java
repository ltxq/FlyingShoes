package com.flightboots.recipe;

import com.flightboots.FlightBootsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<FlightBootsRecipe> FLIGHT_BOOTS_RECIPE = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "flight_boots_crafting"),
        FlightBootsRecipe.SERIALIZER
    );

    public static final RecipeSerializer<FlightBootsUpgradeRecipe> FLIGHT_BOOTS_UPGRADE_RECIPE = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "flight_boots_upgrade"),
        FlightBootsUpgradeRecipe.SERIALIZER
    );

    public static final RecipeSerializer<FlightBootsUncraftRecipe> FLIGHT_BOOTS_UNCRAFT_RECIPE = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "flight_boots_uncraft"),
        FlightBootsUncraftRecipe.SERIALIZER
    );

    public static void initialize() {}
}

package com.flightboots.component;

import com.flightboots.FlightBootsMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ModDataComponents {
    public static final DataComponentType<Integer> TIER = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "tier"),
        DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build()
    );

    public static final DataComponentType<ItemStack> ORIGINAL_BOOTS = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "original_boots"),
        DataComponentType.<ItemStack>builder()
            .persistent(ItemStack.CODEC)
            .networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    public static final DataComponentType<ItemStack> ORIGINAL_ELYTRA = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "original_elytra"),
        DataComponentType.<ItemStack>builder()
            .persistent(ItemStack.CODEC)
            .networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    public static final DataComponentType<ItemStack> REFUND_ELYTRA = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "refund_elytra"),
        DataComponentType.<ItemStack>builder()
            .persistent(ItemStack.CODEC)
            .networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    public static final DataComponentType<ItemStack> REFUND_STAR = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(FlightBootsMod.MOD_ID, "refund_star"),
        DataComponentType.<ItemStack>builder()
            .persistent(ItemStack.CODEC)
            .networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    public static void initialize() {}
}

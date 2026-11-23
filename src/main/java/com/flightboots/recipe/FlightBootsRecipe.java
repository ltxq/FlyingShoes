package com.flightboots.recipe;

import com.flightboots.component.ModDataComponents;
import com.flightboots.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;

public class FlightBootsRecipe extends CustomRecipe {
    public static final MapCodec<FlightBootsRecipe> CODEC = MapCodec.unit(FlightBootsRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, FlightBootsRecipe> STREAM_CODEC = StreamCodec.unit(new FlightBootsRecipe());
    public static final RecipeSerializer<FlightBootsRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    public FlightBootsRecipe() {
        super();
    }

    private static boolean isBaseBoots(ItemStack stack) {
        if (stack.is(ModItems.FLIGHT_BOOTS)) return false;
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        return equippable != null && equippable.slot() == EquipmentSlot.FEET;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack boots = ItemStack.EMPTY;
        ItemStack elytra = ItemStack.EMPTY;
        int count = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                count++;
                if (stack.is(Items.ELYTRA)) {
                    if (!elytra.isEmpty()) return false;
                    elytra = stack;
                } else if (isBaseBoots(stack)) {
                    if (!boots.isEmpty()) return false;
                    boots = stack;
                } else {
                    return false;
                }
            }
        }
        return count == 2 && !boots.isEmpty() && !elytra.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack boots = ItemStack.EMPTY;
        ItemStack elytra = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (isBaseBoots(stack) && boots.isEmpty()) {
                    boots = stack;
                } else if (stack.is(Items.ELYTRA) && elytra.isEmpty()) {
                    elytra = stack;
                }
            }
        }

        if (boots.isEmpty() || elytra.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = new ItemStack(ModItems.FLIGHT_BOOTS);
        result.applyComponents(boots.getComponents());

        // Retain original boots model so it keeps the original appearance (leather, diamond, iron, etc.)
        Identifier modelId = boots.get(DataComponents.ITEM_MODEL);
        if (modelId == null) {
            modelId = BuiltInRegistries.ITEM.getKey(boots.getItem());
        }
        result.set(DataComponents.ITEM_MODEL, modelId);

        result.set(ModDataComponents.TIER, 1);
        result.set(ModDataComponents.ORIGINAL_BOOTS, boots.copyWithCount(1));
        result.set(ModDataComponents.ORIGINAL_ELYTRA, elytra.copyWithCount(1));
        return result;
    }

    @Override
    public RecipeSerializer<FlightBootsRecipe> getSerializer() {
        return SERIALIZER;
    }
}

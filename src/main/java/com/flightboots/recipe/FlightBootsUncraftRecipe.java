package com.flightboots.recipe;

import com.flightboots.component.ModDataComponents;
import com.flightboots.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FlightBootsUncraftRecipe extends CustomRecipe {
    public static final MapCodec<FlightBootsUncraftRecipe> CODEC = MapCodec.unit(FlightBootsUncraftRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, FlightBootsUncraftRecipe> STREAM_CODEC = StreamCodec.unit(new FlightBootsUncraftRecipe());
    public static final RecipeSerializer<FlightBootsUncraftRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    public FlightBootsUncraftRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int count = 0;
        ItemStack boots = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                count++;
                if (stack.is(ModItems.FLIGHT_BOOTS)) {
                    boots = stack;
                }
            }
        }
        return count == 1 && !boots.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack boots = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && stack.is(ModItems.FLIGHT_BOOTS)) {
                boots = stack;
                break;
            }
        }

        if (boots.isEmpty()) return ItemStack.EMPTY;

        ItemStack originalBoots = boots.get(ModDataComponents.ORIGINAL_BOOTS);
        ItemStack result = (originalBoots != null && !originalBoots.isEmpty()) ? originalBoots.copy() : new ItemStack(Items.IRON_BOOTS);
        result.set(DataComponents.DAMAGE, boots.getDamageValue());

        int tier = boots.getOrDefault(ModDataComponents.TIER, 1);
        if (tier == 2) {
            result.set(ModDataComponents.REFUND_STAR, new ItemStack(Items.NETHER_STAR));
        }

        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && stack.is(ModItems.FLIGHT_BOOTS)) {
                ItemStack originalElytra = stack.get(ModDataComponents.ORIGINAL_ELYTRA);
                ItemStack elytraToRefund = (originalElytra != null && !originalElytra.isEmpty()) ? originalElytra.copy() : new ItemStack(Items.ELYTRA);
                remainders.set(i, elytraToRefund);
                break;
            }
        }
        return remainders;
    }

    @Override
    public RecipeSerializer<FlightBootsUncraftRecipe> getSerializer() {
        return SERIALIZER;
    }
}

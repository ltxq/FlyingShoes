package com.flightboots.recipe;

import com.flightboots.component.ModDataComponents;
import com.flightboots.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FlightBootsUpgradeRecipe extends CustomRecipe {
    public static final MapCodec<FlightBootsUpgradeRecipe> CODEC = MapCodec.unit(FlightBootsUpgradeRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, FlightBootsUpgradeRecipe> STREAM_CODEC = StreamCodec.unit(new FlightBootsUpgradeRecipe());
    public static final RecipeSerializer<FlightBootsUpgradeRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    public FlightBootsUpgradeRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack boots = ItemStack.EMPTY;
        ItemStack star = ItemStack.EMPTY;
        int count = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                count++;
                if (stack.is(ModItems.FLIGHT_BOOTS) && stack.getOrDefault(ModDataComponents.TIER, 1) == 1) {
                    if (!boots.isEmpty()) return false;
                    boots = stack;
                } else if (stack.is(Items.NETHER_STAR)) {
                    if (!star.isEmpty()) return false;
                    star = stack;
                } else {
                    return false;
                }
            }
        }
        return count == 2 && !boots.isEmpty() && !star.isEmpty();
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

        ItemStack result = boots.copyWithCount(1);
        result.set(ModDataComponents.TIER, 2);
        return result;
    }

    @Override
    public RecipeSerializer<FlightBootsUpgradeRecipe> getSerializer() {
        return SERIALIZER;
    }
}

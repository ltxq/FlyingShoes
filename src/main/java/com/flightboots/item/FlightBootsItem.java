package com.flightboots.item;

import com.flightboots.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class FlightBootsItem extends Item {
    public FlightBootsItem(Properties properties) {
        super(properties
            .stacksTo(1)
            .durability(400)
            .equippable(EquipmentSlot.FEET)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        int tier = stack.getOrDefault(ModDataComponents.TIER, 1);
        tooltip.accept(Component.literal("Tier " + tier).withStyle(ChatFormatting.GRAY));
        if (tier == 1) {
            tooltip.accept(Component.literal("50% Speed - 1 dur/s").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.accept(Component.literal("100% Speed - 30% Durability Save").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, display, tooltip, flag);
    }
}

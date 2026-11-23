package com.flightboots.handler;

import com.flightboots.component.ModDataComponents;
import com.flightboots.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlightBootsTickHandler {
    private static final Set<UUID> FLIGHT_ACTIVE = new HashSet<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(FlightBootsTickHandler::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            handlePlayerRefunds(player);
            handlePlayerFlight(player);
        }
    }

    private static void handlePlayerRefunds(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            processRefundOnStack(player, stack);
        }
        if (player.containerMenu != null) {
            ItemStack cursor = player.containerMenu.getCarried();
            processRefundOnStack(player, cursor);
        }
    }

    private static void processRefundOnStack(ServerPlayer player, ItemStack stack) {
        if (stack.isEmpty()) return;

        ItemStack refundElytra = stack.get(ModDataComponents.REFUND_ELYTRA);
        if (refundElytra != null && !refundElytra.isEmpty()) {
            stack.remove(ModDataComponents.REFUND_ELYTRA);
            giveOrDrop(player, refundElytra.copy());
        }

        ItemStack refundStar = stack.get(ModDataComponents.REFUND_STAR);
        if (refundStar != null && !refundStar.isEmpty()) {
            stack.remove(ModDataComponents.REFUND_STAR);
            giveOrDrop(player, refundStar.copy());
        }
    }

    private static void giveOrDrop(ServerPlayer player, ItemStack toGive) {
        if (toGive == null || toGive.isEmpty()) return;
        player.getInventory().placeItemBackInInventory(toGive, Prediction.SERVER_ONLY);
        if (player.containerMenu != null) {
            player.containerMenu.broadcastChanges();
        }
        player.inventoryMenu.broadcastChanges();
    }

    private static void handlePlayerFlight(ServerPlayer player) {
        UUID uuid = player.getUUID();

        if (player.isCreative() || player.isSpectator()) {
            FLIGHT_ACTIVE.remove(uuid);
            return;
        }

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        boolean hasBoots = boots.is(ModItems.FLIGHT_BOOTS);

        if (!hasBoots) {
            if (FLIGHT_ACTIVE.remove(uuid)) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.getAbilities().setFlyingSpeed(0.05f);
                player.onUpdateAbilities();
            }
            return;
        }

        FLIGHT_ACTIVE.add(uuid);

        int tier = boots.getOrDefault(ModDataComponents.TIER, 1);
        float targetSpeed = (tier == 2) ? 0.05f : 0.025f;

        boolean syncNeeded = false;
        if (!player.getAbilities().mayfly) {
            player.getAbilities().mayfly = true;
            syncNeeded = true;
        }
        if (player.getAbilities().getFlyingSpeed() != targetSpeed) {
            player.getAbilities().setFlyingSpeed(targetSpeed);
            syncNeeded = true;
        }
        if (syncNeeded) {
            player.onUpdateAbilities();
        }

        if (player.getAbilities().flying) {
            player.resetFallDistance();

            if (player.tickCount % 20 == 0) {
                int maxDamage = boots.getMaxDamage();
                int currentDamage = boots.getDamageValue();
                int remaining = maxDamage - currentDamage;

                boolean consume = true;
                if (tier == 2 && player.getRandom().nextFloat() < 0.30f) {
                    consume = false;
                }

                if (consume) {
                    if (remaining <= 1) {
                        breakBoots(player, boots);
                    } else {
                        boots.hurtAndBreak(1, (ServerLevel) player.level(), player, brokenStack -> breakBoots(player, boots));
                    }
                }
            }
        } else {
            // Apply fall damage when landing if player was falling while wearing boots
            if (player.onGround() && player.fallDistance > 3.0) {
                float fallDamage = (float) (player.fallDistance - 3.0);
                player.hurtServer((ServerLevel) player.level(), player.damageSources().fall(), fallDamage);
                player.resetFallDistance();
            }
        }
    }

    private static void breakBoots(ServerPlayer player, ItemStack boots) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK.value(), SoundSource.PLAYERS, 0.8f, 1.0f);
        player.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);

        ItemStack originalElytra = boots.get(ModDataComponents.ORIGINAL_ELYTRA);
        ItemStack elytraToDrop = (originalElytra != null && !originalElytra.isEmpty()) ? originalElytra.copy() : new ItemStack(Items.ELYTRA);
        elytraToDrop.setDamageValue(elytraToDrop.getMaxDamage() - 1);

        giveOrDrop(player, elytraToDrop);

        int tier = boots.getOrDefault(ModDataComponents.TIER, 1);
        if (tier == 2) {
            ItemStack star = new ItemStack(Items.NETHER_STAR);
            giveOrDrop(player, star);
        }

        FLIGHT_ACTIVE.remove(player.getUUID());
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.getAbilities().setFlyingSpeed(0.05f);
        player.onUpdateAbilities();
    }
}

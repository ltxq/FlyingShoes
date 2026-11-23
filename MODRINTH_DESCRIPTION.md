# Flying shoes!

A lightweight, vanilla-friendly flight mod for Fabric that introduces true creative-style flight through balanced, sensible boots.

---

## Overview

Flying shoes! allows players to combine any pair of boots with an Elytra to enable creative-style flight. It retains the original armor protection, enchantments, and durability of the base boots, while consuming durability only when actively flying. The mod is designed with fair mechanics: items are never permanently lost upon breaking or disassembling.

---

## Features

### Creative-Style Flight
- Equip the boots in your boots slot to fly just like in Creative mode (double-tap jump to toggle flight).
- Durability is consumed only while actively flying (when double-tapped into flight mode), not while walking or standing on the ground.
- While flying, fall distance is safely reset to prevent desync fall damage. When falling while not in flight mode, normal fall damage applies.

### Tiers

- **Tier 1 (Base Flight):**
  - Flight Speed: 50% of Creative flight speed (0.025f).
  - Durability Consumption: Consumes 1 durability per second of active flight.
  - Appearance: Retains the exact model, texture, dyed color, and armor trim of the original base boots.

- **Tier 2 (Upgraded Flight):**
  - Flight Speed: 100% of Creative flight speed (0.05f).
  - Durability Consumption: 30% chance to negate durability consumption each second.
  - Upgraded via Crafting Table using a Nether Star.

---

## Crafting Recipes

### 1. Crafting Tier 1 Flying Shoes
Place **any Boots** (Leather, Iron, Golden, Diamond, Netherite, or Chainmail) and **1 Elytra** anywhere in a Crafting Table.
- **Input:** Any Boots + 1 Elytra
- **Output:** Tier 1 Flying shoes!
- **Note:** Preserves the base boots' enchantments, armor points, armor toughness, custom name, dyed color, and armor trims.

### 2. Upgrading to Tier 2
Combine your **Tier 1 Flying shoes!** with **1 Nether Star** in a Crafting Table.
- **Input:** Tier 1 Flying shoes! + 1 Nether Star
- **Output:** Tier 2 Flying shoes!

---

## Disassembly (Uncrafting)

Want your Elytra or original boots back?
- Place your intact Flying shoes! into any crafting grid.
- Take the original boots from the output slot.
- The original Elytra will immediately appear in the crafting grid slot where the shoes were placed.
- If upgrading to Tier 2, the Nether Star is safely returned directly to your inventory (or safely dropped at your feet if inventory is full).

---

## Breakage Protection

If the boots reach 0 durability while flying:
- The boots break with the vanilla item break sound.
- A **Broken Elytra** (remaining durability = 1) is automatically returned to your inventory or safely dropped at your feet.
- If it was a Tier 2 pair, the Nether Star is also returned.
- Flight mode immediately disables so you fall naturally.

---

## Installation & Requirements

- **Minecraft Version:** 26.3
- **Mod Loader:** Fabric Loader (>= 0.20.1)
- **Dependencies:** Fabric API

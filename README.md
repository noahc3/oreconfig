# OreTweaker
A Minecraft Forge mod to disable existing ore generation features and add your own.

Supports Minecraft 1.16.4.

Download on CurseForge: https://www.curseforge.com/minecraft/mc-mods/oretweaker

## Configuration

All configuration for this mod is stored in `config/oretweaker-common.toml`.

### Properties

#### Disable Ore Generation

- **Disable Ores**: a string array of blocks to remove ore generation features for.

The "Disable Ores" array is a string array which stores a list of blocks to disable existing (non-custom) ore generation for. For example, to disable all ore generation for valuable overworld ores in vanilla Minecraft:

`"Disable Ores" = ["minecraft:coal_ore", "minecraft:iron_ore", "minecraft:gold_ore", "minecraft:redstone_ore", "minecraft:diamond_ore", "minecraft:lapis_ore", "minecraft:emerald_ore"]`

Note things like andesite, diorite, granite, as well as underground dirt and gravel are also "ores". You can similarly disable those by adding them to that list.

This can also disable ore generation for modded ores, provided those mods use the standard OreFeature or ReplaceBlockFeature world generation features for their ore generation. Simply supply the ID of the modded ore blocks to disable.

This option only disables ore generation not configured by this mod (ex. vanilla/modded ore generation). If you want to define a custom ore that "overrides" the existing ore generation, you need to disable the existing ore generation here first.

#### Custom Ore Generation

Before defining new ores, if you don't want vanilla ore generation to also take place, make sure you disable the ore before defining custom generation for that ore. Otherwise, both your custom generation and the vanilla ore generation will occur.

- **\[\["Custom Ore"\]\]**
    - **Ore Name**: The block ID to use as the ore (ex. minecraft:iron_ore).
    - **Filler Name**: The block ID to replace in the world (ex. minecraft:stone).
    - **Max Vein Size**: The maximum vein sizes to create (0-64).
    - **Max Vein Level**: The maximum y-level to try spawning veins at (1-256).
    - **Spawn Rate**: The vein spawn frequency (-10-128) (I have no idea what negative values do but they are specified as accepted by the game).
    

Below "Disable Ores", define a "Custom Ore" block for each custom ore generation feature you want to add. For example,

```
[["Custom Ore"]]
	"Ore Name" = "minecraft:coal_ore"
    "Filler Name" = "minecraft:stone"
	"Max Vein Size" = 48
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:iron_ore"
    "Filler Name" = "minecraft:stone"
	"Max Vein Size" = 48
	"Max Vein Level" = 256
	"Spawn Rate" = 48
    
[["Custom Ore"]]
	"Ore Name" = "minecraft:gold_ore"
    "Filler Name" = "minecraft:stone"
	"Max Vein Size" = 48
	"Max Vein Level" = 256
	"Spawn Rate" = 48
```

The above configuration will add custom ore generation for coal (minecraft:coal_ore), iron (minecraft:iron_ore) and gold (minecraft:gold_ore). These ores will replace stone blocks (minecraft:stone), generate in veins up to 48 blocks in size up to y level 256 (world height limit), with a spawn frequency of 48 (the ore vein will try to generate in stone 48 times per chunk).

You can replace things other than stone, like dirt or grass. These configurations also work across dimensions, so you can replace netherrack or end stone, and ores can spawn in the Nether or the End.

You can create ore generation for blocks that aren't normally ores, like iron blocks, logs, crafting tables, as well as modded blocks. Just supply the correct block ID for the "Ore Name" field.


#### Example Configuration

The following configuration:

```
"Disable Ores" = ["minecraft:coal_ore", "minecraft:iron_ore", "minecraft:gold_ore", "minecraft:redstone_ore", "minecraft:diamond_ore", "minecraft:lapis_ore", "minecraft:emerald_ore"]

[["Custom Ore"]]
	"Ore Name" = "minecraft:coal_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:iron_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:gold_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:redstone_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:diamond_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:lapis_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48

[["Custom Ore"]]
	"Ore Name" = "minecraft:emerald_ore"
	"Max Vein Size" = 48
	"Filler Name" = "minecraft:stone"
	"Max Vein Level" = 256
	"Spawn Rate" = 48
```

has fun effects in amplified world types:

![](https://i.imgur.com/Ecf1mTY.png)

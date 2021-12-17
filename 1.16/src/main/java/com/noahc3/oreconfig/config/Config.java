package com.noahc3.oreconfig.config;

import com.noahc3.oreconfig.utility.Logger;
import com.noahc3.oreconfig.world.Features;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private static boolean configLocked = false;

    public static boolean debugOutput;
    public static List<Block> disabledOres;
    public static List<CustomOreConfig.CustomOre> customOres;

    public static List<Block> trackedDisabledOres;

    public static void bake() {
        if (!configLocked) {
            debugOutput = ConfigSpec.COMMON.debugOutput.get();

            disabledOres = new ArrayList<>();
            customOres = new ArrayList<>();
            if (debugOutput) trackedDisabledOres = new ArrayList<>();

            for(String k : ConfigSpec.COMMON.disableOres.get()) {
                disabledOres.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(k)));
                if (debugOutput) trackedDisabledOres.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(k)));
            }

            customOres.addAll(ConfigSpec.oreConfigs.customOres);

            Features.registerCustomOreGens();

            if (debugOutput) {
                Logger.debug("Configuration loaded.");
                Logger.debug("Got disabled ores:");
                for (Block k : disabledOres) {
                    Logger.debug("    " + k.getRegistryName());
                }

                Logger.debug("Got custom ores:");
                for (CustomOreConfig.CustomOre k : customOres) {
                    Logger.debug("    " + k.getOreRegistryName() + "/" + k.getFillerRegistryName());
                }
            }

            configLocked = true;
        }
    }
}

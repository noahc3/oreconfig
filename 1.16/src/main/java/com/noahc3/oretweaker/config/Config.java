package com.noahc3.oretweaker.config;

import com.noahc3.oretweaker.utility.Logger;
import com.noahc3.oretweaker.world.Features;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private static boolean configLocked = false;

    public static List<Block> disabledOres;
    public static List<CustomOreConfig.CustomOre> customOres;

    public static void bake() {
        if (!configLocked) {
            disabledOres = new ArrayList<>();
            customOres = new ArrayList<>();

            for(String k : ConfigSpec.COMMON.disableOres.get()) {
                disabledOres.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(k)));
            }

            customOres.addAll(ConfigSpec.oreConfigs.customOres);

            Features.registerCustomOreGens();

            configLocked = true;
        }
    }
}

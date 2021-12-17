package com.noahc3.oreconfig;

import com.electronwill.nightconfig.core.Config;
import com.noahc3.oreconfig.config.ConfigSpec;
import com.noahc3.oreconfig.intercepts.BiomeIntercept;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("oreconfig")
public class OreConfig {

    public OreConfig() {
        Config.setInsertionOrderPreserved(true);
        MinecraftForge.EVENT_BUS.register(ConfigSpec.class);
        MinecraftForge.EVENT_BUS.register(BiomeIntercept.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSpec.COMMON_SPEC);
    }
}

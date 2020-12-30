package com.noahc3.oretweaker;

import com.electronwill.nightconfig.core.Config;
import com.noahc3.oretweaker.config.ConfigSpec;
import com.noahc3.oretweaker.intercepts.BiomeIntercept;
import com.noahc3.oretweaker.utility.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("oretweaker")
public class OreTweaker {

    public OreTweaker() {
        Config.setInsertionOrderPreserved(true);
        MinecraftForge.EVENT_BUS.register(ConfigSpec.class);
        MinecraftForge.EVENT_BUS.register(BiomeIntercept.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSpec.COMMON_SPEC);
    }
}
